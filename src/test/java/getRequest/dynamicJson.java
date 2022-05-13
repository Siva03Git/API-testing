package getRequest;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.commonMethods;
import files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class dynamicJson {

	@BeforeClass

	public void setup() {
		RestAssured.baseURI = "http://216.10.245.166";
	}
	
	@Test(dataProvider = "BookData")
	
	public void addBook(String isbn, String aisle, String Test) 
	{
		String response = given().header("Content-Type", "application/json")
		.body(payload.bookDetails(isbn,aisle)).when().post("/Library/Addbook.php")
		.then().assertThat().statusCode(200).extract().response().asString();
		JsonPath js=commonMethods.getJsonObject(response);
		String id=js.getString("ID");
		System.out.println("Id==="+id);
		System.out.println("Test#"+Test);
	}
	
	//Array = Collection of elements
	//MultidementionalArray= Collection of arrays
	
	@DataProvider(name="BookData")
	
	public Object[] getData() 
	{
		return new Object[][] {{"aaaa","1111a","ab"},{"bbbb","2222b","cd"},{"cccc","3333c","ef"}};	 
	}
}
