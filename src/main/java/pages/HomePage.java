package pages;

import core.PageBase;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends PageBase {

    private static final By LOGIN_SECTION = By.xpath("//*[@id=\"header__container\"]/div/div[2]/div/div[2]/div/div[1]/a/svg/path");
    private static final By SEARCH_INPUT = By.xpath("//*[@id=\"header__container\"]/div/div[3]/div[1]/div/button");
    private static final By VIEW_MORE_PRODUCT_BTN = By.xpath("//*[@id=\"divModels\"]/div[7]/div/div[4]/a/p");
    private static final By PRODUCT = By.xpath("");


    public HomePage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected void check() {
        Assert.assertTrue("Log in section isn't visible!",
                pageWait.until(ExpectedConditions.visibilityOfElementLocated(LOGIN_SECTION)).isDisplayed());
    }

    /**
     * Searches given text in the search bar
     *
     * @param text variable that you want to search
     * @return TestDevicesPage
     */
    public HomePage searchInput(String text) {
        clear(SEARCH_INPUT);
        pause(300);
        char[] segmentToChar = text.toCharArray();
        for (int i = 0; i < text.length(); i++) {
            find(SEARCH_INPUT).sendKeys(String.valueOf(segmentToChar[i]));
            pause(200);
        }
        pause(1000);
        return this;
    }

    /**
     * Clicks view more product button
     */
    public void clickViewMoreProductBtn() {
        click(VIEW_MORE_PRODUCT_BTN);
        pageWait.until(ExpectedConditions.presenceOfElementLocated(PRODUCT));
    }
}