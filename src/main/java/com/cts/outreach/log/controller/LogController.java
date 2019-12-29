package com.cts.outreach.log.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.outreach.log.entity.LogEntity;
import com.cts.outreach.log.repo.LogRepo;

@RestController
public class LogController {
	
	private Logger LOGGER = LoggerFactory.getLogger(LogController.class);
	
	@Autowired
	private LogRepo logRepo;
	
	@GetMapping("/v1/getAllLogs")
	public List<LogEntity> getAllLogs() {
		LOGGER.info("Log requested");
		return logRepo.getAllLogs();
	}

}
