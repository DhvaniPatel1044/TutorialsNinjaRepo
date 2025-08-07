package tutorialsninja.tests;

import java.util.Properties;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import pages.AccountPage;
import pages.FooterOptions;
import pages.HeaderOptions;
import pages.LandingPage;
import pages.LoginPage;
import pages.RightColumnOptions;
import tutorialsninja.base.Base;
import utils.CommonUtils;

public class Login extends Base {

	public WebDriver driver;
	Properties prop;
	 ExtentReports extent;

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

	@BeforeMethod
	public void setup() {

		driver = openBrowserAndApplication();
		prop = CommonUtils.loadProperties();

		landingPage = new LandingPage(driver);
		landingPage.clickOnMyAccount();
		loginPage = landingPage.selectLoginOption();

	}

	@AfterMethod
	public void teardown() {
		if (driver != null) {
			driver.quit();
		}
	}

	@Test(priority = 1)
	public void verifyLoginWithValidCredentials() {

		Assert.assertTrue(loginPage.didWeNavigateToLoginPage());
		loginPage.clearEmailField();
		loginPage.clearPasswordField();
		loginPage.enterEmail(prop.getProperty("emailForLoginThree"));
		loginPage.enterPassword(prop.getProperty("validPassword"));
		accountPage = loginPage.clickOnLoginButton();
		Assert.assertTrue(accountPage.isUserLoggedIn());
		Assert.assertTrue(accountPage.didWeNavigateToAccountPage());

	}

	@Test(priority = 2)
	public void verifyLoginWithInvalidCredentials() {

		loginPage.enterEmail(CommonUtils.generateNewEmail());
		loginPage.enterPassword(prop.getProperty("invalidPassword"));
		accountPage = loginPage.clickOnLoginButton();
		String expectedWarning = "Warning: No match for E-Mail Address and/or Password.";
		Assert.assertEquals(loginPage.getWarningMessage(), expectedWarning);

	}

	@Test(priority = 3)
	public void verifyLoginWithInvalidEmailAndValidPassword() {

		loginPage.enterEmail(CommonUtils.generateNewEmail());
		loginPage.enterPassword(prop.getProperty("validPassword"));
		accountPage = loginPage.clickOnLoginButton();
		String expectedWarning = "Warning: No match for E-Mail Address and/or Password.";
		Assert.assertEquals(loginPage.getWarningMessage(), expectedWarning);

	}

	@Test(priority = 4)
	public void verifyLoginWithvalidEmailAndInvalidPassword() {

		loginPage.enterEmail(CommonUtils.validEmailRandomizeGenerator());
		loginPage.enterPassword(prop.getProperty("invalidPassword"));
		accountPage = loginPage.clickOnLoginButton();
		String expectedWarning = "Warning: No match for E-Mail Address and/or Password.";
		Assert.assertEquals(loginPage.getWarningMessage(), expectedWarning);

	}

	@Test(priority = 5)
	public void verifyLoginWithoutCredentials() {

		loginPage.clickOnLoginButton();
		String expectedWarning = "Warning: No match for E-Mail Address and/or Password.";
		Assert.assertEquals(loginPage.getWarningMessage(), expectedWarning);

	}

	@Test(priority = 6)
	public void verifyForgottendPasswordLinkOnLoginPage() {

		Assert.assertTrue(loginPage.didWeNavigateToLoginPage());
		Assert.assertTrue(loginPage.availabilityOfForgottenPasswordLink());
		forgottenPasswordPage = loginPage.clickOnForgottenPasswordLink();
		Assert.assertTrue(forgottenPasswordPage.didWeNavigateToForgottenPasswordPage());

	}

	@Test(priority = 7)
	public void verifyLoggingIntoTheApplicationUsingKeyboardKeys() {

		if(prop.getProperty("browserName").equals("chrome") || (prop.getProperty("browserName").equals("edge"))){
			driver = pressKeyMultipleTimes(driver, Keys.TAB, 23);
			driver = enterDetailsIntoLoginPageFields(driver);
			accountPage = new AccountPage(driver);
			Assert.assertTrue(accountPage.isUserLoggedIn());
			Assert.assertTrue(accountPage.didWeNavigateToAccountPage());
		}
		

	}

