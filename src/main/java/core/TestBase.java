package core;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import utilities.OptionsManager;
import utilities.PropertyManager;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

public class TestBase {

    private final String baseUrl = PropertyManager.getInstance().getbaseUrl();

    protected static ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();
    protected static ThreadLocal<String> sessionId = new ThreadLocal<>();

    public enum RunType {LOCAL, REMOTE}

    /**
     * @return the {@link WebDriver} for the current thread
     */
    public WebDriver getWebDriver() {
        return webDriver.get();
    }

    private void createRemoteDriver(MutableCapabilities capabilities, String methodName) {
        MutableCapabilities remoteOptions = new MutableCapabilities();
        remoteOptions.setCapability("name", methodName);
        capabilities.setCapability("remote:options", remoteOptions);

        String REMOTE_URL = "remote_IP";
        try {
            webDriver.set(new RemoteWebDriver(new URL(REMOTE_URL), capabilities));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        sessionId.set(((RemoteWebDriver) getWebDriver()).getSessionId().toString());
        ((RemoteWebDriver) getWebDriver()).setFileDetector(new LocalFileDetector());
    }

    private void createLocalDriver(MutableCapabilities capabilities) {
        WebDriverManager.chromedriver().setup();
        webDriver.set(new ChromeDriver((ChromeOptions) capabilities));
    }

    @Before
    public void createDriver(Method method) {
        String methodName = method.getName();
        String browserName = System.getProperty("browserName") == null ? "chrome" : System.getProperty("browserName");
        RunType runType = ((System.getProperty("runType") != null) && System.getProperty("runType").equals("remote"))
                ? RunType.REMOTE : RunType.LOCAL;

        MutableCapabilities capabilities = new MutableCapabilities();

        if (browserName.equals("chrome")) {
            capabilities = OptionsManager.getChromeOptions();
        } else if (browserName.equals("firefox")) {
            capabilities = OptionsManager.getFirefoxOptions();
        }

        switch (runType) {
            case LOCAL:
                createLocalDriver(capabilities);
                break;
            case REMOTE:
                capabilities = OptionsManager.getChromeOptions();
                createRemoteDriver(capabilities, methodName);
                break;
        }
        getWebDriver().get(baseUrl);
    }

    @After
    public void tearDown() {
        getWebDriver().quit();
    }

    @AfterClass
    public static void terminate() {
        webDriver.remove();
    }
}
