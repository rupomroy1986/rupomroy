package stepDefinations;

import java.io.IOException;

import io.cucumber.java.Before;

public class Hooks {
	
	@Before("@DeletePlace")
	public void beforeScenario() throws IOException
	{		//execute this code only when place id is null
		//write a code that will give you place id
		
		StepDefination m =new StepDefination();
		// as per the java standard, if any variable is staic, we should call using classname followed by variable/method, 
		//if we remove the tags it will take the previous payload
		//it is not mandatory to give the if condition
		/*if(StepDefination.place_id==null)
		{*/
		
		m.add_Place_Payload_with("Shettyrupom", "german", "Asia");
		m.user_calls_with_http_request("AddPlaceAPI", "POST");
		m.verify_place_Id_created_maps_to_using("Shettyrupom", "getPlaceAPI");
		}
		
		

	}


