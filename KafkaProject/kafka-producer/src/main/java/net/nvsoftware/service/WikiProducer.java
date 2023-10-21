package net.nvsoftware.service;

import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.EventSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.concurrent.TimeUnit;

@Service
public class WikiProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMsg() throws InterruptedException {
        EventHandler eventHandler = new WikiProducerHelper(kafkaTemplate, "wiki");
        EventSource eventSource = new EventSource.Builder(eventHandler, URI.create("https://stream.wikimedia.org/v2/stream/recentchange"))
                .build();
        eventSource.start();

        TimeUnit.SECONDS.sleep(15);
    }
}
