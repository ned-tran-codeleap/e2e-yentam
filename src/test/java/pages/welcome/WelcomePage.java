package pages.welcome;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;

public class WelcomePage {

    private final AppiumDriver driver;

    public WelcomePage(AppiumDriver driver) {
        this.driver = driver;
    }

    public void tapToContinue() {
        driver.findElement(AppiumBy.accessibilityId("welcome-signin-button"))
                .click();
    }
}