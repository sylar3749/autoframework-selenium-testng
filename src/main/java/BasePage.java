import org.apache.log4j.Logger;
import org.ho.yaml.Yaml;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import org.testng.asserts.Assertion;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class BasePage {

	public WebDriver driver;
	private String yamlFile;
	public final Logger log = Logger.getLogger(BaseTest.class);
	private static Assertion ae = new Assertion();
	private HashMap<String, HashMap<String, String>> ml;
	private final static int WAIT_TIME = 30;

	/**
	 * Structure function
	 *
	 * @param dr1 (WebDriver)
	 */
	protected BasePage(WebDriver dr1) {
		this.driver = dr1;
		yamlFile = "element";
		this.getYamlFile();
	}

	@SuppressWarnings("unchecked")
	private void getYamlFile() {
		File f = new File("testData/" + yamlFile + ".yaml");
		try {
			ml = Yaml.loadType(new FileInputStream(f.getAbsolutePath()), HashMap.class);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Find element with keys
	 *
	 * @param key Key value maintained in the yaml file
	 * @return WebElement
	 */
	protected WebElement getElement(String key) {
		WebElement element = null;
		try {
			if (ml.containsKey(key)) {
				HashMap<String, String> m = ml.get(key);
				String type = m.get("type");
				String value = m.get("value");
				log.info("\tLooking for element: \"" + key + "\" with " + type + " = \"" + value + "\"...");
				By by = this.getBy(type, value);
				element = driver.findElement(by);
			} else {
				log.error("\tElement \"" + key + "\"is not existed in the " + yamlFile + ".yaml file.");
			}
		} catch (Exception e) {
			logObjectNotFound(e);
		}
		return element;
	}

	/**
	 * Find elements with a key
	 *
	 * @param key Key value maintained in the yaml file
	 * @return WebElements
	 */
	protected List<WebElement> getElements(String key) {
		waitMs(500);
		List<WebElement> eles = null;
		try {
			if (ml.containsKey(key)) {
				HashMap<String, String> m = ml.get(key);
				String type = m.get("type");
				String value = m.get("value");
				By by = this.getBy(type, value);
				eles = driver.findElements(by);
			} else
				log.error("\tElement \"" + key + "\" are not exist in the " + yamlFile + ".yaml file.");
		} catch (Exception e) {
			logObjectNotFound(e);
		}
		assert eles != null;
		// Highlight the elements interacts with currently
		return eles;
	}

	/**
	 * Generate By value with input types and related values
	 *
	 * @param type  Key's type in the yaml file
	 * @param value Key's value in the yaml file
	 * @return By
	 */
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

	/**
	 * Get By value with a keyword from yaml file
	 *
	 * @param key keyword of an element in the yaml file (String)
	 * @return By value (By)
	 */
	protected By getElementBy(String key) {
		if (ml.containsKey(key)) {
			HashMap<String, String> m = ml.get(key);
			String type = m.get("type");
			String value = m.get("value");
			return this.getBy(type, value);
		} else {
			log.error("\tElement \"" + key + "\"is not existed in the " + yamlFile + ".yaml file.");
		}
		return null;
	}

	// Check if an element is existed or not

	protected boolean isElementExist(WebDriver driver, By by) {
		boolean flag = false;
		try {
			WebElement element = driver.findElement(by);
			flag = null != element;

		} catch (NoSuchElementException e) {
			log.error("\tElement:" + by.toString() + " does not exist!");
		}

		return flag;
	}

	/**
	 * Wait for an Element with WebElement
	 *
	 * @param ele WebElement
	 * @return True / False
	 */
	protected boolean waitFor(final WebElement ele) {
		boolean wait;
		if (ele == null)
			return false;
		wait = new WebDriverWait(driver, WAIT_TIME).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				return ele.isDisplayed();
			}
		});
		return wait;
	}

	/**
	 * Wait for an Element with By value
	 *
	 * @param by By
	 * @return True / False
	 */
	protected boolean waitFor(final By by) {
		boolean wait;
		if (by == null)
			return false;
		wait = new WebDriverWait(driver, WAIT_TIME).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				return driver.findElement(by).isDisplayed();
			}
		});
		return wait;
	}

	/**
	 * Wait for an Element with By key
	 *
	 * @param key key value in the yaml file
	 * @return True / False
	 */
	protected boolean waitFor(final String key) {
		boolean wait;
		if (key == null)
			return false;
		wait = new WebDriverWait(driver, WAIT_TIME).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				return getElement(key).isDisplayed();
			}
		});
		return wait;
	}

	protected boolean waitElementDisappear(final WebElement ele) {
		boolean wait = false;
		if (ele == null)
			return false;
		try {
			wait = new WebDriverWait(driver, WAIT_TIME).until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver d) {
					return !ele.isDisplayed();
				}
			});
		} catch (Exception e) {
			log.error("\tElement is still displayed");
		}
		return wait;
	}

	protected void waitMs(int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	protected void click(String element) {
		WebElement e = getElement(element);
		ae.assertNotNull(e, "Element not found...");
		e.click();
		log.info("\tClick on \"" + element + "\"");
	}

	protected void clear(String element) {
		WebElement e = getElement(element);
		ae.assertNotNull(e, "Element not found...");
		log.info("\tClear field \"" + element + "\"");
		e.clear();
	}

	protected void type(String element, String values) {
		WebElement e = getElement(element);
		ae.assertNotNull(e, "Element not found...");
		e.clear();
		log.info("\tInput \"" + values + "\" into text field \"" + element + "\"");
		e.sendKeys(values);
	}

	protected void select(String element, String values) {

		WebElement e = getElement(element);
		ae.assertNotNull(e, "Element not found...");
		Select sel = new Select(e);
		log.info("\tOption: \"" + values + "\"" + " selected.");
		sel.selectByValue(values);
	}

	/**
	 * If target cannot be found, log the error
	 *
	 * @param e Exception
	 */
	protected void logObjectNotFound(Exception e) {
		log.error("\tCan not find element, please see errors below: ");
		log.error("\t" + Arrays.toString(e.getStackTrace()));
	}

	// Execute java scripts
	private void executeJS(String script) {
		log.info("\tRun the javascript from page ,the java script is:" + script);
		JavascriptExecutor je = (JavascriptExecutor) driver;
		je.executeScript(script);
	}

	private void executeJS(String script, WebElement ele) {
		log.info("\tRun the javascript from page ,the java script is:" + script);
		JavascriptExecutor je = (JavascriptExecutor) driver;
		je.executeScript(script, ele);
	}

	protected void scrollPage(int offset) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0," + offset + ")", "");
		waitMs(500);
	}

	protected void scrollToTop() {
		log.info("\tNow we scroll the view to the top of the page");
		executeJS("window.scrollTo(0,0)");
		waitMs(500);
	}

	protected void scrollToElement(WebElement ele) {
		log.info("\tNow we scroll the view to the position we can see");
		executeJS("window.scrollTo(0," + ele.getLocation().y + ")");
		waitMs(500);
	}

	protected void clickElementWithJS(String xpath) {
		log.info("\tNow we click on an element with xpath by using javascript");
		executeJS("document.evaluate(\"" + xpath + "\","
				+ " document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.click()");
		waitMs(500);
	}

	protected void setAttributeWithJS(String xpath, int index, String attribute, String value) {
		log.info("\tNow we are going to find elements by using javascript");
		executeJS("var result = document.evaluate(\"" + ml.get(xpath).get("value") + "\","
				+ " document, null, XPathResult.ANY_TYPE, null);" + "var node = result.iterateNext();"
				+ "var nodes = [];" + "while (node) { nodes.push(node); node = result.iterateNext(); }" + "nodes["
				+ index + "].setAttribute('" + attribute + "','" + value + "');");
	}

	protected void highlightElementWithJS(WebElement ele) {
		log.info("\tNow we are going to highlight the element currently focused on by using javascript");
		executeJS("element = arguments[0];" + "original_style = element.getAttribute('style');"
				+ "element.setAttribute('style', original_style + \";"
				+ "background: yellow; border: 2px solid red;\");"
				+ "setTimeout(function(){element.setAttribute('style', original_style);}, 2000);", ele);
	}
}
