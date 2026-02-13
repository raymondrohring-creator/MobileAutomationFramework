package pageObjects;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.SupportsContextSwitching;
import java.util.Set;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class WebviewPage extends BasePage {
	// Constructor

	public WebviewPage(AndroidDriver driver) {
		super(driver);
	}

	// Locators

	@FindBy(name = "q")
	WebElement searchField;

	// Actions

	public void searchText(String textToSearch) {
		 searchField.sendKeys();
		 searchField.sendKeys(Keys.ENTER);
	}

	public void switchToWebviewContext(WebDriver driver) {
		BasePage.waitForContext(driver, "WEBVIEW_com.androidsample.generalstore");

		SupportsContextSwitching ctxDriver = (SupportsContextSwitching) driver;
		Set<String> contexts = ctxDriver.getContextHandles();

		BasePage.switchContexts(driver, contexts, "WEBVIEW");
	}

	public void switchToNativeContext(WebDriver driver) {
		SupportsContextSwitching ctxDriver = (SupportsContextSwitching) driver;
		Set<String> contexts = ctxDriver.getContextHandles();

		BasePage.waitForContext(driver, "NATIVE_APP");
		BasePage.switchContexts(driver, contexts, "NATIVE_APP");
	}

	public void searchForTerm(WebDriver driver, String searchTerm) throws InterruptedException {
		switchToWebviewContext(driver);

        searchField.click();

        searchField.sendKeys(searchTerm);

        searchField.sendKeys(Keys.ENTER);
	}

}
