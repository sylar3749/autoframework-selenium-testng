package Framework;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenShot {
	public WebDriver driver;
	Log log = new Log(this.getClass());

	public ScreenShot(WebDriver driver) {
		this.driver = driver;
	}

	private void dotakeScreenshot(String screenPath) {
		try {
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(screenPath));
			System.out.println(driver.getWindowHandle());
		} catch (IOException e) {
			System.out.println("Screen shot error: " + screenPath);
		}
	}

	public void takeScreenshot(String testname) {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd HH_mm_ss");
		String dateString = formatter.format(currentTime);
		String screenName = testname + "_" + dateString + ".jpg";
		File dir = new File("testResultErrorScreenshot");
		if (!dir.exists()) {
            final boolean mkdirs = dir.mkdirs();
        }
		String screenPath = dir.getAbsolutePath() + "/" + screenName;
		this.dotakeScreenshot(screenPath);
		log.error("ErrorScreenshot:" + screenPath);
	}
}