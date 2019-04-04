package autoframework;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.util.Map;
import org.testng.annotations.AfterTest;

public class TestCase extends BaseTest {
	
	@BeforeTest
	public void BeforeTest() {
		driver = init.init(BOWER_CHROME, URL);
	}
	
	@Test(dataProvider = "mapDataProvider", dataProviderClass = ExcelUtil.class)
	public void Test_WithDataDriven(Map<String, String> map) {
		BasePage base = new BasePage(driver);
		base.type("login_UserName", map.get("Username"));
		base.type("login_Password", map.get("Password"));
		base.click("login_LogIn");
	}
		
	@AfterTest
	public void AfterTest() {
		driver.close();
	}
}
