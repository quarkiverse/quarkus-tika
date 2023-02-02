package io.quarkus.tika.runtime.graal;

import org.graalvm.nativeimage.ImageSingletons;
import org.graalvm.nativeimage.hosted.Feature;
import org.graalvm.nativeimage.impl.RuntimeClassInitializationSupport;

public class TikaFeature implements Feature {
    @Override
    public void afterRegistration(AfterRegistrationAccess access) {
        final RuntimeClassInitializationSupport runtimeInit = ImageSingletons.lookup(RuntimeClassInitializationSupport.class);
        final String reason = "Quarkus run time init for Apache Tika";
        runtimeInit.initializeAtRunTime("org.apache.pdfbox.pdmodel", reason);
        runtimeInit.initializeAtRunTime("org.apache.pdfbox.rendering", reason);
        runtimeInit.initializeAtRunTime("org.apache.poi.hssf.util", reason);
        runtimeInit.initializeAtRunTime("org.apache.poi.ss.format", reason);
    }

    @Override
    public String getDescription() {
        return "Quarkus runtime initialization for Apache Tika";
    }
}
