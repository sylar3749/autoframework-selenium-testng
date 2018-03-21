package Framework;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CustomTest {
	/**
	 * @return 当前时间大于指定时间后，该测试方法被激活，字符串格式："yyyy-mm-dd hh:mm"
	 */
	public String activeAfter() default "";

	/**
	 * 数据驱动文件名
	 */
	public String excelName() default "";

	/**
	 * 数据驱动sheet名
	 */
	public String sheetName() default "";

	/**
	 * 指定测试方法执行顺序
	 */
	public int order() default 0;
}
