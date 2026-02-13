package testBase;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseClass {

    private static final Logger logger = LogManager.getLogger(BaseClass.class);

    private static ThreadLocal<AndroidDriver> driver = new ThreadLocal<>();
    private static Properties config;

    // ===============================
    // CONFIG LOADER
    // ===============================
    private void loadConfig() {
        try {
            config = new Properties();

            InputStream inputStream =
                    getClass().getClassLoader()
                            .getResourceAsStream("config.properties");

            if (inputStream == null) {
                throw new RuntimeException("config.properties not found in classpath");
            }

            config.load(inputStream);
            logger.info("config.properties loaded successfully");

        } catch (Exception e) {
            logger.error("Failed to load config.properties", e);
            throw new RuntimeException(e);
        }
    }

    // ===============================
    // DRIVER INITIALIZATION
    // ===============================
    @BeforeMethod(alwaysRun = true)
    @Parameters({"deviceName", "systemPort", "os"})
    public void setUp(
            @Optional("emulator-5554") String deviceName,
            @Optional("8200") String systemPort,
            @Optional("Android") String os) {

        try {
            logger.info("========== Test Setup Started ==========");
            loadConfig();

            String serverUrl = config.getProperty("appium.server.url");

            UiAutomator2Options options = new UiAutomator2Options();

            logger.info("Running on OS: {}", os);

            if (os.equalsIgnoreCase("Android")) {

                options.setPlatformName("Android");
                options.setAutomationName("UiAutomator2");

                logger.info("Device Name: {}", deviceName);
                options.setDeviceName(deviceName);
                options.setUdid(deviceName);

                logger.info("System Port: {}", systemPort);
                options.setSystemPort(Integer.parseInt(systemPort));

                options.setAppWaitActivity("*");
                options.setAutoGrantPermissions(true);
                options.setNewCommandTimeout(Duration.ofSeconds(120));

                options.setCapability("uiautomator2ServerInstallTimeout", 120000);
                options.setCapability("uiautomator2ServerLaunchTimeout", 120000);

                // ===============================
                // App Path
                // ===============================
                String appPath = System.getProperty("user.dir")
                        + "/src/test/resources/"
                        + config.getProperty("appName");

                logger.debug("App Path: {}", appPath);
                options.setApp(appPath);

                // ===============================
                // Chromedriver (For Hybrid Apps)
                // ===============================
                if (config.getProperty("chromedriver.path") != null) {

                    String chromePath = config.getProperty("chromedriver.path");

                    logger.debug("Chromedriver Path: {}", chromePath);

                    options.setChromedriverExecutable(chromePath);
                }

            } else {
                throw new RuntimeException("Currently only Android is supported");
            }

            logger.info("Initializing AndroidDriver...");
            AndroidDriver androidDriver =
                    new AndroidDriver(new URI(serverUrl).toURL(), options);

            androidDriver.manage()
                    .timeouts()
                    .implicitlyWait(Duration.ofSeconds(10));

            driver.set(androidDriver);

            logger.info("Driver initialized successfully");

        } catch (Exception e) {
            logger.error("Driver initialization failed", e);
            throw new RuntimeException(e);
        }
    }

    // ===============================
    // THREAD-SAFE DRIVER
    // ===============================
    public static AndroidDriver getDriver() {
        return driver.get();
    }

    // ===============================
    // TEARDOWN
    // ===============================
    @AfterMethod(alwaysRun = true)
    public void tearDown() {

        try {
            logger.info("========== Test Teardown Started ==========");

            if (driver.get() != null) {
                driver.get().quit();
                driver.remove();
                logger.info("Driver quit successfully");
            } else {
                logger.warn("Driver was null during teardown");
            }

        } catch (Exception e) {
            logger.error("Error during teardown", e);
        }
    }

    // ===============================
    // SCREENSHOT UTILITY
    // ===============================
    public String captureScreen(String testName) {

        try {
            String timeStamp =
                    new SimpleDateFormat("yyyyMMddHHmmss")
                            .format(new Date());

            File sourceFile =
                    ((TakesScreenshot) getDriver())
                            .getScreenshotAs(OutputType.FILE);

            String targetFilePath =
                    System.getProperty("user.dir")
                            + "/screenshots/"
                            + testName + "_" + timeStamp + ".png";

            FileUtils.copyFile(sourceFile, new File(targetFilePath));

            logger.info("Screenshot captured: {}", targetFilePath);

            return targetFilePath;

        } catch (Exception e) {
            logger.error("Screenshot capture failed", e);
            throw new RuntimeException(e);
        }
    }
}
