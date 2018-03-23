package Framework;

import org.ho.yaml.Yaml;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class BasePage {

	public WebDriver driver;
	private String yamlFile;
	public Log log = new Log(this.getClass());

	protected static Assertion ae = new Assertion();

	protected BasePage(WebDriver dr1) {
		this.driver = dr1;
		yamlFile = "element";
		this.getYamlFile();
	}

	public HashMap<String, HashMap<String, String>> ml;

	private HashMap<String, HashMap<String, String>> extendLocator;

	@SuppressWarnings("unchecked")
	private void getYamlFile() {
		File f = new File("testData/" + yamlFile + ".yaml");
		try {
			ml = Yaml.loadType(new FileInputStream(f.getAbsolutePath()), HashMap.class);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private void loadExtendLocator(String fileName) {
		File f = new File("locator/" + fileName + ".yaml");
		try {
			extendLocator = Yaml.loadType(new FileInputStream(f.getAbsolutePath()), HashMap.class);
			ml.putAll(extendLocator);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void setLocatorVariableValue(String variable, String value) {
		Set<String> keys = ml.keySet();
		for (String key : keys) {
			String v = ml.get(key).get("value").replaceAll("%" + variable + "%", value);
			ml.get(key).put("value", v);
		}
	}

	private String getLocatorString(String locatorString, String[] ss) {
		for (String s : ss) {
			locatorString = locatorString.replaceFirst("%s", s);
		}
		return locatorString;
	}

	private By getBy(String type, String value) {
		By by = null;
		if (type.equals("id")) {
			by = By.id(value);
		}
		if (type.equals("name")) {
			by = By.name(value);
		}
		if (type.equals("xpath")) {
			by = By.xpath(value);
		}
		if (type.equals("className")) {
			by = By.className(value);
		}
		if (type.equals("linkText")) {
			by = By.linkText(value);
		}
		if (type.equals("tagName")) {
			by = By.tagName(value);
		}
		if (type.equals("cssSelector")) {
			by = By.cssSelector(value);
		}
		if (type.equals("partialLinkText")) {
			by = By.partialLinkText(value);
		}
		return by;
	}

	// 判断元素是否存在
	protected boolean isElementExsit(WebDriver driver, By by) {
		boolean flag = false;
		try {
			WebElement element = driver.findElement(by);
			flag = null != element;

		} catch (NoSuchElementException e) {
			log.error("Element:" + by.toString() + " is not exsit!");
		}

		return flag;
	}

	private WebElement watiForElement(final By by) {
		WebElement element = null;
		int waitTime = 10;
		try {
			element = new WebDriverWait(driver, waitTime).until(new ExpectedCondition<WebElement>() {
				public WebElement apply(WebDriver d) {
					return d.findElement(by);
				}
			});
		} catch (Exception e) {
			log.error("Element not found, timeout at： " + waitTime);
		}
		return element;
	}

	protected boolean waitFor(final WebElement element) {
		boolean wait = false;
		if (element == null)
			return false;
		try {
			wait = new WebDriverWait(driver, 10).until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver d) {
					return element.isDisplayed();
				}
			});
		} catch (Exception e) {
			log.error("element is not displayed");
		}
		return wait;
	}

	protected boolean waitFor(final By by) {
		boolean wait = false;
		if (by == null)
			return false;
		try {
			wait = new WebDriverWait(driver, 10).until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver d) {
					return driver.findElement(by).isDisplayed();
				}
			});
		} catch (Exception e) {
			log.error("element is not displayed");
		}
		return wait;
	}

	protected boolean waitFor(final String element) {
		boolean wait = false;
		if (element == null)
			return false;
		try {
			wait = new WebDriverWait(driver, 10).until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver d) {
					return getElement(element).isDisplayed();
				}
			});
		} catch (Exception e) {
			log.error("element is not displayed");
		}
		return wait;
	}

	protected boolean waitElementDisappear(final WebElement element) {
		boolean wait = false;
		if (element == null)
			return false;
		try {
			wait = new WebDriverWait(driver, 10).until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver d) {
					return !element.isDisplayed();
				}
			});
		} catch (Exception e) {
			log.error("element is still displayed");
		}
		return wait;
	}

	private WebElement getLocator(String key, String[] replace, boolean wait) {
		WebElement element = null;
		try {
			if (ml.containsKey(key)) {
				HashMap<String, String> m = ml.get(key);
				String type = m.get("type");
				String value = m.get("value");
				log.info("Looking for element:\"" + key + "\" Fuction:\"" + type + "\" Value:\"" + value + "\"");
				if (replace != null)
					value = this.getLocatorString(value, replace);
				By by = this.getBy(type, value);
				if (wait) {
					element = this.watiForElement(by);
					((JavascriptExecutor) driver).executeScript(
							"element = arguments[0];" + "original_style = element.getAttribute('style');"
									+ "element.setAttribute('style', original_style + \";"
									+ "background: yellow; border: 2px solid red;\");"
									+ "setTimeout(function(){element.setAttribute('style', original_style);}, 2000);",
							element);
					boolean flag = this.waitFor(element);
					if (!flag)
						element = null;
				} else {
					element = driver.findElement(by);
				}
			} else {
				log.error("Element \"" + key + "\"is not existed in the " + yamlFile + ".yaml file.");
			}
		} catch (Exception e) {
			return element;
		}
		return element;
	}

	protected WebElement getElement(String key) {
		waitMs(500);
		return this.getLocator(key, null, true);
	}

	protected WebElement getElement(String key1, String key2) {
		waitMs(500);
		return this.getLocator(key1 + key2, null, true);
	}

	protected String getElementText(String key) {
		WebElement element = getElement(key);
		return element == null ? null : element.getText();
	}

	protected String getElementNoWaitText(String key) {
		WebElement element = getElementNoWait(key);
		return element == null ? null : element.getText();
	}

	protected List<WebElement> getElements(String key) {
		waitMs(500);
		List<WebElement> eles = null;
		if (ml.containsKey(key)) {
			HashMap<String, String> m = ml.get(key);
			String type = m.get("type");
			String value = m.get("value");
			By by = this.getBy(type, value);
			eles = driver.findElements(by);
		} else
			log.error("Elemets \"" + key + "\" are not exist in the " + yamlFile + ".yaml file.");
		assert eles != null;
		for (WebElement e : eles) {
			((JavascriptExecutor) driver)
					.executeScript(
							"element = arguments[0];" + "original_style = element.getAttribute('style');"
									+ "element.setAttribute('style', original_style + \";"
									+ "background: yellow; border: 2px solid red;\");"
									+ "setTimeout(function(){element.setAttribute('style', original_style);}, 1000);",
							e);
		}
		return eles;
	}

	protected WebElement getElementNoWait(String key) {
		return this.getLocator(key, null, false);
	}

	protected WebElement getElement(String key, String[] replace) {
		return this.getLocator(key, replace, true);
	}

	protected WebElement getElementNoWait(String key, String[] replace) {
		return this.getLocator(key, replace, false);
	}

	protected void waitMs(int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	protected void clear(String element) {
		WebElement e = getElement(element);
		log.info("Clear the field...");
		e.clear();
	}

	protected void click(String element) {
		WebElement e = getElement(element);
		ae.assertNotNull("Button", e);
		e.click();
		log.info("Click on button");
	}

	protected void type(String element, String values) {
		WebElement e = getElement(element);
		ae.assertNotNull("Text field", e);
		log.info("Clear text field");
		e.clear();
		log.info("Input \"" + values + "\"");
		// Reporter.log("输入 【" + values + "】");
		e.sendKeys(values);
	}

	/**
	 * 使用aotuit 脚本 实现上传附件操作。
	 * 
	 * @param values String
	 * @throws IOException e
	 */
	protected void ImportScript(String values) throws IOException {

		Runtime.getRuntime().exec(values);

	}

	/**
	 * 多层嵌套frame里的元素定位时使用franmeElement 方法定位元素，没有id or name元素使用xpatn
	 * 
	 * @param element String
	 */
	protected void frameElement(String element) {
		WebElement frame = getElement(element);
		driver.switchTo().frame(frame);
	}

	protected void select(String element, String values) {

		WebElement e = getElement(element);
		log.info("Selet option: ");
		Select sel = new Select(e);
		log.info("selected \"" + values + "\"");
		sel.selectByValue(values);
	}

	protected WebElement getTotleByXpath(String key) {
		WebElement element;
		key = "//p[contains(.,'" + key + "')]";
		element = getByXpath(key);
		return element;
	}

	// Alt + 按键
	protected static void keyPressWithAlt(Robot r, int key) {
		r.keyPress(KeyEvent.VK_ALT);
		r.keyPress(key);
		r.keyRelease(key);
		r.keyRelease(KeyEvent.VK_ALT);
		r.delay(100);
	}

	// shift + 按键
	protected static void keyPressWithShift(Robot r, int key) {

		r.keyPress(KeyEvent.VK_SHIFT);
		r.keyPress(key);
		r.keyRelease(key);
		r.keyRelease(KeyEvent.VK_SHIFT);
		r.delay(100);
	}

	// ctrl+ 按键
	protected static void keyPressWithCtrl(Robot r, int key) {
		r.keyPress(KeyEvent.VK_CONTROL);
		r.keyPress(key);
		r.keyRelease(key);
		r.keyRelease(KeyEvent.VK_CONTROL);
		r.delay(100);
	}

	/**
	 * 通过xpath 的value直接调用，不介意使用 统一使用get
	 */
	protected WebElement getByXpath(String key) {
		WebElement element = null;
		if (ml.containsKey(key)) {
			HashMap<String, String> m = ml.get(key);
			// String type = m.get("type");
			String value = m.get("value");
			log.info("Find xpath element" + key + ": \"" + value + "\"");
			By by = By.xpath(value);
			try {
				element = this.watiForElement(by);
				((JavascriptExecutor) driver).executeScript(
						"element = arguments[0];" + "original_style = element.getAttribute('style');"
								+ "element.setAttribute('style', original_style + \";"
								+ "background: yellow; border: 2px solid red;\");"
								+ "setTimeout(function(){element.setAttribute('style', original_style);}, 1000);",
						element);
				boolean flag = this.waitFor(element);
				if (!flag)
					element = null;
			} catch (Exception e) {
				log.info(e.getMessage());
			}
		} else {
			log.error("Element \"" + key + "\" is not in the elements file: " + yamlFile + ".yaml");
		}
		return element;
	}

    /**
     * If target cannot be found, log the error
     *
     * @param e Exception
     */
    public void logObjectNotFound(Exception e) {
        log.error("找不到目标对象，报错信息如下：");
        log.error(Arrays.toString(e.getStackTrace()));
    }
}
