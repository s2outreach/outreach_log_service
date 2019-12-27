package com.cts.outreach.log;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import com.cts.outreach.log.repo.LogRepo;
import com.cts.outreach.log.service.KafkaConsumer;

@RunWith(SpringRunner.class)
@ComponentScan(basePackages = {"com.cts.outreach.event"})
@SpringBootTest
public class KafkaConsumerTest {
	
	@Autowired
	KafkaConsumer kafkaConsumer;
	
	@MockBean
	LogRepo logRepo;

	@Test
	public void consumeAddLogTest() throws IOException {
		String message ="{\"logid\":\"1\",\"eventname\":\"test\","
				+ "\"username\":\"test\",\"action\":\"user registered\",\"timestamp\":\"\"}";
		kafkaConsumer.consumeAddLog(message);
	}
}
