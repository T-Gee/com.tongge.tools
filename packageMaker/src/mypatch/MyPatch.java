package mypatch;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.tongge.createPackage.PatchCreater;
import com.tongge.createPackage.exception.AppException;
import com.tongge.createPackage.exception.PropertiesException;
import com.tongge.createPackage.util.FileUtil;
import com.tongge.createPackage.util.PropertiesUtil;

/**
 * 升级包制作程序
 * 
 * @author 王帅 shuai-wang@neusoft.com
 * @update 佟广恩 tge0503020211@163.com
 * @version 0.2
 */
public class MyPatch {

	/**
	 * 主程序入口
	 * 
	 * @param args
	 * @throws AppException
	 * @throws Exception
	 */
	public static void main(String[] args) throws IOException, AppException {
		// MypathFace mypathFace1=new MypathFace("整理升级包");
		// mypathFace1.setVisible(true);

		MyPatch patch = new MyPatch();
		// 1. 配置后缀集合
		patch.suffixSet.add(".java");
		patch.suffixSet.add(".class");
		patch.suffixSet.add(".jsp");
		patch.suffixSet.add(".jpg");
		patch.suffixSet.add(".gif");
		patch.suffixSet.add(".css");
		patch.suffixSet.add(".xml");
		patch.suffixSet.add(".inc");
		patch.suffixSet.add(".properties");
		patch.suffixSet.add(".js");
		patch.suffixSet.add(".tld");
		patch.suffixSet.add(".xls");
		patch.suffixSet.add(".license");
		// 2. 读系统配置文件
		patch.readSysConfig();
		// 3‘ createPatch ########add by tongge##########
		if (new Boolean(PropertiesUtil.getProperty(Constant.Need_PATCH)).booleanValue()) {
			PatchCreater.createPatch();
		}
		// 4. 创建一个新的patch文件
		Constant.NEW_PATCH = PropertiesUtil.getProperty(Constant.PACKAGE_DIR) + "/newPatch.txt";
		// 4. 读patch.txt
		patch.readPatch();
		// 5. 文件分类
		patch.assortFiles();
		// 6. 创建空升级包
		File pack_template = new File("升级包模板");
		String dest_dir_name = new String(patch.prop.getProperty("package_dir").getBytes("ISO8859-1"), "GB18030");
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateStr = df.format(new Date());
		dest_dir_name = dest_dir_name.replaceAll("sysdate", dateStr);
		Constant.NEW_PATCH = Constant.NEW_PATCH.replaceAll("sysdate", dateStr);
		File dest_dir = new File(dest_dir_name);
		FileUtils.copyDirectory(pack_template, dest_dir);
		// 7. 复制工程文件，写“说明.txt”
		String javaSourceFolderName = "JAVA源文件";
		patch.writeReadmeFile(dest_dir_name + File.separator + javaSourceFolderName + File.separator + "说明.txt", patch,
				patch.javaSources, dest_dir_name, javaSourceFolderName);
		String javaClassFolderName = "类文件";
		patch.writeReadmeFile(dest_dir_name + File.separator + javaClassFolderName + File.separator + "说明.txt", patch,
				patch.javaClasses, dest_dir_name, javaClassFolderName);
		String jspFolderName = "JSP文件";
		patch.writeReadmeFile(dest_dir_name + File.separator + jspFolderName + File.separator + "说明.txt", patch,
				patch.jspFiles, dest_dir_name, jspFolderName);
		String configFolderName = "配置文件";
		patch.writeReadmeFile(dest_dir_name + File.separator + configFolderName + File.separator + "说明.txt", patch,
				patch.configFiles, dest_dir_name, configFolderName);
		// 8.删除多余目录。例如：如果升级包中不包含JAVA文件，那么就不在升级包中保留“JAVA文件”和“类文件”两个目录。
		patch.removeUnUsedFolder(dest_dir);
		// 9. 打开资源管理器，定位至升级包目录
		Runtime.getRuntime().exec("cmd /C explorer.exe " + dest_dir_name);
	}

	/**
	 * 复制工程文件，写“说明.txt”
	 * 
	 * @param fileName
	 * @param patch
	 * @param subtypeFileList
	 *            分类文件列表
	 * @param dest_dir_name
	 * @param subfolder
	 * @throws IOException
	 */
	private void writeReadmeFile(String fileName, MyPatch patch, List subtypeFileList, String dest_dir_name,
			String subfolder) throws IOException {
		String projectName = new String(patch.prop.getProperty("project").getBytes("ISO8859-1"), "GB18030"); // 工程名
		Iterator it = null;
		FileWriter writer = null;

		File readme = new File(fileName);
		writer = new FileWriter(readme);
		FileWriter writer2 = new FileWriter(Constant.NEW_PATCH, true);
		it = subtypeFileList.iterator();
		while (it.hasNext()) {
			File elem = (File) it.next();
			String sourcePath = elem.getAbsolutePath();
			sourcePath = sourcePath.substring(sourcePath.lastIndexOf(projectName) + projectName.length());
			FileUtils
					.copyFile(elem, new File(dest_dir_name + File.separator + subfolder + File.separator + sourcePath));
			String filePath = elem.getAbsolutePath();
			filePath = filePath.substring(filePath.lastIndexOf(projectName) + projectName.length());
			writer.write(filePath + "\r\n");
			writer.flush();
			writer2.write(filePath + "\r\n");
			writer2.flush();
		}
	}

