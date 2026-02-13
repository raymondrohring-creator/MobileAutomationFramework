package pageObjects;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.WebElement;

public class CartPage extends BasePage {
	// Constructor

	public CartPage(AndroidDriver driver) {
		super(driver);
	}

	// Locators

	@AndroidFindBy(id = "com.androidsample.generalstore:id/productName")
	WebElement productName;

	@AndroidFindBy(className = "android.widget.CheckBox")
	WebElement checkBox;

	@AndroidFindBy(id = "com.androidsample.generalstore:id/btnProceed")
	WebElement btnProceed;

	@AndroidFindBy(id = "com.androidsample.generalstore:id/termsButton")
	WebElement termsButton;

	@AndroidFindBy(id = "com.androidsample.generalstore:id/alertTitle")
	WebElement alertTitle;

	// Actions

	public String getAddedProductName() throws InterruptedException {
		Thread.sleep(1000);
		return productName.getText();
	}

	public void clickTCCheckBox() {
		checkBox.click();
	}

	public void clickVisitToWeb() throws InterruptedException {
		btnProceed.click();
		Thread.sleep(1000);
	}

	public void longClickTermsButton() {
		((JavascriptExecutor) driver).executeScript("mobile: longClickGesture", ImmutableMap.of("elementId", ((RemoteWebElement) termsButton).getId()));
	}

	public String getAlertTitle() {
		return alertTitle.getText();
	}

}
