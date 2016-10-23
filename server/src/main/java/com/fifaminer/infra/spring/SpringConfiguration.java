package com.fifaminer.infra.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by Alexandr on 23.10.2016.
 */
public class SpringConfiguration {

    private static final String PACKAGE_TO_SCAN = "com.fifaminer";

    public void configure() {
        new AnnotationConfigApplicationContext(PACKAGE_TO_SCAN);
    }
}
