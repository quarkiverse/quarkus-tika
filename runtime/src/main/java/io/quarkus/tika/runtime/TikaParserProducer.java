package io.quarkus.tika.runtime;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;

import io.quarkus.tika.TikaParser;

@ApplicationScoped
public class TikaParserProducer {

    private volatile TikaParser parser;

    void initialize(TikaParser parser) {
        this.parser = parser;
    }

    @Singleton
    @Produces
    public TikaParser tikaParser() {
        return parser;
    }
}
