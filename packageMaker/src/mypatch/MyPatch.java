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
 * ��������������
 * 
 * @author ��˧ shuai-wang@neusoft.com
 * @update ١��� tge0503020211@163.com
 * @version 0.2
 */
public class MyPatch {

	/**
	 * ���������
	 * 
	 * @param args
	 * @throws AppException
	 * @throws Exception
	 */
	public static void main(String[] args) throws IOException, AppException {
		// MypathFace mypathFace1=new MypathFace("����������");
		// mypathFace1.setVisible(true);

		MyPatch patch = new MyPatch();
		// 1. ���ú�׺����
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
		// 2. ��ϵͳ�����ļ�
		patch.readSysConfig();
		// 3�� createPatch ########add by tongge##########
		if (new Boolean(PropertiesUtil.getProperty(Constant.Need_PATCH)).booleanValue()) {
			PatchCreater.createPatch();
		}
		// 4. ����һ���µ�patch�ļ�
		Constant.NEW_PATCH = PropertiesUtil.getProperty(Constant.PACKAGE_DIR) + "/newPatch.txt";
		// 4. ��patch.txt
		patch.readPatch();
		// 5. �ļ�����
		patch.assortFiles();
		// 6. ������������
		File pack_template = new File("������ģ��");
		String dest_dir_name = new String(patch.prop.getProperty("package_dir").getBytes("ISO8859-1"), "GB18030");
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateStr = df.format(new Date());
		dest_dir_name = dest_dir_name.replaceAll("sysdate", dateStr);
		Constant.NEW_PATCH = Constant.NEW_PATCH.replaceAll("sysdate", dateStr);
		File dest_dir = new File(dest_dir_name);
		FileUtils.copyDirectory(pack_template, dest_dir);
		// 7. ���ƹ����ļ���д��˵��.txt��
		String javaSourceFolderName = "JAVAԴ�ļ�";
		patch.writeReadmeFile(dest_dir_name + File.separator + javaSourceFolderName + File.separator + "˵��.txt", patch,
				patch.javaSources, dest_dir_name, javaSourceFolderName);
		String javaClassFolderName = "���ļ�";
		patch.writeReadmeFile(dest_dir_name + File.separator + javaClassFolderName + File.separator + "˵��.txt", patch,
				patch.javaClasses, dest_dir_name, javaClassFolderName);
		String jspFolderName = "JSP�ļ�";
		patch.writeReadmeFile(dest_dir_name + File.separator + jspFolderName + File.separator + "˵��.txt", patch,
				patch.jspFiles, dest_dir_name, jspFolderName);
		String configFolderName = "�����ļ�";
		patch.writeReadmeFile(dest_dir_name + File.separator + configFolderName + File.separator + "˵��.txt", patch,
				patch.configFiles, dest_dir_name, configFolderName);
		// 8.ɾ������Ŀ¼�����磺����������в�����JAVA�ļ�����ô�Ͳ����������б�����JAVA�ļ����͡����ļ�������Ŀ¼��
		patch.removeUnUsedFolder(dest_dir);
		// 9. ����Դ����������λ��������Ŀ¼
		Runtime.getRuntime().exec("cmd /C explorer.exe " + dest_dir_name);
	}