	@Test(priority = 8)
	public void verifyLoginFieldsPlaceholders() {

		String expectedEmailFieldPlaceholder = "E-Mail Address";
		String expectedPasswordPlaceholder = "Password";
		Assert.assertEquals(loginPage.getEmailPlaceholder(), expectedEmailFieldPlaceholder);
		Assert.assertEquals(loginPage.getPasswordPlaceholder(), expectedPasswordPlaceholder);

	}

	@Test(priority = 9)
	public void verifyBrowserBackAfterLogin() throws InterruptedException {

		loginPage.enterEmail(prop.getProperty("email"));
		loginPage.enterPassword(prop.getProperty("password"));
		loginPage.clickOnLoginButton();
		Thread.sleep(3000);
		driver = navigateBack(driver);
		loginPage = new LoginPage(driver);
		accountPage = loginPage.clickOnMyAccountRightColumnOption();
		Assert.assertTrue(accountPage.isUserLoggedIn());

	}

	@Test(priority = 10)
	public void verifyBrowserBackAfterLoggingOut() {

		loginPage.enterEmail(prop.getProperty("email"));
		loginPage.enterPassword(prop.getProperty("password"));
		accountPage = loginPage.clickOnLoginButton();
		accountPage.clickOnLogoutOption();
		driver = navigateBack(driver);
		accountPage = new AccountPage(driver);
		accountPage.clickOnEditYourAccountInformationOption();
		loginPage = new LoginPage(driver);
		Assert.assertTrue(loginPage.didWeNavigateToLoginPage());

	}

	@Test(priority = 11)
	public void verifyLoginWithInactiveCredentials() {

		loginPage.enterEmail(prop.getProperty("inactiveEmail"));
		loginPage.enterPassword(prop.getProperty("validPassword"));
		loginPage.clickOnLoginButton();
		String expectedWarning = "Warning: No match for E-Mail Address and/or Password.";
		Assert.assertEquals(loginPage.getWarningMessage(), expectedWarning);

	}

	@Test(priority = 12)
	public void verifyNumberOfUnsuccessfulLoginAttemps() {

		loginPage.enterEmail(CommonUtils.generateNewEmail());
		loginPage.enterPassword(prop.getProperty("validPassword"));
		String expectedWarning = "Warning: No match for E-Mail Address and/or Password.";
		loginPage.clickOnLoginButton();
		Assert.assertEquals(loginPage.getWarningMessage(), expectedWarning);
		loginPage.clickOnLoginButton();
		Assert.assertEquals(loginPage.getWarningMessage(), expectedWarning);
		loginPage.clickOnLoginButton();
		Assert.assertEquals(loginPage.getWarningMessage(), expectedWarning);
		loginPage.clickOnLoginButton();
		Assert.assertEquals(loginPage.getWarningMessage(), expectedWarning);
		loginPage.clickOnLoginButton();
		Assert.assertEquals(loginPage.getWarningMessage(), expectedWarning);
		loginPage.clickOnLoginButton();
		expectedWarning = "Warning: Your account has exceeded allowed number of login attempts. Please try again in 1 hour.";
		Assert.assertEquals(loginPage.getWarningMessage(), expectedWarning);

	}

	@Test(priority = 13)
	public void verifyTextEnteredIntoPasswordFieldIsToggledToHideItsVisibility() {

		String expectedType = "password";
		Assert.assertEquals(loginPage.getPasswordFieldType(), expectedType);

	}

	@Test(priority = 14)
	public void verifyCopyingOfTextEnteredIntoPasswordField() {

		String passwordText = prop.getProperty("samplePassword");
		loginPage.enterPassword(passwordText);
		driver = loginPage.selectPasswordFieldTextAndCopy(driver);
		loginPage.pasteCopiedPasswordTextIntoEmailField(driver);
		Assert.assertNotEquals(loginPage.getTextCopiedIntoEmailField(), passwordText);
	}

	@Test(priority = 15)
	public void verifyPasswordIsStoredInHTMLCodeOfThePage() {
		String passwordText = prop.getProperty("samplePassword");
		loginPage.enterPassword(passwordText);
		Assert.assertFalse(getHTMLCodeOfThePage().contains(passwordText));
		loginPage.clickOnLoginButton();
		Assert.assertFalse(getHTMLCodeOfThePage().contains(passwordText));
	}

