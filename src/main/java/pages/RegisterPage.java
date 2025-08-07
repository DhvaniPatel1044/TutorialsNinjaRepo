package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegisterPage {

	WebDriver driver;

	public RegisterPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "input-firstname")
	private WebElement firstNameField;

	@FindBy(id = "input-lastname")
	private WebElement lastNameField;

	@FindBy(id = "input-email")
	private WebElement emailField;

	@FindBy(id = "input-telephone")
	private WebElement telephoneField;

	@FindBy(id = "input-password")
	private WebElement passwordField;

	@FindBy(id = "input-confirm")
	private WebElement confirmPasswordField;

	@FindBy(name = "agree")
	private WebElement privacyPolicyField;

	@FindBy(xpath = "//input[@value='Continue']")
	private WebElement continueButton;

	@FindBy(xpath = "//input[@name='newsletter'][@value='1']")
	private WebElement yesNewsletterOption;

	@FindBy(xpath = "//input[@name='newsletter'][@value='0']")
	private WebElement noNewsletterOption;

	@FindBy(xpath = "//input[@id='input-firstname']/following-sibling::div")
	private WebElement firstNameWarning;

	@FindBy(xpath = "//input[@name='lastname']/following-sibling::div")
	private WebElement lastNameWarning;

	@FindBy(xpath = "//input[@id='input-email']/following-sibling::div")
	private WebElement emailWarning;

	@FindBy(xpath = "//input[@id='input-telephone']/following-sibling::div")
	private WebElement telephoneWarning;

	@FindBy(xpath = "//input[@id='input-password']/following-sibling::div")
	private WebElement passwordWarning;

	@FindBy(xpath = "//input[@id='input-confirm']/following-sibling::div")
	private WebElement passwordConfirmWarning;

	@FindBy(xpath = "//div[@class='alert alert-danger alert-dismissible']")
	private WebElement existingEmailWarning;

	@FindBy(xpath = "//ul[@class='breadcrumb']/following-sibling::div")
	private WebElement privacyPolicyWarning;   //div[@class='alert alert-danger alert-dismissible']

	@FindBy(xpath = "//ul[@class='breadcrumb']//a[text()='Register']")
	private WebElement registerBreadcrumb;

	@FindBy(xpath = "//span[text()='My Account']")
	private WebElement myAccountDropMenu;

	@FindBy(linkText = "Login")
	private WebElement loginOption;

	@FindBy(css = "label[for='input-firstname']")
	private WebElement firstNameLabel;

	@FindBy(css = "label[for='input-lastname']")
	private WebElement lastNameLabel;

	@FindBy(css = "label[for='input-email']")
	private WebElement emailLable;

	@FindBy(css = "label[for='input-telephone']")
	private WebElement telephoneLable;

	@FindBy(css = "label[for='input-password']")
	private WebElement passwordLable;

	@FindBy(css = "label[for='input-confirm']")
	private WebElement passwordConfirmLable;

	@FindBy(css = "[class='pull-right']")
	private WebElement privacyPolicyLable;

	@FindBy(linkText = "login page")
	private WebElement loginPageOption;

	@FindBy(xpath = "//a[@class='agree']//b[text()='Privacy Policy']")
	private WebElement privacyPolicyOption;

	@FindBy(xpath = "//button[text()='×']")
	private WebElement closePrivacyPolicyDialogOption;

	@FindBy(xpath = "//button[text()='×']")
	private WebElement xOption;
	
	private By xOptionPrivacyPolicy = By.xpath("//button[text()='×']");
	
	
	
	@FindBy(xpath="//div[@id='content']//h1")
	private WebElement registerPageHeading;
	
	
	public WebDriver getDriverFromRegisterPage() {
		return driver;
	}
	
	public String getRegisterPageHeading() {
		return registerPageHeading.getText();
	}
	
	
	
	public boolean waitAndCheckDisplayStatusOfClosePrivacyPolicyOption(WebDriver driver, int seconds) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		try {
		    WebElement closeButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='×']")));
		    closeButton.click();
		    wait.until(ExpectedConditions.invisibilityOf(closeButton));
		    return true;
		} catch (TimeoutException e) {
		    System.out.println("Close button did not appear, continuing test.");
			/* return xOption.isDisplayed(); */
		}
		return false;
	}
	
	public void closePrivacyPolicyOption() {
		xOption.click();
	}

	public void clickOnPrivacyPolicyOption() {
		privacyPolicyOption.click();
	}

	public LoginPage clickOnLoginPageLink() {
		loginPageOption.click();
		return new LoginPage(driver);
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

	public void enterFirstName(String firstNameText) {
		firstNameField.sendKeys(firstNameText);
	}

	public void enterLastName(String lastNameTest) {
		lastNameField.sendKeys(lastNameTest);
	}

	public void enterEmail(String emailText) {
		emailField.sendKeys(emailText);
	}

	public void enterTelephoneNumber(String telephoneText) {
		telephoneField.sendKeys(telephoneText);
	}

	public void enterPassword(String passwordText) {
		passwordField.sendKeys(passwordText);
	}

	public void enterConfirmPassword(String passwordText) {
		confirmPasswordField.sendKeys(passwordText);
	}

	public void selectPrivacyPolicy() {
		privacyPolicyField.click();
	}

	public AccountSuccessPage clickOnContinueButton() {
		continueButton.click();
		return new AccountSuccessPage(driver);
	}

	public void selectYesNewsletterOption() {
		yesNewsletterOption.click();
	}

	public void selectNoNewsletterOption() {
		noNewsletterOption.click();
	}

	public String getFirstNameWarning() {
		return firstNameWarning.getText();
	}

	public String getLastNameWarning() {
		return lastNameWarning.getText();
	}

	public String getEmailWarning() {
		return emailWarning.getText();
	}

	public String getTelephoneWarning() {
		return telephoneWarning.getText();
	}

	public String getPasswordWarning() {
		return passwordWarning.getText();
	}

	public String getPrivacyPolicyWarning() {
		return privacyPolicyWarning.getText();
	}

	public boolean didWeNavigateToRegisterAccountPage() {
		return registerBreadcrumb.isDisplayed();
	}

	public RegisterPage clickOnRegisterBreadcrumb() {
		registerBreadcrumb.click();
		return new RegisterPage(driver);
	}

	public void clickOnMyAccount() {
		myAccountDropMenu.click();
	}

	public LoginPage selectLoginOption() {
		loginOption.click();
		return new LoginPage(driver);
	}

	public String getPasswordConfirmWarning() {
		return passwordConfirmWarning.getText();
	}

	public String getExistingEmailWarning() {
		return existingEmailWarning.getText();
	}

	public void clearEmailField() {
		emailField.clear();
	}

	public String getPlaceholderTextFromFirstNameField() {
		return firstNameField.getDomAttribute("placeholder");
	}

	public String getPlaceholderTextFromLastNameField() {
		return lastNameField.getDomAttribute("placeholder");
	}

	public String getPlaceholderTextFromEmailField() {
		return emailField.getDomAttribute("placeholder");
	}

	public String getPlaceholderTextFromTelephoneField() {
		return telephoneField.getDomAttribute("placeholder");
	}

	public String getPlaceholderTextFromPasswordField() {
		return passwordField.getDomAttribute("placeholder");
	}

	public String getPlaceholderTextFromPasswordConfirmField() {
		return confirmPasswordField.getDomAttribute("placeholder");
	}

	public String getFirstNameLableContent(WebDriver driver) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		String fnContent = (String) jse.executeScript(
				"return window.getComputedStyle(arguments[0], '::before').getPropertyValue('content');",
				firstNameLabel);
		return fnContent;
	}

	public String getFirstNameLableColor(WebDriver driver) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		String fnColor = (String) jse.executeScript(
				"return window.getComputedStyle(arguments[0], '::before').getPropertyValue('color');", firstNameLabel);
		return fnColor;
	}

	public String getLastNameLableContent(WebDriver driver) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		String lnContent = (String) jse.executeScript(
				"return window.getComputedStyle(arguments[0], '::before').getPropertyValue('content');", lastNameLabel);
		return lnContent;
	}

	public String getLastNameLableColor(WebDriver driver) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		String lnColor = (String) jse.executeScript(
				"return window.getComputedStyle(arguments[0], '::before').getPropertyValue('color');", lastNameLabel);
		return lnColor;
	}

	public String getEmailLableContent(WebDriver driver) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		String emailContent = (String) jse.executeScript(
				"return window.getComputedStyle(arguments[0], '::before').getPropertyValue('content');", emailLable);
		return emailContent;
	}

	public String getEmailLableColor(WebDriver driver) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		String emailColor = (String) jse.executeScript(
				"return window.getComputedStyle(arguments[0], '::before').getPropertyValue('color');", emailLable);
		return emailColor;
	}

	public String getTelephoneLableContent(WebDriver driver) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		String telephoneContent = (String) jse.executeScript(
				"return window.getComputedStyle(arguments[0], '::before').getPropertyValue('content');",
				telephoneLable);
		return telephoneContent;
	}

	public String getTelephoneLableColor(WebDriver driver) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		String telephoneColor = (String) jse.executeScript(
				"return window.getComputedStyle(arguments[0], '::before').getPropertyValue('color');", telephoneLable);
		return telephoneColor;
	}

	public String getPasswordLableContent(WebDriver driver) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		String passwordContent = (String) jse.executeScript(
				"return window.getComputedStyle(arguments[0], '::before').getPropertyValue('content');", passwordLable);
		return passwordContent;
	}

	public String getPasswordLableColor(WebDriver driver) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		String passwordColor = (String) jse.executeScript(
				"return window.getComputedStyle(arguments[0], '::before').getPropertyValue('color');", passwordLable);
		return passwordColor;
	}

	public String getPasswordConfirmLableContent(WebDriver driver) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		String passwordConfirmContent = (String) jse.executeScript(
				"return window.getComputedStyle(arguments[0], '::before').getPropertyValue('content');",
				passwordConfirmLable);
		return passwordConfirmContent;
	}

	public String getPasswprdConfirmLableColor(WebDriver driver) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		String passwordConfirmColor = (String) jse.executeScript(
				"return window.getComputedStyle(arguments[0], '::before').getPropertyValue('color');",
				passwordConfirmLable);
		return passwordConfirmColor;
	}

	public String getPrivacyPolicyLableContent(WebDriver driver) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		String privacyPolicyContent = (String) jse.executeScript(
				"return window.getComputedStyle(arguments[0], '::before').getPropertyValue('content');",
				privacyPolicyLable);
		return privacyPolicyContent;
	}

	public String getPrivacyPolicyLableColor(WebDriver driver) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		String privacyPolicyColor = (String) jse.executeScript(
				"return window.getComputedStyle(arguments[0], '::before').getPropertyValue('color');",
				privacyPolicyLable);
		return privacyPolicyColor;
	}

	public String getFirstNameFieldHeight() {
		return firstNameField.getCssValue("height");
	}

	public String getFirstNameFieldWidth() {
		return firstNameField.getCssValue("width");
	}

	public void clearFirstNameField() {
		firstNameField.clear();
	}

	public boolean isFirstWarningDisplayed() {
		boolean status = false;
		try {
			status = firstNameWarning.isDisplayed();
		} catch (NoSuchElementException e) {
			status = false;
		}
		return status;
	}

	public String getLastNameFieldHeight() {
		return lastNameField.getCssValue("height");
	}

	public String getLastNameFieldWidth() {
		return lastNameField.getCssValue("width");
	}

	public void clearLastNameField() {
		lastNameField.clear();
	}

	public boolean isLastNameWarningDisplayed() {
		boolean status = false;
		try {
			status = lastNameWarning.isDisplayed();
		} catch (NoSuchElementException e) {
			status = false;
		}
		return status;
	}

	public String getEmailFieldHeight() {
		return emailField.getCssValue("height");
	}

	public String getEmailFieldWidth() {
		return emailField.getCssValue("width");
	}

	public boolean isEmailWarningDisplayed() {
		boolean status = false;
		try {
			status = emailWarning.isDisplayed();
		} catch (NoSuchElementException e) {
			status = false;
		}
		return status;
	}

	public String getTelephoneFieldHeight() {
		return telephoneField.getCssValue("height");
	}

	public String getTelephoneFieldWidth() {
		return telephoneField.getCssValue("width");
	}

	public void clearTelephoneField() {
		telephoneField.clear();
	}

	public boolean isTelephoneWarningDisplayed() {
		boolean status = false;
		try {
			status = telephoneWarning.isDisplayed();
		} catch (NoSuchElementException e) {
			status = false;
		}
		return status;
	}

	public String getPasswordFieldHeight() {
		return passwordField.getCssValue("height");
	}

	public String getPasswordFieldWidth() {
		return passwordField.getCssValue("width");
	}

	public void clearPasswordField() {
		passwordField.clear();
	}

	public boolean isPasswordWarningDisplayed() {
		boolean status = false;
		try {
			status = passwordWarning.isDisplayed();
		} catch (NoSuchElementException e) {
			status = false;
		}
		return status;
	}

	public boolean isPasswordWarningDisplayAndMatch(String expectedPasswordWarning) {
		boolean state = false;

		try {
			String actualwarning = getPasswordWarning();
			if (actualwarning.equals(expectedPasswordWarning)) {
				state = true;
			}
		} catch (NoSuchElementException e) {
			state = false;
		}
		return state;
	}

	public String getPasswordConfirmFieldHeight() {
		return confirmPasswordField.getCssValue("height");
	}

	public String getPasswordConfirmFieldWidth() {
		return confirmPasswordField.getCssValue("width");
	}

	public void clearPasswordConfirmField() {
		confirmPasswordField.clear();
	}

	public boolean isPasswordConfirmWarningDisplayed() {
		boolean status = false;
		try {
			status = passwordConfirmWarning.isDisplayed();
		} catch (NoSuchElementException e) {
			status = false;
		}
		return status;
	}

	public boolean isPrivacyPolicySelected() {
		return privacyPolicyField.isSelected();
	}

	public String getPasswordFieldType() {
		return passwordField.getDomAttribute("type");
	}

	public String getPasswordConfirmFieldType() {
		return confirmPasswordField.getDomAttribute("type");
	}

}
