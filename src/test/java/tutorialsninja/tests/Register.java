package tutorialsninja.tests;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import pages.AboutUsPage;
import pages.AccountPage;
import pages.AccountSuccessPage;
import pages.AffiliateLoginPage;
import pages.BrandsPage;
import pages.ContactUsPage;
import pages.DeliveryInformationPage;
import pages.EditAccountInformationPage;
import pages.FooterOptions;
import pages.ForgottenPasswordPage;
import pages.GiftCertificatesPage;
import pages.HeaderOptions;
import pages.LandingPage;
import pages.LoginPage;
import pages.NewsletterPage;
import pages.PrivacyPolicyPage;
import pages.ProductReturnsPage;
import pages.RegisterPage;
import pages.RightColumnOptions;
import pages.SearchPage;
import pages.ShoppingCartPage;
import pages.SiteMapPage;
import pages.SpecialOffersPage;
import pages.TermsAndConditionsPage;
import tutorialsninja.base.Base;
import utils.CommonUtils;
import utils.MyXLSReader;

public class Register extends Base {

    public WebDriver driver;
	Properties prop;
	ExtentReports extent;
	
	
	@BeforeMethod
	public void setup() {

		driver = openBrowserAndApplication();
		prop = CommonUtils.loadProperties();

		landingPage = new LandingPage(driver);
		landingPage.clickOnMyAccount();
		registerPage = landingPage.selectRegisterOption();

	}
	
	@BeforeSuite
	public void setupReport() {
	    ExtentSparkReporter spark = new ExtentSparkReporter("test-output/ExtentReport.html");
	    extent = new ExtentReports();
	    extent.attachReporter(spark);
	}

	@AfterSuite
	public void tearDownReport() {
	    extent.flush();
	}

	@AfterMethod
	public void teardown() {
		if (driver != null) {
			driver.quit();
		}
	}

	@Test(priority = 1)
	public void verifyRegisteringWithMandatoryFeild() {

		registerPage.enterFirstName(prop.getProperty("firstName"));
		registerPage.enterLastName(prop.getProperty("lastName"));
		registerPage.enterEmail(CommonUtils.generateNewEmail());
		registerPage.enterTelephoneNumber(prop.getProperty("telephoneNumber"));
		registerPage.enterPassword(prop.getProperty("validPassword"));
		registerPage.enterConfirmPassword(prop.getProperty("validPassword"));
		registerPage.selectPrivacyPolicy();
		accountSuccessPage = registerPage.clickOnContinueButton();

		Assert.assertTrue(accountSuccessPage.isUserLoggedIn());
		String expectedHeading = "Your Account Has Been Created!";
		Assert.assertEquals(accountSuccessPage.getPageHeading(), expectedHeading);
		String expectedProperDetailsOne = "Congratulations! Your new account has been successfully created!";
		String expectedProperDetailsTwo = "You can now take advantage of member privileges to enhance your online shopping experience with us.";
		String expectedProperDetailsThree = "If you have ANY questions about the operation of this online shop, please e-mail the store owner.";
		String expectedProperDetailsFour = "contact us.";
		String actualproperDetails = accountSuccessPage.getPageContent();
		Assert.assertTrue(actualproperDetails.contains(expectedProperDetailsOne));
		Assert.assertTrue(actualproperDetails.contains(expectedProperDetailsTwo));
		Assert.assertTrue(actualproperDetails.contains(expectedProperDetailsThree));
		Assert.assertTrue(actualproperDetails.contains(expectedProperDetailsFour));
		accountPage = accountSuccessPage.clickOnContinueButton();
		Assert.assertTrue(accountPage.didWeNavigateToAccountPage());

	}

	@Test(priority = 2)
	public void verifyRegisteringWithAllFeild() {

		registerPage.enterFirstName(prop.getProperty("firstName"));
		registerPage.enterLastName(prop.getProperty("lastName"));
		registerPage.enterEmail(CommonUtils.generateNewEmail());
		registerPage.enterTelephoneNumber(prop.getProperty("telephoneNumber"));
		registerPage.enterPassword(prop.getProperty("validPassword"));
		registerPage.enterConfirmPassword(prop.getProperty("validPassword"));
		registerPage.selectYesNewsletterOption();
		registerPage.selectPrivacyPolicy();
		accountSuccessPage = registerPage.clickOnContinueButton();

		Assert.assertTrue(accountSuccessPage.isUserLoggedIn());
		Assert.assertTrue(accountSuccessPage.didWeNavigateToAccountSuccessPage());

		String expectedHeading = "Your Account Has Been Created!";
		Assert.assertEquals(driver.findElement(By.xpath("//div[@id='common-success']//h1")).getText(), expectedHeading);
		String expectedProperDetailsOne = "Congratulations! Your new account has been successfully created!";
		String expectedProperDetailsTwo = "You can now take advantage of member privileges to enhance your online shopping experience with us.";
		String expectedProperDetailsThree = "If you have ANY questions about the operation of this online shop, please e-mail the store owner.";
		String expectedProperDetailsFour = "contact us.";
		String actualproperDetails = accountSuccessPage.getPageContent();
		Assert.assertTrue(actualproperDetails.contains(expectedProperDetailsOne));
		Assert.assertTrue(actualproperDetails.contains(expectedProperDetailsTwo));
		Assert.assertTrue(actualproperDetails.contains(expectedProperDetailsThree));
		Assert.assertTrue(actualproperDetails.contains(expectedProperDetailsFour));
		accountPage = accountSuccessPage.clickOnContinueButton();
		Assert.assertTrue(accountPage.didWeNavigateToAccountPage());

	}

	@Test(priority = 3)
	public void verifyResigteringAccountWithoutFillFields() {
		registerPage.clickOnContinueButton();
		String expectedFirstNameWarning = "First Name must be between 1 and 32 characters!";
		String expectedLastNameWarning = "Last Name must be between 1 and 32 characters!";
		String expectedEmailWarning = "E-Mail Address does not appear to be valid!";
		String expectedTelephoneWarning = "Telephone must be between 3 and 32 characters!";
		String expectedPasswordWarning = "Password must be between 4 and 20 characters!";
		String expectedPrivacyPolicyWarning = "Warning: You must agree to the Privacy Policy!";

		Assert.assertEquals(registerPage.getFirstNameWarning(), expectedFirstNameWarning);
		Assert.assertEquals(registerPage.getLastNameWarning(), expectedLastNameWarning);
		Assert.assertEquals(registerPage.getEmailWarning(), expectedEmailWarning);
		Assert.assertEquals(registerPage.getTelephoneWarning(), expectedTelephoneWarning);
		Assert.assertEquals(registerPage.getPasswordWarning(), expectedPasswordWarning);
		Assert.assertEquals(registerPage.getPrivacyPolicyWarning(), expectedPrivacyPolicyWarning);

	}

