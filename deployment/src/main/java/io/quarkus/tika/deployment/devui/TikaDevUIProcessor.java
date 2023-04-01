package io.quarkus.tika.deployment.devui;

import org.apache.tika.detect.Detector;

import io.quarkus.deployment.IsDevelopment;
import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.devui.spi.page.CardPageBuildItem;
import io.quarkus.devui.spi.page.Page;
import io.quarkus.devui.spi.page.PageBuilder;

/**
 * Dev UI card for displaying important details such as the Tika library version.
 */
public class TikaDevUIProcessor {

    @BuildStep(onlyIf = IsDevelopment.class)
    void createVersion(BuildProducer<CardPageBuildItem> cardPageBuildItemBuildProducer) {
        final CardPageBuildItem card = new CardPageBuildItem();

        final PageBuilder versionPage = Page.externalPageBuilder("Tika Version")
                .icon("font-awesome-solid:tag")
                .url("https://tika.apache.org/")
                .doNotEmbed()
                .staticLabel(Detector.class.getPackage().getImplementationVersion());

        card.addPage(versionPage);

        card.setCustomCard("qwc-tika-card.js");

        cardPageBuildItemBuildProducer.produce(card);
    }
}