package Orange_HRM.oHRM.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import Orange_HRM.oHRM.utils.DriverManager;

public class LoginPgObj extends BasePage{
	private String homepageUrl;   // ✅ Store homepageUrl as a private variable
	 String currentUrl;
	 
		
	 public LoginPgObj(WebDriver driver) {
	        super(driver);
	        this.homepageUrl = DriverManager.getHomepageUrl();
	    }
	
	 // Example of static element with @FindBy
	@FindBy(name = "username")
    public WebElement usernameInput;

    @FindBy(name = "password")
    public WebElement passwordInput;

    @FindBy(css = "button[type='submit']")
    public WebElement loginButton;

    @FindBy(xpath = "//*[contains(@class,'orangehrm-login-forgot-header')]")
    public WebElement forgotPasswordLink;

    @FindBy(xpath = "//*[contains(@class,'oxd-input-field-error-message')]")
    private WebElement errorMessage;

    @FindBy(xpath="//*[contains(@class,'oxd-alert-content-text')]")
    private WebElement invalidErrCred;

    @FindBy(xpath="//*[contains(@class,'orangehrm-forgot-password-title')]")
    public WebElement resetPwdHeader;

    @FindBy(xpath="//button[normalize-space() = 'Reset Password']")
    public WebElement resetSubmit;

    @FindBy(xpath="//button[normalize-space() = 'Cancel']")
    public WebElement resetCancel;

    public void navigateToHomepage() {
        driver.get(homepageUrl);
        DriverManager.waitForPageLoad(driver);
    }

    public void login(String username, String password) {
        wt.until(ExpectedConditions.visibilityOf(usernameInput)).clear();
        usernameInput.sendKeys(username);
        passwordInput.clear();
        passwordInput.sendKeys(password);
        wt.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }

    public boolean isUsernameDisplayed() { return usernameInput.isDisplayed(); }
    public boolean isPasswordDisplayed() { return passwordInput.isDisplayed(); }
    public boolean isForgotPasswordDisplayed() { return forgotPasswordLink.isDisplayed(); }
    public String getErrorMessage() { return errorMessage.getText(); }
    public String getInvalidCredMessage() { return invalidErrCred.getText(); }
    public String getHeaderText() { return resetPwdHeader.getText(); }
    public String getUrl() { return driver.getCurrentUrl(); }
}
