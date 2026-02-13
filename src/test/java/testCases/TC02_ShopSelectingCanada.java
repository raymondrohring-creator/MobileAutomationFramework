package testCases;

import org.testng.annotations.Test;
import org.testng.Assert;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import pageObjects.HomePage;
import pageObjects.ProductsPage;
import testBase.BaseClass;
import utilities.RetryAnalyzer;

public class TC02_ShopSelectingCanada extends BaseClass {

    private static final Logger logger =
            LogManager.getLogger(TC02_ShopSelectingCanada.class);

    @Test(
        groups = {"regression"},
        retryAnalyzer = RetryAnalyzer.class
    )
    public void ShopSelectingCanadaTest() {

        logger.info("========== TC02_ShopSelectingCanada STARTED ==========");

        try {

            logger.debug("Initializing HomePage object");
            HomePage hp = new HomePage(getDriver());

            logger.debug("Clicking Country Dropdown");
            hp.clickCountryDropdown();

            logger.debug("Scrolling and selecting country: Canada");
            hp.scrollTextIntoViewAndClick("Canada");

            logger.debug("Entering name: Rae");
            hp.enterName("Rae");

            logger.debug("Selecting gender: female");
            hp.selectRadioButton("female");

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

            logger.error("Assertion Failed in ShopSelectingCanadaTest", ae);

            try {
                String screenshotPath =
                        captureScreen("ShopSelectingCanadaTest");
                logger.info("Screenshot captured at: {}", screenshotPath);
            } catch (Exception e) {
                logger.error("Failed to capture screenshot after assertion failure", e);
            }

            throw ae; // Important for RetryAnalyzer

        } catch (Exception e) {

            logger.error("Unexpected exception occurred in ShopSelectingCanadaTest", e);

            try {
                String screenshotPath =
                        captureScreen("ShopSelectingCanadaTest_Exception");
                logger.info("Screenshot captured at: {}", screenshotPath);
            } catch (Exception ex) {
                logger.error("Failed to capture screenshot after exception", ex);
            }

            throw new RuntimeException(e);
        }

        logger.info("========== TC02_ShopSelectingCanada COMPLETED ==========");
    }
}