	@Test(priority = 4)
	public void verifyRegisteringAccountBySubscribingToNewsletter() {

		registerPage.enterFirstName(prop.getProperty("firstName"));
		registerPage.enterLastName(prop.getProperty("lastName"));
		registerPage.enterEmail(CommonUtils.generateNewEmail());
		registerPage.enterTelephoneNumber(prop.getProperty("telephoneNumber"));
		registerPage.enterPassword(prop.getProperty("validPassword"));
		registerPage.enterConfirmPassword(prop.getProperty("validPassword"));
		registerPage.selectYesNewsletterOption();
		registerPage.selectPrivacyPolicy();
		accountSuccessPage = registerPage.clickOnContinueButton();
		accountPage = accountSuccessPage.clickOnContinueButton();
		newsletterPage = accountPage.selectSubscribeUnsubscribeNewsletterOption();
		Assert.assertTrue(newsletterPage.didWeNavigateToNewsletterPage());
		Assert.assertTrue(newsletterPage.isYesNewsletterOptionSelected());

	}

	@Test(priority = 5)
	public void verifyRegisteringAccountBySayingNoToNewsletter() {

		registerPage.enterFirstName(prop.getProperty("firstName"));
		registerPage.enterLastName(prop.getProperty("lastName"));
		registerPage.enterEmail(CommonUtils.generateNewEmail());
		registerPage.enterTelephoneNumber(prop.getProperty("telephoneNumber"));
		registerPage.enterPassword(prop.getProperty("validPassword"));
		registerPage.enterConfirmPassword(prop.getProperty("validPassword"));
		registerPage.selectNoNewsletterOption();
		registerPage.selectPrivacyPolicy();
		accountSuccessPage = registerPage.clickOnContinueButton();
		accountPage = accountSuccessPage.clickOnContinueButton();
		newsletterPage = accountPage.selectSubscribeUnsubscribeNewsletterOption();
		Assert.assertTrue(newsletterPage.didWeNavigateToNewsletterPage());
		Assert.assertTrue(newsletterPage.isNoNewsletterOptionSelected());
	}

	@Test(priority = 6)
	public void verifyNavigatingToRegisterAccountUsingMultipleWay() {

		Assert.assertTrue(registerPage.didWeNavigateToRegisterAccountPage());
		driver = registerPage.getDriverFromRegisterPage();
		headeroptions = new HeaderOptions(driver);
		headeroptions.clickOnMyAccountDropMenu();
		loginPage = registerPage.selectLoginOption();
		loginPage.clickOnContinueButoon();
		Assert.assertTrue(registerPage.didWeNavigateToRegisterAccountPage());
		headeroptions = new HeaderOptions(driver);
		headeroptions.clickOnMyAccountDropMenu();
		loginPage = registerPage.selectLoginOption();
		loginPage.clickOnRegisterOption();
		Assert.assertTrue(registerPage.didWeNavigateToRegisterAccountPage());

	}

	@Test(priority = 7)
	public void verifyRegisteringAccountByProvidingMismatchingPassword() {

		registerPage.enterFirstName(prop.getProperty("firstName"));
		registerPage.enterLastName(prop.getProperty("lastName"));
		registerPage.enterEmail(CommonUtils.generateNewEmail());
		registerPage.enterTelephoneNumber(prop.getProperty("telephoneNumber"));
		registerPage.enterPassword(prop.getProperty("validPassword"));
		registerPage.enterConfirmPassword(prop.getProperty("mismatchingPassword"));
		registerPage.selectYesNewsletterOption();
		registerPage.selectPrivacyPolicy();
		accountSuccessPage = registerPage.clickOnContinueButton();
		String expectedWarningMessege = "Password confirmation does not match password!";
		Assert.assertEquals(registerPage.getPasswordConfirmWarning(), expectedWarningMessege);

	}

	@Test(priority = 8)
	public void verifyRegisteringAccountUsingExistingEmail() {

		registerPage.enterFirstName(prop.getProperty("firstName"));
		registerPage.enterLastName(prop.getProperty("lastName"));
		registerPage.enterEmail(prop.getProperty("existingEmail"));
		registerPage.enterTelephoneNumber(prop.getProperty("telephoneNumber"));
		registerPage.enterPassword(prop.getProperty("validPassword"));
		registerPage.enterConfirmPassword(prop.getProperty("validPassword"));
		registerPage.selectYesNewsletterOption();
		registerPage.selectPrivacyPolicy();
		accountSuccessPage = registerPage.clickOnContinueButton();
		String expectedWarningMessage = "Warning: E-Mail Address is already registered!";
		Assert.assertEquals(registerPage.getExistingEmailWarning(), expectedWarningMessage);

	}

	@Test(priority = 9)
	public void verifyRegisteringAccountUsingInvalidEmail() throws InterruptedException, IOException {

		String browserName = prop.getProperty("browserName");

		registerPage.enterFirstName(prop.getProperty("firstName"));
		registerPage.enterLastName(prop.getProperty("lastName"));
		registerPage.enterEmail(prop.getProperty("invalidEmailOne"));
		registerPage.enterTelephoneNumber(prop.getProperty("telephoneNumber"));
		registerPage.enterPassword(prop.getProperty("validPassword"));
		registerPage.enterConfirmPassword(prop.getProperty("validPassword"));
		registerPage.selectYesNewsletterOption();
		registerPage.selectPrivacyPolicy();
		accountSuccessPage = registerPage.clickOnContinueButton();	
		/* CommonUtils.takeScreenshot(driver, "\\src\\Screenshot\\sc1Actual.png"); */
		
		  Thread.sleep(3000); File srcScreenshot1 =
		  driver.findElement(By.xpath("//form[@class='form-horizontal']"))
		  .getScreenshotAs(OutputType.FILE); FileHandler.copy(srcScreenshot1, new
		  File(System.getProperty("user.dir") + "\\src\\Screenshot\\sc1Actual.png"));
		  Thread.sleep(3000);

		Assert.assertFalse(CommonUtils.compareTwoScreenshots(System.getProperty("user.dir") + "\\src\\Screenshot\\sc1Actual.png",
						System.getProperty("user.dir") + "\\src\\Screenshot\\sc1expected.png"));
		registerPage.clearEmailField();
		registerPage.enterEmail(prop.getProperty("invalidEmailTwo"));
		registerPage.clickOnContinueButton();
		//CommonUtils.takeScreenshot(driver, "\\src\\Screenshot\\sc2Actual.png");
		
		  Thread.sleep(3000); 
			File srcScreenshot2 = driver.findElement(By.xpath("//form[@class='form-horizontal']"))
					.getScreenshotAs(OutputType.FILE);
			FileHandler.copy(srcScreenshot2, new File(System.getProperty("user.dir") + "\\src\\Screenshot\\sc2Actual.png"));
			Thread.sleep(3000);

			Assert.assertFalse(CommonUtils.compareTwoScreenshots(
					System.getProperty("user.dir") + "\\src\\Screenshot\\sc2Actual.png",
					System.getProperty("user.dir") + "\\src\\Screenshot\\sc2expected.png"));

	}

