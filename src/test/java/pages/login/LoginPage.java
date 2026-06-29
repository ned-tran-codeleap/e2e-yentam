package pages.login;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;

public class LoginPage {

    private final AppiumDriver driver;

    public LoginPage(AppiumDriver driver) {
        this.driver = driver;
    }

    public void enterEmail(String email) {
        driver.findElement(AppiumBy.accessibilityId("signin-text-input"))
                .sendKeys(email);
    }

    public void tapContinue() {
        driver.findElement(AppiumBy.accessibilityId("sign-in-button")).click();
    }

    public void enterPassword(String password) {
        driver.findElement(AppiumBy.accessibilityId("signin-password-text-input")).sendKeys(password);
    }

    public void tapSignIn() {
        driver.findElement(AppiumBy.accessibilityId("signin-password-continue")).click();
    }
}