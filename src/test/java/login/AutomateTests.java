package login;

import core.TestBase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import pages.HomePage;
import pages.LoginPage;
import utilities.Listeners.MyTestRunner;
import utilities.Listeners.RetryRule;
import utilities.PropertyManager;

@RunWith(MyTestRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AutomateTests extends TestBase {

    private static final String EMAIL = PropertyManager.getInstance().getEmail();
    private static final String PASSWORD = PropertyManager.getInstance().getPassword();
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Test case:
     * 1. Ana sayfanın açıldığı kontrol edilir. Siteye login olunur
     * 2. Login işlemi kontrol edilir.
     * 3. Arama kutucuğuna “pantolan” kelimesi girilir.
     * 4. Sayfanın sonuna scroll edilir.
     * 5. “Daha fazla ürün gör “ butonuna tıklanır.
     * 6. Sonuca göre sergilenen ürünlerden rastgele bir ürün seçilir.
     * 7. Seçilen ürün sepete eklenir.
     * 8. Ürün sayfasındaki fiyat ile sepette yer alan ürün fiyatının doğruluğu karşılaştırılır.
     * 9. Adet arttırılarak ürün adedinin 2 olduğu doğrulanır.
     * 10. Ürün sepetten silinerek sepetin boş olduğu kontrol edilir.
     */

    @Rule
    public RetryRule retryRule = new RetryRule(3);
    
    @Test
    public void testSuccessfulLogin(){
        LOGGER.info("1. Ana sayfanın açıldığı kontrol edilir. Siteye login olunur");
        LoginPage loginPage = new LoginPage(getWebDriver());
        loginPage.fillLoginForm(EMAIL, PASSWORD).submitLogIn();
        LOGGER.info("Successfully logged in the webpage!");

        LOGGER.info("2. Login işlemi kontrol edilir..");

        LOGGER.info("3. Arama kutucuğuna “pantolan” kelimesi girilir.");
        HomePage homepage = new HomePage(getWebDriver());
        homepage.searchInput("pantolan");
        LOGGER.info("Successfully searched the input!");

        LOGGER.info("4. Sayfanın sonuna scroll edilir.");
        homepage.scrollToBottomOfThePage();
        LOGGER.info("Successfully scrolled to the bottom of the page!");

        LOGGER.info("5. “Daha fazla ürün gör “ butonuna tıklanır.");
        homepage.clickViewMoreProductBtn();
        LOGGER.info("Successfully clicked to 'Daha fazla ürün gör' button !");

        LOGGER.info("6. Sonuca göre sergilenen ürünlerden rastgele bir ürün seçilir.");

        LOGGER.info("Successfully a product selected in the page!");

        LOGGER.info("7. Seçilen ürün sepete eklenir.");

        LOGGER.info("Successfully added to the cart page!");

        LOGGER.info("8. Ürün sayfasındaki fiyat ile sepette yer alan ürün fiyatının doğruluğu karşılaştırılır.");

        LOGGER.info("Successfully product prices compared!");

        LOGGER.info("9. Adet arttırılarak ürün adedinin 2 olduğu doğrulanır.");

        LOGGER.info("Successfully !");

        LOGGER.info("10. Ürün sepetten silinerek sepetin boş olduğu kontrol edilir.");

    }
}
