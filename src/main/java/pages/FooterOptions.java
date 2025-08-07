package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class FooterOptions {
	
	WebDriver driver;

	public FooterOptions(WebDriver driver) {

		this.driver = driver;
		PageFactory.initElements(driver, this);

	}
	
	@FindBy(linkText="About Us")
	private WebElement aboutUsFooterOption;
	
	@FindBy(linkText="Delivery Information")
	private WebElement deliveryInformationFooterOption;
	
	@FindBy(xpath="//ul[@class='list-unstyled']//a[text()='Privacy Policy']")
    private WebElement privacyPolicyFooterOption;
	
	@FindBy(linkText="Terms & Conditions")
	private WebElement termAndConditionsFooterOption;
	
	@FindBy(linkText="Contact Us")
	private WebElement contactsFooterOption;
	
	@FindBy(xpath="//ul[@class='list-unstyled']//a[text()='Returns']")
	private WebElement returnsFooterOption;
	
	@FindBy(linkText="Site Map")
	private WebElement siteMapFooterOption;
	
	@FindBy(linkText="Brands")
	private WebElement brandsFooterOption;
	
	@FindBy(xpath="//ul[@class='list-unstyled']//a[text()='Gift Certificates']")
	private WebElement giftCertificateFooterOption;
	
	@FindBy(linkText="Affiliate")
	private WebElement affiliateFooterOption;
	
	@FindBy(linkText="Specials")
	private WebElement specialsFooterOption;
	
	@FindBy(xpath="//ul[@class='list-unstyled']//a[text()='My Account']")
	private WebElement myAccountFooterOption;
	
	@FindBy(xpath="//ul[@class='list-unstyled']//a[text()='Order History']")
	private WebElement OrderHistoryFooterOption;
	
	@FindBy(xpath="//ul[@class='list-unstyled']//a[text()='Wish List']")
	private WebElement WishListFooterOption;
	
	@FindBy(xpath="//ul[@class='list-unstyled']//a[text()='Newsletter']")
	private WebElement newsletterFooterOption;
	
	
	public LoginPage clickOnNewsletterFooterOption() {
		newsletterFooterOption.click();
		return new LoginPage(driver);
	}
	
	public LoginPage clickOnWishListFooterOption() {
		WishListFooterOption.click();
		return new LoginPage(driver);
	}
	
	public LoginPage clickOnOrderHistoryFooterOption() {
		OrderHistoryFooterOption.click();
		return new LoginPage(driver);
	}
	
	public LoginPage clickOnMyAccountFooterOption() {
		myAccountFooterOption.click();
		return new LoginPage(driver);
	}
	
	public SpecialOffersPage clickOnSpecialsFooterOption() {
		specialsFooterOption.click();
		return new SpecialOffersPage(driver);
	}
	
	public AffiliateLoginPage clickOnAffiliateFooterOption() {
		affiliateFooterOption.click();
		return new AffiliateLoginPage(driver);
	}
	
	public GiftCertificatesPage clickOnGiftCertificatesFooterOption() {
		giftCertificateFooterOption.click();
		return new GiftCertificatesPage(driver);
	}
	
	public BrandsPage clickOnBrandsFooterOption() {
		brandsFooterOption.click();
		return new BrandsPage(driver);
	}

	public SiteMapPage clickOnSiteMapFooterOption() {
		siteMapFooterOption.click();
		return new SiteMapPage(driver);
	}
	
	public ProductReturnsPage clickOnReturnsFooterOption() {
		returnsFooterOption.click();
		return new ProductReturnsPage(driver);
	}
	
	public ContactUsPage clickOnContactUsFooterOption() {
		contactsFooterOption.click();
		return new ContactUsPage(driver);
	}
	
	public TermsAndConditionsPage clickOnTermAndConditionsFooterOption() {
		termAndConditionsFooterOption.click();
		return new TermsAndConditionsPage(driver);
	}
	
	public PrivacyPolicyPage clickOnPrivacyPolicyFooterOption() {
		privacyPolicyFooterOption.click();
		return new PrivacyPolicyPage(driver);
	}
	
	public DeliveryInformationPage clickOnDeliveryInformationOption() {
		deliveryInformationFooterOption.click();
		return new DeliveryInformationPage(driver);
	}
	
	
	public AboutUsPage clickOnAboutUsFooterOption() {
		aboutUsFooterOption.click();
		return new AboutUsPage(driver);
	}

}
