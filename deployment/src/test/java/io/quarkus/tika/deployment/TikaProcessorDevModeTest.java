package io.quarkus.tika.deployment;

import static org.hamcrest.CoreMatchers.is;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import io.quarkus.test.QuarkusDevModeTest;
import io.restassured.RestAssured;

public class TikaProcessorDevModeTest {

    private static Class<?>[] testClasses = {
            TikaParserResource.class
    };

    @RegisterExtension
    static final QuarkusDevModeTest test = new QuarkusDevModeTest()
            .withApplicationRoot((jar) -> jar
                    .addClasses(testClasses)
                    .addAsResource("tika-config.xml")
                    .addAsResource("application-dev-mode.properties", "application.properties"));

    @Test
    public void testPdf() throws Exception {
        RestAssured.given()
                .when().header("Content-Type", "application/pdf")
                .body(getClass().getClassLoader().getResourceAsStream("quarkus.pdf"))
                .post("/parse/text")
                .then()
                .statusCode(200)
                .body(is("Hello Quarkus"));
    }
}
