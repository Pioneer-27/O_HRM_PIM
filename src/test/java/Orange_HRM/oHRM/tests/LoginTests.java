package Orange_HRM.oHRM.tests;

import java.util.List;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import Orange_HRM.oHRM.base.BaseTest;
import Orange_HRM.oHRM.utils.ExtFileReader;

public class LoginTests extends BaseTest{	
	
	 @Test (groups = {"UI", "Login"}, priority = 1)
	    public void testVerifyUIElements() {
		// lgn.navigateToHomepage();
	        Assert.assertTrue(lgn.isUsernameDisplayed(), "Username field is not displayed");
	        Assert.assertTrue(lgn.isPasswordDisplayed(), "Password field is not displayed");
	        Assert.assertTrue(lgn.isForgotPasswordDisplayed(), "Forgot Password link is not displayed");
	    }
	 
	 @Test (groups = {"UI", "Login"}, priority = 3)
	 public void testEmptyUsernameAndPassword() throws InterruptedException {
		 lgn.login("", "");  // both username and password empty
	     Assert.assertEquals(lgn.getErrorMessage(), "Required");
	 }
	 
	 @Test (groups = {"UI", "Login"}, priority = 2)
	 public void testEmptyUsername() {
		 lgn.passwordInput.sendKeys(ExtFileReader.getProperty("password"));
	     lgn.loginButton.click();
	      Assert.assertEquals(lgn.getErrorMessage(), "Required");
	      driver.navigate().refresh();
	 }
	 
	 @Test (groups = {"UI", "Login"}, priority = 4)
	 public void testEmptyPassword() {
		 lgn.usernameInput.sendKeys(ExtFileReader.getProperty("username"));
	     lgn.loginButton.click();
	     Assert.assertEquals(lgn.getErrorMessage(), "Required");
	 }
	 
	 @Test (groups = {"UI", "Login"}, priority = 5)
	 public void testInvalidUsername() throws InterruptedException {
		 lgn.login(ExtFileReader.getProperty("invalidusrname"), ExtFileReader.getProperty("password"));
		 Assert.assertEquals(lgn.getInvalidCredMessage(), "Invalid credentials");	 
	 }
	 
	 @Test (groups = {"UI", "Login"}, priority = 6)
	 public void testInvalidPassword() throws InterruptedException {
		 lgn.login(ExtFileReader.getProperty("username"), ExtFileReader.getProperty("invalidpwd"));
		 Assert.assertEquals(lgn.getInvalidCredMessage(), "Invalid credentials");	 
	 }
	 
	 @Test (groups = {"UI", "Login"}, priority = 7)
	 public void testInvalidUsernameAndPassword() throws InterruptedException {
		 lgn.login(ExtFileReader.getProperty("invalidusrname"), ExtFileReader.getProperty("invalidpwd"));
		 Assert.assertEquals(lgn.getInvalidCredMessage(), "Invalid credentials");	 
	 }
	 
	 @Test (groups = {"UI", "Login"}, priority = 8)
	 public void testCaseSensitiveLogin() throws InterruptedException{
		 lgn.login(ExtFileReader.getProperty("username"), ExtFileReader.getProperty("caseswitchpwd"));
		 Assert.assertEquals(lgn.getInvalidCredMessage(), "Invalid credentials");
		 lgn.login(ExtFileReader.getProperty("caseswitchusername"), ExtFileReader.getProperty("password"));
	     System.out.println("Current URL after login: " + lgn.getUrl());
	     Assert.assertTrue(lgn.getUrl().endsWith("/dashboard/index"), "URL does not end with '/dashboard/index'. Actual URL: " + lgn.getUrl());
	 }
	 
	 @Test (groups = {"UI", "Login"}, priority = 9)
	 public void testLogoutAndCopyUrlValidation() throws InterruptedException {
		 wt.until(ExpectedConditions.visibilityOf(dboard.userMenuDrop));
		//Step:1 Logout from account
	     dboard.clickDropdownAndHoverThenClickLogout();
	     Thread.sleep(5000);
		 dboard.copyUrlAndVerifyNewTabAccess();
	     Thread.sleep(3000);
	 }
	 
	 @Test (groups = {"UI", "Login"}, priority = 10)
	 public void testForgotPasswordLinkFlow() throws InterruptedException {
		wt.until(ExpectedConditions.visibilityOf(lgn.forgotPasswordLink));
		lgn.forgotPasswordLink.click();
	     Thread.sleep(1000);
		Assert.assertTrue(lgn.getUrl().endsWith("/requestPasswordResetCode"), "URL does not end with '/requestPasswordResetCode'. Actual URL: " + lgn.getUrl());
		Assert.assertEquals(lgn.getHeaderText(), "Reset Password");
		lgn.usernameInput.sendKeys(ExtFileReader.getProperty("username"));
	     Thread.sleep(1000);
		lgn.resetSubmit.click();
	     Thread.sleep(1000);
		Assert.assertEquals(lgn.getHeaderText(), "Reset Password link sent successfully");
		driver.navigate().back();
	     Thread.sleep(2000);
		Assert.assertTrue(lgn.getUrl().endsWith("/requestPasswordResetCode"), "URL does not end with '/requestPasswordResetCode'. Actual URL: " + lgn.getUrl());
		lgn.resetCancel.click();
	     Thread.sleep(2000);
		Assert.assertTrue(lgn.getUrl().endsWith("/login"), "URL does not end with '/login'. Actual URL: " + lgn.getUrl());
		
	 }
	 @Test (groups = {"UI", "Login"}, priority = 11)
	 public void testValidLogin() throws InterruptedException {
	     lgn. passwordInput.clear();
	     lgn.usernameInput.sendKeys(ExtFileReader.getProperty("username"));
	     lgn. passwordInput.clear();
	     lgn. passwordInput.sendKeys(ExtFileReader.getProperty("password"));
	     Thread.sleep(2000);
		 lgn.loginButton.sendKeys(Keys.ENTER);
	     Thread.sleep(5000);
		 Assert.assertTrue(lgn.getUrl().endsWith("/dashboard/index"), "URL does not end with '/dashboard/index'. Actual URL: " + lgn.getUrl());
	 }
	 
