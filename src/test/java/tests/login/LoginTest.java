package tests.login;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import io.appium.java_client.AppiumBy;
import pages.login.LoginPage;
import pages.welcome.WelcomePage;

public class LoginTest extends BaseTest {

    @Test
    public void testSuccessfulLogin() {
        new WelcomePage(driver).tapToContinue();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail("yourEmail");
        loginPage.tapContinue();
        loginPage.enterPassword("yourPassword");
        loginPage.tapSignIn();

        // TODO: Add assertions
        Assert.assertTrue(driver.findElement(AppiumBy.accessibilityId("dashboard")).isDisplayed());
    }
}