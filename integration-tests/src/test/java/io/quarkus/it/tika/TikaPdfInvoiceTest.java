package io.quarkus.it.tika;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.startsWith;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class TikaPdfInvoiceTest {

    @Test
    public void testGetPlainTextFromInvoice() throws Exception {
        given()
                .when().header("Content-Type", "application/pdf")
                .body(getClass().getClassLoader().getResourceAsStream("invoice.pdf"))
                .post("/invoice/text")
                .then()
                .statusCode(200)
                .body(allOf(startsWith("PDF Invoice Example"),
                        containsString("DEMO - Sliced Invoices Order Number 12345"),
                        new OrderCheckingMatcher()));
    }

    private static class OrderCheckingMatcher extends BaseMatcher<String> {
        int from;
        int to;

        @Override
        public void describeTo(Description description) {
        }

        @Override
        public boolean matches(Object item) {
            String text = (String) item;
            from = text.indexOf("From:");
            to = text.indexOf("To:");
            return from > 0 && to > 0 && from < to;
        }

        @Override
        public void describeMismatch(Object item, Description mismatchDescription) {
            mismatchDescription.appendText("The invoice does not have From preceeding To");
        }
    }
}