	@Test(priority = 10)
	public void verifyRegisterAccountProvidingInvalidTelephoneNumber() {

		registerPage.enterFirstName(prop.getProperty("firstName"));
		registerPage.enterLastName(prop.getProperty("lastName"));
		registerPage.enterEmail(CommonUtils.generateNewEmail());
		registerPage.enterTelephoneNumber(prop.getProperty("invalidTelephoneNumber"));
		registerPage.enterPassword(prop.getProperty("validPassword"));
		registerPage.enterConfirmPassword(prop.getProperty("validPassword"));
		registerPage.selectYesNewsletterOption();
		registerPage.selectPrivacyPolicy();
		accountSuccessPage = registerPage.clickOnContinueButton();
		String expectedWarningMessage = "Telephone number does not appear to be valid";

		boolean state = false;

		try {
			String actualWarningMessage = registerPage.getTelephoneWarning();
			if (actualWarningMessage.equals(expectedWarningMessage)) {
				state = true;
			}
		} catch (NoSuchElementException e) {
			state = false;
		}

		Assert.assertTrue(state);
	}

	@Test(priority = 11)
	public void verifyRegisterAccountUsingKeyboardKeys() {
		
		driver = pressKeyMultipleTimes(driver,Keys.TAB,23);
		driver = enterDetailsIntoRegisterAccountPageFields(driver);
		accountSuccessPage = new AccountSuccessPage(driver);
		Assert.assertTrue(accountSuccessPage.isUserLoggedIn());
		Assert.assertTrue(accountSuccessPage.didWeNavigateToAccountSuccessPage());

	}

	@Test(priority = 12)
	public void verifyPlaceHoldersofTextFieldsInRegisterAccountPage() {

		String expectedFirstNamePlaceHolderText = "First Name";
		Assert.assertEquals(registerPage.getPlaceholderTextFromFirstNameField(), expectedFirstNamePlaceHolderText);

		String expectedLastNamePlaceHolderText = "Last Name";
		Assert.assertEquals(registerPage.getPlaceholderTextFromLastNameField(), expectedLastNamePlaceHolderText);

		String expectedEmailPlaceHolderText = "E-Mail";
		Assert.assertEquals(registerPage.getPlaceholderTextFromEmailField(), expectedEmailPlaceHolderText);

		String expectedTelephonePlaceHolderText = "Telephone";
		Assert.assertEquals(registerPage.getPlaceholderTextFromTelephoneField(), expectedTelephonePlaceHolderText);

		String expectedPasswordPlaceHolderText = "Password";
		Assert.assertEquals(registerPage.getPlaceholderTextFromPasswordField(), expectedPasswordPlaceHolderText);

		String expectedPasswordConfirmPlaceHolderText = "Password Confirm";
		Assert.assertEquals(registerPage.getPlaceholderTextFromPasswordConfirmField(),
				expectedPasswordConfirmPlaceHolderText);

	}

	@Test(priority = 13)
	public void verifyMandatoryFeildsSymbolAndColorInRegisterAccountPage() {

		String expectedContent = "\"* \"";
		String expectedColor = "rgb(255, 0, 0)";

		Assert.assertEquals(registerPage.getFirstNameLableContent(driver), expectedContent);
		Assert.assertEquals(registerPage.getFirstNameLableColor(driver), expectedColor);
		Assert.assertEquals(registerPage.getLastNameLableContent(driver), expectedContent);
		Assert.assertEquals(registerPage.getLastNameLableColor(driver), expectedColor);
		Assert.assertEquals(registerPage.getEmailLableContent(driver), expectedContent);
		Assert.assertEquals(registerPage.getEmailLableColor(driver), expectedColor);
		Assert.assertEquals(registerPage.getTelephoneLableContent(driver), expectedContent);
		Assert.assertEquals(registerPage.getTelephoneLableColor(driver), expectedColor);
		Assert.assertEquals(registerPage.getPasswordLableContent(driver), expectedContent);
		Assert.assertEquals(registerPage.getPasswordLableColor(driver), expectedColor);
		Assert.assertEquals(registerPage.getPasswordConfirmLableContent(driver), expectedContent);
		Assert.assertEquals(registerPage.getPasswordLableColor(driver), expectedColor);

	}

	@Test(priority = 14)
	public void verifyRegisteringAccountWithOnlySpaces() {

		registerPage.enterFirstName(" ");
		registerPage.enterLastName(" ");
		registerPage.enterEmail(" ");
		registerPage.enterTelephoneNumber(" ");
		registerPage.enterPassword(" ");
		registerPage.enterConfirmPassword(" ");
		registerPage.selectYesNewsletterOption();
		registerPage.selectPrivacyPolicy();
		accountSuccessPage = registerPage.clickOnContinueButton();

		String firstNameWarning = "First Name must be between 1 and 32 characters!";
		String lastNameWarning = "Last Name must be between 1 and 32 characters!";
		String emailWarning = "E-Mail Address does not appear to be valid!";
		String telephoneWarning = "Telephone must be between 3 and 32 characters!";
		String passwordWarning = "Password must be between 4 and 20 characters!";

		Assert.assertEquals(registerPage.getFirstNameWarning(), firstNameWarning);
		Assert.assertEquals(registerPage.getLastNameWarning(), lastNameWarning);
		Assert.assertEquals(registerPage.getEmailWarning(), emailWarning);
		Assert.assertEquals(registerPage.getTelephoneWarning(), telephoneWarning);
		Assert.assertEquals(registerPage.getPasswordWarning(), passwordWarning);

	}

