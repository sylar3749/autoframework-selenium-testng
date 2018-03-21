package Framework;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class FindDriver {
	
	public static String CHROME_DRIVER_EXE ; 
	public static String IEDRIVER_SERVER_EXE ; 
	
	static{
		String packageName = FindDriver.class.getPackage().getName();
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		String packagePath = packageName.replace(".", "/");
		URL url = loader.getResource(packagePath);
		List<String> fileNames = null;
		if (url != null) {
			String type = url.getProtocol();
			if (type.equals("file")) {
				fileNames = getClassNameByFile(url.getPath());
			}
		}
		
		if (fileNames != null) {
			for (String className : fileNames) {
				File file = new File(className);
				if(file.exists()){
					switch (file.getName()) {
					case "chromedriver.exe":
						CHROME_DRIVER_EXE = file.getAbsolutePath();
						break;
					case "IEDriverServer.exe":
						IEDRIVER_SERVER_EXE = file.getAbsolutePath();
						break;
					default:
						break;
					}
				}else{
					System.out.println("file not found");
				}
				
			}
		}
	}
	
//	public static void main(String [] args){
//		System.out.println("find driver" + CHROME_DRIVER_EXE);
//
//		System.out.println("find driver"+ IEDRIVER_SERVER_EXE);
//	}
	
	
	/**
	 * 从项目文件获取某包下所有类
	 * @param filePath 文件路径
	 * @return 类的完整名称
	 */
	private static List<String> getClassNameByFile(String filePath) {
		List<String> myClassName = new ArrayList<>();
		File file = new File(filePath);
		File[] childFiles = file.listFiles();
		assert childFiles != null;
		for (File childFile : childFiles) {
			if (childFile.isDirectory()) {
//				if (childPackage) {
//					myClassName.addAll(getClassNameByFile(childFile.getPath(), myClassName, childPackage));
//				}
			} else {
				String childFilePath = childFile.getPath();
				if (childFilePath.endsWith(".exe")) {
//					childFilePath = childFilePath.replaceAll("\\\\", "/");
//					childFilePath = childFilePath.substring(childFilePath.indexOf("/classes") + 9, childFilePath.lastIndexOf("."));
//					childFilePath = childFilePath.replace("/", ".");
					myClassName.add(childFilePath);
				}
			}
		}

		return myClassName;
	}
	
}
