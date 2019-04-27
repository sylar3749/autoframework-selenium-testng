import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.io.File;
import org.apache.log4j.Logger;

public class Init {
	public final Logger log = Logger.getLogger(BaseTest.class);

	/**
	 * Open a new explorer which does not require credentials
	 * 
	 * @param browser Browser Type = IE, Chrome or Firefox
	 * @param url
	 * @return driver
	 */
	public WebDriver init(String browser, String url) {
		switch (browser) {
		case "IE": {
			File driverpath = new File("driver/IEDriverServer.exe");
			System.setProperty("webdriver.ie.driver", driverpath.getAbsolutePath());
			WebDriver driver = new InternetExplorerDriver();
			driver.manage().window().maximize();
			log.info("\tLaunch IE");
			driver.get(url);
			log.info("\tOpen URL: " + url);
			return driver;
		}
		case "Firefox": {
			ProfilesIni pi = new ProfilesIni();
			FirefoxProfile profile = pi.getProfile("default");
			WebDriver driver = new FirefoxDriver(profile);
			driver.manage().window().maximize();
			log.info("\tLaunch Firefox");
			driver.get(url);
			log.info("\tOpen URL: " + url);
			return driver;
		}
		case "Chrome": {
			File driverpath = new File("driver/chromedriver.exe");
			System.setProperty("webdriver.chrome.driver", driverpath.getAbsolutePath());
			ChromeOptions options = new ChromeOptions();
			options.setExperimentalOption("useAutomationExtension", false);
			DesiredCapabilities capability = DesiredCapabilities.chrome();
			/* capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true); */
			capability.setCapability(ChromeOptions.CAPABILITY, options);
			WebDriver driver = new ChromeDriver(options);
			driver.manage().window().maximize();
			log.info("\tLaunch Chrome");
			driver.get(url);
			log.info("\tOpen URL: " + url);
			return driver;
		}
		default: {
			log.error("\tExplorer not supported, please use IE, Firefox or Chrome");
			return null;
		}
		}
	}

	/**
	 * Combine a url with related username and password
	 * 
	 * @param dr
	 * @param protocol Protocol = http or https
	 * @param url
	 * @param username
	 * @param password
	 */
	public void doLogin(WebDriver dr, String protocol, String url, String username, String password) {
		String URL = protocol + "://" + username + ":" + password + "@" + url + "/";
		log.info("\tOpen URL: " + URL);
		dr.get(URL);
		log.info("\tSign in with username: " + username + " & password: " + password);
	}

	/**
	 * Open a new explorer which requires credentials
	 * 
	 * @param browser  Browser Type = IE, Chrome or Firefox
	 * @param protocol Protocol = http or https
	 * @param url
	 * @param username
	 * @param password
	 * @return driver
	 */
	public WebDriver initAndLogin(String browser, String protocol, String url, String username, String password) {
		switch (browser) {
		case "IE": {
			File driverpath = new File("driver/IEDriverServer.exe");
			System.setProperty("webdriver.ie.driver", driverpath.getAbsolutePath());
			WebDriver driver = new InternetExplorerDriver();
			driver.manage().window().maximize();
			log.info("\tLaunch IE");
			doLogin(driver, protocol, url, username, password);
			return driver;
		}
		case "Firefox": {
			ProfilesIni pi = new ProfilesIni();
			FirefoxProfile profile = pi.getProfile("default");
			WebDriver driver = new FirefoxDriver(profile);
			driver.manage().window().maximize();
			log.info("\tLaunch Firefox");
			doLogin(driver, protocol, url, username, password);
			return driver;
		}
		case "Chrome": {
			File driverpath = new File("driver/chromedriver.exe");
			System.setProperty("webdriver.chrome.driver", driverpath.getAbsolutePath());
			ChromeOptions options = new ChromeOptions();
			options.setExperimentalOption("useAutomationExtension", false);
			DesiredCapabilities capbility = DesiredCapabilities.chrome();
			/* capbility.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true); */
			capbility.setCapability(ChromeOptions.CAPABILITY, options);
			WebDriver driver = new ChromeDriver(options);
			driver.manage().window().maximize();
			log.info("\tLaunch Chrome");
			doLogin(driver, protocol, url, username, password);
			return driver;
		}

		default: {
			log.error("\tExplorer not supported, please use IE, Firefox or Chrome");
			return null;
		}
		}
	}
}
