package core;

import org.openqa.selenium.WebDriver;

public abstract class PageBase extends BaseFunctions {

    public PageBase(WebDriver driver) {
        super(driver);
        check();
    }

    protected abstract void check();
}
