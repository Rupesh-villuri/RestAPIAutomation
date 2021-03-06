package com.qa.tests;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.data.Users;

public class PostApiTest {

	//creating all global variables , so that it will be used across all the methods
		TestBase testBase;
		String serviceUrl;
		String apiUrl;
		String url="https://reqres.in/api/users";
		RestClient restClient;
		CloseableHttpResponse closeableHttpResponse;
		
		@BeforeMethod
		public void setUp() {
			/*
			testBase = new TestBase();
			serviceUrl=prop.getProperty("URL");
			apiUrl =prop.getProperty("serviceURL");
			// we need like this https://reqres.in/api/users
			url=serviceUrl+apiUrl;		
		*/	
		}
		
		@Test
		public void postAPITest() throws JsonGenerationException, JsonMappingException, IOException {
			
			restClient= new RestClient();
			
			HashMap<String,String> headerMap = new HashMap<String,String>();
			headerMap.put("Content-Type","application/json");
			//Jackson API
			ObjectMapper mapper = new ObjectMapper();
			//expected users object
			Users users = new Users("morpheus","leader");//calling constructor and passing the data of payload 
						
			//Object to JSON file
			mapper.writeValue(new File("B:\\Eclipse\\Workspace\\restapi\\src\\main\\java\\com\\qa\\data\\users.json"), users);
			
			//Object to Json in String
			
			String usersJsonString = mapper.writeValueAsString(users);
			System.out.println(usersJsonString);
			
			closeableHttpResponse = restClient.post(url, usersJsonString, headerMap);
			
			//1.Status Code
			int statusCode=closeableHttpResponse.getStatusLine().getStatusCode();
			//Assert.assertEquals(statusCode,testBase.Response_Status_Code_201);
			
			//whatever the response i am getting i am convertng into entity so that i can read in properly in format UTF-8
			String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
			
			//now converting this to proper JSON response
			JSONObject responseJson = new JSONObject(responseString);
			System.out.println("The response from API is : "+responseJson);
			
			//now i do un-marshelling and compare the result with actual java object
			//JSON to java Object
			Users usersResObj = mapper.readValue(responseString, Users.class);//actual users Object
			System.out.println(usersResObj);
			
			System.out.println(usersResObj.getId() +" "+ usersResObj.getCreatedAt());
			
			System.out.println(users.getName().equals(usersResObj.getName()));
			System.out.println(users.getJob().equals(usersResObj.getJob()));
			
			Assert.assertTrue(users.getName().equals(usersResObj.getName()));
		}
}

