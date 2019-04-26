package autoframework;

import org.openqa.selenium.WebDriver;
import org.testng.asserts.Assertion;
import org.apache.log4j.Logger;

public class BaseTest {

	protected static final String BOWER_FIREFOX = "Firefox";
	protected static final String BOWER_CHROME = "Chrome";
	protected static final String BOWER_IE = "IE";
	protected static final String URL = "https://test.salesforce.com/";

	public final Logger log = Logger.getLogger(BaseTest.class);

	protected static WebDriver driver;

	protected static Assertion ae = new Assertion();

	protected static Init init = new Init();

	public WebDriver getDriver() {
		return driver;
	}
}