	@Test(priority = 15, dataProvider = "passwordSupplier")
	public void verifyRegistringAccountAndCheckingPasswordComplexityStanderds(HashMap<String,String> hMap)
			throws InterruptedException {

		registerPage.enterFirstName(prop.getProperty("firstName"));
		registerPage.enterLastName(prop.getProperty("lastName"));
		registerPage.enterEmail(CommonUtils.generateNewEmail());
		registerPage.enterTelephoneNumber(prop.getProperty("telephoneNumber"));
		registerPage.selectYesNewsletterOption();
		registerPage.selectPrivacyPolicy();
		registerPage.enterPassword(hMap.get("Passwords"));
		registerPage.enterConfirmPassword(hMap.get("Passwords"));
		registerPage.clickOnContinueButton();

		Thread.sleep(3000);

		String warningMessege = "Password entered is not matching the Complexity standards";
		boolean state = false;

		try {
			String actualwarningMessage = registerPage.getPasswordWarning();
			if (actualwarningMessage.equals(warningMessege)) {
				state = true;
			}
		} catch (NoSuchElementException e) {
			state = false;
		}

		Assert.assertTrue(state);
		Assert.assertFalse(registerPage.didWeNavigateToRegisterAccountPage());

	}

	@DataProvider(name = "passwordSupplier")
	public Object[][] supplyPassword() {

		myXLSReader = new MyXLSReader(System.getProperty("user.dir")+"\\src\\test\\resourses\\tutorialsninja.xlsx");
		
		Object[][] data = CommonUtils.getTestData(myXLSReader, "RegisterTestSupplyPassword","data");
		return data;

	}

