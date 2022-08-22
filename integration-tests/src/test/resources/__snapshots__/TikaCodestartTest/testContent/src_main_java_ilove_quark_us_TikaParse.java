package ilove.quark.us;

import java.io.FileInputStream;

import javax.inject.Inject;

import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import io.quarkus.tika.TikaParser;

// Enable with config: quarkus.package.main-class=tika (build-time)
@QuarkusMain(name = "tika")
public class TikaParse implements QuarkusApplication {

    @Inject
    TikaParser parser;

    @Override public int run(String... args) throws Exception {
        System.out.println(parser.getText(new FileInputStream(args[0])));
        return 0;
    }
}