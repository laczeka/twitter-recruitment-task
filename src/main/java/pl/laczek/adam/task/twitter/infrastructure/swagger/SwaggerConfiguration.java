package pl.laczek.adam.task.twitter.infrastructure.swagger;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI springOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Twitter-like Recruitment Task app REST API")
                        .description("API documentation for the Twitter-like app for one of Krakow IT company")
                        .version("0.0.1-SNAPSHOT")
                        .termsOfService("gdpr")
                        .contact(author())
                        .license(licence()))
                .externalDocs(new ExternalDocumentation().description("Real Twitter Documentation")
                        .url("https://developer.twitter.com/en/docs/api-reference-index#twitter-api-v2"));
    }

    private static License licence() {
        return new License().name("CC-BY").url("https://creativecommons.org/licenses/by/3.0/pl/deed.pl");
    }

    private static Contact author() {
        return new Contact().name("Adam Łaczek").email("adam.laczek <malpa> gmail com");
    }


}
