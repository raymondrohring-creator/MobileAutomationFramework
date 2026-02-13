package utilities;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RetryAnalyzer implements IRetryAnalyzer {

    private static final Logger logger =
            LogManager.getLogger(RetryAnalyzer.class);

    private int retryCount = 0;
    private static final int maxRetryCount = 2;

    @Override
    public boolean retry(ITestResult result) {

        if (retryCount < maxRetryCount) {
            retryCount++;
            logger.warn("Retrying test: {} | Attempt: {}",
                    result.getName(),
                    retryCount);
            return true;
        }

        logger.error("Max retry attempts reached for test: {}",
                result.getName());

        return false;
    }
}

