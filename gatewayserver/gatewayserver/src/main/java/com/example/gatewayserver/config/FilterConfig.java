package com.example.gatewayserver.config;

import org.springframework.core.env.Environment;

public class FilterConfig {
    Environment env;

    public FilterConfig(Environment env) {
        this.env = env;
    }
}

