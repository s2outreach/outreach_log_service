package com.cts.outreach.log.repo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.cts.outreach.log.entity.LogEntity;

@Repository
public class LogRepo {

	@Autowired
	private DynamoDBMapper mapper;
	
	@Autowired
	private AmazonDynamoDB amazonDynamoDB;
	
	private static final String TABLENAME = "log";
	
	public void addLog(LogEntity log) {
		mapper.save(log);
	}
	
	public List<LogEntity> getAllLogs() {
		ScanRequest scanRequest = new ScanRequest()
			    .withTableName(TABLENAME);
		ScanResult result = amazonDynamoDB.scan(scanRequest);
		List<LogEntity> logs = new ArrayList<LogEntity>();
		for (Map<String, AttributeValue> item : result.getItems()){
			AttributeValue logidAV = item.getOrDefault("logid", new AttributeValue());
		    String logid = logidAV.getS();
		    AttributeValue eventnameAV = item.getOrDefault("eventname", new AttributeValue());
		    String eventname = eventnameAV.getS();
		    AttributeValue usernameAV = item.getOrDefault("username", new AttributeValue());
		    String username = usernameAV.getS();
		    AttributeValue actionAV = item.getOrDefault("action", new AttributeValue());
		    String action = actionAV.getS();
		    LogEntity record = new LogEntity(logid, eventname, username, action);
		    logs.add(record);
		}
		return logs;
	}
}
