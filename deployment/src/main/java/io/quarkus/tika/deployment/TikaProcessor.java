package io.quarkus.tika.deployment;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.tika.detect.Detector;
import org.apache.tika.detect.EncodingDetector;
import org.apache.tika.parser.Parser;

import io.quarkus.arc.deployment.AdditionalBeanBuildItem;
import io.quarkus.arc.deployment.BeanContainerBuildItem;
import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.annotations.ExecutionTime;
import io.quarkus.deployment.annotations.Record;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.deployment.builditem.NativeImageEnableAllCharsetsBuildItem;
import io.quarkus.deployment.builditem.nativeimage.NativeImageResourceBuildItem;
import io.quarkus.deployment.builditem.nativeimage.ServiceProviderBuildItem;
import io.quarkus.deployment.util.ServiceUtil;
import io.quarkus.tika.TikaParseException;
import io.quarkus.tika.runtime.TikaConfiguration;
import io.quarkus.tika.runtime.TikaParserProducer;
import io.quarkus.tika.runtime.TikaRecorder;

public class TikaProcessor {

    private static final String FEATURE = "tika";

    private static final Set<String> NOT_NATIVE_READY_PARSERS = Set.of(
            "org.apache.tika.parser.mat.MatParser",
            "org.apache.tika.parser.journal.GrobidRESTParser",
            "org.apache.tika.parser.journal.JournalParser",
            "org.apache.tika.parser.jdbc.SQLite3Parser",
            "org.apache.tika.parser.mail.RFC822Parser",
            "org.apache.tika.parser.pkg.CompressorParser",
            "org.apache.tika.parser.geo.topic.GeoParser");

