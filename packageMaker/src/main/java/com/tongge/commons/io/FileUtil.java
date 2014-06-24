/**
 * Created on 2011-2-12
 * <p>Title:tge0503020211@163.com/p>
 * <p>Description: [描述该类概要功能介绍]</p>
 * @author:佟广恩
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
 * <p>Description: [描述该类概要功能介绍]</p>
 * <p>Copyright:   Copyright (c) 2009</p>
 * <p>Company:     </p>
 * <p>Department:  </p>
 * @author:佟广恩
 * @email:tge0503020211@163.com
 * @version        1.0
 */
public class FileUtil {
	
	
	/**
	 * 
	 *  Created on 2011-2-12 
	 * <p>Description: [找到某个文件路径下的所有文件，并通过正则表达式对文件进行筛选]</p>
	 * @author:佟广恩 tge0503020211@163.com
	 * @update:[日期YYYY-MM-DD] [更改人姓名][修改说明]
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
	 * <p>Description: [直接在List<File>中查找满足正则表达式的文件]</p>
	 * @author:佟广恩 tge0503020211@163.com
	 * @update:[日期YYYY-MM-DD] [更改人姓名][修改说明]
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
	 * <p>Description: [找到某个文件路径下的所有文件]</p>
	 * @author:佟广恩 tge0503020211@163.com
	 * @update:[日期YYYY-MM-DD] [更改人姓名][修改说明]
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
	 * <p>Description: [循环找到某个文件（夹）下的所有文件]</p>
	 * @author:佟广恩 tge0503020211@163.com
	 * @update:[日期YYYY-MM-DD] [更改人姓名][修改说明]
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
	 * <p>Description: [方法功能中文描述]</p>
	 * @author:佟广恩 tge0503020211@163.com
	 * @update:[日期YYYY-MM-DD] [更改人姓名][修改说明]
	 * @param args.
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	 /**
     * 
     *  Created on 2010-9-18 
     * <p>Description: [根据文件名称,返回一个URL]</p>
     * @author:佟广恩 tge0503020211@163.com
     * @update:[日期YYYY-MM-DD] [更改人姓名][修改说明]
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
     * <p>Description: [取得文件的绝对路径,用decode兼容中文目录]</p>
     * @author:佟广恩 tge0503020211@163.com
     * @update:[日期YYYY-MM-DD] [更改人姓名][修改说明]
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
     * <p>Description: [取得文件输入流]</p>
     * @author:佟广恩 tge0503020211@163.com
     * @update:[日期YYYY-MM-DD] [更改人姓名][修改说明]
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
	 * 删除目录（文件夹）以及目录下的文件
	 * 
	 * @param dir
	 *            被删除目录的文件路径
	 * @return 目录删除成功返回true,否则返回false
	 */
	public static boolean deleteDirectory(String dir) {
		// 如果dir不以文件分隔符结尾，自动添加文件分隔符
		if (!dir.endsWith(File.separator)) {
			dir = dir + File.separator;
		}
		File dirFile = new File(dir);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			System.out.println("删除目录失败" + dir + "目录不存在！");
			return false;
		}
		boolean flag = true;
		// 删除文件夹下的所有文件(包括子目录)
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag) {
					break;
				}
			}
			// 删除子目录
			else {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag) {
					break;
				}
			}
		}
		if (!flag) {
			System.out.println("删除目录失败");
			return false;
		}

		// 删除当前目录
		if (dirFile.delete()) {
			System.out.println("删除目录" + dir + "成功！");
			return true;
		} else {
			System.out.println("删除目录" + dir + "失败！");
			return false;
		}
	}

	/**
	 * 删除单个文件
	 * 
	 * @param fileName
	 *            被删除文件的文件名
	 * @return 单个文件删除成功返回true,否则返回false
	 */
	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		if (file.isFile() && file.exists()) {
			file.delete();
			System.out.println("删除单个文件" + fileName + "成功！");
			return true;
		} else {
			System.out.println("删除单个文件" + fileName + "失败！");
			return false;
		}
	}
}
