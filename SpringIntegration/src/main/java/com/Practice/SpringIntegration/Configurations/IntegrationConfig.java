package com.Practice.SpringIntegration.Configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.*;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.FileWritingMessageHandler;

import java.io.File;

@Configuration
public class IntegrationConfig {

    @Autowired
    private Transformer transformer;
    @Bean
    public IntegrationFlow integrationFlow()
    {
        return IntegrationFlow.from(fileReader(),
                 spec -> spec.poller(Pollers.fixedDelay(500)))
                .transform(transformer, "transform")
                .handle(fileWriter())
                .get();
    }

    @Bean
    public FileWritingMessageHandler fileWriter() {

        FileWritingMessageHandler handler=new FileWritingMessageHandler(
        new File("destination")
        );
        handler.setExpectReply(false);
        return handler;

    }

    @Bean
    public FileReadingMessageSource fileReader() {

        FileReadingMessageSource source= new FileReadingMessageSource();
        source.setDirectory(new File("source"));
        return source;
    }

}
