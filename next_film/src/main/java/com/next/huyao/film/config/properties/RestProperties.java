package com.next.huyao.film.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = RestProperties.REST_PREFIX)
public class RestProperties {
    public static final String REST_PREFIX = "rest";

    private boolean authOpen;
}
