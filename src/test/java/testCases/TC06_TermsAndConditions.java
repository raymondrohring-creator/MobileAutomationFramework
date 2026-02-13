package testCases;

import org.testng.annotations.Test;
import org.testng.Assert;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import pageObjects.CartPage;
import pageObjects.HomePage;
import pageObjects.ProductsPage;
import testBase.BaseClass;
import utilities.RetryAnalyzer;

public class TC06_TermsAndConditions extends BaseClass {

    private static final Logger logger =
            LogManager.getLogger(TC06_TermsAndConditions.class);

    String productName = "Air Jordan 9 Retro";

    @Test(
        groups = {"regression"},
        retryAnalyzer = RetryAnalyzer.class
    )
    public void TermsAndConditionsTest() {

        logger.info("========== TC06_TermsAndConditions STARTED ==========");
        logger.debug("Product selected for validation: {}", productName);

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

            logger.info("Performing long click on Terms button");
            cp.longClickTermsButton();

            logger.debug("Fetching alert title");
            String title = cp.getAlertTitle();

            logger.debug("Alert Title captured: {}", title);

            logger.info("Validating alert title");
            Assert.assertEquals(title, "Terms Of Conditions");

            logger.info("Assertion Passed: Terms Of Conditions title verified successfully");

        } catch (AssertionError ae) {

            logger.error("Assertion Failed in TermsAndConditionsTest", ae);

            try {
                String screenshotPath =
                        captureScreen("TermsAndConditionsTest");
                logger.info("Screenshot captured at: {}", screenshotPath);
            } catch (Exception e) {
                logger.error("Failed to capture screenshot after assertion failure", e);
            }

            throw ae; // Required for RetryAnalyzer

        } catch (Exception e) {

            logger.error("Unexpected exception occurred in TermsAndConditionsTest", e);

            try {
                String screenshotPath =
                        captureScreen("TermsAndConditionsTest_Exception");
                logger.info("Screenshot captured at: {}", screenshotPath);
            } catch (Exception ex) {
                logger.error("Failed to capture screenshot after exception", ex);
            }

            throw new RuntimeException(e);
        }

        logger.info("========== TC06_TermsAndConditions COMPLETED ==========");
    }
}
