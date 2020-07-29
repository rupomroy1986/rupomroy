package stepDefinations;
import static io.restassured.RestAssured.given;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.APIResources;
//import pojo.AddPlace;
//import pojo.Location;
//import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;
//of we dont use static it will show null pointer exception
public class StepDefination extends Utils {
	RequestSpecification res;
	ResponseSpecification resspec;
	Response response;
	TestDataBuild data=new TestDataBuild();
	static String place_id;
	
    @Given("^Add Place Payload with (.+), (.+), (.+)$")
	//@Given("Add Place Payload with {string}, {string}, {string}")
	public void add_Place_Payload_with(String name, String language, String address) throws IOException {
		//as we are inheriting we can directly call the method name
	     res =given().spec(requestSpecification()).body(data.addPlacePayLoad(name, language, address ));
	}

	@When("user calls {string} with {string} http request")
	public void user_calls_with_http_request(String resource, String method) {
		//constructor will be called with the value of the resource which you pass-part of enum class
		APIResources resourceAPI=APIResources.valueOf(resource);
		System.out.println(resourceAPI.getResource());
		//it is pointing to the global variables
		resspec=new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		if(method.equalsIgnoreCase("POST"))
			 response =res.when().post(resourceAPI.getResource());
			else if(method.equalsIgnoreCase("GET"))
				 response =res.when().get(resourceAPI.getResource());
		
	}

	@Then("the API call got success with status code {int}")
	public void the_API_call_got_success_with_status_code(Integer int1) {
		             //here we are validating the results
		assertEquals(response.getStatusCode(),200);	
		         
		
		
		}
	//once we get the response we are fetching the value from the response.below is the code. Here we are creating under utils to make it genereic

	@Then("{string} in response body is {string}")
	public void in_response_body_is(String keyValue, String ExpectedValue) {
		//here we are checking for the response
		/*String resp = response.asString();
		js=new JsonPath(resp);
		assertEquals(js.get(keyValue).toString(), ExpectedValue);*/
		
		//as we have created the generic files, use the below code.
		
		assertEquals(getJsonPath(response,keyValue),ExpectedValue);
	}
	//two ways we can write, so given in the below codes
	@And("^verify place_Id created maps to (.+) using \"([^\"]*)\"$")
	//@Then("verify place_Id created maps to {string} using {string}")
	public void verify_place_Id_created_maps_to_using(String expectedName, String resource) throws IOException {
	  
		//create 1st request specification
		
		place_id=getJsonPath(response,"place_id");
		res=given().spec(requestSpecification()).queryParam("place_id",place_id);
		//here we can directly use it as below:
		user_calls_with_http_request(resource,"GET");
		  String actualName=getJsonPath(response,"name");
		  assertEquals(actualName,expectedName);
		
		}
	@Given("DeletePlace Payload")
	public void deleteplace_Payload() throws IOException {
	    // Write code here that turns the phrase above into concrete actions
	   
		res =given().spec(requestSpecification()).body(data.deletePlacePayload(place_id));
	}









}
