package framework.pageObject;

import framework.Log;
import framework.browserFactory.BrowserFactory;
import framework.elements.Button;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;


public abstract class BasePage {
    private Log log = Log.getInstance();
    private WebDriver driver = BrowserFactory.getInstance().getDriver();
    private Button button;
    private By locator;

    public BasePage(By locator) {
        init(locator);
        Assert.assertEquals(true, isOpen(locator));
        log.info(log.getLoc("loc.open.page"));
    }

    public boolean isOpen(By titleLocator) {
        this.button = new Button(titleLocator);
        return button.isDisplayed();
    }

    public void init(By pageLocator) {
        this.locator = pageLocator;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void escapeFromADB() {
        WebElement body = getDriver().findElement(By.tagName("body"));
        body.sendKeys(Keys.SPACE);
    }
}
