package Framework;

import org.openqa.selenium.WebDriver;

public class BaseTest {

	public static final String BOWER_FIREFOX = "Firefox"; // "Chrome";"Firefox";
	public static final String BOWER_CHROME = "Chrome"; // "Chrome";"Firefox";
	public static final String BOWER_IE = "IE"; // "Chrome";"Firefox";
	public final Log log = new Log(this.getClass());

	protected static WebDriver driver;

	protected static Assertion ae = new Assertion();

	protected static Init init = new Init();

	public WebDriver getDriver() {
		return driver;
	}

	public void waitMs(int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
