/**
 * Created on 2011-2-12
 * <p>Title:tge0503020211@163.com/p>
 * <p>Description: [���������Ҫ���ܽ���]</p>
 * @author:١���
 * @email:tge0503020211@163.com
 * @version        1.0
*/
package com.tongge.commons.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;

/**
 * Created on 2011-2-12
 * <p>Title:       tge0503020211@163.com/p>
 * <p>Description: [���������Ҫ���ܽ���]</p>
 * <p>Copyright:   Copyright (c) 2009</p>
 * <p>Company:     </p>
 * <p>Department:  </p>
 * @author:١���
 * @email:tge0503020211@163.com
 * @version        1.0
 */
public class FileUtil {
	
	
	/**
	 * 
	 *  Created on 2011-2-12 
	 * <p>Description: [�ҵ�ĳ���ļ�·���µ������ļ�����ͨ��������ʽ���ļ�����ɸѡ]</p>
	 * @author:١��� tge0503020211@163.com
	 * @update:[����YYYY-MM-DD] [����������][�޸�˵��]
	 * @param path
	 * @param regex
	 * @return.
	 */
	public static List getOneKindFiles(String path,String regex){
		List newList = new ArrayList();
		List list = getAllFile(path);
		Iterator it = list.iterator();
		while(it.hasNext()){
			File file = (File) it.next();
			String uri = file.getPath();
			if(Pattern.matches(regex, uri)){
				newList.add(file);
			};
		}
		return newList;
	}
	/**
	 * 
	 *  Created on 2011-2-12 
	 * <p>Description: [ֱ����List<File>�в�������������ʽ���ļ�]</p>
	 * @author:١��� tge0503020211@163.com
	 * @update:[����YYYY-MM-DD] [����������][�޸�˵��]
	 * @param files
	 * @param regex
	 * @return.
	 */
	public static List getOneKindFiles(List files,String regex){
		List newList = new ArrayList();
		Iterator it = files.iterator();
		while(it.hasNext()){
			File file = (File) it.next();
			String uri = file.getPath();
			if(Pattern.matches(regex, uri)){
				newList.add(file);
			};
		}
		return newList;
	}
	/**
	 * 
	 *  Created on 2011-2-12 
	 * <p>Description: [�ҵ�ĳ���ļ�·���µ������ļ�]</p>
	 * @author:١��� tge0503020211@163.com
	 * @update:[����YYYY-MM-DD] [����������][�޸�˵��]
	 * @param file
	 * @return.
	 */
	public static List getAllFile(String path){
		File file = new File(path);
		return getAllFile(file);
	}
	/**
	 * 
	 *  Created on 2011-2-12 
	 * <p>Description: [ѭ���ҵ�ĳ���ļ����У��µ������ļ�]</p>
	 * @author:١��� tge0503020211@163.com
	 * @update:[����YYYY-MM-DD] [����������][�޸�˵��]
	 * @param file
	 * @return.
	 */
	public static List getAllFile(File file) {
		List fileList = new ArrayList();
		File[] files = file.listFiles();
		if (files != null && files.length > 0) {
			fileList.addAll(Arrays.asList(files));
			for (int i = 0; i < files.length; i++) {
				fileList.addAll(getAllFile(files[i]));
			}
		}
		return fileList;
	}

	/**
	 *  Created on 2011-2-12 
	 * <p>Description: [����������������]</p>
	 * @author:١��� tge0503020211@163.com
	 * @update:[����YYYY-MM-DD] [����������][�޸�˵��]
	 * @param args.
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	 /**
     * 
     *  Created on 2010-9-18 
     * <p>Description: [�����ļ�����,����һ��URL]</p>
     * @author:١��� tge0503020211@163.com
     * @update:[����YYYY-MM-DD] [����������][�޸�˵��]
     * @param fileName
     * @return.
     */
    public URL getFileURL(String fileName)
    {
        ClassLoader loader = this.getClass().getClassLoader();
        URL fileUrl = loader.getResource(fileName);
        return fileUrl;
    }

    /**
     * 
     *  Created on 2010-9-18 
     * <p>Description: [ȡ���ļ��ľ���·��,��decode��������Ŀ¼]</p>
     * @author:١��� tge0503020211@163.com
     * @update:[����YYYY-MM-DD] [����������][�޸�˵��]
     * @param fileName
     * @return.
     */
    public String getFilePath(String fileName)
    {
        URL url = getFileURL(fileName);
        try
        {
            return URLDecoder.decode(url.getPath(), "UTF-8");
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 
     *  Created on 2010-9-18 
     * <p>Description: [ȡ���ļ�������]</p>
     * @author:١��� tge0503020211@163.com
     * @update:[����YYYY-MM-DD] [����������][�޸�˵��]
     * @param fileName
     * @return.
     */
    public InputStream getFileInputStream(String fileName)
    {
        String path = getFilePath(fileName);
        InputStream is = null;
        try
        {
            is = new FileInputStream(path);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        finally
        {
            IOUtils.closeQuietly(is);
        }
        return is;
    }

	/**
	 * ɾ��Ŀ¼���ļ��У��Լ�Ŀ¼�µ��ļ�
	 * 
	 * @param dir
	 *            ��ɾ��Ŀ¼���ļ�·��
	 * @return Ŀ¼ɾ���ɹ�����true,���򷵻�false
	 */
	public static boolean deleteDirectory(String dir) {
		// ���dir�����ļ��ָ�����β���Զ�����ļ��ָ���
		if (!dir.endsWith(File.separator)) {
			dir = dir + File.separator;
		}
		File dirFile = new File(dir);
		// ���dir��Ӧ���ļ������ڣ����߲���һ��Ŀ¼�����˳�
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			System.out.println("ɾ��Ŀ¼ʧ��" + dir + "Ŀ¼�����ڣ�");
			return false;
		}
		boolean flag = true;
		// ɾ���ļ����µ������ļ�(������Ŀ¼)
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// ɾ�����ļ�
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag) {
					break;
				}
			}
			// ɾ����Ŀ¼
			else {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag) {
					break;
				}
			}
		}
		if (!flag) {
			System.out.println("ɾ��Ŀ¼ʧ��");
			return false;
		}

		// ɾ����ǰĿ¼
		if (dirFile.delete()) {
			System.out.println("ɾ��Ŀ¼" + dir + "�ɹ���");
			return true;
		} else {
			System.out.println("ɾ��Ŀ¼" + dir + "ʧ�ܣ�");
			return false;
		}
	}

	/**
	 * ɾ�������ļ�
	 * 
	 * @param fileName
	 *            ��ɾ���ļ����ļ���
	 * @return �����ļ�ɾ���ɹ�����true,���򷵻�false
	 */
	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		if (file.isFile() && file.exists()) {
			file.delete();
			System.out.println("ɾ�������ļ�" + fileName + "�ɹ���");
			return true;
		} else {
			System.out.println("ɾ�������ļ�" + fileName + "ʧ�ܣ�");
			return false;
		}
	}
}
