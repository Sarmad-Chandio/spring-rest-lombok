package com.systems.springframework.spring6restmvc.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaLogProducer {

    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendLogs(String topic, String message) {
        kafkaTemplate.send(topic, message);
    }



}
