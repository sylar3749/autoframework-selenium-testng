package Framework;


import java.util.ArrayList;
import java.util.List;

import jxl.Cell;

/**
 * Excel放在Data文件夹下</p> Excel命名方式：测试类名.xls</p> Excel的sheet命名方式：测试方法名</p>
 * Excel第一行为Map键值</p>
 * 
 * @author jirimutu
 */
public class ListDataProvider extends ExcelDataProvider {

	public ListDataProvider(String clazzName, String methodname) {
		super(clazzName, methodname);
	}

	@Override
	public Object[] next() {
		Cell[] c = sheet.getRow(this.currentRowNo);
		// Map<String, String> data = new HashMap<String, String>();
		List<Data> data = new ArrayList<>();
		for (int i = 0; i < this.columnNum; i++) {

			String temp = "";

			try {
				temp = c[i].getContents();
			} catch (ArrayIndexOutOfBoundsException ex) {
				temp = "";
			}
			// data.put(this.columnnName[i], temp);
			data.add(new Data(this.columnnName[i], temp));
		}
		Object object[] = new Object[1];
		object[0] = data;
		this.currentRowNo++;
		return object;
	}

	/**
	 * key-value形式存放测试数据
	 * 
	 * @author jirimutu
	 * 
	 *         new Data("loginUrl","http://www.gg.com/login1"); new
	 *         Data("username","qqqqqqq"); new Data("password","121232")
	 * 
	 *         List<Data> datas = new ArrayList<Data>() datas.add
	 * 
	 */
	public static class Data {
		private String key;
		private String value;

		public Data(String key, String value) {
			super();
			this.key = key;
			this.value = value;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public Data self(String key) {
			if (this.key.equals(key)) {
				return this;
			}
			return null;
		}
	}
}