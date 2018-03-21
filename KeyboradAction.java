package Framework;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

public class KeyboradAction {

	private WebDriver driver;

	public KeyboradAction(WebDriver dr) {
		this.driver = dr;
	}

	public void pressKeyborad(String key) {
		Actions action = new Actions(driver);
		String keyword = "Keys." + key;
		action.sendKeys(keyword);
	}

}
