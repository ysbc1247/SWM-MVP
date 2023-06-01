package com.swm.mvp.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
@Configuration
@EnableConfigurationProperties(ThymeleafConfig.Thymeleaf3Properties.class) // Enable Configuration Properties for Thymeleaf3Properties
public class ThymeleafConfig {

    @Autowired
    private Thymeleaf3Properties thymeleaf3Properties; // Autowire the properties

    @Bean
    public SpringResourceTemplateResolver thymeleafTemplateResolver(
            SpringResourceTemplateResolver defaultTemplateResolver
    ) {
        defaultTemplateResolver.setUseDecoupledLogic(thymeleaf3Properties.decoupledLogic());
        return defaultTemplateResolver;
    }

    @ConfigurationProperties(prefix = "spring.thymeleaf3")
    public record Thymeleaf3Properties(boolean decoupledLogic) {}

}
