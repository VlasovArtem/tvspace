package com.vlasovartem.tvspace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.Collections;

/**
 * Created by artemvlasov on 20/11/15.
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication();
        application.setAdditionalProfiles("development");
        application.setSources(Collections.singleton(Application.class));
        application.run(args);
//        application.run(Application.class, args);
    }
}
