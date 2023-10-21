package net.nvsoftware.service;

import lombok.extern.log4j.Log4j2;
import net.nvsoftware.entity.WikiEntity;
import net.nvsoftware.repository.WikiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class KafkaConsumerService {
    @Autowired
    private WikiRepository wikiRepository;

    @KafkaListener(topics = "wiki", groupId = "wikiGroup")
    public void consume(String msg) {
        log.info("Msg Received: " + msg);
        WikiEntity wikiEntity = WikiEntity.builder()
                .wikiMsg(msg)
                .build();
        wikiRepository.save(wikiEntity);
    }
}
