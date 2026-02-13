package pageObjects;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import java.time.Duration;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebElement;

public class HomePage extends BasePage {
	// Constructor
	// HomePage is a subclass of BasePage. When a HomePage object is created, it needs a AndroidDriver instance to work with.
	// super(driver); is a call to the constructor of the parent class (BasePage), passing along the WebDriver.

	public HomePage(AndroidDriver driver) {
		super(driver);
	}

	// Locators

	@AndroidFindBy(xpath = "//android.widget.TextView[@resource-id=\"com.androidsample.generalstore:id/toolbar_title\"]")
	WebElement pageTitle;

	@AndroidFindBy(id = "com.androidsample.generalstore:id/spinnerCountry")
	WebElement countryDropdown;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text=\\\"Your Name\\\"]")
	WebElement yourName;

	@AndroidFindBy(id = "com.androidsample.generalstore:id/nameField")
	WebElement nameField;

	@AndroidFindBy(id = "com.androidsample.generalstore:id/radioMale")
	WebElement maleRadioButton;

	@AndroidFindBy(id = "com.androidsample.generalstore:id/radioFemale")
	WebElement femaleRadioButton;

	@AndroidFindBy(id = "com.androidsample.generalstore:id/btnLetsShop")
	WebElement btnLetsShop;

	@AndroidFindBy(xpath = "(//android.widget.Toast)[1]")
	WebElement getToastMsg;

	// Actions
	
	public void clickCountryDropdown() {
		countryDropdown.click();
	}

	public String getPageTitle() {
		String title = pageTitle.getText();
		return title;
	}

	public String verifyYourName() {
		return yourName.getText();
	}
	public String verifyNameFieldDefaultText() {
		return nameField.getText();
	}

	public void enterName(String name) {
		nameField.sendKeys(name);
	}
	
	public void selectRadioButton(String gender) throws Exception {
		switch (gender.toLowerCase()) {
		case "male":
			maleRadioButton.click();
			break;

		case "female":
			femaleRadioButton.click();
			break;

		default:
			throw new Exception("Only male or female allowed for this selection.");
		}
	}
	
	public void clickLetsShop() {
		new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(btnLetsShop));
		btnLetsShop.click();
	}

	public String getToastMsg() {
		return getToastMsg.getAttribute("name");
	}

}
