package com.cts.outreach.log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.cts.outreach.log.entity.LogEntity;
import com.cts.outreach.log.repo.LogRepo;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LogRepo.class)
@Import(DynamoDBConfigLocalTest.class)
public class LogRepoTest {
	
	@Autowired
	LogRepo logRepo;
	
	private static DynamoDBMapper mapper;
	private static AmazonDynamoDB amazonDynamoDB;
	private static DynamoDB dynamoDB;
	
	private static String TABLENAME = "log";
	private static String ATTRIBUTE1 = "logid";
	private static String ATTRIBUTE2 = "timestamp";
	
	@Before
    public void setupClass() throws InterruptedException {
        amazonDynamoDB = AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(
                new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-west-2"))
                .build();
        mapper = new DynamoDBMapper(amazonDynamoDB);
        dynamoDB = new DynamoDB(amazonDynamoDB);
        
        Table table = dynamoDB.getTable(TABLENAME);
        System.out.println("Issuing DeleteTable request for " + TABLENAME);
//        table.delete();

        System.out.println("Waiting for " + TABLENAME + " to be deleted...this may take a while...");
//        table.waitForDelete();
        
        ArrayList<AttributeDefinition> attributeDefinitions = new ArrayList<AttributeDefinition>();
        
        ArrayList<KeySchemaElement> tableKeySchema = new ArrayList<KeySchemaElement>();
        tableKeySchema.add(new KeySchemaElement().withAttributeName(ATTRIBUTE1).withKeyType(KeyType.HASH));
        tableKeySchema.add(new KeySchemaElement().withAttributeName(ATTRIBUTE2).withKeyType(KeyType.RANGE));
        
        attributeDefinitions.add(new AttributeDefinition(ATTRIBUTE1, ScalarAttributeType.S));
        attributeDefinitions.add(new AttributeDefinition(ATTRIBUTE2, ScalarAttributeType.S));
        
        CreateTableRequest createTableRequest = new CreateTableRequest()
        	    .withTableName(TABLENAME)
        	    .withProvisionedThroughput(new ProvisionedThroughput()
        	        .withReadCapacityUnits((long) 5)
        	        .withWriteCapacityUnits((long) 1))
        	    .withAttributeDefinitions(attributeDefinitions)
        	    .withKeySchema(tableKeySchema);
        
//        table = dynamoDB.createTable(createTableRequest);
//        table.waitForActive();
        
        LogEntity newLog = new LogEntity("1", "Event1", "user1", "user registered", new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
		logRepo.addLog(newLog);
        
        System.out.println(table.getDescription());
            
    }
	
	@Test
	public void addLogTest() {
		LogEntity newLog = new LogEntity("1", "Event1", "user1", "user registered", new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
		logRepo.addLog(newLog);
	}
	
	@Test
	public void getAllLogs() {
		List<LogEntity> allLogs = logRepo.getAllLogs();
	}

}
