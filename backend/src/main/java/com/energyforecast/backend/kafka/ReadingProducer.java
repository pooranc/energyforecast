package com.energyforecast.backend.kafka;

import com.energyforecast.backend.domain.EnergyReading;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ReadingProducer {

    private static final String TOPIC = "sensor-readings";
    private final KafkaTemplate<String, EnergyReading> kafkaTemplate;

    public ReadingProducer(KafkaTemplate<String, EnergyReading> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(EnergyReading reading){
        kafkaTemplate.send(TOPIC, reading.getSource(),reading);
        System.out.println("Published reading to kafka "+ reading.getSource());
    }
}
