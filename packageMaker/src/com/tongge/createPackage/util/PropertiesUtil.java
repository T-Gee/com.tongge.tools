package com.tongge.createPackage.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import mypatch.Constant;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.tongge.createPackage.exception.NoPropertyFound;
import com.tongge.createPackage.exception.PropertiesException;

/**
 * 
 * Created on 2010-9-18
 * <p>
 * Title: �����_[��ϵͳͳ��]_[ģ����]/p>
 * <p>
 * Description: [���������Ҫ���ܽ���]
 * </p>
 * <p>
 * Copyright: Copyright (c) 2009
 * </p>
 * <p>
 * Company: 
 * </p>
 * <p>
 * Department: 
 * </p>
 * 
 * @author:١���
 * @email:tge0503020211@163.com
 * @version 1.0
 */
public class PropertiesUtil {
	private static String path; // url

	/** ������Ϣ */
	private static Properties propt = null;

	/**
	 * 
	 * Created on 2010-9-18
	 * <p>
	 * Description: [ȡ��config.properties�ļ��е�����]
	 * </p>
	 * 
	 * @author:١��� tge0503020211@163.com
	 * @update:[����YYYY-MM-DD] [����������][�޸�˵��]
	 * @param key
	 * @throws PropertiesException 
	 * @return.
	 */
	public static String getProperty(String key) throws NoPropertyFound {
		return getProperty(null, key);
	}

	/**
	 * 
	 * Created on 2010-9-18
	 * <p>
	 * Description: [ȡ��config.properties�ļ��е�����]
	 * </p>
	 * 
	 * @author:١��� tge0503020211@163.com
	 * @update:[����YYYY-MM-DD] [����������][�޸�˵��]
	 * @param key
	 * @throws PropertiesException 
	 * @return.
	 */
	public static String getProperty(String filePath, String key) throws NoPropertyFound {
		String result = "";
		if (StringUtils.isNotEmpty(filePath)) {
			path = filePath;
		} else {
//			FileUtil fileUtil = new FileUtil();
//			path = fileUtil.getFilePath(Constant.CONFIG_PROPERTIES);
			path = Constant.CONFIG_PROPERTIES;
		}
		InputStream is = null;
		try {
			is = new FileInputStream(path);
			propt = new Properties();
			propt.load(is);
			result = new String(propt.getProperty(key).getBytes("ISO8859-1"),
					"GB18030");
		} catch (Exception e) {
			throw new NoPropertyFound(key);
		} finally {
			IOUtils.closeQuietly(is);
		}

		return result;
	}
}