	@Test(priority = 16)
	public void verifyRegisteringAccountFieldsHeightWidthAligment() throws IOException, InterruptedException {

		String browserName = prop.getProperty("browserName");

		String expectedHeight = "34px";
		String expectedWidth = "701.25px";

		String actualFirstNameFieldheight = registerPage.getFirstNameFieldHeight();
		String actualFirstNameFieldwidth = registerPage.getFirstNameFieldWidth();
		Assert.assertEquals(actualFirstNameFieldheight, expectedHeight);
		Assert.assertEquals(actualFirstNameFieldwidth, expectedWidth);

		registerPage.enterFirstName("");
		registerPage.clickOnContinueButton();
		String expectedFirstNameWarning = "First Name must be between 1 and 32 characters!";
		Assert.assertEquals(registerPage.getFirstNameWarning(), expectedFirstNameWarning);

		registerPage = new RegisterPage(driver);
		registerPage.clearFirstNameField();
		registerPage.enterFirstName("a");
		registerPage.clickOnContinueButton();
		Assert.assertFalse(registerPage.isFirstWarningDisplayed());

		registerPage = new RegisterPage(driver);
		registerPage.clearFirstNameField();
		registerPage.enterFirstName("ab");
		registerPage.clickOnContinueButton();
		Assert.assertFalse(registerPage.isFirstWarningDisplayed());

		registerPage = new RegisterPage(driver);
		registerPage.clearFirstNameField();
		registerPage.enterFirstName("acdefghijklmnopq");
		registerPage.clickOnContinueButton();
		Assert.assertFalse(registerPage.isFirstWarningDisplayed());

		registerPage = new RegisterPage(driver);
		registerPage.clearFirstNameField();
		registerPage.enterFirstName("abcdefghijklmnopabcdefghijklmnop");
		registerPage.clickOnContinueButton();
		Assert.assertFalse(registerPage.isFirstWarningDisplayed());

		registerPage = new RegisterPage(driver);
		registerPage.clearFirstNameField();
		registerPage.enterFirstName("abcdefghijklmnopabcdefghijklmnopq");
		registerPage.clickOnContinueButton();
		Assert.assertEquals(registerPage.getFirstNameWarning(), expectedFirstNameWarning);

		// ------------------------------------------------------------

		registerPage = new RegisterPage(driver);
		String actualLastNameFieldheight = registerPage.getLastNameFieldHeight();
		String actualLastNameFieldWidth = registerPage.getLastNameFieldWidth();
		Assert.assertEquals(actualLastNameFieldheight, expectedHeight);
		Assert.assertEquals(actualLastNameFieldWidth, expectedWidth);
		String expectedLastNameWarning = "Last Name must be between 1 and 32 characters!";
		registerPage.clearLastNameField();
		registerPage.enterLastName("");
		registerPage.clickOnContinueButton();
		Assert.assertEquals(registerPage.getLastNameWarning(), expectedLastNameWarning);

		registerPage = new RegisterPage(driver);
		registerPage.clearLastNameField();
		registerPage.enterLastName("a");
		registerPage.clickOnContinueButton();
		Assert.assertFalse(registerPage.isLastNameWarningDisplayed());

		registerPage = new RegisterPage(driver);
		registerPage.clearLastNameField();
		registerPage.enterLastName("ab");
		registerPage.clickOnContinueButton();
		Assert.assertFalse(registerPage.isLastNameWarningDisplayed());

		registerPage = new RegisterPage(driver);
		registerPage.clearLastNameField();
		registerPage.enterLastName("acdefghijklmnopq");
		registerPage.clickOnContinueButton();
		Assert.assertFalse(registerPage.isLastNameWarningDisplayed());

		registerPage = new RegisterPage(driver);
		registerPage.clearLastNameField();
		registerPage.enterLastName("abcdefghijklmnopabcdefghijklmnop");
		registerPage.clickOnContinueButton();
		Assert.assertFalse(registerPage.isLastNameWarningDisplayed());

		registerPage = new RegisterPage(driver);
		registerPage.clearLastNameField();
		registerPage.enterLastName("abcdefghijklmnopabcdefghijklmnopq");
		registerPage.clickOnContinueButton();
		Assert.assertEquals(registerPage.getLastNameWarning(), expectedLastNameWarning);

		// ---------------------------------

		registerPage = new RegisterPage(driver);
		String actualEmailFieldheight = registerPage.getEmailFieldHeight();
		String actualEmailFieldWidth = registerPage.getEmailFieldWidth();
		Assert.assertEquals(actualEmailFieldheight, expectedHeight);
		Assert.assertEquals(actualEmailFieldWidth, expectedWidth);

		registerPage = new RegisterPage(driver);
		registerPage.clearEmailField();
		registerPage.enterEmail("abcdefghijklmnopabcdefghijklmnopqrstuvwxyz@gmail.com");
		registerPage.clickOnContinueButton();
		Assert.assertFalse(registerPage.isEmailWarningDisplayed());

		// ------------------------------------------
		registerPage = new RegisterPage(driver);
		String actualTelephoneFieldheight = registerPage.getTelephoneFieldHeight();
		String actualTelephoneFieldWidth = registerPage.getTelephoneFieldWidth();
		Assert.assertEquals(actualTelephoneFieldheight, expectedHeight);
		Assert.assertEquals(actualTelephoneFieldWidth, expectedWidth);

		registerPage = new RegisterPage(driver);
		String expectedTelephoneWarning = "Telephone must be between 3 and 32 characters!";
		registerPage.clearTelephoneField();
		registerPage.enterTelephoneNumber("");
		registerPage.clickOnContinueButton();
		Assert.assertEquals(registerPage.getTelephoneWarning(), expectedTelephoneWarning);

		registerPage = new RegisterPage(driver);
		registerPage.clearTelephoneField();
		registerPage.enterTelephoneNumber("a");
		registerPage.clickOnContinueButton();
		Assert.assertEquals(registerPage.getTelephoneWarning(), expectedTelephoneWarning);

		registerPage = new RegisterPage(driver);
		registerPage.clearTelephoneField();
		registerPage.enterTelephoneNumber("ab");
		registerPage.clickOnContinueButton();
		Assert.assertEquals(registerPage.getTelephoneWarning(), expectedTelephoneWarning);

		registerPage = new RegisterPage(driver);
		registerPage.clearTelephoneField();
		registerPage.enterTelephoneNumber("abc");
		registerPage.clickOnContinueButton();
		Assert.assertFalse(registerPage.isTelephoneWarningDisplayed());

		registerPage = new RegisterPage(driver);
		registerPage.clearTelephoneField();
		registerPage.enterTelephoneNumber("abcd");
		registerPage.clickOnContinueButton();
		Assert.assertFalse(registerPage.isTelephoneWarningDisplayed());

		registerPage = new RegisterPage(driver);
		registerPage.clearTelephoneField();
		registerPage.enterTelephoneNumber("abcdefghijklmnopabcdefghijklmnop");
		registerPage.clickOnContinueButton();
		Assert.assertFalse(registerPage.isTelephoneWarningDisplayed());

		registerPage = new RegisterPage(driver);
		registerPage.clearTelephoneField();
		registerPage.enterTelephoneNumber("abcdefghijklmnopabcdefghijklmnopq");
		registerPage.clickOnContinueButton();
		Assert.assertEquals(registerPage.getTelephoneWarning(), expectedTelephoneWarning);

		// ---------------------------------
		registerPage = new RegisterPage(driver);
		String actualPasswordFieldheight = registerPage.getPasswordFieldHeight();
		String actualPasswordFieldWidth = registerPage.getPasswordFieldWidth();
		Assert.assertEquals(actualPasswordFieldheight, expectedHeight);
		Assert.assertEquals(actualPasswordFieldWidth, expectedWidth);

		String expectedPasswordWarning = "Password must be between 4 and 20 characters!";
		registerPage = new RegisterPage(driver);
		registerPage.clearPasswordField();
		registerPage.enterPassword("");
		registerPage.clickOnContinueButton();
		Assert.assertEquals(registerPage.getPasswordWarning(), expectedPasswordWarning);

		registerPage = new RegisterPage(driver);
		registerPage.clearPasswordField();
		registerPage.enterPassword("a");
		registerPage.clickOnContinueButton();
		Assert.assertEquals(registerPage.getPasswordWarning(), expectedPasswordWarning);

		registerPage = new RegisterPage(driver);
		registerPage.clearPasswordField();
		registerPage.enterPassword("ab");
		registerPage.clickOnContinueButton();
		Assert.assertEquals(registerPage.getPasswordWarning(), expectedPasswordWarning);

		registerPage = new RegisterPage(driver);
		registerPage.clearPasswordField();
		registerPage.enterPassword("abc");
		registerPage.clickOnContinueButton();
		Assert.assertEquals(registerPage.getPasswordWarning(), expectedPasswordWarning);

		Thread.sleep(3000);
		registerPage = new RegisterPage(driver);
		registerPage.clearPasswordField();
		registerPage.enterPassword("abcd");
		registerPage.clickOnContinueButton();
		Thread.sleep(3000);
		Assert.assertFalse(registerPage.isPasswordWarningDisplayed());

		registerPage = new RegisterPage(driver);
		registerPage.clearPasswordField();
		registerPage.enterPassword("abcde");
		registerPage.clickOnContinueButton();
		Assert.assertFalse(registerPage.isPasswordWarningDisplayed());

		registerPage = new RegisterPage(driver);
		registerPage.clearPasswordField();
		registerPage.enterPassword("abcdefghij");
		registerPage.clickOnContinueButton();
		Assert.assertFalse(registerPage.isPasswordWarningDisplayed());

		registerPage = new RegisterPage(driver);
		registerPage.clearPasswordField();
		registerPage.enterPassword("abcdefghijabcdefghi");
		registerPage.clickOnContinueButton();
		Assert.assertFalse(registerPage.isPasswordWarningDisplayed());

		registerPage = new RegisterPage(driver);
		registerPage.clearPasswordField();
		registerPage.enterPassword("abcdefghijabcdefghij");
		registerPage.clickOnContinueButton();
		Assert.assertFalse(registerPage.isPasswordWarningDisplayed());

		registerPage = new RegisterPage(driver);
		registerPage.clearPasswordField();
		registerPage.enterPassword("abcdefghijabcdefghijk");
		registerPage.clickOnContinueButton();
		Assert.assertTrue(registerPage.isPasswordWarningDisplayAndMatch(expectedPasswordWarning));

		// ----------------------------------
		registerPage = new RegisterPage(driver);
		String actualPasswordConfirmFieldheight = registerPage.getPasswordConfirmFieldHeight();
		String actualPasswordConfirmFieldWidth = registerPage.getPasswordConfirmFieldWidth();
		Assert.assertEquals(actualPasswordConfirmFieldheight, expectedHeight);
		Assert.assertEquals(actualPasswordConfirmFieldWidth, expectedWidth);
		driver = navigateToRegisterPage(driver, prop.getProperty("registerPageURL"));
		driver = CommonUtils.takeScreenshot(driver, "\\Screenshot\\registerPageActualAligment.png");
		Assert.assertFalse(CommonUtils.compareTwoScreenshots(
				(System.getProperty("user.dir") + "\\Screenshot\\registerPageActualAligment.png"),
				(System.getProperty("user.dir") + "\\Screenshot\\registerPageExpectedAligment.png")));
		

	}

