package Orange_HRM.oHRM.base;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import Orange_HRM.oHRM.pages.*;
import Orange_HRM.oHRM.utils.DriverManager;

public class BaseTest {
	  protected WebDriver driver;
	    protected LoginPgObj lgn; // Declare LoginPgObj here
	    protected WebDriverWait wt; // Declare WebDriverWait here
	    protected DashboardPgObj dboard; // Declare DashboardPgObj here
	    protected Actions actions;
	    

	    @BeforeSuite
	    public void setUp() {
	    	driver = DriverManager.getWbDriver(); 				// Get/Initialize WebDriver
	        driver.get(DriverManager.getHomepageUrl());			// Navigate to the Url
	        System.out.println("Waiting for page to load...");  // Debug message
	        DriverManager.waitForPageLoad(driver);             // Wait for initial page load
	     // Initialize WebDriverWait AFTER the driver is set up
	        wt = new WebDriverWait(driver, Duration.ofSeconds(150));
	     // Initialize your Page Object instance using the initialized driver
	        lgn = new LoginPgObj(driver); // <-- THIS IS THE CRUCIAL LINE for 'login' object
	        dboard=new DashboardPgObj(driver);
	        actions = new Actions(driver);
	        
	    }

    @AfterSuite
    public void tearDown() {
        DriverManager.quitWbDriver();
    }

}
