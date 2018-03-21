package Framework;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class ErrorListener extends TestListenerAdapter {

	Log log = new Log(this.getClass());

	@Override
	public void onTestStart(ITestResult tr) {
		super.onTestStart(tr);
		log.info("Start testing: " + tr.getName());
	}

	@Override
	public void onTestFailure(ITestResult tr) {

		log.error(tr.getName() + " executed failed");
		try {
			BaseTest tb = (BaseTest) tr.getInstance();
			WebDriver driver = tb.getDriver();
			ScreenShot a = new ScreenShot(driver);
			a.takeScreenshot(tr.getName());
//			driver.close();
//			driver.quit();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		super.onTestFailure(tr);

	}

	@Override
	public void onTestSkipped(ITestResult tr) {
		super.onTestSkipped(tr);
		log.info(tr.getName() + " skipped");
	}

	@Override
	public void onTestSuccess(ITestResult tr) {
		super.onTestSuccess(tr);
		log.info(tr.getName() + " executed successfully");
	}
}
