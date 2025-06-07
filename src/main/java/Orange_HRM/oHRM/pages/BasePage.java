package Orange_HRM.oHRM.pages;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wt;
    protected Actions actions;
    protected JavascriptExecutor js;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wt = new WebDriverWait(driver, Duration.ofSeconds(50));
        this.actions = new Actions(driver);
        this.js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }
    
    public void scrollIntoView(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }
}