	@Test(priority = 16)
	public void verifyLoggingIntoApplicationAfterChaningPassword() {

		String oldPassword = null;
		String newPassword = null;

		oldPassword = prop.getProperty("validPassword3");
		newPassword = prop.getProperty("samplePassword3");
		loginPage.enterEmail(prop.getProperty("emailForLogin"));
		loginPage.enterPassword(oldPassword);
		accountPage = loginPage.clickOnLoginButton();
		changePasswordPage = accountPage.clickOnChangeYourPasswordOption();
		changePasswordPage.enterPassword(newPassword);
		changePasswordPage.enterConfirmPassword(newPassword);
		accountPage = changePasswordPage.clickOnContinueButton();
		String expectedMessage = "Success: Your password has been successfully updated.";
		Assert.assertEquals(accountPage.getMessage(), expectedMessage);
		accountLogoutPage = accountPage.clickOnLogoutOption();
		accountLogoutPage.clickOnMyAccountDropMenu();
		loginPage = accountLogoutPage.selectLoginOption();
		loginPage.enterEmail(prop.getProperty("emailForLogin"));
		loginPage.enterPassword(oldPassword);
		loginPage.clickOnLoginButton();
		String expectedWarning = "Warning: No match for E-Mail Address and/or Password.";
		Assert.assertEquals(loginPage.getWarningMessage(), expectedWarning);
		loginPage.clearPassword();
		loginPage.enterPassword(newPassword);
		loginPage.clickOnLoginButton();
		Assert.assertTrue(accountPage.isUserLoggedIn());
		CommonUtils.setUpProperties("validPassword3", newPassword);
		CommonUtils.setUpProperties("samplePassword3", oldPassword);

	}

