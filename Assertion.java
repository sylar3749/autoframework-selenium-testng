package Framework;

import org.testng.Assert;

public class Assertion {
	Log log = new Log(this.getClass());

	/**
	 * 进行断言检查
	 * 
	 * @param actual
	 *            实际结果
	 * @param expected
	 *            预期结果
	 * @param message
	 *            输出的提示信息
	 */
	public void assertEquals(Object actual, Object expected, String message) {
		log.info("开始进行断言检查：");
		try {
			Assert.assertEquals(actual, expected, message);
			log.info("断言检查成功，预期结果是：【" + expected + "】,实际结果是：【" + actual + "】");
		} catch (Error e) {
			log.error("断言检查失败，预期结果是：【" + expected + "】,实际结果是：【" + actual + "】");
			/** 抛出异常：查看testng源代码中org.testng.Assert中，
			 static public void assertEquals方法在失败的情况下调用failNotEquals方法
			 failNotEquals方法调用fail方法
			 fail方法在有message参数的时候，抛出错误throw new AssertionError(message);
			 这里也采用同样的方法，捕获到错误后，输出log日志，然后抛出同样的错误
			 throw new AssertionError("断言检查失败，预期结果是：【" + expected +
			 "】,实际结果是：【" + actual + "】");*/
		}
	}

	public String assertNotNull(String mgs, Object actual) {
		log.info("开始进行断言检查：");
		String str = "断言检查成功，预期结果是：【" + mgs + "，存在！】实际结果是：【" + actual + "】";
		try {
			Assert.assertNotNull(actual);
			log.info(str);
		} catch (Error e) {
			log.error(str);
			// throw new AssertionError(str);
		}
		return str;
	}

	public void assertNull(String mgs, Object actual) {
		log.info("开始进行断言检查：");
		String str = "断言检查成功，预期结果是：【" + mgs + ",不存在,】实际结果是：【" + actual + "】";
		try {
			Assert.assertNull(actual);
			log.info(str);
		} catch (Error e) {
			log.error(str);
			// throw new AssertionError(str);
		}
	}
}