	/**
	 * ���ƹ����ļ���д��˵��.txt��
	 * 
	 * @param fileName
	 * @param patch
	 * @param subtypeFileList
	 *            �����ļ��б�
	 * @param dest_dir_name
	 * @param subfolder
	 * @throws IOException
	 */
	private void writeReadmeFile(String fileName, MyPatch patch, List subtypeFileList, String dest_dir_name,
			String subfolder) throws IOException {
		String projectName = new String(patch.prop.getProperty("project").getBytes("ISO8859-1"), "GB18030"); // ������
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
	 * ��fileVector�����е������ļ�������õ���Ӧ���б���
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
			// �ں�̨�г�δ�����ļ�
			for (int i = 1; it.hasNext(); i++) {
				File elem = (File) it.next();
				System.err.println(i + ". " + elem.getAbsolutePath());
			}
			System.err.println("����δ�����ļ�");
			System.exit(1);
		}
	}

	/**
	 * ϵͳ�����ļ��ڴ澵��
	 */
	private Properties prop = new Properties();

	/**
	 * ��ϵͳ�����ļ�
	 */
	private void readSysConfig() {
		try {
			// prop.loadFromXML(new FileInputStream("config.xml"));
			prop.load(new FileInputStream(Constant.CONFIG_PROPERTIES));
		} catch (FileNotFoundException e) {
			System.err.println("δ�ҵ�ϵͳ�����ļ�" + Constant.CONFIG_PROPERTIES + "�������޷�����");
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			System.err.println("�����ļ�" + Constant.CONFIG_PROPERTIES + "��ȡʧ��");
			e.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * ׼��װ�����ļ�
	 */
	Set fileVector = new TreeSet();

	/**
	 * ��patch.txt�ļ��е�װ���ļ���Ϣ��fileVector
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
			System.err.println("δ�ҵ�Patch�ļ�");
			e.printStackTrace();
			System.exit(1);
		}
		// ���Patch�ļ�����
		if (lReader != null) {
			try {
				String patchFileKind = PropertiesUtil.getProperty(Constant.PatchFile_Kind);
				if (Constant.PatchFile_Kind_CVS.equalsIgnoreCase(patchFileKind)
						|| Constant.PatchFile_Kind_SVN.equalsIgnoreCase(patchFileKind)) {
					while (lReader.ready()) {
						String virtualFileName = lReader.readLine();
						if (virtualFileName.startsWith("Index: ")) { // �������Ϊ�����ļ���Ϣ
							virtualFileName = virtualFileName.substring("Index: ".length());// ��ͷ
							if (isSuffixRegistered(virtualFileName)) {
								File trueFile = locateFile(virtualFileName);
								fileVector.add(trueFile);
								// �����JAVAԴ�ļ�����һ���ҵ������ļ��ŵ�fileVector��
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
							// �����JAVAԴ�ļ�����һ���ҵ������ļ��ŵ�fileVector��
							if (trueFile.getName().endsWith(".java")) {
								File javaClassFile = locateJavaClassFile(trueFile.getAbsolutePath());
								fileVector.add(javaClassFile);
							}
						}
					}
				} else {
					throw new PropertiesException("�����ļ���û���������ֵ��" + Constant.PatchFile_Kind + "��"
							+ PropertiesUtil.getProperty(Constant.PatchFile_Kind));
				}

			} catch (IOException e) {
				System.err.println("Patch�ļ���ȡʧ��");
				e.printStackTrace();
				System.exit(1);
				return false;
			}
		} else {
			System.err.println("Patch�ļ���ȡʧ��");
			System.exit(1);
		}
		return true;
	}

	/**
	 * ��λ�������ļ�
	 * 
	 * @param virtualFileName
	 *            �����ļ�����
	 * @return �������ļ�ȫ��
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	private File locateFile(String virtualFileName) throws FileNotFoundException, UnsupportedEncodingException {
		String trueFileName = virtualFileName.replace('/', File.separatorChar);
		String projectName = new String(prop.getProperty("project").getBytes("ISO8859-1"), "GB18030"); // ������
		if (virtualFileName.indexOf(projectName) != -1) {
			// 1. �����һ����������֮���ȡ�ļ���
			int fileNameStartIndex = virtualFileName.lastIndexOf(projectName) + projectName.length();
			trueFileName = virtualFileName.substring(fileNameStartIndex);
			// 2. ���Ϲ���Ŀ¼
			trueFileName = new String(
					(prop.getProperty("workspace") + projectName + File.separatorChar + trueFileName)
							.getBytes("ISO8859-1"),
					"GB18030");
		} else {
			// 1. ֱ�Ӽ��Ϲ���Ŀ¼
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
	 * ��JAVAԴ�ļ���λ�����ļ�
	 * 
	 * @param virtualFileName
	 *            �����ļ�����
	 * @return �������ļ�ȫ��
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
	 * ��׺����
	 */
	HashSet suffixSet = new HashSet();

	/**
	 * �����ļ���׺�ж��Ƿ�Ϊ��ע��Ŀ���װ�����ļ�����
	 * 
	 * @param line
	 *            �ļ�ȫ��
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
	 * JavaԴ�ļ��б�
	 */
	private Vector javaSources = new Vector();

	/**
	 * ���ļ��б�
	 */
	private Vector javaClasses = new Vector();

	/**
	 * JSP�ļ��б�
	 */
	private Vector jspFiles = new Vector();

	/**
	 * �����ļ��б�
	 */
	private Vector configFiles = new Vector();

	/**
	 * δ�����ļ��б�
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
	 * Description: [�Ƴ�ĳЩ�ļ����У�]
	 * </p>
	 * 
	 * @author:١��� tge0503020211@163.com
	 * @update:[����YYYY-MM-DD] [����������][�޸�˵��]
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
