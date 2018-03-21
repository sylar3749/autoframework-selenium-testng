package Framework;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.testng.Assert;

/**
 * Excel放在Data文件夹下</p> Excel命名方式：测试类名.xls</p> Excel的sheet命名方式：测试方法名</p>
 * Excel第一行为Map键值</p>
 * 
 */
public abstract class ExcelDataProvider extends AbstractDataProvider {

	protected Workbook book = null;
	protected Sheet sheet = null;
	protected int rowNum = 0;
	protected int currentRowNo = 0;
	protected int columnNum = 0;
	protected String[] columnnName;

	/**
	 * @param clazzName
	 *            当前测试类的类名
	 * @param methodname
	 *            测试方法名称，用于找到excel数据文件中对应该测试方法的测试数据(sheet名称)
	 */
	public ExcelDataProvider(String clazzName, String methodname) {
		try {

			String fullName = System.getProperty("user.dir") + "/testData" + File.separator + clazzName + ".xls";

			InputStream inputStream = new FileInputStream(fullName);

			book = Workbook.getWorkbook(inputStream);
			sheet = book.getSheet(methodname);
			rowNum = sheet.getRows();
			Cell[] cell = sheet.getRow(0);
			columnNum = cell.length;
			columnnName = new String[columnNum];

			for (int i = 0; i < cell.length; i++) {
				columnnName[i] = cell[i].getContents();
			}
			this.currentRowNo++;

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("unable to read Excel data");
		}
	}

	@Override
	public boolean hasNext() {

		if (this.rowNum == 0 || this.currentRowNo >= this.rowNum) {

			try {
				book.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		} else {
			// sheet下一行内容为空判定结束
			Cell[] row = sheet.getRow(currentRowNo);
			return row.length != 0 && !"".equals(row[0].getContents());
		}
	}

	@Override
	public abstract Object[] next();

}