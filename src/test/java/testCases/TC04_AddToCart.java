package testCases;

import org.testng.annotations.Test;
import org.testng.Assert;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import pageObjects.CartPage;
import pageObjects.HomePage;
import pageObjects.ProductsPage;
import testBase.BaseClass;
import utilities.DataProviders;
import utilities.RetryAnalyzer;

public class TC04_AddToCart extends BaseClass {

    private static final Logger logger =
            LogManager.getLogger(TC04_AddToCart.class);

    @Test(
        groups = {"sanity", "regression", "datadriven"},
		dataProvider = "LoginData",
        dataProviderClass = DataProviders.class,
        retryAnalyzer = RetryAnalyzer.class
    )
    public void AddToCartTest(String country, String name, String gender, String productName) {

        logger.info("========== TC04_AddToCart STARTED ==========");
        logger.debug("Product to be added to cart: {}", productName);

        try {

            logger.debug("Initializing HomePage object");
            HomePage hp = new HomePage(getDriver());
            
            logger.debug("Clicking Country Dropdown");
            hp.clickCountryDropdown();

            logger.debug("Scrolling and selecting country: {}", country);
            hp.scrollTextIntoViewAndClick(country);

            logger.debug("Entering name: {}", name);
            hp.enterName(name);

            logger.debug("Selecting gender: {}", gender);
            hp.selectRadioButton(gender);

            logger.debug("Clicking 'Let's Shop'");
            hp.clickLetsShop();

            logger.debug("Initializing ProductsPage object");
            ProductsPage pp = new ProductsPage(getDriver());

            logger.debug("Scrolling to product: {}", productName);
            pp.scrollTextIntoView(productName);

            logger.debug("Clicking 'Add to Cart' for product: {}", productName);
            pp.clickProductAddCart(productName);

            logger.debug("Clicking Cart button");
            pp.clickBtnCart();

            logger.debug("Initializing CartPage object");
            CartPage cp = new CartPage(getDriver());

            logger.debug("Fetching product name from Cart page");
            String cartPageProduct = cp.getAddedProductName();

            logger.debug("Product found in cart: {}", cartPageProduct);

            logger.info("Validating product added to cart");
            Assert.assertEquals(cartPageProduct, productName);

            logger.info("Assertion Passed: Correct product added to cart");

        } catch (AssertionError ae) {

            logger.error("Assertion Failed in AddToCartTest", ae);

            try {
                String screenshotPath = captureScreen("AddToCartTest");
                logger.info("Screenshot captured at: {}", screenshotPath);
            } catch (Exception e) {
                logger.error("Failed to capture screenshot after assertion failure", e);
            }

            throw ae; // Required for RetryAnalyzer

        } catch (Exception e) {

            logger.error("Unexpected exception occurred in AddToCartTest", e);

            try {
                String screenshotPath = captureScreen("AddToCartTest_Exception");
                logger.info("Screenshot captured at: {}", screenshotPath);
            } catch (Exception ex) {
                logger.error("Failed to capture screenshot after exception", ex);
            }

            throw new RuntimeException(e);
        }

        logger.info("========== TC04_AddToCart COMPLETED ==========");
    }
}
