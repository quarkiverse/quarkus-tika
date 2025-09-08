package io.quarkus.it.tika;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class TikaDetectTest {

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
                .post("/detect/text")
                .then()
                .statusCode(200)
                .body(startsWith("application/pdf"));
    }

    private void checkText(String contentType, String extension) throws Exception {
        given()
                .when().header("Content-Type", contentType)
                .body(getClass().getClassLoader().getResourceAsStream("quarkus." + extension).readAllBytes())
                .post("/detect/text")
                .then()
                .statusCode(200)
                .body(is(contentType));
    }
}