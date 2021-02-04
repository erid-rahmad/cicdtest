package sample.actuator.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sample.actuator.model.Request;
import sample.actuator.service.KafkaSender;

@RestController
@RequestMapping("/kafkaProducer")
public class KafkaProducerController {

	@Autowired
	private KafkaSender sender;
	
	@PostMapping
	public ResponseEntity<String> sendData(){
		Request set = new Request(null,"asd","asd","asd","asd","asd","asd","asd","asd");
//		sender.sendData(set);
		return new ResponseEntity<>("Data sent to Kafka", HttpStatus.OK);
	}
}