	@Test(priority = 17)
	public void verifyLeadingAndTrailingSpacesWhileRegisteringAccount() {

		SoftAssert softAssert = new SoftAssert();

		String enterFirstName = "     " + prop.getProperty("firstName") + "    ";
		registerPage.enterFirstName(enterFirstName);
		String enterLastName = "    " + prop.getProperty("lastName") + "   ";
		registerPage.enterLastName(enterLastName);
		String enterEmail = "    " + CommonUtils.generateNewEmail() + "     ";
		registerPage.enterEmail(enterEmail);
		String enterTelephone = "     " + prop.getProperty("telephoneNumber") + "      ";
		registerPage.enterTelephoneNumber(enterTelephone);
		registerPage.enterPassword(prop.getProperty("validPassword"));
		registerPage.enterConfirmPassword(prop.getProperty("validPassword"));
		registerPage.selectPrivacyPolicy();
		accountSuccessPage = registerPage.clickOnContinueButton();
		accountPage = accountSuccessPage.clickOnContinueButton();
		editAccountInformationPage = accountPage.clickOnEditYourAccountInformationOption();

		softAssert.assertEquals(editAccountInformationPage.getFirstNameFieldValue(), enterFirstName.trim());
		softAssert.assertEquals(editAccountInformationPage.getLastNameFieldValue(), enterLastName.trim());
		softAssert.assertEquals(editAccountInformationPage.getEmailFieldValue(), enterEmail.trim());
		softAssert.assertEquals(editAccountInformationPage.getTelephoneFieldValue(), enterTelephone.trim());
		//softAssert.assertAll();

	}

	@Test(priority = 18)
	public void verifyPrivacyPolicyFielOnRegisterAccountPage() {

		Assert.assertFalse(registerPage.isPrivacyPolicySelected());

	}

	@Test(priority = 19)
	public void verifyRegisterAccountWithoutPrivacyPolicySelection() {

		registerPage.enterFirstName(prop.getProperty("firstName"));
		registerPage.enterLastName(prop.getProperty("lastName"));
		registerPage.enterEmail(CommonUtils.generateNewEmail());
		registerPage.enterTelephoneNumber(prop.getProperty("telephoneNumber"));
		registerPage.enterPassword(prop.getProperty("validPassword"));
		registerPage.enterConfirmPassword(prop.getProperty("validPassword"));
		registerPage.selectNoNewsletterOption();
		//registerPage.selectPrivacyPolicy();
		accountSuccessPage = registerPage.clickOnContinueButton();

		String expectedPrivacyPolicyWarning = "Warning: You must agree to the Privacy Policy!";
		Assert.assertEquals(registerPage.getPrivacyPolicyWarning(), expectedPrivacyPolicyWarning);

	}

	@Test(priority = 20)
	public void verifyVisibilityTogglineOfPasswordFieldOnRegisterAccount() {

		Assert.assertEquals(registerPage.getPasswordFieldType(), "password");
		Assert.assertEquals(registerPage.getPasswordConfirmFieldType(), "password");

	}

