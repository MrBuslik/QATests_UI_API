package framework.browserFactory;

import framework.BaseEntity;
import framework.Log;
import framework.PropertyReader;
import framework.utils.CapabilityGenerator;
import framework.utils.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class BrowserFactory extends BaseEntity {
    private static final String OPERATING_SYSTEM_NAME = System.getProperty("os.name");
    private static final String PROPERTY_CHROME = "webdriver.chrome.driver";
    private static final String PROPERTY_FIREFOX = "webdriver.gecko.driver";
    private static final String PROPERTY_IE = "webdriver.ie.driver";
    private static final String DRIVER_CHROME = "drivers/chromedriver";
    private static final String DRIVER_FIREFOX = "drivers/geckodriver";
    private static final String DRIVER_IE = "IEDriverServer";
    private static final String CHROME = "chrome";
    private static final String FIREFOX = "firefox";
    private static final String IE = "internet explorer";
    private static final String LINUX = "linux";
    private static final String BROWSER_NAME = PropertyReader.getProperty("browserName");
    private static final String URL = PropertyReader.getProperty("url");
    private static BrowserFactory instance;
    private static String driverPath = "src/main/resources/";
    private static Log log = Log.getInstance();

    private BrowserFactory() throws IOException {
        driverPath = new File(driverPath).getCanonicalPath();
        initBrowser(BROWSER_NAME);
        WebDriverManager.openUrl(driver, URL);
    }

    public static String getDriverPath() {
        return driverPath;
    }

    public static BrowserFactory getInstance() {
        try {
            if (instance == null) {
                instance = new BrowserFactory();
            }
        } catch (IOException ex) {
            log.error("loc.err.open.browser");
        }
        return instance;
    }

    private static String initOS(String operatingSystemName) {
        System.out.println("Current Operating System: " + System.getProperties().getProperty("os.name"));
        return operatingSystemName.toLowerCase().equals(LINUX) ? "" : ".exe";
    }

    private void SetPropertyBrowser(String prop, String driverName) {
        System.setProperty(prop, Paths.get(driverPath, driverName.concat(initOS(OPERATING_SYSTEM_NAME))).toString());
    }

    public WebDriver getDriver() {
        return driver;
    }

    private void initBrowser(String browserName) {
        System.out.println("Current Browser Selection: " + browserName);
        switch (browserName.toLowerCase()) {
            case CHROME:
                SetPropertyBrowser(PROPERTY_CHROME, DRIVER_CHROME);
                driver = new ChromeDriver(CapabilityGenerator.getChromeCapability());
                break;
            case FIREFOX:
                SetPropertyBrowser(PROPERTY_FIREFOX, DRIVER_FIREFOX);
                driver = new FirefoxDriver(CapabilityGenerator.getFirefoxCapability());
                break;
            case IE:
                SetPropertyBrowser(PROPERTY_IE, DRIVER_IE);
                driver = new InternetExplorerDriver(CapabilityGenerator.getIECapability());
                break;
            default:
                SetPropertyBrowser(PROPERTY_CHROME, DRIVER_CHROME);
                driver = new ChromeDriver(CapabilityGenerator.getChromeCapability());

        }
    }

    @Override
    protected String formatLogMsg(String message) {
        return message;
    }
}
