package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HeaderOptions {
	
	WebDriver driver;

	public HeaderOptions(WebDriver driver) {

		this.driver = driver;
		PageFactory.initElements(driver, this);

	}
	
	@FindBy(xpath = "//a/i[@class='fa fa-phone']")
	private WebElement phoneIconOption;

	@FindBy(xpath = "//a/i[@class='fa fa-heart']")
	private WebElement heartIconeOption;

	@FindBy(xpath = "//span[text()='Shopping Cart']")
	private WebElement shoppingCartOption;

	@FindBy(xpath = "//span[text()='Checkout']")
	private WebElement checkoutOption;
	
	@FindBy(xpath = "//div[@id='logo']//a[text()='Qafox.com']")
	private WebElement logoOption;

	@FindBy(xpath = "//button[@class='btn btn-default btn-lg']")
	private WebElement searchButton;

	@FindBy(xpath = "//a[normalize-space()='Account']")//a[normalize-space()='Account']
	private WebElement accountBreadcrumb;

	@FindBy(xpath = "//i[@class='fa fa-home']")//i[@class='fa fa-home']/html[1]/body[1]/div[2]/ul[1]/li[1]/a[1]/i[1]
	private WebElement homeBreadcrumb;
	
	@FindBy(xpath="//span[text()='My Account']")
	private WebElement myAccountDropMenu;
	
	@FindBy(linkText="Login")
	private WebElement loginOption;
	
	
	public LoginPage selectLoginOption() {
		loginOption.click();
		return new LoginPage(driver);
	}
	
	public void clickOnMyAccountDropMenu() {
		myAccountDropMenu.click();
	}
	
	public LandingPage ClickOnHomeBreadcrumb() {
		homeBreadcrumb.click();
		return new LandingPage(driver);
	}
	
	public LandingPage clickOnHomeBresdcrumb() {
		 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		    // Wait for the element to be visible
		    WebElement homeIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("i.fa.fa-home")));
		    // Scroll to the element
		    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", homeIcon);
		    // Click using JavaScript to avoid interception
		    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", homeIcon);
		return new LandingPage(driver);
	}
	
	public LoginPage clickOnAccountBreadcrumb() {
		accountBreadcrumb.click();
		return new LoginPage(driver);
	}

	public SearchPage clickOnSearchButton() {
		searchButton.click();
		return new SearchPage(driver);
	}

	public LandingPage selectLogoOption() {
		logoOption.click();
		return new LandingPage(driver);
	}
	
	
	public ContactUsPage selectPhoneIconOption() {
		phoneIconOption.click();
		return new ContactUsPage(driver);
	}

	public LoginPage selectHeartIconOption() {
		heartIconeOption.click();
		return new LoginPage(driver);
	}

	public ShoppingCartPage selectShoppingCartOption() {
		shoppingCartOption.click();
		return new ShoppingCartPage(driver);
	}

	public ShoppingCartPage selectCheckoutOption() {
		checkoutOption.click();
		return new ShoppingCartPage(driver);
	}


}