	@Test(priority = 21)
	public void verifyWorkingOfEveryLinkOnRegisterAccountPage() {
		
		driver = registerPage.getDriverFromRegisterPage();
		headeroptions = new HeaderOptions(driver);
		contactUsPage = headeroptions.selectPhoneIconOption();
		Assert.assertTrue(contactUsPage.didWeNavigateToContactUsPage());
		driver = navigateBack(driver);

		registerPage = new RegisterPage(driver);
		driver = registerPage.getDriverFromRegisterPage();
		headeroptions = new HeaderOptions(driver);
		loginPage = headeroptions.selectHeartIconOption();
		Assert.assertTrue(loginPage.didWeNavigateToLoginPage());
		driver = navigateBack(driver);

		registerPage = new RegisterPage(driver);
		driver = registerPage.getDriverFromRegisterPage();
		headeroptions = new HeaderOptions(driver);
		shoppingCartPage = headeroptions.selectShoppingCartOption();
		Assert.assertTrue(shoppingCartPage.didWeNavigateToShoppingCartPage());
		driver = navigateBack(driver);

		registerPage = new RegisterPage(driver);
		driver = registerPage.getDriverFromRegisterPage();
		headeroptions = new HeaderOptions(driver);
		shoppingCartPage = headeroptions.selectCheckoutOption();
		Assert.assertTrue(shoppingCartPage.didWeNavigateToShoppingCartPage());
		driver = navigateBack(driver);

		registerPage = new RegisterPage(driver);
		driver = registerPage.getDriverFromRegisterPage();
		headeroptions = new HeaderOptions(driver);
		headeroptions.selectLogoOption();
		Assert.assertEquals(driver.getCurrentUrl(), prop.getProperty("landingPageURL"));
		driver = navigateBack(driver);

		registerPage = new RegisterPage(driver);
		driver = registerPage.getDriverFromRegisterPage();
		headeroptions = new HeaderOptions(driver);
		searchPage = headeroptions.clickOnSearchButton();
		Assert.assertTrue(searchPage.didWeNavigetToSearchPage());
		driver = navigateBack(driver);

		registerPage = new RegisterPage(driver);
		registerPage =registerPage.clickOnRegisterBreadcrumb();
		Assert.assertTrue(registerPage.didWeNavigateToRegisterAccountPage());
		//driver = navigateBack(driver);

		registerPage = new RegisterPage(driver);
		driver = registerPage.getDriverFromRegisterPage();
		headeroptions = new HeaderOptions(driver);
		loginPage = headeroptions.clickOnAccountBreadcrumb();
		Assert.assertTrue(loginPage.didWeNavigateToLoginPage());
		driver = navigateBack(driver);

		registerPage = new RegisterPage(driver);
		System.out.println(driver.getTitle());
		landingPage = headeroptions.clickOnHomeBresdcrumb();
		Assert.assertEquals(getPageURL(driver), prop.getProperty("landingPageURL"));
		driver = navigateBack(driver);

		registerPage = new RegisterPage(driver);
		loginPage = registerPage.clickOnLoginPageLink();
		Assert.assertTrue(loginPage.didWeNavigateToLoginPage());
		driver = navigateBack(driver);

		registerPage = new RegisterPage(driver);
		registerPage.clickOnPrivacyPolicyOption();
		registerPage.waitAndCheckDisplayStatusOfClosePrivacyPolicyOption(driver, 10);
	//	registerPage.closePrivacyPolicyOption();

		registerPage = new RegisterPage(driver);
		registerPage.clickOnContinueButton();
		Assert.assertTrue(registerPage.didWeNavigateToRegisterAccountPage());

		registerPage = new RegisterPage(driver);
		driver = registerPage.getDriverFromRegisterPage();
		rightColumnOptions = new RightColumnOptions(driver);
		loginPage = rightColumnOptions.clickOnRightSideLoginOption();
		Assert.assertTrue(loginPage.didWeNavigateToLoginPage());
		driver = navigateBack(driver);

		registerPage = new RegisterPage(driver);
		driver = registerPage.getDriverFromRegisterPage();
		rightColumnOptions = new RightColumnOptions(driver);
		registerPage = rightColumnOptions.clickOnRightSideRegisterOption();
		Assert.assertTrue(registerPage.didWeNavigateToRegisterAccountPage());

		registerPage = new RegisterPage(driver);
		driver = registerPage.getDriverFromRegisterPage();
		rightColumnOptions = new RightColumnOptions(driver);
		forgottenPasswordPage = rightColumnOptions.clickOnRightSideForgottenPassswordOption();
		Assert.assertTrue(forgottenPasswordPage.didWeNavigateToForgottenPasswordPage());
		driver = navigateBack(driver);

		registerPage = new RegisterPage(driver);
		driver = registerPage.getDriverFromRegisterPage();
		rightColumnOptions = new RightColumnOptions(driver);
		loginPage = rightColumnOptions.clickOnRightSideMyAccountOption();
		Assert.assertTrue(loginPage.didWeNavigateToLoginPage());
		driver = navigateBack(driver);

		registerPage = new RegisterPage(driver);
		driver = registerPage.getDriverFromRegisterPage();
		rightColumnOptions = new RightColumnOptions(driver);
		loginPage = rightColumnOptions.clickOnRightSideAddressBookOption();
		Assert.assertTrue(loginPage.didWeNavigateToLoginPage());
		driver = navigateBack(driver);

		registerPage = new RegisterPage(driver);
		driver = registerPage.getDriverFromRegisterPage();
		rightColumnOptions = new RightColumnOptions(driver);
		loginPage = rightColumnOptions.clickOnRightSideWishListOption();
		Assert.assertTrue(loginPage.didWeNavigateToLoginPage());
		driver = navigateBack(driver);

		registerPage = new RegisterPage(driver);
		driver = registerPage.getDriverFromRegisterPage();
		rightColumnOptions = new RightColumnOptions(driver);
		rightColumnOptions.clickOnRightSideOrderHistoryOption();
		Assert.assertTrue(loginPage.didWeNavigateToLoginPage());
		driver = navigateBack(driver);

		registerPage = new RegisterPage(driver);
		driver = registerPage.getDriverFromRegisterPage();
		rightColumnOptions = new RightColumnOptions(driver);
		rightColumnOptions.clickOnRightSideDownloadsOption();
		Assert.assertTrue(loginPage.didWeNavigateToLoginPage());
		driver = navigateBack(driver);

		registerPage = new RegisterPage(driver);
		driver = registerPage.getDriverFromRegisterPage();
		rightColumnOptions = new RightColumnOptions(driver);
		rightColumnOptions.clickOnRightSideRecurringPaymentsOption();
		Assert.assertTrue(loginPage.didWeNavigateToLoginPage());
		driver = navigateBack(driver);

		registerPage = new RegisterPage(driver);
		driver = registerPage.getDriverFromRegisterPage();
		rightColumnOptions = new RightColumnOptions(driver);
		rightColumnOptions.clickOnRightSideRewardPointsOption();
		Assert.assertTrue(loginPage.didWeNavigateToLoginPage());
		driver = navigateBack(driver);

		registerPage = new RegisterPage(driver);
		driver = registerPage.getDriverFromRegisterPage();
		rightColumnOptions = new RightColumnOptions(driver);
		rightColumnOptions.clickOnRightSideReturnsOption();
		Assert.assertTrue(loginPage.didWeNavigateToLoginPage());
		driver = navigateBack(driver);

		registerPage = new RegisterPage(driver);
		driver = registerPage.getDriverFromRegisterPage();
		rightColumnOptions = new RightColumnOptions(driver);
		rightColumnOptions.clickOnRightSideTransactionsOption();
		Assert.assertTrue(loginPage.didWeNavigateToLoginPage());
		driver = navigateBack(driver);

		registerPage = new RegisterPage(driver);
		driver = registerPage.getDriverFromRegisterPage();
		rightColumnOptions = new RightColumnOptions(driver);
		rightColumnOptions.clickOnRightSideNewsletterOption();
		Assert.assertTrue(loginPage.didWeNavigateToLoginPage());
		driver = navigateBack(driver);

		registerPage = new RegisterPage(driver);
		driver = registerPage.getDriverFromRegisterPage();
		footerOptions = new FooterOptions(driver);
		aboutUsPage = footerOptions.clickOnAboutUsFooterOption();
		Assert.assertTrue(aboutUsPage.didWeNavigateToAboutUsBreadcrumb());
		driver = navigateBack(driver);

		registerPage = new RegisterPage(driver);
		driver = registerPage.getDriverFromRegisterPage();
		footerOptions = new FooterOptions(driver);
		deliveryInformationPage = footerOptions.clickOnDeliveryInformationOption();
		Assert.assertTrue(deliveryInformationPage.didWeNavigateToDeliveryInformationPage());
		driver = navigateBack(driver);

		registerPage = new RegisterPage(driver);
		driver = registerPage.getDriverFromRegisterPage();
		footerOptions = new FooterOptions(driver);
		privacyPolicyPage = footerOptions.clickOnPrivacyPolicyFooterOption();
		Assert.assertTrue(privacyPolicyPage.didWeNavi8gateToPrivacyPolicyPage());
		driver = navigateBack(driver);

		registerPage = new RegisterPage(driver);
		driver = registerPage.getDriverFromRegisterPage();
		footerOptions = new FooterOptions(driver);
		termsAndConditionsPage = footerOptions.clickOnTermAndConditionsFooterOption();
		Assert.assertTrue(termsAndConditionsPage.didWeNavigateToTermsAndConditionsPage());
		driver = navigateBack(driver);

		registerPage = new RegisterPage(driver);
		driver = registerPage.getDriverFromRegisterPage();
		footerOptions = new FooterOptions(driver);
		contactUsPage = footerOptions.clickOnContactUsFooterOption();
		Assert.assertTrue(contactUsPage.didWeNavigateToContactUsPage());
		driver = navigateBack(driver);

		registerPage = new RegisterPage(driver);
		driver = registerPage.getDriverFromRegisterPage();
		footerOptions = new FooterOptions(driver);
		productReturnsPage = footerOptions.clickOnReturnsFooterOption();
		Assert.assertTrue(productReturnsPage.didWeNavigateToProductReturnsPage());
		driver = navigateBack(driver);

		registerPage = new RegisterPage(driver);
		driver = registerPage.getDriverFromRegisterPage();
		footerOptions = new FooterOptions(driver);
		siteMapPage = footerOptions.clickOnSiteMapFooterOption();
		Assert.assertTrue(siteMapPage.didWeNavigateToSiteMapPage());
		driver = navigateBack(driver);

		registerPage = new RegisterPage(driver);
		driver = registerPage.getDriverFromRegisterPage();
		footerOptions = new FooterOptions(driver);
		brandsPage = footerOptions.clickOnBrandsFooterOption();
		Assert.assertTrue(brandsPage.didWeNavigateToBrandsPage());
		driver = navigateBack(driver);

		registerPage = new RegisterPage(driver);
		driver = registerPage.getDriverFromRegisterPage();
		footerOptions = new FooterOptions(driver);
		giftCertificates = footerOptions.clickOnGiftCertificatesFooterOption();
		Assert.assertTrue(giftCertificates.didWeNavigateToGiftCertificatesPage());
		driver = navigateBack(driver);

		registerPage = new RegisterPage(driver);
		driver = registerPage.getDriverFromRegisterPage();
		footerOptions = new FooterOptions(driver);
		affiliateLoginPage = footerOptions.clickOnAffiliateFooterOption();
		Assert.assertEquals(driver.getCurrentUrl(), prop.getProperty("affiliateLoginPageURL"));
		driver = navigateBack(driver);

		registerPage = new RegisterPage(driver);
		driver = registerPage.getDriverFromRegisterPage();
		footerOptions = new FooterOptions(driver);
		specialOffersPage = footerOptions.clickOnSpecialsFooterOption();
		Assert.assertTrue(specialOffersPage.didWeNavigateToSpecialOffersPage());
		driver = navigateBack(driver);

		registerPage = new RegisterPage(driver);
		driver = registerPage.getDriverFromRegisterPage();
		footerOptions = new FooterOptions(driver);
		footerOptions.clickOnMyAccountFooterOption();
		Assert.assertTrue(loginPage.didWeNavigateToLoginPage());
		driver = navigateBack(driver);

		registerPage = new RegisterPage(driver);
		driver = registerPage.getDriverFromRegisterPage();
		footerOptions = new FooterOptions(driver);
		footerOptions.clickOnOrderHistoryFooterOption();
		Assert.assertTrue(loginPage.didWeNavigateToLoginPage());
		driver = navigateBack(driver);

		registerPage = new RegisterPage(driver);
		driver = registerPage.getDriverFromRegisterPage();
		footerOptions = new FooterOptions(driver);
		footerOptions.clickOnWishListFooterOption();
		Assert.assertTrue(loginPage.didWeNavigateToLoginPage());
		driver = navigateBack(driver);

		registerPage = new RegisterPage(driver);
		driver = registerPage.getDriverFromRegisterPage();
		footerOptions = new FooterOptions(driver);
		loginPage =	footerOptions.clickOnNewsletterFooterOption();
		Assert.assertTrue(loginPage.didWeNavigateToLoginPage());
		driver = navigateBack(driver);
	}

