package com.cts.outreach.log.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.cts.outreach.log.entity.LogEntity;

@Repository
public class LogRepo {

	@Autowired
	private DynamoDBMapper mapper;
	
	@Autowired
	private AmazonDynamoDB amazonDynamoDB;
	
	private DynamoDB dynamoDB;
	
	private static final String TABLENAME = "log";
	
	public void addLog(LogEntity log) {
		mapper.save(log);
	}
}
