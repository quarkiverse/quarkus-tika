package io.quarkus.it.tika;

import java.io.IOException;
import java.io.InputStream;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.apache.tika.Tika;

@Path("/detect")
public class TikaDetectResource {
    // Avoiding the injection, otherwise the recorded tika-config.xml intended for TikaPdfInvoiceTest is used
    Tika tika = new Tika();

    @POST
    @Path("/text")
    @Consumes({ "text/plain", "application/pdf", "application/vnd.oasis.opendocument.text" })
    @Produces(MediaType.TEXT_PLAIN)
    public String extractText(InputStream stream) throws IOException {
        return tika.detect(stream);
    }

}