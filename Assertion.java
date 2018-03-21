package Framework;

import org.testng.Assert;

public class Assertion {
	Log log = new Log(this.getClass());

	/**
	 * Assert check
	 * 
	 * @param actual Object
	 * @param expected Object
	 * @param message String
	 */
	public void assertEquals(Object actual, Object expected, String message) {
		log.info("Assert begins: ");
		try {
			Assert.assertEquals(actual, expected, message);
			log.info("Assert succeeded, expected result is: \"" + expected + "\", actual result is:\"" + actual + "\"");
		} catch (Error e) {
			log.error("Assert failed, expected result is: \"" + expected + "\", actual result is:\"" + actual + "\"");
		}
	}

	public String assertNotNull(String mgs, Object actual) {
		log.info("Assert begins: ");
		String str = "Assert succeeded, expected result is: \"" + mgs + ", exists!\", actual result is:\"" + actual + "\"";
		try {
			Assert.assertNotNull(actual);
			log.info(str);
		} catch (Error e) {
			log.error(str);
		}
		return str;
	}

	public void assertNull(String mgs, Object actual) {
		log.info("Assert begins: ");
		String str = "Assert succeeded, expected result is: \"" + mgs + ", does not exist!\", actual result is:\"" + actual + "\"";
		try {
			Assert.assertNull(actual);
			log.info(str);
		} catch (Error e) {
			log.error(str);
		}
	}
}
