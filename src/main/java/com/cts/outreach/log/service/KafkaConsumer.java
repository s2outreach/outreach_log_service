package com.cts.outreach.log.service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.cts.outreach.log.entity.LogEntity;
import com.cts.outreach.log.repo.LogRepo;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class KafkaConsumer {
	
	private Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);
	
	@Autowired
	private LogRepo logRepo;
	
	@KafkaListener(topics = "${kafka.add-log}", groupId = "${kafka.group-id}")
	public void consumeAddLog(String message) throws IOException{
		ObjectMapper mapper = new ObjectMapper();
		LogEntity logRecord = mapper.readValue(message, LogEntity.class);
		LOGGER.info(logRecord.getAction());
		logRepo.addLog(logRecord);
	}

}

