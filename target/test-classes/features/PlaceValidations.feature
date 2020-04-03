Feature: Validating Place API's
//outline means we are telling that there is some data
Scenario Outline: Verify if Place is being Succesfully added using AddPlaceAPI
         Given Add Place Payload with "<name>", "<language>", "<address>"
         When user calls "AddPlaceAPI" with "POST" http request
         Then the API call got success with status code 200
	     And "status" in response body is "OK"
	     And "scope" in response body is "APP"

Examples:
      |name	|language	|address	|
      |victor	| Bengali	|silchar	|
      |soma gope	|	english	|	kolkota	|
      