	@Test(priority = 22)
	public void verifyRegisteringAccountWithoutEnteringPasswordIntoConfirmField() {

		registerPage.enterFirstName(prop.getProperty("firstName"));
		registerPage.enterLastName(prop.getProperty("lastName"));
		registerPage.enterEmail(CommonUtils.generateNewEmail());
		registerPage.enterTelephoneNumber(prop.getProperty("telephoneNumber"));
		registerPage.enterPassword(prop.getProperty("validPassword"));
		registerPage.selectYesNewsletterOption();
		registerPage.selectPrivacyPolicy();
		accountSuccessPage = registerPage.clickOnContinueButton();

		String expectedWarning = "Password confirmation does not match password!";
		Assert.assertEquals(registerPage.getPasswordConfirmWarning(), expectedWarning);

	}

	@Test(priority = 23)
	public void verifyBreadcrumbURLHeadingTitleOfRegisterAccountPage() {

		Assert.assertTrue(registerPage.didWeNavigateToRegisterAccountPage());
		Assert.assertEquals(registerPage.getRegisterPageHeading(), prop.getProperty("registerPageHeading"));
		Assert.assertEquals(getPageURL(driver), prop.getProperty("registerPageURL"));
		Assert.assertEquals(getPageTitle(driver), prop.getProperty("registerPageTitle"));

	}

	@Test(priority = 24)
	public void verifyUIOfRegisterAccountPage()  {
		/*
		 * CommonUtils.takeScreenshot(driver,
		 * "\\src\\Screenshot\\actualLoginPageUI.png");
		 */
		if(prop.getProperty("browserName").equals("chrome")) {
		CommonUtils.takeScreenshot(driver,"\\Screenshot\\actualRegisterPageUI.png");
		Assert.assertFalse(CommonUtils.compareTwoScreenshots(System.getProperty("user.dir") + "\\Screenshot\\actualRegisterPageUI.png",System.getProperty("user.dir") + "\\Screenshot\\expectedRegisterPageUI.png"));
		}else if(prop.getProperty("browserName").equals("firefox")) {
			CommonUtils.takeScreenshot(driver,"\\Screenshot\\actualfirefoxRegisterPageUI.png");
			Assert.assertFalse(CommonUtils.compareTwoScreenshots(System.getProperty("user.dir") + "\\Screenshot\\actualfirefoxRegisterPageUI.png",System.getProperty("user.dir") + "\\Screenshot\\expectedfirefoxRegisterPageUI.png"));
		}else if(prop.getProperty("browserName").equals("edge")) {
			CommonUtils.takeScreenshot(driver,"\\Screenshot\\actualedgeRegisterPageUI.png");
			Assert.assertFalse(CommonUtils.compareTwoScreenshots(System.getProperty("user.dir") + "\\Screenshot\\actualedgeRegisterPageUI.png",System.getProperty("user.dir") + "\\Screenshot\\expectededgeRegisterPageUI.png"));
		}
	}

	@Test(priority = 25)
	public void verifyRegisterAccountInDifferentEnvironment() {

		registerPage.enterFirstName(prop.getProperty("firstName"));
		registerPage.enterLastName(prop.getProperty("lastName"));
		registerPage.enterEmail(CommonUtils.generateNewEmail());
		registerPage.enterTelephoneNumber(prop.getProperty("telephoneNumber"));
		registerPage.enterPassword(prop.getProperty("validPassword"));
		registerPage.enterConfirmPassword(prop.getProperty("validPassword"));
		registerPage.selectYesNewsletterOption();
		registerPage.selectPrivacyPolicy();
		accountSuccessPage = registerPage.clickOnContinueButton();

		Assert.assertTrue(accountSuccessPage.isUserLoggedIn());
		Assert.assertTrue(accountSuccessPage.didWeNavigateToAccountSuccessPage());
		accountSuccessPage.clickOnContinueButton();
		// Assert.assertTrue(driver.findElement(By.xpath("//ul[@class='breadcrumb']//a[text()='Account']")).isDisplayed());
		Assert.assertEquals(driver.getTitle(), prop.getProperty("accountPageTitle"));

	}

}