	@Test(priority = 17)
	public void verifyNavigatingToDifferentPageFromLoginPage() {
		
		driver = loginPage.getDriver();
		headeroptions= new HeaderOptions(driver);
		contactUsPage = headeroptions.selectPhoneIconOption();
		Assert.assertTrue(contactUsPage.didWeNavigateToContactUsPage());
		driver = navigateBack(driver);
		
		loginPage = new LoginPage(driver);
		driver = loginPage.getDriver();
		headeroptions= new HeaderOptions(driver);
		loginPage = headeroptions.selectHeartIconOption();
		Assert.assertTrue(loginPage.didWeNavigateToLoginPage());
		
		loginPage = new LoginPage(driver);
		driver = loginPage.getDriver();
		headeroptions = new HeaderOptions(driver);
		shoppingCartPage = headeroptions.selectShoppingCartOption();
		Assert.assertTrue(shoppingCartPage.didWeNavigateToShoppingCartPage());
		driver = navigateBack(driver);
		
		loginPage = new LoginPage(driver);
		driver = loginPage.getDriver();
		headeroptions = new HeaderOptions(driver);
		shoppingCartPage = headeroptions.selectCheckoutOption();
		Assert.assertTrue(shoppingCartPage.didWeNavigateToShoppingCartPage());
		driver = navigateBack(driver);

		loginPage = new LoginPage(driver);
		driver = loginPage.getDriver();
		headeroptions = new HeaderOptions(driver);
		headeroptions.selectLogoOption();
		Assert.assertEquals(driver.getCurrentUrl(), prop.getProperty("landingPageURL"));
		driver = navigateBack(driver);
		
		loginPage = new LoginPage(driver);
		driver = loginPage.getDriver();
		headeroptions = new HeaderOptions(driver);
		searchPage = headeroptions.clickOnSearchButton();
		Assert.assertTrue(searchPage.didWeNavigetToSearchPage());
		driver = navigateBack(driver);
		
		loginPage = new LoginPage(driver);
		loginPage = loginPage.clickOnLoginBreadcrumb();
		Assert.assertTrue(loginPage.didWeNavigateToLoginPage());

		loginPage = new LoginPage(driver);
		driver = loginPage.getDriver();
		headeroptions = new HeaderOptions(driver);
		loginPage = headeroptions.clickOnAccountBreadcrumb();
		Assert.assertTrue(loginPage.didWeNavigateToLoginPage());
		
		loginPage = new LoginPage(driver);
		driver = loginPage.getDriver();
		headeroptions = new HeaderOptions(driver);
		//if we are running it on chrome then use clickOnHomeBreadcrumb(); otherwise useClick......
		landingPage = headeroptions.clickOnHomeBresdcrumb();
		Assert.assertEquals(getPageURL(driver), prop.getProperty("landingPageURL"));
		driver = navigateBack(driver);

		loginPage = new LoginPage(driver);
		registerPage = loginPage.clickOnContinueButoon();
		Assert.assertTrue(registerPage.didWeNavigateToRegisterAccountPage());
		driver = navigateBack(driver);
		
		loginPage = new LoginPage(driver);
		forgottenPasswordPage = loginPage.clickOnForgottenPasswordLink();
		Assert.assertTrue(forgottenPasswordPage.didWeNavigateToForgottenPasswordPage());
		driver = navigateBack(driver);
		
		loginPage = new LoginPage(driver);
	    loginPage.clickOnLoginButton();
		Assert.assertTrue(loginPage.didWeNavigateToLoginPage());
		
		loginPage = new LoginPage(driver);
		driver = loginPage.getDriver();
		rightColumnOptions = new RightColumnOptions(driver);
		loginPage = rightColumnOptions.clickOnRightSideLoginOption();
		Assert.assertTrue(loginPage.didWeNavigateToLoginPage());
		
		loginPage = new LoginPage(driver);
		driver = loginPage.getDriver();
		rightColumnOptions = new RightColumnOptions(driver);
		registerPage = rightColumnOptions.clickOnRightSideRegisterOption();
		Assert.assertTrue(registerPage.didWeNavigateToRegisterAccountPage());
		driver = navigateBack(driver);

		loginPage = new LoginPage(driver);
		driver = loginPage.getDriver();
		rightColumnOptions = new RightColumnOptions(driver);
		forgottenPasswordPage = rightColumnOptions.clickOnRightSideForgottenPassswordOption();
		Assert.assertTrue(forgottenPasswordPage.didWeNavigateToForgottenPasswordPage());
		driver = navigateBack(driver);

		loginPage = new LoginPage(driver);
		driver = loginPage.getDriver();
		rightColumnOptions = new RightColumnOptions(driver);
		loginPage = rightColumnOptions.clickOnRightSideMyAccountOption();
		Assert.assertTrue(loginPage.didWeNavigateToLoginPage());

		loginPage = new LoginPage(driver);
		driver = loginPage.getDriver();
		rightColumnOptions = new RightColumnOptions(driver);
		loginPage = rightColumnOptions.clickOnRightSideAddressBookOption();
		Assert.assertTrue(loginPage.didWeNavigateToLoginPage());

		loginPage = new LoginPage(driver);
		driver = loginPage.getDriver();
		rightColumnOptions = new RightColumnOptions(driver);
		loginPage = rightColumnOptions.clickOnRightSideWishListOption();
		Assert.assertTrue(loginPage.didWeNavigateToLoginPage());
		driver = navigateBack(driver);

		loginPage = new LoginPage(driver);
		driver = loginPage.getDriver();
		rightColumnOptions = new RightColumnOptions(driver);
		rightColumnOptions.clickOnRightSideOrderHistoryOption();
		Assert.assertTrue(loginPage.didWeNavigateToLoginPage());

		loginPage = new LoginPage(driver);
		driver = loginPage.getDriver();
		rightColumnOptions = new RightColumnOptions(driver);
		rightColumnOptions.clickOnRightSideDownloadsOption();
		Assert.assertTrue(loginPage.didWeNavigateToLoginPage());

		loginPage = new LoginPage(driver);
		driver = loginPage.getDriver();
		rightColumnOptions = new RightColumnOptions(driver);
		rightColumnOptions.clickOnRightSideRecurringPaymentsOption();
		Assert.assertTrue(loginPage.didWeNavigateToLoginPage());

		loginPage = new LoginPage(driver);
		driver = loginPage.getDriver();
		rightColumnOptions = new RightColumnOptions(driver);
		rightColumnOptions.clickOnRightSideRewardPointsOption();
		Assert.assertTrue(loginPage.didWeNavigateToLoginPage());

		loginPage = new LoginPage(driver);
		driver = loginPage.getDriver();
		rightColumnOptions = new RightColumnOptions(driver);
		rightColumnOptions.clickOnRightSideReturnsOption();
		Assert.assertTrue(loginPage.didWeNavigateToLoginPage());

		loginPage = new LoginPage(driver);
		driver = loginPage.getDriver();
		rightColumnOptions = new RightColumnOptions(driver);
		rightColumnOptions.clickOnRightSideTransactionsOption();
		Assert.assertTrue(loginPage.didWeNavigateToLoginPage());

		loginPage = new LoginPage(driver);
		driver = loginPage.getDriver();
		rightColumnOptions = new RightColumnOptions(driver);
		rightColumnOptions.clickOnRightSideNewsletterOption();
		Assert.assertTrue(loginPage.didWeNavigateToLoginPage());

		loginPage = new LoginPage(driver);
		driver = loginPage.getDriver();
		footerOptions = new FooterOptions(driver);
		aboutUsPage = footerOptions.clickOnAboutUsFooterOption();
		Assert.assertTrue(aboutUsPage.didWeNavigateToAboutUsBreadcrumb());
		driver = navigateBack(driver);

		loginPage = new LoginPage(driver);
		driver = loginPage.getDriver();
		footerOptions = new FooterOptions(driver);
		deliveryInformationPage = footerOptions.clickOnDeliveryInformationOption();
		Assert.assertTrue(deliveryInformationPage.didWeNavigateToDeliveryInformationPage());
		driver = navigateBack(driver);

		loginPage = new LoginPage(driver);
		driver = loginPage.getDriver();
		footerOptions = new FooterOptions(driver);
		privacyPolicyPage = footerOptions.clickOnPrivacyPolicyFooterOption();
		Assert.assertTrue(privacyPolicyPage.didWeNavi8gateToPrivacyPolicyPage());
		driver = navigateBack(driver);

		loginPage = new LoginPage(driver);
		driver = loginPage.getDriver();
		footerOptions = new FooterOptions(driver);
		termsAndConditionsPage = footerOptions.clickOnTermAndConditionsFooterOption();
		Assert.assertTrue(termsAndConditionsPage.didWeNavigateToTermsAndConditionsPage());
		driver = navigateBack(driver);

		loginPage = new LoginPage(driver);
		driver = loginPage.getDriver();
		footerOptions = new FooterOptions(driver);
		contactUsPage = footerOptions.clickOnContactUsFooterOption();
		Assert.assertTrue(contactUsPage.didWeNavigateToContactUsPage());
		driver = navigateBack(driver);

		loginPage = new LoginPage(driver);
		driver = loginPage.getDriver();
		footerOptions = new FooterOptions(driver);
		productReturnsPage = footerOptions.clickOnReturnsFooterOption();
		Assert.assertTrue(productReturnsPage.didWeNavigateToProductReturnsPage());
		driver = navigateBack(driver);

		loginPage = new LoginPage(driver);
		driver = loginPage.getDriver();
		footerOptions = new FooterOptions(driver);
		siteMapPage = footerOptions.clickOnSiteMapFooterOption();
		Assert.assertTrue(siteMapPage.didWeNavigateToSiteMapPage());
		driver = navigateBack(driver);

		loginPage = new LoginPage(driver);
		driver = loginPage.getDriver();
		footerOptions = new FooterOptions(driver);
		brandsPage = footerOptions.clickOnBrandsFooterOption();
		Assert.assertTrue(brandsPage.didWeNavigateToBrandsPage());
		driver = navigateBack(driver);

		loginPage = new LoginPage(driver);
		driver = loginPage.getDriver();
		footerOptions = new FooterOptions(driver);
		giftCertificates = footerOptions.clickOnGiftCertificatesFooterOption();
		Assert.assertTrue(giftCertificates.didWeNavigateToGiftCertificatesPage());
		driver = navigateBack(driver);

		loginPage = new LoginPage(driver);
		driver = loginPage.getDriver();
		footerOptions = new FooterOptions(driver);
		affiliateLoginPage = footerOptions.clickOnAffiliateFooterOption();
		Assert.assertEquals(driver.getCurrentUrl(), prop.getProperty("affiliateLoginPageURL"));
		driver = navigateBack(driver);

		loginPage = new LoginPage(driver);
		driver = loginPage.getDriver();
		footerOptions = new FooterOptions(driver);
		specialOffersPage = footerOptions.clickOnSpecialsFooterOption();
		Assert.assertTrue(specialOffersPage.didWeNavigateToSpecialOffersPage());
		driver = navigateBack(driver);

		loginPage = new LoginPage(driver);
		driver = loginPage.getDriver();
		footerOptions = new FooterOptions(driver);
		footerOptions.clickOnMyAccountFooterOption();
		Assert.assertTrue(loginPage.didWeNavigateToLoginPage());

		loginPage = new LoginPage(driver);
		driver = loginPage.getDriver();
		footerOptions = new FooterOptions(driver);
		footerOptions.clickOnOrderHistoryFooterOption();
		Assert.assertTrue(loginPage.didWeNavigateToLoginPage());

		loginPage = new LoginPage(driver);
		driver = loginPage.getDriver();
		footerOptions = new FooterOptions(driver);
		footerOptions.clickOnWishListFooterOption();
		Assert.assertTrue(loginPage.didWeNavigateToLoginPage());

		loginPage = new LoginPage(driver);
		driver = loginPage.getDriver();
		footerOptions = new FooterOptions(driver);
		loginPage = footerOptions.clickOnNewsletterFooterOption();
		Assert.assertTrue(loginPage.didWeNavigateToLoginPage());

	}
	
