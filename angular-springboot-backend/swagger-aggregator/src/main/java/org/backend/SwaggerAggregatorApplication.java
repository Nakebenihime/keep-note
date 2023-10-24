package org.backend;

import org.backend.configuration.SwaggerAggregatorConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableConfigurationProperties({SwaggerAggregatorConfiguration.class, SwaggerAggregatorConfiguration.SwaggerServices.class})
public class SwaggerAggregatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(SwaggerAggregatorApplication.class, args);
    }
}