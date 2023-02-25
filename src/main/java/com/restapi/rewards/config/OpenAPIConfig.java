package com.restapi.rewards.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI subscriptionAPI() {
        return new OpenAPI().info(new Info()
                        .title("Customers and Transaction Points Reward API")
                        .description("API group to manage Customers and there rewards")
                        .contact(new Contact().email("f.shahzad@hotmail.com").url("https://github.com")))
                .addServersItem(new Server().url("http://localhost:8090").description("LOCAL Server"));
    }

}
