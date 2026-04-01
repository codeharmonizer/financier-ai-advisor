package com.neo;

import com.neo.config.SwaggerConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;

@SpringBootApplication
@EnableFeignClients
@Import(value = {SwaggerConfig.class, BeanValidatorPluginsConfiguration.class})
public class Application {

    private static final String SPRING_PROPERTY_CONFIG = "spring.config.name=application,httpConnection";

    public static void main(String[] args) {
        new SpringApplicationBuilder().sources(Application.class).properties(SPRING_PROPERTY_CONFIG).run(args);
    }
}