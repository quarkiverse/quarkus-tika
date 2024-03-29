package io.quarkus.it.tika;

import java.io.InputStream;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.RecursiveParserWrapper;
import org.apache.tika.parser.microsoft.OfficeParser;
import org.apache.tika.parser.pdf.PDFParser;

import io.quarkus.tika.TikaContent;
import io.quarkus.tika.TikaParser;

@Path("/embedded")
public class TikaEmdeddedContentResource {

    // Avoiding the injection, otherwise the recorded tika-config.xml intended for TikaPdfInvoiceTest is used
    TikaParser parser = new TikaParser(new RecursiveParserWrapper(
            new AutoDetectParser(new OfficeParser(), new PDFParser()), true), false);

    @POST
    @Path("/outerText")
    @Consumes("application/vnd.ms-excel")
    @Produces(MediaType.TEXT_PLAIN)
    public String extractOuterText(InputStream stream) {
        TikaContent content = parser.parse(stream);
        return content.getText();
    }

    @POST
    @Path("/innerText")
    @Consumes("application/vnd.ms-excel")
    @Produces(MediaType.TEXT_PLAIN)
    public String extractInnerText(InputStream stream) {
        TikaContent content = parser.parse(stream);
        return content.getEmbeddedContent().get(0).getText();
    }
}
