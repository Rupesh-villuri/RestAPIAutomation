package com.qa.tests;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.util.TestUtil;

public class GetAPITest extends TestBase{

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
	
	@Test(priority=1)
	public void getApiTest() throws ClientProtocolException, IOException {
		restClient=new RestClient();
		closeableHttpResponse= restClient.get(url);
		
		//1.Status Code
		int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status Code is  "+ statusCode );
		Assert.assertEquals(statusCode, Response_Status_Code_200, "Status code is not 200");
		
		
		//2.JSON String
		String ResponseString = EntityUtils.toString(closeableHttpResponse.getEntity(),"UTF-8");
				
		JSONObject responseJson = new  JSONObject(ResponseString);//converting to JSOn format in braces form
		System.out.println("Response Json from API -- > "+responseJson);
		
		//Single value assertion
		//per_page
		String perPageValue = TestUtil.getValueByJPath(responseJson, "/per_page");
		System.out.println("Value of per page is --> "+perPageValue);
		Assert.assertEquals(Integer.parseInt(perPageValue), 6);
		
		//total
		String totalValue = TestUtil.getValueByJPath(responseJson, "/total");
		System.out.println("Value of total is -->"+totalValue);
		Assert.assertEquals(Integer.parseInt(totalValue), 12);
		
		//Get the value from JSON array
		String lastname= TestUtil.getValueByJPath(responseJson, "/data[0]/last_name");
		String avatar = TestUtil.getValueByJPath(responseJson, "/data[0]/avatar");
		String email = TestUtil.getValueByJPath(responseJson, "/data[0]/email");
		String firstName =TestUtil.getValueByJPath(responseJson, "/data[1]/first_name");
		
		System.out.println(lastname+" ,"+avatar+" ,"+email+" ,"+firstName);
		
		//3.All Headers
		Header[] headersArray=closeableHttpResponse.getAllHeaders();
		//now i will convert this header array into some hash map which is storing in key value format
		HashMap<String, String> allHeaders=new HashMap<String,String>();

		for (Header header : headersArray) 
		{
			allHeaders.put(header.getName(), header.getValue());
			
		}
		System.out.println("Headers array ---> "+allHeaders);
	}
	
	@Test(priority=2)
	public void getAPITestWithHeaders() throws ClientProtocolException, IOException {
		
		restClient = new RestClient();
		
		//here for calling get function we are passing 2 things url and headers in hashmap form
		HashMap<String,String> headerMap = new HashMap<String,String>();
		headerMap.put("Content-Type", "application/json");
		
		//creating just 2 other dummy headers but not calling along with our url
	//	headerMap.put("username", "Rupesh");
	//	headerMap.put("password", "B@ba123");
	//	headerMap.put("AuthToken", "qcftreshauj5h");
		
		closeableHttpResponse = restClient.get(url, headerMap);
		
		//1.Status Code
		int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status Code is  "+ statusCode );
		Assert.assertEquals(statusCode, Response_Status_Code_200, "Status code is not 200");

		//2.JSON String
		String ResponseString = EntityUtils.toString(closeableHttpResponse.getEntity(),"UTF-8");

		JSONObject responseJson = new  JSONObject(ResponseString);//converting to JSOn format in braces form
		System.out.println("Response Json from API -- > "+responseJson);

		//Single value assertion
		String perPageValue = TestUtil.getValueByJPath(responseJson, "/per_page");
		System.out.println("Value of per page is --> "+perPageValue);
		Assert.assertEquals(Integer.parseInt(perPageValue), 6);

		//Get the value from JSON array
		String lastname= TestUtil.getValueByJPath(responseJson, "/data[0]/last_name");
		String firstName =TestUtil.getValueByJPath(responseJson, "/data[1]/first_name");

		System.out.println(lastname+" ,"+firstName);

		//3.All Headers
		Header[] headersArray=closeableHttpResponse.getAllHeaders();
		HashMap<String, String> allHeaders=new HashMap<String,String>();

		for (Header header : headersArray) 
		{
			allHeaders.put(header.getName(), header.getValue());

		}
		System.out.println("Headers array ---> "+allHeaders);		
	}
}