	@Test(priority = 18)
	public void verifyDifferentWayOfNavigatingToLoginPage() {
		
		registerPage = loginPage.clickOnContinueButoon();
		loginPage = registerPage.clickOnLoginPageLink();
		Assert.assertTrue(loginPage.didWeNavigateToLoginPage());
		driver = loginPage.getDriver();
		rightColumnOptions = new RightColumnOptions(driver);
		loginPage = rightColumnOptions.clickOnRightSideLoginOption();
		Assert.assertTrue(loginPage.didWeNavigateToLoginPage());
		driver = loginPage.getDriver();
		headeroptions = new HeaderOptions(driver);
		headeroptions.clickOnMyAccountDropMenu();
		loginPage = headeroptions.selectLoginOption();
		Assert.assertTrue(loginPage.didWeNavigateToLoginPage());
		
	}
	
	@Test(priority = 19)
	public void verifyBreadcrumbPageHeadingTitleAndPageURLOfLoginPage(){
		
	Assert.assertTrue(loginPage.didWeNavigateToLoginPage());
	Assert.assertEquals(getPageTitle(driver), prop.getProperty("loginPageTitle"));
	Assert.assertEquals(getPageURL(driver), prop.getProperty("loginPageURL"));//h2[normalize-space()='New Customer']
	Assert.assertEquals(loginPage.getPageHeadingOne(), prop.getProperty("registerPageHeadingOne"));
	Assert.assertEquals(loginPage.getPageHeadingTwo(), prop.getProperty("registerPageHeadingTwo"));
		
	}
	
