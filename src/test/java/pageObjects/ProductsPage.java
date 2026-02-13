package pageObjects;

import java.util.List;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

public class ProductsPage extends BasePage {
	// Constructor

	public ProductsPage(AndroidDriver driver) {
		super(driver);
	}

	// Locators

	@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"com.androidsample.generalstore:id/toolbar_title\")")
	WebElement productsPageTitle;

	@AndroidFindBy(id = "com.androidsample.generalstore:id/appbar_btn_cart")
	WebElement btnCart;

	@AndroidFindBy(id = "com.androidsample.generalstore:id/productName")
	List<WebElement> productNames;

	@AndroidFindBy(id = "com.androidsample.generalstore:id/productAddCart")
	List<WebElement> productAddCart;

	// Actions

	public String getProductsPageTitle() {
		return productsPageTitle.getText();
	}

	public void clickBtnCart() {
		btnCart.click();
	}

	public void clickProductAddCart(String productToSelect) {
		int productOnScreenCount = productNames.size();

		for (int i = 0; i < productOnScreenCount; i++)
		{
			String productName = productNames.get(i).getText();
			if (productName.equalsIgnoreCase(productToSelect))
			{
				productAddCart.get(i).click();
				break; // Stop after clicking once
			}
		}
	}

}
