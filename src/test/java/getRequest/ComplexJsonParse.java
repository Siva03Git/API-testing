package getRequest;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import files.payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	@BeforeClass

	public void setup() {
		
	}

	@Test
	public void testComplexJson() 
	{
		JsonPath js=new JsonPath(payload.CoursePrice());
		//Print number of courses returned by API
		int count= js.getInt("courses.size()");
		System.out.println("Num of courses : "+count);
		
		//Print purchase amount.
		int purchaseAmt=js.getInt("dashboard.purchaseAmount");
		System.out.println("Purchase amount : "+purchaseAmt);
		
		//Print title of the first course
		String firstCourseTitle=js.getString("courses[0].title");
		System.out.println("firstCourseTitle : "+firstCourseTitle);
		
		//Print All course titles and their respective Prices
		
		for(int i=0; i<count;i++) 
		{
			System.out.println(js.getString("courses["+i+"].title")) ;
			System.out.println(js.getInt("courses["+i+"].price")) ;
		}
		
		//Print no of copies sold by RPA Course
		for(int i=0; i<count;i++) 
		{
			if(js.getString("courses["+i+"].title").equals("RPA")) 
			{
				System.out.println("No of copies sold by RPA Course = "+js.getInt("courses["+i+"].copies")) ;
				break;
			}
			
		}
		//Verify if Sum of all Course prices matches with Purchase Amount
		
		int sum=0;
		for(int i=0; i<count;i++) 
		{
			int numOfCopies=js.getInt("courses["+i+"].copies");
			int price=js.getInt("courses["+i+"].price");
			
			sum=sum+(numOfCopies*price);
		}
		System.out.println("Total Purchase amount = "+sum);
		Assert.assertEquals(purchaseAmt, sum);
	}
}