	@Test(priority = 20)
	public void verifyUIOfLoginPage() {
		
		if(prop.getProperty("browserName").equals("chrome")) {
		CommonUtils.takeScreenshot(driver, "\\src\\Screenshot\\actualLoginPageUI.png");
		Assert.assertFalse(CommonUtils.compareTwoScreenshots(System.getProperty("user.dir") + "\\src\\Screenshot\\actualLoginPageUI.png",System.getProperty("user.dir") + "\\src\\Screenshot\\expectedLoginPageUI.png"));
		}else if(prop.getProperty("browserName").equals("edge")) {
			CommonUtils.takeScreenshot(driver, "\\src\\Screenshot\\actualEdgeLoginPageUI.png");
			Assert.assertFalse(CommonUtils.compareTwoScreenshots(System.getProperty("user.dir") + "\\src\\Screenshot\\actualEdgeLoginPageUI.png",System.getProperty("user.dir") + "\\src\\Screenshot\\expectedEdgeLoginPageUI.png"));
		}else if(prop.getProperty("browserName").equals("firefox")) {
			CommonUtils.takeScreenshot(driver, "\\src\\Screenshot\\actualfirefoxLoginPageUI.png");
			Assert.assertFalse(CommonUtils.compareTwoScreenshots(System.getProperty("user.dir") + "\\src\\Screenshot\\actualfirefoxLoginPageUI.png",System.getProperty("user.dir") + "\\src\\Screenshot\\expectedfirefoxLoginPageUI.png"));
		}

	}
	
	@Test(priority = 21)
	public void verifyLoginFunctionalityInAllEnvioronments() {
		
		loginPage.enterEmail(prop.getProperty("existingEmail"));
		loginPage.enterPassword(prop.getProperty("validPasswordThree"));
		accountPage = loginPage.clickOnLoginButton();
		Assert.assertTrue(accountPage.isUserLoggedIn());
		Assert.assertTrue(accountPage.didWeNavigateToAccountPage());
		
	}

}
