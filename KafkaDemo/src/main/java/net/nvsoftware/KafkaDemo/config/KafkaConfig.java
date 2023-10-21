package net.nvsoftware.KafkaDemo.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {
    @Bean
    public NewTopic topicAmazon() {
        return TopicBuilder.name("amazon").build();
    }

    @Bean
    public NewTopic topicGoogle() {
        return TopicBuilder.name("google").build();
    }
}
