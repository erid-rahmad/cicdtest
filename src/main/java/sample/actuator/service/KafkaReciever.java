package sample.actuator.service;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class KafkaReciever {

//	@Autowired
//	RestTemplate restTemplate;

	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaReciever.class);

	@KafkaListener(topics = "${kafka.topic.consume.name}")
	public void recieveData(String student) {
		Map<String,Object> bls = new HashMap<>();
		Map<String,Object> tossti = new HashMap<>();
		log.info("This From Kafka {}",student);
//		bls=restTemplate.postForObject("urlinq",tossti, Map.class);

	}
}