	/**
	 * 将fileVector属性中的所有文件分类放置到相应的列表中
	 */
	private void assortFiles() {
		Iterator it = fileVector.iterator();
		while (it.hasNext()) {
			File elem = (File) it.next();
			if (elem.getName().endsWith(".java")) {
				javaSources.add(elem);
			} else if (elem.getName().endsWith(".class")) {
				javaClasses.add(elem);
			} else if (elem.getName().endsWith(".jsp") || elem.getName().endsWith(".jpg")
					|| elem.getName().endsWith(".gif") || elem.getName().endsWith(".css")
					|| elem.getName().endsWith(".inc") || elem.getName().endsWith(".js")) {
				jspFiles.add(elem);
			} else if (elem.getName().endsWith(".xml") || elem.getName().endsWith(".properties")
					|| elem.getName().endsWith(".xls") || elem.getName().endsWith(".tld")
					|| elem.getName().endsWith(".license")) {
				configFiles.add(elem);
			} else {
				unSortedFiles.add(elem);
			}
		}
		if (!unSortedFiles.isEmpty()) {
			it = unSortedFiles.iterator();
			System.err.println("Following files unsorted: ");
			// 在后台列出未归类文件
			for (int i = 1; it.hasNext(); i++) {
				File elem = (File) it.next();
				System.err.println(i + ". " + elem.getAbsolutePath());
			}
			System.err.println("存在未分类文件");
			System.exit(1);
		}
	}

	/**
	 * 系统配置文件内存镜像
	 */
	private Properties prop = new Properties();

