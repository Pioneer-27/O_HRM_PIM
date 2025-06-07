package Orange_HRM.oHRM.utils;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverManager {
		private static WebDriver driver;
		 private static String browserType;
		 private static String homepageUrl;

		// Load browser type and homepage URL from properties file
		    static {
		        browserType = ExtFileReader.getProperty("browser");
		        homepageUrl = ExtFileReader.getProperty("url");

		        if (browserType == null || browserType.isEmpty()) {
		            browserType = "chrome"; // Default to Chrome if not specified
		        }

		        if (homepageUrl == null || homepageUrl.isEmpty()) {
		            homepageUrl = "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login/"; // Default URL if not specified
		        }
		    }
		    
		 // Initialize and return WebDriver
		    public static WebDriver getWbDriver() {
		        if (driver == null) {
		            switch (browserType.toLowerCase()) {
		                case "firefox":
		                    //System.setProperty("webdriver.gecko.driver", "C:/Users/soura/Music/Mindera_Shopify/drivers/geckodriver.exe");
		                	WebDriverManager.firefoxdriver().setup();
		                	driver = new FirefoxDriver();
		                    break;
		                case "edge":
		                   // System.setProperty("webdriver.edge.driver", "C:/Users/soura/Music/Mindera_Shopify/drivers/msedgedriver.exe");
		                	 WebDriverManager.edgedriver().setup();
		                	driver = new EdgeDriver();
		                    break;
		                default:
		                    //System.setProperty("webdriver.chrome.driver", "C:/Users/soura/Music/Mindera_Shopify/drivers/chromedriver.exe");
		                    WebDriverManager.chromedriver().setup();
		                    driver = new ChromeDriver();
		                    break;
		            }
		            driver.manage().deleteAllCookies();
		            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
		            driver.manage().window().maximize();
		            System.out.println("Initialized WebDriver for browser: " + browserType);
		        }
		        return driver;
		    }
		    
		    // Method to quit the driver
		    public static void quitWbDriver() {
		        if (driver != null) {
		            driver.quit();
		            driver = null;
		        }
		    }
		 // Getter for homepageUrl (exposed for use in the page object)
		    public static String getHomepageUrl() {
		    	System.out.println("Fetching homepage URL: " + homepageUrl);
		        return homepageUrl;
		    }
		    
		 // Getter method to fetch browser type
		    public static String getBrowserType() {
		        return browserType;
		    }
		    
		    public static void waitForPageLoad(WebDriver driver) {
		    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));

		        // Wait for document.readyState to be complete
		        wait.until(webDriver -> {
		            String state = (String) ((JavascriptExecutor) webDriver).executeScript("return document.readyState");
		            System.out.println("Document ReadyState: " + state);
		            return "complete".equals(state);
		        });

		        // Additional JavaScript to wait for jQuery/AJAX to be inactive (if used)
		        try {
		            wait.until(webDriver -> ((JavascriptExecutor) webDriver)
		                    .executeScript("return (typeof jQuery === 'undefined') || (jQuery.active === 0)")
		                    .equals(true));
		        } catch (Exception e) {
		            System.out.println("jQuery wait skipped (jQuery may not be used).");
		        }

		        // Wait for login section's dynamic element
		        try {
		            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".orangehrm-login-forgot")));
		        } catch (TimeoutException e) {
		            System.out.println("Timeout: .orangehrm-login-forgot not visible");
		            throw e;
		        }
		    }
	}

