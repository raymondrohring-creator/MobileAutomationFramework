package testCases;

import org.testng.annotations.Test;
import org.testng.Assert;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import pageObjects.HomePage;
import testBase.BaseClass;
import utilities.RetryAnalyzer;

public class TC01_LaunchApp extends BaseClass {

    private static final Logger logger =
            LogManager.getLogger(TC01_LaunchApp.class);

    @Test(
        groups = {"sanity", "regression"},
        retryAnalyzer = RetryAnalyzer.class
    )
    public void LaunchAppTest() {

        logger.info("========== TC01_LaunchApp STARTED ==========");

        try {
            logger.debug("Initializing HomePage object");
            HomePage hp = new HomePage(getDriver());

            logger.debug("Fetching default text from Name field");
            String homePage = hp.verifyNameFieldDefaultText();

            logger.debug("Actual Text Retrieved: {}", homePage);

            logger.info("Validating default name field text");
            Assert.assertEquals(homePage, "Enter name here");

            logger.info("Assertion Passed: Default text is correct");

        } catch (AssertionError ae) {

            logger.error("Assertion Failed in LaunchAppTest", ae);

            try {
                String screenshotPath = captureScreen("LaunchAppTest");
                logger.info("Screenshot captured at: {}", screenshotPath);
            } catch (Exception e) {
                logger.error("Failed to capture screenshot after assertion failure", e);
            }

            throw ae; // Important: rethrow so TestNG marks test failed

        } catch (Exception e) {

            logger.error("Unexpected exception occurred in LaunchAppTest", e);

            try {
                String screenshotPath = captureScreen("LaunchAppTest_Exception");
                logger.info("Screenshot captured at: {}", screenshotPath);
            } catch (Exception ex) {
                logger.error("Failed to capture screenshot after exception", ex);
            }

            throw new RuntimeException(e);
        }

        logger.info("========== TC01_LaunchApp COMPLETED ==========");
    }
}
