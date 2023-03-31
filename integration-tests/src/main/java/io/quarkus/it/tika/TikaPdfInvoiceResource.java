package io.quarkus.it.tika;

import java.io.InputStream;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import io.quarkus.tika.TikaParser;

@Path("/invoice")
public class TikaPdfInvoiceResource {

    @Inject
    TikaParser parser;

    @POST
    @Path("/text")
    @Consumes("application/pdf")
    @Produces(MediaType.TEXT_PLAIN)
    public String extractTextAsPlainText(InputStream stream) {
        return parser.getText(stream);
    }
}
