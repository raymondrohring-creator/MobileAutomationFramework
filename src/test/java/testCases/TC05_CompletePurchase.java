package testCases;

import java.util.Set;
import org.testng.annotations.Test;
import org.testng.Assert;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import pageObjects.CartPage;
import pageObjects.HomePage;
import pageObjects.ProductsPage;
import pageObjects.WebviewPage;
import testBase.BaseClass;
import utilities.RetryAnalyzer;

public class TC05_CompletePurchase extends BaseClass {

    private static final Logger logger =
            LogManager.getLogger(TC05_CompletePurchase.class);

    String productName = "Jordan 6 Rings";

    @Test(
        groups = {"sanity", "regression"},
        retryAnalyzer = RetryAnalyzer.class
    )
    public void CompletePurchaseTest() {

        logger.info("========== TC05_CompletePurchase STARTED ==========");
        logger.debug("Product selected for purchase: {}", productName);

        try {

            logger.debug("Initializing HomePage object");
            HomePage hp = new HomePage(getDriver());

            logger.debug("Entering name: Ray");
            hp.enterName("Ray");

            logger.debug("Clicking 'Let's Shop'");
            hp.clickLetsShop();

            logger.debug("Initializing ProductsPage object");
            ProductsPage pp = new ProductsPage(getDriver());

            logger.debug("Scrolling to product: {}", productName);
            pp.scrollTextIntoView(productName);

            logger.debug("Adding product to cart: {}", productName);
            pp.clickProductAddCart(productName);

            logger.debug("Navigating to Cart page");
            pp.clickBtnCart();

            logger.debug("Initializing CartPage object");
            CartPage cp = new CartPage(getDriver());

            logger.debug("Clicking Terms & Conditions checkbox");
            cp.clickTCCheckBox();

            logger.debug("Clicking Complete Purchase (Navigating to WEBVIEW)");
            cp.clickVisitToWeb();

            Thread.sleep(2000);

            logger.debug("Initializing WebviewPage object");
            WebviewPage wp = new WebviewPage(getDriver());

            // =========================
            // Context Handling
            // =========================
            logger.info("Fetching available contexts");
            Set<String> contexts = getDriver().getContextHandles();

            for (String contextName : contexts) {
                logger.debug("Available Context: {}", contextName);
            }

            logger.info("Searching term in WebView");
            wp.searchForTerm(getDriver(), "CloudBerry QA Bootcamp");

            logger.info("Pressing back to exit WebView");
            wp.pressBack();

            logger.info("Switching back to NATIVE_APP context");
            wp.switchToNativeContext(getDriver());

            logger.debug("Fetching Home Page title after returning to Native context");
            String homePageTitle = hp.getPageTitle();

            logger.debug("Home Page Title: {}", homePageTitle);

            logger.info("Validating Home Page Title");
            Assert.assertEquals(homePageTitle, "General Store");

            logger.info("Assertion Passed: Successfully completed purchase flow");

        } catch (AssertionError ae) {

            logger.error("Assertion Failed in CompletePurchaseTest", ae);

            try {
                String screenshotPath =
                        captureScreen("CompletePurchaseTest");
                logger.info("Screenshot captured at: {}", screenshotPath);
            } catch (Exception e) {
                logger.error("Failed to capture screenshot after assertion failure", e);
            }

            throw ae; // Required for RetryAnalyzer

        } catch (Exception e) {

            logger.error("Unexpected exception occurred in CompletePurchaseTest", e);

            try {
                String screenshotPath =
                        captureScreen("CompletePurchaseTest_Exception");
                logger.info("Screenshot captured at: {}", screenshotPath);
            } catch (Exception ex) {
                logger.error("Failed to capture screenshot after exception", ex);
            }

            throw new RuntimeException(e);
        }

        logger.info("========== TC05_CompletePurchase COMPLETED ==========");
    }
}
