package org.odb.it.apps.asb;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class OdbItAppsAsbApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(OdbItAppsAsbApplication.class)
                .run(args);
    }
}
