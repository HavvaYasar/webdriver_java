package core;

import com.google.common.base.Stopwatch;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class BaseFunctions {

    public final WebDriver driver;
    public WebDriverWait pageWait;

    public BaseFunctions(WebDriver driver) {
        this.driver = driver;
        pageWait = new WebDriverWait(this.driver, 35);
    }

    /**
     * Wait Wrapper Method by given locator
     *
     * @param locator locator of the element
     * @return web element
     */
    public WebElement waitVisibility(By locator) {
        return pageWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * click element by given locator
     *
     * @param locator locator of the element
     */
    public void click(By locator) {
        waitVisibility(locator);
        driver.findElement(locator).click();
    }

    /**
     * clear element by given locator
     *
     * @param locator locator of the element
     */
    public void clear(By locator) {
        waitVisibility(locator);
        driver.findElement(locator).clear();
    }

    /**
     * write text on element by given locator
     *
     * @param locator locator of the element
     */
    public void writeText(By locator, String text) {
        waitVisibility(locator);
        driver.findElement(locator).sendKeys(text);
    }

    /**
     * read text of element by given locator
     *
     * @param locator locator of the element
     */
    public String readText(By locator) {
        waitVisibility(locator);
        return driver.findElement(locator).getText();
    }

    /**
     * Find element by given locator
     *
     * @param locator locator of the element
     */
    public WebElement find(By locator) {
        if (isElementPresent(locator)) {
            return driver.findElement(locator);
        } else throw new NoSuchElementException(String.format("Cannot find an element using by %s", locator));
    }

    /**
     * Check if given element's locator is visible during default wait period or not
     *
     * @param by locator of the input
     */
    public boolean isElementVisible(By by) {
        for (int i = 0; i < 3; i++) {
            try {
                WebElement elemWithDriver = find(by);
                return elemWithDriver.isDisplayed();
            } catch (StaleElementReferenceException se) {
            } catch (UnhandledAlertException uae) {
                throw uae;
            } catch (WebDriverException e) {
                return false;
            }
        }
        return false;
    }

    /**
     * Check if given element's locator is present during default wait period or not
     *
     * @param locator locator of the input
     */
    public boolean isElementPresent(By locator) {
        List<WebElement> elements = driver.findElements(locator);
        return elements.size() != 0;
    }

    /**
     * Scroll page to the needed element
     */
    public void scrollToBottomOfThePage() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0,document.body.scrollHeight);");
    }

    /**
     * Delay a certain number of seconds, rather than to respond as soon as possible
     *
     * @param milliseconds time of pause in milliseconds
     */
    public void pause(Integer milliseconds) {
        try {
            TimeUnit.MILLISECONDS.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Find elements list by given locator
     *
     * @param locator locator of the element
     */
    public List<WebElement> findElements(By locator) {
        final Stopwatch timer = Stopwatch.createStarted();
        try {
            pause(500);
            return driver.findElements(locator);
        } finally {
            timer.stop();
        }
    }

    /**
     * Hover to the given element
     */
    public void hoverTo(By locator) {
        Actions actions = new Actions(driver);
        WebElement element = waitVisibility(locator);
        actions.moveToElement(element).build().perform();
    }
}
