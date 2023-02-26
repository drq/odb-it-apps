package org.odb.it.apps.zmq;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class OdbItAppsZmqApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(OdbItAppsZmqApplication.class)
                .run(args);
    }
}
