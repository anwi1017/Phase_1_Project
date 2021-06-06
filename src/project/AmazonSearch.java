package project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class AmazonSearch {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//open website
		
		System.setProperty("webdriver.chrome.driver","chromedriver");
		
		WebDriver driver = new ChromeDriver();
		
		driver.get("https://www.amazon.in/");
		
		driver.manage().window().maximize();
		
		driver.manage().timeouts().implicitlyWait(5000, TimeUnit.MILLISECONDS);
		
		//connection database
		
		String Category = null;
		String Searchterm = null;
		
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
		
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommerce","root","root");
			
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from Amazon");
			
			while(rs.next()) {
				
				System.out.println(rs.getString(2)+" "+rs.getString(3));
				
				Category = rs.getString(2);
				
				Searchterm = rs.getString(3);
				
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("Class not found");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("SQL exception");
			
		}
		
		//category drop down menu
				
 		WebElement department = driver.findElement(By.xpath("//*[@id='searchDropdownBox']"));
               
        department.sendKeys(Category);
        		
		//search term
	
 		WebElement keyword = driver.findElement(By.xpath("//*[@id='twotabsearchtextbox']"));
        
        keyword.sendKeys(Searchterm);
        
        //search submit button
        
 		WebElement submit = driver.findElement(By.xpath("//*[@id='nav-search-submit-button']"));
 		
 		submit.click();
 		
 		//count results
 		
		//List<WebElement> results = driver.findElements(By.xpath("//div[@data-component-type='s-search-result']"));
		List<WebElement> results = driver.findElements(By.xpath("//h2[@class='a-size-mini a-spacing-none a-color-base s-line-clamp-4']"));
		
		System.out.println("First page results total = " + results.size());
		
		//print list of items
		
		  for (WebElement result:results) {
	            System.out.println(result.getText()); 
		  }
		  
		//check count
		  
		WebElement ResultBarText = driver.findElement(By.xpath("//*[@class='a-section a-spacing-small a-spacing-top-small']/span[1]"));
		
		System.out.println("Result bar: " + ResultBarText.getText());
		
		//driver.close();        

	}

}
