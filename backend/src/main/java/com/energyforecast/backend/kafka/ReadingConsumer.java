package com.energyforecast.backend.kafka;

import com.energyforecast.backend.domain.EnergyReading;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ReadingConsumer {

    @KafkaListener(topics = "sensor-readings", groupId = "energyforecast-group-v2")
    public void consume(EnergyReading reading) {
        System.out.println("Received from Kafka: " + reading.getSource() + " value=" + reading.getValue());
    }
}
