package testCases;

import org.testng.annotations.Test;
import org.testng.Assert;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import pageObjects.HomePage;
import testBase.BaseClass;
import utilities.RetryAnalyzer;

public class TC07_ValidateToastMessage extends BaseClass {

	private static final Logger logger =
            LogManager.getLogger(TC07_ValidateToastMessage.class);

	@Test(
		groups = {"regression"},
		retryAnalyzer = RetryAnalyzer.class
	)
	public void ValidateToastMessageTest() {

		logger.info("========== TC07_ValidateToastMessage STARTED ==========");

		try {

			logger.debug("Initializing HomePage object");
            HomePage hp = new HomePage(getDriver());

            logger.debug("Clicking 'Let's Shop'");
            hp.clickLetsShop();
            
            logger.debug("Fetching Toast Msg");
            String msg = hp.getToastMsg();
            
            logger.debug("Actual Toast Msg: {}", msg);
            
            logger.info("Validating Toast Msg");
            Assert.assertEquals(msg, "Please enter your name");

            logger.info("Assertion Passed: Toast Msg verified successfully");

		} catch (AssertionError ae) {

            logger.error("Assertion Failed in TermsAndConditionsTest", ae);

            try {
                String screenshotPath =
                        captureScreen("ValidateToastMessageTest");
                logger.info("Screenshot captured at: {}", screenshotPath);
            } catch (Exception e) {
                logger.error("Failed to capture screenshot after assertion failure", e);
            }

            throw ae; // Required for RetryAnalyzer

        } catch (Exception e) {

            logger.error("Unexpected exception occurred in ValidateToastMessageTest", e);

            try {
                String screenshotPath =
                        captureScreen("ValidateToastMessageTest_Exception");
                logger.info("Screenshot captured at: {}", screenshotPath);
            } catch (Exception ex) {
                logger.error("Failed to capture screenshot after exception", ex);
            }

            throw new RuntimeException(e);
        }

        logger.info("========== TC07_ValidateToastMessage COMPLETED ==========");
	}
}
