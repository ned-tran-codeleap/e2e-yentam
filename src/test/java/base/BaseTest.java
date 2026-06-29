package base;

import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;

public class BaseTest {

    protected AppiumDriver driver;

    private static final String APPIUM_URL = System.getProperty("appium.url", "http://127.0.0.1:4723");
    private static final String BS_HUB = "https://hub-cloud.browserstack.com/wd/hub";

    // Important
    private static final String BS_USERNAME = System.getProperty("bs.username", "yourBrowserStackUserName");
    private static final String BS_ACCESS_KEY = System.getProperty("bs.accessKey", "yourBrowserStackAccesKey");
    private static final String BS_APP_URL = System.getProperty("bs.app", "yourBrowserStackAppUrl");

    private boolean isBrowserStack() {
        return "browserstack".equalsIgnoreCase(System.getProperty("env"));
    }

    @BeforeClass
    @Parameters("platform")
    public void setUp(String platform) throws Exception {

        if (isBrowserStack()) {
            if ("ios".equalsIgnoreCase(platform)) {
                driver = createBrowserStackIOSDriver();
            } else {
                driver = createBrowserStackAndroidDriver();
            }
        } else {
            if ("ios".equalsIgnoreCase(platform)) {
                driver = createIOSDriver();
            } else {
                driver = createAndroidDriver();
            }
            dismissDebugPopupIfPresent();
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    private void dismissDebugPopupIfPresent() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement dismissBtn = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//android.widget.Button[@text='OK' or @text='Cancel' or @text='Dismiss']")));
            dismissBtn.click();
        } catch (Exception ignored) {
            // No debug popup present, continue normally
        }
    }

    // Local drivers
    private AppiumDriver createAndroidDriver() throws Exception {
        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName("Android")
                .setDeviceName("emulator-5554")
                .setApp("/Users/trananhtienned/Maivita/maivita-mobile/android/app/build/outputs/apk/debug/app-debug.apk");

        return new AndroidDriver(new URL(APPIUM_URL), options);
    }

    private AppiumDriver createIOSDriver() throws Exception {
        XCUITestOptions options = new XCUITestOptions()
                .setPlatformName("iOS")
                .setDeviceName("Small Iphone screen")
                .setPlatformVersion("26.2")
                .setApp("/Users/trananhtienned/Maivita/maivita-mobile/android/app/build/outputs/apk/debug/app-debug.apk");

        return new IOSDriver(new URL(APPIUM_URL), options);
    }

    // BrowserStack drivers
    private AppiumDriver createBrowserStackAndroidDriver() throws Exception {
        HashMap<String, Object> bstackOptions = new HashMap<>();
        bstackOptions.put("userName", BS_USERNAME);
        bstackOptions.put("accessKey", BS_ACCESS_KEY);
        bstackOptions.put("projectName", "Maivita E2E");
        bstackOptions.put("buildName", "Android Build");
        bstackOptions.put("sessionName", "Android Test");

        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName("Android")
                .setDeviceName("Samsung Galaxy S23")
                .setPlatformVersion("13.0")
                .setApp(BS_APP_URL);
        options.setCapability("bstack:options", bstackOptions);

        return new AndroidDriver(new URL(BS_HUB), options);
    }

    private AppiumDriver createBrowserStackIOSDriver() throws Exception {
        HashMap<String, Object> bstackOptions = new HashMap<>();
        bstackOptions.put("userName", BS_USERNAME);
        bstackOptions.put("accessKey", BS_ACCESS_KEY);
        bstackOptions.put("projectName", "Maivita E2E");
        bstackOptions.put("buildName", "iOS Build");
        bstackOptions.put("sessionName", "iOS Test");

        XCUITestOptions options = new XCUITestOptions()
                .setPlatformName("iOS")
                .setDeviceName("iPhone 15")
                .setPlatformVersion("17")
                .setApp(BS_APP_URL);
        options.setCapability("bstack:options", bstackOptions);

        return new IOSDriver(new URL(BS_HUB), options);
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
