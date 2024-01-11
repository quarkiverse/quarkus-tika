package io.quarkus.tika.deployment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import io.quarkus.test.QuarkusUnitTest;

public class TikaProcessorTest {

    @RegisterExtension
    static final QuarkusUnitTest quarkusUnitTest = new QuarkusUnitTest();

    @Test
    public void testPDFParserName() throws Exception {
        Set<String> names = getParserNames(null, "pdf");
        assertEquals(1, names.size());
        assertTrue(names.contains("org.apache.tika.parser.pdf.PDFParser"));
    }

    @Test
    public void testODFParserName() throws Exception {
        Set<String> names = getParserNames(null, "odf");
        assertEquals(1, names.size());
        assertTrue(names.contains("org.apache.tika.parser.odf.OpenDocumentParser"));
    }

    @Test
    public void testSupportedParserNames() throws Exception {
        Set<String> names = getParserNames(null, "pdf,odf");
        assertEquals(2, names.size());
        assertTrue(names.contains("org.apache.tika.parser.pdf.PDFParser"));
        assertTrue(names.contains("org.apache.tika.parser.odf.OpenDocumentParser"));
    }

    @Test
    public void testResolvableCustomAbbreviation() throws Exception {
        Set<String> names = getParserConfig(null, "pdf,opendoc", Collections.emptyMap(),
                Collections.singletonMap("opendoc",
                        "org.apache.tika.parser.odf.OpenDocumentParser"))
                .keySet();
        assertEquals(2, names.size());
        assertTrue(names.contains("org.apache.tika.parser.pdf.PDFParser"));
        assertTrue(names.contains("org.apache.tika.parser.odf.OpenDocumentParser"));
    }

    @Test
    public void testPdfParserConfig() throws Exception {
        Map<String, List<TikaProcessor.TikaParserParameter>> parserConfig = getParserConfig(null, "pdf",
                Collections.singletonMap("pdf",
                        Collections.singletonMap("sort-by-position", "true")),
                Collections.emptyMap());
        assertEquals(1, parserConfig.size());

        String pdfParserFullName = "org.apache.tika.parser.pdf.PDFParser";
        assertEquals(1, parserConfig.get(pdfParserFullName).size());
        assertEquals("sortByPosition", parserConfig.get(pdfParserFullName).get(0).getName());
        assertEquals("true", parserConfig.get(pdfParserFullName).get(0).getValue());
    }

    @Test
    public void testTesseractParserConfig() throws Exception {
        String ocrParserFullName = "org.apache.tika.parser.ocr.TesseractOCRParser";
        Map<String, List<TikaProcessor.TikaParserParameter>> parserConfig = getParserConfig(null, "ocr",
                Collections.singletonMap("ocr",
                        Collections.singletonMap("tesseract-path", "/opt/tesseract/")),
                Collections.singletonMap("ocr", ocrParserFullName));
        assertEquals(1, parserConfig.size());

        assertEquals(1, parserConfig.get(ocrParserFullName).size());
        assertEquals("tesseractPath", parserConfig.get(ocrParserFullName).get(0).getName());
        assertEquals("/opt/tesseract/", parserConfig.get(ocrParserFullName).get(0).getValue());
    }

    @Test
    public void testUnknownParserConfig() throws Exception {
        String ocrParserFullName = "org.apache.tika.parser.ocr.TesseractOCRParser";
        try {
            Map<String, List<TikaProcessor.TikaParserParameter>> parserConfig = getParserConfig(null, "ocr",
                    Collections.singletonMap("ocr",
                            Collections.singletonMap("tesseract-unknown-opt", "/opt/tesseract/")),
                    Collections.singletonMap("ocr", ocrParserFullName));
        } catch (Exception e) {
            // expected
            assertEquals("Parser org.apache.tika.parser.ocr.TesseractOCRParser has no tesseractUnknownOpt property",
                    e.getMessage());
        }
    }

    @Test
    public void testUnresolvableCustomAbbreviation() throws Exception {
        try {
            getParserNames(null, "classparser");
            fail("'classparser' is not resolvable");
        } catch (IllegalStateException ex) {
            // expected
        }
    }

