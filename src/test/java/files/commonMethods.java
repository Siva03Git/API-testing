package files;

import io.restassured.path.json.JsonPath;

public class commonMethods {
	
	public static JsonPath getJsonObject(String response) 
	{
		//Test
		JsonPath js=new JsonPath(response);
		return js;
	}

}