    private static final Map<String, String> PARSER_ABBREVIATIONS = Map.of(
            "pdf", "org.apache.tika.parser.pdf.PDFParser",
            "odf", "org.apache.tika.parser.odf.OpenDocumentParser");

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }

    @BuildStep
    AdditionalBeanBuildItem beans() {
        return AdditionalBeanBuildItem.unremovableOf(TikaParserProducer.class);
    }

    /**
     * Prevents the java.io.UnsupportedEncodingException: windows-1252 error in native mode
     */
    @BuildStep
    NativeImageEnableAllCharsetsBuildItem enableAllCharsets() {
        return new NativeImageEnableAllCharsetsBuildItem();
    }

    @BuildStep
    public void registerTikaCoreResources(BuildProducer<NativeImageResourceBuildItem> resource) {
        resource.produce(new NativeImageResourceBuildItem("org/apache/tika/mime/tika-mimetypes.xml"));
        resource.produce(new NativeImageResourceBuildItem("org/apache/tika/parser/external/tika-external-parsers.xml"));
    }

    @BuildStep
    public void registerTikaParsersResources(BuildProducer<NativeImageResourceBuildItem> resource) {
        resource.produce(new NativeImageResourceBuildItem("org/apache/tika/parser/pdf/PDFParser.properties"));
    }

    @BuildStep
    @Record(ExecutionTime.STATIC_INIT)
    void initializeTikaParser(BeanContainerBuildItem beanContainer, TikaRecorder recorder,
            BuildProducer<ServiceProviderBuildItem> serviceProvider, TikaConfiguration configuration)
            throws Exception {
        Map<String, List<TikaParserParameter>> parsers = getSupportedParserConfig(configuration.tikaConfigPath(),
                configuration.parsers(),
                configuration.parserOptions(), configuration.parser());
        String tikaXmlConfiguration = generateTikaXmlConfiguration(parsers);
        serviceProvider.produce(new ServiceProviderBuildItem(Parser.class.getName(), parsers.keySet()));
        serviceProvider
                .produce(new ServiceProviderBuildItem(Detector.class.getName(), getProviderNames(Detector.class.getName())));
        serviceProvider.produce(new ServiceProviderBuildItem(EncodingDetector.class.getName(),
                getProviderNames(EncodingDetector.class.getName())));

        recorder.initTikaParser(beanContainer.getValue(), configuration, tikaXmlConfiguration);
    }

    private static Set<String> getProviderNames(String serviceProviderName) throws Exception {
        return ServiceUtil.classNamesNamedIn(TikaProcessor.class.getClassLoader(),
                "META-INF/services/" + serviceProviderName);
    }

    public static Map<String, List<TikaParserParameter>> getSupportedParserConfig(Optional<String> tikaConfigPath,
            Optional<String> requiredParsers,
            Map<String, Map<String, String>> parserParamMaps,
            Map<String, String> parserAbbreviations) throws Exception {
        Predicate<String> pred = p -> !NOT_NATIVE_READY_PARSERS.contains(p);
        Set<String> providerNames = getProviderNames(Parser.class.getName());
        if (tikaConfigPath.isPresent() || requiredParsers.isEmpty()) {
            return providerNames.stream().filter(pred).collect(Collectors.toMap(Function.identity(),
                    p -> Collections.emptyList()));
        } else {
            List<String> abbreviations = Arrays.stream(requiredParsers.get().split(",")).map(String::trim)
                    .toList();
            Map<String, String> fullNamesAndAbbreviations = abbreviations.stream()
                    .collect(Collectors.toMap(p -> getParserNameFromConfig(p, parserAbbreviations), Function.identity()));
            return providerNames.stream().filter(pred).filter(fullNamesAndAbbreviations::containsKey)
                    .collect(Collectors.toMap(Function.identity(),
                            p -> getParserConfig(p, parserParamMaps.get(fullNamesAndAbbreviations.get(p)))));
        }
    }

    private static String generateTikaXmlConfiguration(Map<String, List<TikaParserParameter>> parserConfig) {
        StringBuilder tikaXmlConfigurationBuilder = new StringBuilder();
        tikaXmlConfigurationBuilder.append("<properties>");
        tikaXmlConfigurationBuilder.append("<parsers>");
        for (Entry<String, List<TikaParserParameter>> parserEntry : parserConfig.entrySet()) {
            tikaXmlConfigurationBuilder.append("<parser class=\"").append(parserEntry.getKey()).append("\">");
            if (!parserEntry.getValue().isEmpty()) {
                appendParserParameters(tikaXmlConfigurationBuilder, parserEntry.getValue());
            }
            tikaXmlConfigurationBuilder.append("</parser>");
        }
        tikaXmlConfigurationBuilder.append("</parsers>");
        tikaXmlConfigurationBuilder.append("</properties>");
        return tikaXmlConfigurationBuilder.toString();
    }

    private static void appendParserParameters(StringBuilder tikaXmlConfigurationBuilder,
            List<TikaParserParameter> parserParams) {
        tikaXmlConfigurationBuilder.append("<params>");
        for (TikaParserParameter parserParam : parserParams) {
            tikaXmlConfigurationBuilder.append("<param name=\"").append(parserParam.getName());
            tikaXmlConfigurationBuilder.append("\" type=\"").append(parserParam.getType()).append("\">");
            tikaXmlConfigurationBuilder.append(parserParam.getValue());
            tikaXmlConfigurationBuilder.append("</param>");
        }
        tikaXmlConfigurationBuilder.append("</params>");
    }

    private static List<TikaParserParameter> getParserConfig(String parserName, Map<String, String> parserParamMap) {
        List<TikaParserParameter> parserParams = new LinkedList<>();
        if (parserParamMap != null) {
            for (Map.Entry<String, String> entry : parserParamMap.entrySet()) {
                String paramName = camelCase(entry.getKey());
                String paramType = getParserParamType(parserName, paramName);
                parserParams.add(new TikaParserParameter(paramName, entry.getValue(), paramType));
            }
        }
        return parserParams;
    }

    private static String getParserNameFromConfig(String abbreviation, Map<String, String> parserAbbreviations) {
        if (PARSER_ABBREVIATIONS.containsKey(abbreviation)) {
            return PARSER_ABBREVIATIONS.get(abbreviation);
        }

        if (parserAbbreviations.containsKey(abbreviation)) {
            return parserAbbreviations.get(abbreviation);
        }

        throw new IllegalStateException("The custom abbreviation `" + abbreviation
                + "` can not be resolved to a parser class name, please set a "
                + "quarkus.tika.parser-name." + abbreviation + " property");
    }

    // Convert a property name such as "sort-by-position" to "sortByPosition"
    public static String camelCase(String paramName) {
        StringBuilder sb = new StringBuilder();
        String[] words = paramName.split("-");
        for (int i = 0; i < words.length; i++) {
            sb.append(i > 0 ? capitalize(words[i]) : words[i]);
        }
        return sb.toString();
    }

    private static String capitalize(String paramName) {
        if (paramName == null || paramName.isEmpty()) {
            return paramName;
        }
        char[] chars = paramName.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]);
        return new String(chars);
    }

    // TODO: Remove the reflection code below once TikaConfig becomes capable
    // of loading the parameters without the type attribute: TIKA-2944

    private static Class<?> loadParserClass(String parserName) {
        try {
            return TikaProcessor.class.getClassLoader().loadClass(parserName);
        } catch (Throwable t) {
            final String errorMessage = "Parser " + parserName + " can not be loaded";
            throw new TikaParseException(errorMessage);
        }
    }

    private static String getParserParamType(String parserName, String paramName) {
        try {
            Class<?> parserClass = loadParserClass(parserName);
            Method[] methods = parserClass.getMethods();
            String setterMethodName = "set" + capitalize(paramName);
            String paramType;
            for (Method method : methods) {
                if (method.getName().equals(setterMethodName) && method.getParameterCount() == 1) {
                    paramType = method.getParameterTypes()[0].getSimpleName().toLowerCase();
                    if (paramType.equals(boolean.class.getSimpleName())) {
                        // TikaConfig Param class does not recognize 'boolean', only 'bool'
                        // This whole reflection code is temporary anyway
                        paramType = "bool";
                    }
                    return paramType;
                }
            }
        } catch (Throwable t) {
            throw new TikaParseException(String.format("Parser %s has no %s property", parserName, paramName));
        }
        throw new TikaParseException(String.format("Parser %s has no %s property", parserName, paramName));
    }

    public static class TikaParserParameter {
        private final String name;
        private final String value;
        private final String type;

        public TikaParserParameter(String name, String value, String type) {
            this.name = name;
            this.value = value;
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public String getType() {
            return type;
        }

        public String getValue() {
            return value;
        }
    }
}