    @Test
    public void testAllSupportedParserNames() throws Exception {
        String[] expected = { "org.apache.tika.parser.microsoft.xml.WordMLParser",
                "org.apache.tika.parser.microsoft.rtf.RTFParser",
                "org.apache.tika.parser.audio.MidiParser",
                "org.apache.tika.parser.external.CompositeExternalParser",
                "org.apache.tika.parser.warc.WARCParser", "org.apache.tika.parser.wacz.WACZParser",
                "org.apache.tika.parser.microsoft.ooxml.OOXMLParser", "org.apache.tika.parser.csv.TextAndCSVParser",
                "org.apache.tika.parser.apple.PListParser", "org.apache.tika.parser.asm.ClassParser",
                "org.apache.tika.parser.font.AdobeFontMetricParser", "org.apache.tika.parser.xliff.XLIFF12Parser",
                "org.apache.tika.parser.html.JSoupParser", "org.apache.tika.parser.font.TrueTypeParser",
                "org.apache.tika.parser.microsoft.WMFParser", "org.apache.tika.parser.http.HttpParser",
                "org.apache.tika.parser.xliff.XLZParser", "org.apache.tika.parser.image.HeifParser",
                "org.apache.tika.parser.image.TiffParser", "org.apache.tika.parser.dif.DIFParser",
                "org.apache.tika.parser.microsoft.OfficeParser", "org.apache.tika.parser.hwp.HwpV5Parser",
                "org.apache.tika.parser.feed.FeedParser", "org.apache.tika.parser.microsoft.OldExcelParser",
                "org.apache.tika.parser.pkg.PackageParser", "org.apache.tika.parser.mp4.MP4Parser",
                "org.apache.tika.parser.dwg.DWGParser", "org.apache.tika.parser.mif.MIFParser",
                "org.apache.tika.parser.iptc.IptcAnpaParser", "org.apache.tika.parser.microsoft.EMFParser",
                "org.apache.tika.parser.wordperfect.WordPerfectParser", "org.apache.tika.parser.sas.SAS7BDATParser",
                "org.apache.tika.parser.microsoft.JackcessParser", "org.apache.tika.parser.mbox.MboxParser",
                "org.apache.tika.parser.image.WebPParser", "org.apache.tika.parser.image.ICNSParser",
                "org.apache.tika.parser.odf.FlatOpenDocumentParser", "org.apache.tika.parser.crypto.Pkcs7Parser",
                "org.apache.tika.parser.microsoft.MSOwnerFileParser", "org.apache.tika.parser.xml.DcXMLParser",
                "org.apache.tika.parser.image.JXLParser", "org.apache.tika.parser.code.SourceCodeParser",
                "org.apache.tika.parser.apple.AppleSingleFileParser", "org.apache.tika.parser.indesign.IDMLParser",
                "org.apache.tika.parser.microsoft.activemime.ActiveMimeParser",
                "org.apache.tika.parser.microsoft.TNEFParser", "org.apache.tika.parser.dgn.DGN8Parser",
                "org.apache.tika.parser.microsoft.ooxml.xwpf.ml2006.Word2006MLParser",
                "org.apache.tika.parser.image.BPGParser", "org.apache.tika.parser.dbf.DBFParser",
                "org.apache.tika.parser.microsoft.pst.OutlookPSTParser",
                "org.apache.tika.parser.iwork.iwana.IWork18PackageParser", "org.apache.tika.parser.video.FLVParser",
                "org.apache.tika.parser.prt.PRTParser", "org.apache.tika.parser.pkg.RarParser",
                "org.apache.tika.parser.tmx.TMXParser", "org.apache.tika.parser.iwork.IWorkPackageParser",
                "org.apache.tika.parser.microsoft.onenote.OneNoteParser",
                "org.apache.tika.parser.iwork.iwana.IWork13PackageParser",
                "org.apache.tika.parser.microsoft.xml.SpreadsheetMLParser",
                "org.apache.tika.parser.microsoft.chm.ChmParser",
                "org.apache.tika.parser.executable.ExecutableParser", "org.apache.tika.parser.image.JpegParser",
                "org.apache.tika.parser.image.PSDParser", "org.apache.tika.parser.xml.FictionBookParser",
                "org.apache.tika.parser.audio.AudioParser", "org.apache.tika.parser.epub.EpubParser",
                "org.apache.tika.parser.image.ImageParser", "org.apache.tika.parser.pdf.PDFParser",
                "org.apache.tika.parser.odf.OpenDocumentParser", "org.apache.tika.parser.mp3.Mp3Parser",
                "org.apache.tika.parser.wordperfect.QuattroProParser", "org.apache.tika.parser.crypto.TSDParser",
                "org.apache.tika.parser.ocr.TesseractOCRParser", "org.apache.tika.parser.microsoft.pst.PSTMailItemParser",
                "org.apache.tika.parser.executable.UniversalExecutableParser" };
        assertThat(getParserNames(null, null)).containsExactlyInAnyOrder(expected);
    }

