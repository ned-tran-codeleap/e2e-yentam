package base;

import java.net.URL;
import java.time.Duration;

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

    private static final String APPIUM_URL =
            System.getProperty("appium.url", "http://127.0.0.1:4723");

    @BeforeClass
    @Parameters("platform")
    public void setUp(String platform) throws Exception {

        if ("ios".equalsIgnoreCase(platform)) {
            driver = createIOSDriver();
        } else {
            driver = createAndroidDriver();
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    // Create Android Driver
    private AppiumDriver createAndroidDriver() throws Exception {
        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName("Android")
                .setDeviceName("emulator-5554")
                .setApp("/Users/trananhtienned/Maivita/maivita-mobile/android/app/build/outputs/apk/release/app-release.apk");

        return new AndroidDriver(new URL(APPIUM_URL), options);
    }

    // Create IOS Driver
    private AppiumDriver createIOSDriver() throws Exception {
        XCUITestOptions options = new XCUITestOptions()
                .setPlatformName("iOS")
                .setDeviceName("Small Iphone screen")
                .setPlatformVersion("26.2")
                .setApp("/Users/trananhtienned/Maivita/maivita-mobile/android/app/build/outputs/apk/release/app-release.apk");

        return new IOSDriver(new URL(APPIUM_URL), options);
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}