import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;
import org.apache.poi.ss.usermodel.DataFormatter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ExcelUtil {
	protected XSSFWorkbook ExcelWBook;
	protected XSSFSheet ExcelWSheet;
	protected XSSFCell Cell;
	protected XSSFRow Row;

	@DataProvider(name = "mapDataProvider")
	protected Object[][] ExcelDataProvider() throws IOException {
		// Open the Excel file under testData folder
		String path = System.getProperty("user.dir") + "/testData" + File.separator + "Test Data" + ".xlsx";
		FileInputStream ExcelFile = new FileInputStream(path);
		// Access the required test data sheet
		ExcelWBook = new XSSFWorkbook(ExcelFile);
		ExcelWSheet = ExcelWBook.getSheet("data");
		ExcelWBook.close();
		Row = ExcelWSheet.getRow(0);
		int rownum = ExcelWSheet.getPhysicalNumberOfRows();
		int colnum = Row.getLastCellNum();
		Object Data[][] = new Object[rownum - 1][1];
		DataFormatter formatter = new DataFormatter();

		for (int i = 0; i + 1 < rownum; i++) {
			Map<Object, Object> datamap = new HashMap<>();
			XSSFRow row = ExcelWSheet.getRow(i + 1);
			for (int j = 0; j < colnum; j++) {
				if (row == null)
					datamap.put(ExcelWSheet.getRow(0).getCell(j).toString(), "");
				else {
					XSSFCell cell = row.getCell(j);
					if (cell == null)
						datamap.put(ExcelWSheet.getRow(0).getCell(j).toString(), "");
					else {
						String value = formatter.formatCellValue(cell);
						datamap.put(ExcelWSheet.getRow(0).getCell(j).toString(), value);
					}
				}
			}
			Data[i][0] = datamap;
		}
		return Data;
	}
}
