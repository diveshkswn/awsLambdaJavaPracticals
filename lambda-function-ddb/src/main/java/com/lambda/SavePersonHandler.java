package com.lambda;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;


//To build-> mvn clean package shade:shade
//For more information
//https://www.baeldung.com/aws-lambda-dynamodb-java
//https://www.tutorialspoint.com/dynamodb/dynamodb_batch_writing.htm

public class SavePersonHandler implements RequestHandler<PersonRequest, String> {

	private DynamoDB dynamoDb;
    private String DYNAMODB_TABLE_NAME = "Person";
    private Regions REGION = Regions.US_WEST_2;
	
	
	public String handleRequest(PersonRequest input, Context context) {
		  LambdaLogger logger = context.getLogger();
		  
		this.initDynamoDbClient();
		 logger.log("EVENT: " + input.getId());
	
		//Adding Data to DynamoDb
		persistData(input);
		
		Item itemresponse=new Item().withInt("statusCode", 200).withString("body", "Item Saved SuccessFully");
		
		return itemresponse.toJSONPretty();
	}
	
	
	//PutItem Modular Function
	private PutItemOutcome persistData(PersonRequest personRequest)   {
		
		
		Item item=new Item().withPrimaryKey("Id", personRequest.getId())
				.withString("firstName", personRequest.getFirstName())
				.withString("lastname",  personRequest.getLastName());
	//	return this.dynamoDb.getTable(DYNAMODB_TABLE_NAME).putItem(new PutItemSpec().withItem(item));
		return this.dynamoDb.getTable(DYNAMODB_TABLE_NAME).putItem(item);
	}	
	
	
	
	//Initializing Connection
	@SuppressWarnings("deprecation")
	private void initDynamoDbClient() {
		AmazonDynamoDBClient client= new AmazonDynamoDBClient();
		Region.getRegion(REGION);
		this.dynamoDb=new DynamoDB(client);
		
	}
	

}
