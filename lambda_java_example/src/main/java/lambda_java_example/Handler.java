package lambda_java_example;

import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class Handler implements RequestHandler<Map<String,String>, String>{

	 Gson gson = new GsonBuilder().setPrettyPrinting().create();
	  
	  public String handleRequest(Map<String,String> event, Context context)
	  {
	    LambdaLogger logger = context.getLogger();
	   
	    // log execution details
	    logger.log("ENVIRONMENT VARIABLES: " + gson.toJson(System.getenv()));
	    logger.log("CONTEXT: " + gson.toJson(context));
	    // process event
	    logger.log("EVENT: " + gson.toJson(event));
	    logger.log("EVENT TYPE: " + event.getClass().toString());
	    
	    
	    Data d=new Data();
	    d.setId(event.get("id"));
	    d.setName(event.get("name"));
	    d.setStatusCode(200);
	    
	    	    return gson.toJson(d);
	  }
	
	
	
	
}
