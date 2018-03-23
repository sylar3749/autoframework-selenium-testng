package Framework;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;


public class Init {

	public Log log = new Log(this.getClass());

	public void doLogin(WebDriver dr, String url, String username, String password) {
		dr.manage().window().maximize();
		log.info("Open URL: " + url);
		dr.get(url);
		log.info("Sign in with username: " + username + " & password: " + password);
	}

	public WebDriver initAndLogin(String browser, String url, String username, String password) {
		switch (browser) {
			case "IE": {
				File driverpath = new File("driver/IEDriverServer.exe");
				System.setProperty("webdriver.ie.driver", driverpath.getAbsolutePath());
				WebDriver driver = new InternetExplorerDriver();
				driver.manage().window().maximize();
				log.info("Launch IE");
				doLogin(driver, url, username, password);
				return driver;
			}
			case "Firefox": {
				ProfilesIni pi = new ProfilesIni();
				FirefoxProfile profile = pi.getProfile("default");
				WebDriver driver = new FirefoxDriver(profile);
				driver.manage().window().maximize();
				log.info("Launch Firefox");
				doLogin(driver, url, username, password);
				return driver;
			}
			case "Chrome": {
				File driverpath = new File("driver/chromedriver.exe");
				System.setProperty("webdriver.chrome.driver", driverpath.getAbsolutePath());
				ChromeOptions options = new ChromeOptions();
				//通过配置参数禁止data;的出现
				//options.addArguments("--user-data-dir=G:/Users/Sylar/AppData/Local/Google/Chrome/User Data/Default");

				options.setExperimentalOption("useAutomationExtension", false);
				DesiredCapabilities capbility = DesiredCapabilities.chrome();
				capbility.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				capbility.setCapability(ChromeOptions.CAPABILITY, options);
				WebDriver driver = new ChromeDriver(options);
				driver.manage().window().maximize();
				log.debug("Launch Chrome");
				doLogin(driver, url, username, password);
				return driver;
			}

			default: {
				log.error("Explorer not supported, please use IE, Firefox or Chrome");
				return null;
			}
		}
	}

	public WebDriver init(String browser, String url) {
		switch (browser) {
			case "IE": {
				File driverpath = new File("driver/IEDriverServer.exe");
				System.setProperty("webdriver.ie.driver", driverpath.getAbsolutePath());
				WebDriver driver = new InternetExplorerDriver();
				driver.manage().window().maximize();
				log.info("Launch IE");
				driver.get(url);
				log.info("Open URL: " + url);
				return driver;
			}
			case "Firefox": {
				ProfilesIni pi = new ProfilesIni();
				FirefoxProfile profile = pi.getProfile("default");
				WebDriver driver = new FirefoxDriver(profile);
				driver.manage().window().maximize();
				log.info("Launch Firefox");
				driver.get(url);
				log.info("Open URL: " + url);
				return driver;
			}
			case "Chrome": {
				File driverpath = new File("driver/chromedriver.exe");
				System.setProperty("webdriver.chrome.driver", driverpath.getAbsolutePath());
				ChromeOptions options = new ChromeOptions();
				//通过配置参数禁止data;的出现
				//options.addArguments("--user-data-dir=G:/Users/Sylar/AppData/Local/Google/Chrome/User Data/Default");

				options.setExperimentalOption("useAutomationExtension", false);
				DesiredCapabilities capbility = DesiredCapabilities.chrome();
				capbility.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				capbility.setCapability(ChromeOptions.CAPABILITY, options);
				WebDriver driver = new ChromeDriver(options);
				driver.manage().window().maximize();
				log.info("Launch Chrome");
				driver.get(url);
				log.info("Open URL: " + url);
				return driver;
			}

			default: {
				log.error("Explorer not supported, please use IE, Firefox or Chrome");
				return null;
			}
		}
	}

}