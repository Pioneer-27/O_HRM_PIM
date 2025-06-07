package Orange_HRM.oHRM.pages;

import java.util.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.*;
import Orange_HRM.oHRM.utils.ExtFileReader;

public class DashboardPgObj extends BasePage{
	protected LoginPgObj lgn; // Declare LoginPgObj here
	String originalWindowHandle;
	public List<String> createdEmployeeIds; //Declared list to hold multiple employee IDs
	public String currentEmpId;
	
	public DashboardPgObj(WebDriver driver) {
        super(driver);
        this.lgn = new LoginPgObj(driver);
        createdEmployeeIds = new ArrayList<>();
        PageFactory.initElements(driver, this);

    } 
	public String[] firstNames = {
	        ExtFileReader.getProperty("firstName"),
	        ExtFileReader.getProperty("firstName1"),
	        ExtFileReader.getProperty("firstName2"),
	        ExtFileReader.getProperty("firstName3")
	    };

	   public String[] lastNames = {
	        ExtFileReader.getProperty("lastName"),
	        ExtFileReader.getProperty("lastName1"),
	        ExtFileReader.getProperty("lastName2"),
	        ExtFileReader.getProperty("lastName3")
	    };
	
	   @FindBy(css = ".oxd-userdropdown")
	    public WebElement userMenuDrop;

	    @FindBy(xpath = "//*[@class='oxd-dropdown-menu']//a[normalize-space()='Logout']")
	    public WebElement logout;

	    @FindBy(xpath = "//*[@class='oxd-main-menu-item-wrapper' and .//span[normalize-space()='PIM']]")
	    public WebElement pimNavMenuItem;

	    @FindBy(xpath = "//*[text()='Add Employee' and contains(@class,'orangehrm-main-title')]")
	    public WebElement pimNavAddEmpHeader;

	    @FindBy(name = "firstName")
	    public WebElement firstNameInput;

	    @FindBy(name = "lastName")
	    public WebElement lastNameInput;

	    @FindBy(xpath="//*[normalize-space()='Employee Id']//input")
	    public WebElement employeeIdInput;

	    @FindBy(xpath="//button[@type='submit' and normalize-space()='Save']")
	    public WebElement saveButton;
	    
	    @FindBy(xpath = "//button[@type='submit' and normalize-space()='Search']")
	    public WebElement searchButton;
	    
	    @FindBy(xpath = "//button[@type='reset' and normalize-space()='Reset']")
	    public WebElement resetButton;

	    @FindBy(xpath="//*[contains(@class,'oxd-text--toast-message') and contains(normalize-space(), 'Successfully Saved')]")
	    public WebElement savedSuccessPop;
	    
	    @FindBy(xpath = "(//*[contains(@class,'oxd-table-th') and contains(normalize-space(),'Id')]//ancestor::*[contains(@class,'orangehrm-employee-list')]//*[contains(@class,'oxd-table-cell')])[3]")
	    public WebElement firstRowEmpName;
	    
	    @FindBy(xpath = "(//*[contains(@class,'oxd-table-th') and contains(normalize-space(),'Id')]//ancestor::*[contains(@class,'orangehrm-employee-list')]//*[contains(@class,'oxd-table-cell')])[4]")
	    public WebElement lastRowEmpName;
	    
	    @FindBy(xpath = "(//*[contains(@class,'oxd-table-th') and contains(normalize-space(),'Id')]//ancestor::*[contains(@class,'orangehrm-employee-list')]//*[contains(@class,'oxd-table-cell')])[2]")
	    public WebElement resultId;
	    

	    public DashboardPgObj clickDropdownAndHoverThenClickLogout() {
	        userMenuDrop.click();
	        wt.until(ExpectedConditions.elementToBeClickable(logout)).click();
	        return this;
	    }
	    
	    /*public DashboardPgObj clickDropdownAndHoverThenClickLogout() throws InterruptedException {
	    	userMenuDrop.click();
	        actions.moveToElement(logout).build().perform();
	        Thread.sleep(3000);
	        logout.click(); 
	        return new DashboardPgObj(driver); 
	    }*/	

	    public String copyUrlAndVerifyNewTabAccess() {
	        String originalUrl = lgn.getUrl();
	        originalWindowHandle = driver.getWindowHandle();
	        driver.switchTo().newWindow(WindowType.TAB);

	        Set<String> handles = driver.getWindowHandles();
	        for (String handle : handles) {
	            if (!handle.equals(originalWindowHandle)) {
	                driver.switchTo().window(handle);
	                break;
	            }
	        }

	        driver.get(originalUrl);
	        wt.until(ExpectedConditions.urlContains("login"));
	        String redirectedUrl = driver.getCurrentUrl();
	        driver.close();
	        driver.switchTo().window(originalWindowHandle);
	        return redirectedUrl;
	    }

	    public DashboardPgObj clickPIM() {
	        actions.moveToElement(pimNavMenuItem).click().perform();
	        return this;
	    }

	    public boolean isAddEmpHeaderDisplayed() {
	        return pimNavAddEmpHeader.isDisplayed();
	    }

	    public WebElement clickTopbarNavLink(String linkText) throws InterruptedException {
	    	String dynamicXPath = "//*[@class='oxd-topbar-body-nav-tab']/ancestor::*[@class='oxd-topbar-body-nav']//a[normalize-space() = '" + linkText + "']";
	        System.out.println("DEBUG: Generated XPath: " + dynamicXPath); // For debugging
	        WebElement link = driver.findElement(By.xpath(dynamicXPath));
	        System.out.println("DEBUG: Waiting to click on "+link);
	        link.click();
	        Thread.sleep(2500);
	        System.out.println("DEBUG: Clicked on "+link);
	        return link; // Return the clicked element if needed for further checks
	    }
	    
	    public DashboardPgObj clickSaveButton() throws InterruptedException {
	        wt.until(ExpectedConditions.visibilityOf(saveButton));
	        System.out.println("DEBUG: Waiting for visibility of Save button.");
	        Thread.sleep(2000);
	        saveButton.click();
	        Thread.sleep(3800);
	        System.out.println("DEBUG: Clicked Save button.");
	        try {
	        	wt.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(@class, 'oxd-input-field-error-message') and normalize-space()='Required']")));
	        	System.out.println("DEBUG: Validation error messages appeared, staying on Add Employee page.");
	            return this; // Still on the Add Employee page
	        } catch (org.openqa.selenium.TimeoutException e) {
	            System.out.println("DEBUG: No immediate validation errors.");
	            return new DashboardPgObj(driver); // Return the next Page Object
	        }
	    }

	    public DashboardPgObj fillEmployeeDetails(String firstName, String lastName) throws InterruptedException {
	        wt.until(ExpectedConditions.visibilityOf(firstNameInput)).sendKeys(firstName);
	        Thread.sleep(800);
	        wt.until(ExpectedConditions.visibilityOf(lastNameInput)).sendKeys(lastName);
	        String empId = getEmployeeIdValue();
	        createdEmployeeIds.add(empId);
	        saveButton.click();
            System.out.println("DEBUG: Details Added");
	        wt.until(ExpectedConditions.visibilityOf(savedSuccessPop));
            System.out.println("DEBUG: Saved Success notified.");
	        Thread.sleep(3800);
	        return this;
	    }

	    public String getEmployeeIdValue() {
	        return employeeIdInput.getAttribute("value");
	    }

	    public List<String> getCreatedEmployeeIds() {
	        return createdEmployeeIds;
	    }
	}
	
