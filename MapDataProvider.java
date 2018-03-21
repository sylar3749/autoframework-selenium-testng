package Framework;


import java.util.HashMap;
import java.util.Map;
import jxl.Cell;

/**
 * Excel放在Data文件夹下</p> Excel命名方式：测试类名.xls</p> Excel的sheet命名方式：测试方法名</p>
 * Excel第一行为Map键值</p>
 * 
 */
public class MapDataProvider extends ExcelDataProvider {

	public MapDataProvider(String clazzName, String methodname) {
		super(clazzName, methodname);
	}

	@Override
	public Object[] next() {
		Cell[] c = sheet.getRow(this.currentRowNo);
		// Map<String, String> data = new HashMap<String, String>();
		Map<String, String> map = new HashMap<>();
		for (int i = 0; i < this.columnNum; i++) {

			String temp = "";

			try {
				temp = c[i].getContents();
			} catch (ArrayIndexOutOfBoundsException ex) {
				temp = "";
			}
			map.put(this.columnnName[i], temp);
		}
		Object object[] = new Object[1];
		object[0] = map;
		this.currentRowNo++;
		return object;
	}

}