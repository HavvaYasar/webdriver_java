package pages;

import core.PageBase;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends PageBase {

    private static final By EMAIL_INPUT = By.id("inputEmailDiv");
    private static final By PASS_INPUT = By.id("Password");
    private static final By LOGIN_BTN = By.id("loginLink");
    private static final By LOGO = By.id("");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected void check() {

    }

    public LoginPage fillLoginForm(String username, String password) {
        WebElement emailField = find(EMAIL_INPUT);
        Actions actions = new Actions(driver);
        actions.click(emailField)
                .sendKeys(Keys.END)
                .keyDown(Keys.SHIFT)
                .sendKeys(Keys.HOME)
                .keyUp(Keys.SHIFT)
                .sendKeys(Keys.BACK_SPACE)
                .sendKeys(username)
                .perform();
        WebElement passField = find(PASS_INPUT);
        actions.click(passField)
                .sendKeys(Keys.END)
                .keyDown(Keys.SHIFT)
                .sendKeys(Keys.HOME)
                .keyUp(Keys.SHIFT)
                .sendKeys(Keys.BACK_SPACE)
                .sendKeys(password)
                .perform();
        return this;
    }

    /**
     * Submit log in
     */
    public void submitLogIn() {
        find(LOGIN_BTN).click();
        pause(750);
        Assert.assertTrue("Your login attempt is unsuccessful", pageWait.until(ExpectedConditions.
                        visibilityOfElementLocated(LOGO)).isDisplayed());
    }
}

