package pageObjects;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.remote.SupportsContextSwitching;
import java.time.Duration;
import java.util.Set;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebDriver;

public class BasePage {
	// Constructor

	AndroidDriver driver; // This is the constructor for the BasePage class. It takes a AndroidDriver object as an argument, which is used to interact with the browser.

	public BasePage(AndroidDriver driver) {
		this.driver = driver; // The passed driver is assigned to the instance variable driver. This allows the class and its subclasses to use it for browser interactions.
		PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);

		// PageFactory.initElements(driver, this);
		// The above line initializes the web elements defined in the class using Selenium's PageFactory.
		// PageFactory.initElements() tells Selenium to scan the current class (this) for any @FindBy annotations and connect them to actual elements on the page using the provided driver.
	}

	// Locators

	// Actions

	// Generic scrollTextIntoView
	public void scrollTextIntoView(String country) {
		driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true))" + ".scrollTextIntoView(\"" + country + "\")"));
	}

	// Generic scrollTextIntoViewAndClick
	public void scrollTextIntoViewAndClick(String country) throws InterruptedException {
		driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true))" + ".scrollTextIntoView(\"" + country + "\")"));
		new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.presenceOfElementLocated(AppiumBy.androidUIAutomator("new UiSelector().text(\"" + country + "\")")));
		driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"" + country + "\")")).click();
	}

	// Press Android BACK button
	public void pressBack() {
		driver.pressKey(new KeyEvent(AndroidKey.BACK));
	}

	
	// switchContext (NATIVE_APP / WEBVIEW)
	public void switchContext(String context) {
		driver.context(context);
		}
	 

	// switchContext (NATIVE_APP / WEBVIEW)
	public static void switchContexts(WebDriver driver, Set<String> contexts, String switchToContext) {
		if (!(driver instanceof SupportsContextSwitching)) {
			throw new IllegalArgumentException("Driver does not support context switching: " + driver.getClass());
		}
		SupportsContextSwitching ctxDriver = (SupportsContextSwitching) driver;
		for (String context : contexts) {
			if (context.contains(switchToContext)) {
				ctxDriver.context(context);
				return;
			}
		}
		throw new IllegalStateException("No matching context found containing: " + switchToContext);
	}

	public static void waitForContext(WebDriver driver, String contextToWaitFor) {
		if (!(driver instanceof SupportsContextSwitching)) {
			throw new IllegalArgumentException("Driver does not support context switching: " + driver.getClass());
		}
		SupportsContextSwitching ctxDriver = (SupportsContextSwitching) driver;
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver d) {
				Set<String> contexts = ctxDriver.getContextHandles();
				return contexts.contains(contextToWaitFor);
			}

			@Override
			public String toString() {
				return "Context '" + contextToWaitFor + "' to become available.";
			}
		});
	}

}
