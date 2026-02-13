package testCases;

import org.testng.annotations.Test;
import org.testng.Assert;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import pageObjects.HomePage;
import pageObjects.ProductsPage;
import testBase.BaseClass;
import utilities.RetryAnalyzer;

public class TC03_LetsShop extends BaseClass {

    private static final Logger logger =
            LogManager.getLogger(TC03_LetsShop.class);

    @Test(
        groups = {"sanity", "regression"},
        retryAnalyzer = RetryAnalyzer.class
    )
    public void LetsShopTest() {

        logger.info("========== TC03_LetsShop STARTED ==========");

        try {

            logger.debug("Initializing HomePage object");
            HomePage hp = new HomePage(getDriver());

            logger.debug("Entering name: Ray");
            hp.enterName("Ray");

            logger.debug("Selecting gender: male");
            hp.selectRadioButton("male");

            logger.debug("Clicking 'Let's Shop'");
            hp.clickLetsShop();

            logger.debug("Initializing ProductsPage object");
            ProductsPage pp = new ProductsPage(getDriver());

            logger.debug("Fetching Products Page Title");
            String products = pp.getProductsPageTitle();

            logger.debug("Actual Products Page Title: {}", products);

            logger.info("Validating Products page title");
            Assert.assertEquals(products, "Products");

            logger.info("Assertion Passed: Successfully navigated to Products page");

        } catch (AssertionError ae) {

            logger.error("Assertion Failed in LetsShopTest", ae);

            try {
                String screenshotPath = captureScreen("LetsShopTest");
                logger.info("Screenshot captured at: {}", screenshotPath);
            } catch (Exception e) {
                logger.error("Failed to capture screenshot after assertion failure", e);
            }

            throw ae; // Required for RetryAnalyzer

        } catch (Exception e) {

            logger.error("Unexpected exception occurred in LetsShopTest", e);

            try {
                String screenshotPath = captureScreen("LetsShopTest_Exception");
                logger.info("Screenshot captured at: {}", screenshotPath);
            } catch (Exception ex) {
                logger.error("Failed to capture screenshot after exception", ex);
            }

            throw new RuntimeException(e);
        }

        logger.info("========== TC03_LetsShop COMPLETED ==========");
    }
}
