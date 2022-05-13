package getRequest;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import files.commonMethods;
import files.payload;
//import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import io.restassured.RestAssured;

public class addPlace {

	@BeforeClass

	public void setup() {
		RestAssured.baseURI = "https://rahulshettyacademy.com";
	}

	@Test
	public void addAndUpdatePlace() {
		
		// Add Place (POST)

		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(payload.placeToAdd()).when().post("maps/api/place/add/json").then().assertThat().statusCode(200)
				.body("scope", equalTo("APP")).header("Server", equalTo("Apache/2.4.41 (Ubuntu)")).extract().response()
				.asString();

		// Fetch place id
		
		String placeId = commonMethods.getJsonObject(response).getString("place_id");
		System.out.println("---------Place Id-------" + placeId);

		// Update place with new address (PUT)

		given().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body("{\r\n" + "\"place_id\":\"" + placeId + "\",\r\n" + "\"address\":\"Adams Garden\",\r\n"
						+ "\"key\":\"qaclick123\"\r\n" + "}")
				.when().put("maps/api/place/update/json").then().assertThat().statusCode(200)
				.body("msg", equalTo("Address successfully updated"));

		// Get place to validate if address is present on the new address (GET)

		String result = given().queryParam("key", "qaclick123").queryParam("place_id", placeId)
				.when().get("maps/api/place/get/json").then().assertThat()
				.statusCode(200).body("address", equalTo("Adams Garden")).extract().response().asString();

		String address = commonMethods.getJsonObject(result).getString("address");
		System.out.println("---------Verify Address-------" + address);
		System.out.println("---------Verify Address-------" + address);
		System.out.println("---------Verify Address-------" + address);
		System.out.println("---------Verify Address-------" + address);
		Assert.assertEquals(address, "Adams Garden");
	}
	
	
	@Test
	public void getBodyFromJsonFile() throws IOException {
		
		// Content of the file to string - Content of the Json file data can convert from byte-byte data to String

		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(new String(Files.readAllBytes(Paths.
				 get("C:\\Users\\Siva\\eclipse-workspace\\com.learn.restassured\\src\\test\\resources\\addPlace.Json"))))
				.when().post("maps/api/place/add/json").then().assertThat().statusCode(200)
				.body("scope", equalTo("APP")).header("Server", equalTo("Apache/2.4.41 (Ubuntu)")).extract().response()
				.asString();		String placeId = commonMethods.getJsonObject(response).getString("place_id");
		System.out.println("---------Place Id-------" + placeId);

	}

}