	 @Test (groups = {"UI", "Dashboard"}, priority = 12)
	 public void testPIMNavigation() {
	 dboard.clickPIM();
	 }
	 
	 @Test (groups = {"UI", "Dashboard"}, priority = 13)
	 public void testAddEmployeeNavigation() throws InterruptedException{
	     Thread.sleep(800);
		 dboard.clickTopbarNavLink(ExtFileReader.getProperty("pimnavitemaddemp"));
		 wt.until(ExpectedConditions.urlContains("addEmployee"));
	     Assert.assertTrue(driver.getCurrentUrl().contains("addEmployee"), "Not redirected to Add Employee page.");
	     Assert.assertTrue(dboard.isAddEmpHeaderDisplayed());
	 }
	 @Test (groups = {"UI", "Dashboard"}, priority = 14)
	 public void testAddEmployeeDetails() throws InterruptedException {
		 dboard.clickSaveButton();
		 System.out.println("DEBUG: I've been clicked");
		 for (int i = 0; i < dboard.firstNames.length; i++) {
		        if (i != 0) {
		            // Navigate to Add Employee page again for 2nd, 3rd, etc.
		            dboard.clickTopbarNavLink(ExtFileReader.getProperty("pimnavitemaddemp"));
			        Thread.sleep(2200);
		            wt.until(ExpectedConditions.urlContains("addEmployee"));
		            Assert.assertTrue(dboard.isAddEmpHeaderDisplayed());
		        }

		        dboard.fillEmployeeDetails(dboard.firstNames[i], dboard.lastNames[i]);
		        System.out.println("DEBUG: Entry " + (i + 1) + " Completed for " + dboard.firstNames[i] + " " + dboard.lastNames[i]);
		        Thread.sleep(1000);
		    }

		    // Verify final URL ends with expected employee ID path
		    String expectedUrlPattern = "/viewPersonalDetails/empNumber";
		    Assert.assertTrue(lgn.getUrl().contains(expectedUrlPattern),
		            "URL did not contain expected pattern. Expected: '" + expectedUrlPattern + "', but got: '" + lgn.getUrl() + "'");
		    System.out.println("DEBUG: URL verified. Contains: " + expectedUrlPattern);
	 
	 }
	 @Test (groups = {"UI", "Dashboard"}, priority = 15)
	 public void testPreviewEmployeeDetails() throws InterruptedException {
		 dboard.clickTopbarNavLink(ExtFileReader.getProperty("pimnavitememplist"));
		 Thread.sleep(4000);  // Optional: wait for UI to load
		    List<String> empIds = dboard.getCreatedEmployeeIds();
		    System.out.println("DEBUG: Retrieved Employee IDs for validation: " + empIds);
		    for (int i = 0; i < empIds.size(); i++) {
		        String empId = empIds.get(i);
		        String expectedFullName = dboard.firstNames[i] + " " + dboard.lastNames[i];
		        
		        // Clear and enter ID into search box
		        dboard.scrollIntoView(dboard.employeeIdInput);
		        dboard.employeeIdInput.clear();
		        Thread.sleep(1200); 
		        dboard.employeeIdInput.sendKeys(empId);
		        Thread.sleep(2200); // Allow results to load
		        dboard.searchButton.click();   // Click search button
		        Thread.sleep(2800); // Allow results to load
		        dboard.scrollIntoView(dboard.firstRowEmpName);
	            Thread.sleep(2000);
		        // Combine first and last name
		        String actualFirstName = dboard.firstRowEmpName.getText().trim();
		        String actualLastName = dboard.lastRowEmpName.getText().trim();
		        String actualFullName = actualFirstName + " " + actualLastName;

		        // Verify Employee ID matches
		        String actualId = dboard.resultId.getText().trim();
		        Assert.assertEquals(actualId, empId, "Employee ID mismatch in search result for: " + empId);
		        // Verify Employee Name
		        Assert.assertEquals(actualFullName, expectedFullName, "Employee Name mismatch for ID: " + empId);
		        System.out.println("Name Verified for Employee ID " + empId + ": " + actualFullName);
		        dboard.scrollIntoView(dboard.resetButton);
		        dboard.resetButton.click();
	            Thread.sleep(2300);
		    }
	 }
	 @Test (groups = {"UI", "Dashboard"}, priority = 16)
	 public void testfinalLogout() throws InterruptedException {
	 dboard.clickDropdownAndHoverThenClickLogout();
     Thread.sleep(4000);
	 	}
	 
	 
}
