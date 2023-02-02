package io.quarkus.it.tika;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class TikaParserTest {

    @Test
    public void testGetTextFromTextFormat() throws Exception {
        checkText("text/plain", "txt");
    }

    @Test
    public void testGetTextFromOdtFormat() throws Exception {
        checkText("application/vnd.oasis.opendocument.text", "odt");
    }

    @Test
    public void testGetTextFromPdfFormat() throws Exception {
        checkText("application/pdf", "pdf");
    }

    @Test
    public void testGetTextFromPdfFormatWithFonts() throws Exception {
        given()
                .when().header("Content-Type", "application/pdf")
                .body(getClass().getClassLoader().getResourceAsStream("americanexpress.pdf"))
                .post("/parse/text")
                .then()
                .statusCode(200)
                .body(startsWith("American express card"));
    }

    @Test
    public void testGetMetadataFromTextFormat() throws Exception {
        checkMetadata("text/plain", "txt");
    }

    @Test
    public void testGetMetadataFromOdtFormat() throws Exception {
        checkMetadata("application/vnd.oasis.opendocument.text", "odt");
    }

    @Test
    public void testGetMetadataFromPdfFormat() throws Exception {
        checkMetadata("application/pdf", "pdf");
    }

    private void checkText(String contentType, String extension) throws Exception {
        given()
                .when().header("Content-Type", contentType)
                .body(getClass().getClassLoader().getResourceAsStream("quarkus." + extension).readAllBytes())
                .post("/parse/text")
                .then()
                .statusCode(200)
                .body(is("Hello Quarkus"));
    }

    private void checkMetadata(String contentType, String extension) throws Exception {
        given()
                .when().header("Content-Type", contentType)
                .body(getClass().getClassLoader().getResourceAsStream("quarkus." + extension))
                .post("/parse/metadata")
                .then()
                .statusCode(200)
                .body(containsString("X-TIKA:Parsed-By"));

    }
}
