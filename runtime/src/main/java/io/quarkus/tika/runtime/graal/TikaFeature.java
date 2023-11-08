package io.quarkus.tika.runtime.graal;

import org.graalvm.nativeimage.hosted.Feature;
import org.graalvm.nativeimage.hosted.RuntimeClassInitialization;

public class TikaFeature implements Feature {
    @Override
    public void afterRegistration(AfterRegistrationAccess access) {
        final String reason = "Quarkus run time init for Apache Tika";
        RuntimeClassInitialization.initializeAtRunTime("org.apache.pdfbox.pdmodel", reason);
        RuntimeClassInitialization.initializeAtRunTime("org.apache.pdfbox.rendering", reason);
        RuntimeClassInitialization.initializeAtBuildTime(org.apache.pdfbox.rendering.ImageType.class);
    }

    @Override
    public String getDescription() {
        return "Quarkus runtime initialization for Apache Tika";
    }
}
