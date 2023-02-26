package org.odb.it.apps.rabbitmq;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class OdbItAppsRabbitmqApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(OdbItAppsRabbitmqApplication.class)
                .run(args);
    }
}
