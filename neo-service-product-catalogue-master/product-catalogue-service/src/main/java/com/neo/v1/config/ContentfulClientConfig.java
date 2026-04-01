package com.neo.v1.config;

import com.contentful.java.cda.CDAClient;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.neo.v1.reader.PropertyReader;

@Configuration
@RequiredArgsConstructor
public class ContentfulClientConfig {

    private final PropertyReader propertyReader;

    @Bean
    public CDAClient contentfulCDAClient() {
        return CDAClient.builder()
                .setToken(propertyReader.getContentfulAccessToken())
                .setSpace(propertyReader.getContentfulSpaceId())
                .setEnvironment(propertyReader.getContentfulEnvironmentId())
                .build();
    }

    @Bean("giftCard")
    public CDAClient ilabankspacecontentfulCDAClient() {
        return CDAClient.builder()
                .setToken(propertyReader.getIlabankspacecontentfulAccessToken())
                .setSpace(propertyReader.getIlabankspacecontentfulSpaceId())
                .setEnvironment(propertyReader.getIlabankspacecontentfulEnviron())
                .build();
    }
}