    @Test
    public void testSupportedParserNamesWithTikaConfigPath() throws Exception {
        String[] expected = { "org.apache.tika.parser.microsoft.xml.WordMLParser",
                "org.apache.tika.parser.microsoft.rtf.RTFParser",
                "org.apache.tika.parser.audio.MidiParser",
                "org.apache.tika.parser.external.CompositeExternalParser",
                "org.apache.tika.parser.warc.WARCParser", "org.apache.tika.parser.wacz.WACZParser",
                "org.apache.tika.parser.microsoft.ooxml.OOXMLParser", "org.apache.tika.parser.csv.TextAndCSVParser",
                "org.apache.tika.parser.apple.PListParser", "org.apache.tika.parser.asm.ClassParser",
                "org.apache.tika.parser.font.AdobeFontMetricParser", "org.apache.tika.parser.xliff.XLIFF12Parser",
                "org.apache.tika.parser.html.JSoupParser", "org.apache.tika.parser.font.TrueTypeParser",
                "org.apache.tika.parser.microsoft.WMFParser", "org.apache.tika.parser.http.HttpParser",
                "org.apache.tika.parser.xliff.XLZParser", "org.apache.tika.parser.image.HeifParser",
                "org.apache.tika.parser.image.TiffParser", "org.apache.tika.parser.dif.DIFParser",
                "org.apache.tika.parser.microsoft.OfficeParser", "org.apache.tika.parser.hwp.HwpV5Parser",
                "org.apache.tika.parser.feed.FeedParser", "org.apache.tika.parser.microsoft.OldExcelParser",
                "org.apache.tika.parser.pkg.PackageParser", "org.apache.tika.parser.mp4.MP4Parser",
                "org.apache.tika.parser.dwg.DWGParser", "org.apache.tika.parser.mif.MIFParser",
                "org.apache.tika.parser.iptc.IptcAnpaParser", "org.apache.tika.parser.microsoft.EMFParser",
                "org.apache.tika.parser.wordperfect.WordPerfectParser", "org.apache.tika.parser.sas.SAS7BDATParser",
                "org.apache.tika.parser.microsoft.JackcessParser", "org.apache.tika.parser.mbox.MboxParser",
                "org.apache.tika.parser.image.WebPParser", "org.apache.tika.parser.image.ICNSParser",
                "org.apache.tika.parser.odf.FlatOpenDocumentParser", "org.apache.tika.parser.crypto.Pkcs7Parser",
                "org.apache.tika.parser.microsoft.MSOwnerFileParser", "org.apache.tika.parser.xml.DcXMLParser",
                "org.apache.tika.parser.image.JXLParser", "org.apache.tika.parser.code.SourceCodeParser",
                "org.apache.tika.parser.apple.AppleSingleFileParser", "org.apache.tika.parser.indesign.IDMLParser",
                "org.apache.tika.parser.microsoft.activemime.ActiveMimeParser",
                "org.apache.tika.parser.microsoft.TNEFParser", "org.apache.tika.parser.dgn.DGN8Parser",
                "org.apache.tika.parser.microsoft.ooxml.xwpf.ml2006.Word2006MLParser",
                "org.apache.tika.parser.image.BPGParser", "org.apache.tika.parser.dbf.DBFParser",
                "org.apache.tika.parser.microsoft.pst.OutlookPSTParser",
                "org.apache.tika.parser.iwork.iwana.IWork18PackageParser", "org.apache.tika.parser.video.FLVParser",
                "org.apache.tika.parser.prt.PRTParser", "org.apache.tika.parser.pkg.RarParser",
                "org.apache.tika.parser.tmx.TMXParser", "org.apache.tika.parser.iwork.IWorkPackageParser",
                "org.apache.tika.parser.microsoft.onenote.OneNoteParser",
                "org.apache.tika.parser.iwork.iwana.IWork13PackageParser",
                "org.apache.tika.parser.microsoft.xml.SpreadsheetMLParser",
                "org.apache.tika.parser.microsoft.chm.ChmParser",
                "org.apache.tika.parser.executable.ExecutableParser", "org.apache.tika.parser.image.JpegParser",
                "org.apache.tika.parser.image.PSDParser", "org.apache.tika.parser.xml.FictionBookParser",
                "org.apache.tika.parser.audio.AudioParser", "org.apache.tika.parser.epub.EpubParser",
                "org.apache.tika.parser.image.ImageParser", "org.apache.tika.parser.pdf.PDFParser",
                "org.apache.tika.parser.odf.OpenDocumentParser", "org.apache.tika.parser.mp3.Mp3Parser",
                "org.apache.tika.parser.wordperfect.QuattroProParser", "org.apache.tika.parser.crypto.TSDParser",
                "org.apache.tika.parser.ocr.TesseractOCRParser", "org.apache.tika.parser.microsoft.pst.PSTMailItemParser",
                "org.apache.tika.parser.executable.UniversalExecutableParser" };
        assertThat(getParserNames("tika-config.xml", "pdf")).containsExactlyInAnyOrder(expected);
    }

    @Test
    public void testUnhyphenation() {
        assertEquals("sortByPosition", TikaProcessor.camelCase("sort-by-position"));
        assertEquals("position", TikaProcessor.camelCase("position"));
    }

    private Set<String> getParserNames(String tikaConfigPath, String parsers) throws Exception {
        return TikaProcessor.getSupportedParserConfig(
                Optional.ofNullable(tikaConfigPath), Optional.ofNullable(parsers),
                Collections.emptyMap(), Collections.emptyMap()).keySet();
    }

    private Map<String, List<TikaProcessor.TikaParserParameter>> getParserConfig(String tikaConfigPath, String parsers,
            Map<String, Map<String, String>> parserParamMaps,
            Map<String, String> parserAbbreviations) throws Exception {
        return TikaProcessor.getSupportedParserConfig(
                Optional.ofNullable(tikaConfigPath), Optional.ofNullable(parsers),
                parserParamMaps, parserAbbreviations);
    }
}
