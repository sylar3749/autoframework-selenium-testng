package Framework;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;


@SuppressWarnings("ALL")
public class Init {

	public Log log = new Log(this.getClass());

	private WebDriver getIEDriver(String url) {
		File driverpath = new File("driver/IEDriverServer.exe");
		System.setProperty("webdriver.ie.driver", driverpath.getAbsolutePath());
		DesiredCapabilities capbility = DesiredCapabilities.internetExplorer();
		capbility.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		capbility.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
		capbility.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		WebDriver driver = new InternetExplorerDriver(capbility);
		log.info("Launch IE");
		driver.get(url);
		log.info("Open URL: " + url);
		driver.manage().window().maximize();
		return driver;
	}

	private WebDriver getFirefoxDriver(String url) {
		File driverpath = new File("driver/geckodriver.exe");
		System.setProperty("webdriver.gecko.driver", driverpath.getAbsolutePath());
		File pathBinary = new File("C:/Program Files/Mozilla Firefox/firefox.exe");
		FirefoxBinary ffBinary = new FirefoxBinary(pathBinary);
		FirefoxProfile ffProfile = new FirefoxProfile();
		// disable untrusted certification check
		ffProfile.setAcceptUntrustedCertificates(true);
		DesiredCapabilities capbility = DesiredCapabilities.firefox();
		capbility.setCapability(FirefoxDriver.PROFILE, ffProfile);
		FirefoxOptions ffOption = new FirefoxOptions().setBinary(ffBinary).setProfile(ffProfile)
				.addCapabilities(capbility);
		WebDriver driver = new FirefoxDriver(ffOption);
		log.info("Launch Firefox");
		driver.get(url);
		log.info("Open URL: " + url);
		driver.manage().window().maximize();
		return driver;
	}

	private WebDriver getChromeDriver(String url) {
		File driverpath = new File("driver/chromedriver.exe");
		System.setProperty("webdriver.chrome.driver", driverpath.getAbsolutePath());
		ChromeOptions options = new ChromeOptions();
		//To prevent data;
		//options.addArguments("--user-data-dir=C:/Users/swan004/AppData/Local/Google/Chrome/User Data/Default");
		options.setExperimentalOption("useAutomationExtension", false);
		DesiredCapabilities capbility = DesiredCapabilities.chrome();
		capbility.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		capbility.setCapability(ChromeOptions.CAPABILITY, options);
		WebDriver driver = new ChromeDriver(options);
		log.info("Launch Chrome");
		driver.get(url);
		log.info("Open URL: " + url);
		driver.manage().window().maximize();
		return driver;
	}

	@org.jetbrains.annotations.NotNull
	public static WebDriver getSafariDriver() {
		DesiredCapabilities safariCapabilities = DesiredCapabilities.safari();
		SafariOptions options = new SafariOptions();
		safariCapabilities.setCapability(SafariOptions.CAPABILITY, options);
		return new SafariDriver(safariCapabilities);
	}

	@org.jetbrains.annotations.NotNull
	private static WebDriver getAndroidDriver() throws MalformedURLException {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(CapabilityType.BROWSER_NAME, "Chrome");
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("deviceName", "Galaxy S10");
		capabilities.setCapability("platformVersion", "8.0");
		capabilities.setCapability("newCommandTimeout", "300");
		return new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
	}

	@org.jetbrains.annotations.NotNull
	private static WebDriver getIOSDriver() throws MalformedURLException {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("deviceName", "iPhone 8");
		capabilities.setCapability("platformName", "iOS");
		capabilities.setCapability("platformVersion", "11.0");
		capabilities.setCapability("browserName", "safari");
		return new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
	}

	public WebDriver init(String browser, String url) {
		switch (browser) {
		case "IE": {
			getIEDriver(url);
		}
		case "Firefox": {
			getFirefoxDriver(url);
		}
		case "Chrome": {
			getChromeDriver(url);
		}
		default: {
			log.error("Explorer not supported, please use IE, Firefox or Chrome");
			return null;
		}
		}
	}

}
