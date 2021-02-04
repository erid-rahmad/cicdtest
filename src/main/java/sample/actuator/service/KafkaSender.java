package sample.actuator.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;
import sample.actuator.model.Request;

import java.util.HashMap;
import java.util.Map;

@Service
public class KafkaSender {

	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaSender.class);

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@Value("${kafka.topic.name}")
	private String topicName;

	public void sendData(String msg) {
		// TODO Auto-generated method stub
		Map<String, Object> headers = new HashMap<>();
//		headers.put(KafkaHeaders.TOPIC, topicName);
//		kafkaTemplate.send(new GenericMessage<Request>(student, headers));
		kafkaTemplate.send(topicName,msg);
		// use the below to send String values through kafka
		// kafkaTemplate.send(topicName, "some string value")
		LOGGER.info("Data - " + msg + " sent to Kafka Topic - " + topicName);
	}
}