	/**
	 * 读系统配置文件
	 */
	private void readSysConfig() {
		try {
			// prop.loadFromXML(new FileInputStream("config.xml"));
			prop.load(new FileInputStream(Constant.CONFIG_PROPERTIES));
		} catch (FileNotFoundException e) {
			System.err.println("未找到系统配置文件" + Constant.CONFIG_PROPERTIES + "，程序无法运行");
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			System.err.println("配置文件" + Constant.CONFIG_PROPERTIES + "读取失败");
			e.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * 准备装包的文件
	 */
	Set fileVector = new TreeSet();

	/**
	 * 读patch.txt文件中的装包文件信息到fileVector
	 * 
	 * @throws UnsupportedEncodingException
	 * @throws PropertiesException
	 * 
	 * @throws IOException
	 */
	private boolean readPatch() throws UnsupportedEncodingException, PropertiesException {
		String patchFilePath = new String(prop.getProperty("patch_filename").getBytes("ISO8859-1"), "GB18030");
		File patchFile = new File(patchFilePath);
		FileReader fReader = null;
		LineNumberReader lReader = null;
		try {
			fReader = new FileReader(patchFile);
			lReader = new LineNumberReader(fReader);
		} catch (FileNotFoundException e) {
			System.err.println("未找到Patch文件");
			e.printStackTrace();
			System.exit(1);
		}
		// 如果Patch文件可用
		if (lReader != null) {
			try {
				String patchFileKind = PropertiesUtil.getProperty(Constant.PatchFile_Kind);
				if (Constant.PatchFile_Kind_CVS.equalsIgnoreCase(patchFileKind)
						|| Constant.PatchFile_Kind_SVN.equalsIgnoreCase(patchFileKind)) {
					while (lReader.ready()) {
						String virtualFileName = lReader.readLine();
						if (virtualFileName.startsWith("Index: ")) { // 如果本行为有用文件信息
							virtualFileName = virtualFileName.substring("Index: ".length());// 掐头
							if (isSuffixRegistered(virtualFileName)) {
								File trueFile = locateFile(virtualFileName);
								fileVector.add(trueFile);
								// 如果是JAVA源文件，则一并找到其类文件放到fileVector里
								if (trueFile.getName().endsWith(".java")) {
									File javaClassFile = locateJavaClassFile(trueFile.getAbsolutePath());
									fileVector.add(javaClassFile);
								}
							}
						}
					}
				} else if (Constant.PatchFile_Kind_CLEARCASE.equalsIgnoreCase(patchFileKind)
						|| Constant.PatchFile_Kind_NONE.equalsIgnoreCase(patchFileKind)) {
					while (lReader.ready()) {
						String virtualFileName = lReader.readLine();
						if (virtualFileName == null || virtualFileName.trim().length() == 0
								|| virtualFileName.startsWith("#")) {
							continue;
						}
						if (isSuffixRegistered(virtualFileName = virtualFileName.trim())) {
							File trueFile = locateFile(virtualFileName);
							fileVector.add(trueFile);
							// 如果是JAVA源文件，则一并找到其类文件放到fileVector里
							if (trueFile.getName().endsWith(".java")) {
								File javaClassFile = locateJavaClassFile(trueFile.getAbsolutePath());
								fileVector.add(javaClassFile);
							}
						}
					}
				} else {
					throw new PropertiesException("配置文件中没有这个属性值（" + Constant.PatchFile_Kind + "："
							+ PropertiesUtil.getProperty(Constant.PatchFile_Kind));
				}

			} catch (IOException e) {
				System.err.println("Patch文件读取失败");
				e.printStackTrace();
				System.exit(1);
				return false;
			}
		} else {
			System.err.println("Patch文件读取失败");
			System.exit(1);
		}
		return true;
	}

	/**
	 * 定位到物理文件
	 * 
	 * @param virtualFileName
	 *            虚拟文件命名
	 * @return 真正的文件全名
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	private File locateFile(String virtualFileName) throws FileNotFoundException, UnsupportedEncodingException {
		String trueFileName = virtualFileName.replace('/', File.separatorChar);
		String projectName = new String(prop.getProperty("project").getBytes("ISO8859-1"), "GB18030"); // 工程名
		if (virtualFileName.indexOf(projectName) != -1) {
			// 1. 从最后一个工程名串之后截取文件名
			int fileNameStartIndex = virtualFileName.lastIndexOf(projectName) + projectName.length();
			trueFileName = virtualFileName.substring(fileNameStartIndex);
			// 2. 加上工程目录
			trueFileName = new String(
					(prop.getProperty("workspace") + projectName + File.separatorChar + trueFileName)
							.getBytes("ISO8859-1"),
					"GB18030");
		} else {
			// 1. 直接加上工程目录
			trueFileName = new String(
					(prop.getProperty("workspace") + projectName + File.separatorChar + trueFileName)
							.getBytes("ISO8859-1"),
					"GB18030");
		}
		File trueFile = new File(trueFileName);
		if (!trueFile.exists()) {
			System.err.println("Couldn't find file: " + trueFile.getAbsolutePath());
			throw new FileNotFoundException();
		}
		return trueFile;
	}

	/**
	 * 由JAVA源文件定位到类文件
	 * 
	 * @param virtualFileName
	 *            虚拟文件命名
	 * @return 真正的文件全名
	 * @throws FileNotFoundException
	 */
	private File locateJavaClassFile(String virtualFileName) throws FileNotFoundException {
		String trueFileName = StringUtils.replace(virtualFileName, "src", "EAPDomain" + File.separator + "WEB-INF"
				+ File.separator + "classes");
		trueFileName = StringUtils.replace(trueFileName, ".java", ".class");
		File trueFile = new File(trueFileName);
		if (!trueFile.exists()) {
			System.err.println("Couldn't find file: " + trueFile.getAbsolutePath());
			throw new FileNotFoundException();
		}
		return trueFile;
	}

	/**
	 * 后缀集合
	 */
	HashSet suffixSet = new HashSet();

	/**
	 * 根据文件后缀判断是否为已注册的可以装包的文件类型
	 * 
	 * @param line
	 *            文件全名
	 * @return
	 */
	private boolean isSuffixRegistered(String line) {
		String suffix = "";
		try {
			suffix = line.substring(line.lastIndexOf('.'));
			if (suffixSet.contains(suffix))
				return true;
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * Java源文件列表
	 */
	private Vector javaSources = new Vector();

	/**
	 * 类文件列表
	 */
	private Vector javaClasses = new Vector();

	/**
	 * JSP文件列表
	 */
	private Vector jspFiles = new Vector();

	/**
	 * 配置文件列表
	 */
	private Vector configFiles = new Vector();

	/**
	 * 未归类文件列表
	 */
	private Vector unSortedFiles = new Vector();

	private static Set removFolder = new HashSet();
	static {
		removFolder.add(".svn");
		removFolder.add("CVS");
	}

	/**
	 * 
	 * Created on 2011-5-24
	 * <p>
	 * Description: [移除某些文件（夹）]
	 * </p>
	 * 
	 * @author:佟广恩 tge0503020211@163.com
	 * @update:[日期YYYY-MM-DD] [更改人姓名][修改说明]
	 * @param dest_dir
	 *            .
	 */
	private void removeUnUsedFolder(File dest_dir) {
		File[] files = dest_dir.listFiles();
		for (int i = 0, n = files.length; i < n; i++) {
			File file = files[i];
			if (removFolder.contains(file.getName())) {
				FileUtil.deleteDirectory(file.getPath());
			} else if (!file.isFile()) {
				removeUnUsedFolder(file);
			}
		}
		// File[] files2 = dest_dir.listFiles();
		// for(int i=0,n=files2.length;i<n;i++){
		// File file = files2[i];
		// if(!file.isFile() && (file.listFiles().length == 0 ||
		// file.listFiles().length == 1 )){
		// FileUtil.deleteDirectory(file.getPath());
		// }else if(!file.isFile()){
		// removeUnUsedFolder(file);
		// }
		// }
	}
}
