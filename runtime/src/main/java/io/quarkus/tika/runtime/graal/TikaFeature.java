package io.quarkus.tika.runtime.graal;

import org.graalvm.nativeimage.hosted.Feature;
import org.graalvm.nativeimage.hosted.RuntimeClassInitialization;

public class TikaFeature implements Feature {
    @Override
    public void afterRegistration(AfterRegistrationAccess access) {
        RuntimeClassInitialization.initializeAtRunTime(
                "org.apache.pdfbox.pdmodel",
                "org.apache.pdfbox.rendering.GlyphCache",
                "org.apache.pdfbox.rendering.GroupGraphics",
                "org.apache.pdfbox.rendering.PDFRenderer",
                "org.apache.pdfbox.rendering.PageDrawer",
                "org.apache.pdfbox.rendering.PageDrawerParameters",
                "org.apache.pdfbox.rendering.RenderDestination",
                "org.apache.pdfbox.rendering.SoftMask",
                "org.apache.pdfbox.rendering.TilingPaint",
                "org.apache.pdfbox.rendering.TilingPaintFactory");
    }

    @Override
    public String getDescription() {
        return "Quarkus runtime initialization for Apache Tika";
    }
}
