package Framework;


import java.lang.reflect.Method;
import java.util.Iterator;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

public class DataDriven {

	@DataProvider(name = "listDataProvider")
	public static Iterator<Object[]> listDataProvider(Method method, ITestContext context) {
		String excelName = null, sheetName = null;
		CustomTest custom = method.getAnnotation(CustomTest.class);
		if (custom == null) {
			throw new RuntimeException(">>>Usage[ 使用Annotation CustomTest 注解测试方法(" + method.getName()
					+ ")，并指定fileName和sheet值 ]");
		}
		excelName = custom.excelName();
		sheetName = custom.sheetName();
		if ("".equals(excelName) || "".equals(sheetName)) {
			throw new RuntimeException(">>>Usage[ 使用Annotation CustomTest 注解测试方法(" + method.getName()
					+ ")，并指定fileName和sheet值 ]");
		}
		return new ListDataProvider(excelName, sheetName);
	}

	@DataProvider(name = "mapDataProvider")
	public static Iterator<Object[]> mapDataProvider(Method method, ITestContext context) {
		String excelName = null, sheetName = null;
		CustomTest custom = method.getAnnotation(CustomTest.class);
		if (custom == null) {
			throw new RuntimeException(">>>Usage[ 使用Annotation CustomTest 注解测试方法(" + method.getName()
					+ ")，并指定fileName和sheet值 ]");
		}
		excelName = custom.excelName();
		sheetName = custom.sheetName();
		if ("".equals(excelName) || "".equals(sheetName)) {
			throw new RuntimeException(">>>Usage[ 使用Annotation CustomTest 注解测试方法(" + method.getName()
					+ ")，并指定fileName和sheet值 ]");
		}
		return new MapDataProvider(excelName, sheetName);
	}
}
