package com.tongge.commons.io;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.tongge.application.ApplicationContext;
import com.tongge.commons.io.exception.NoPropertyFound;
import com.tongge.commons.io.exception.PropertiesException;

/**
 * 
 * Created on 2010-9-18
 * <p>
 * Title: 出差补助_[子系统统名]_[模块名]/p>
 * <p>
 * Description: [描述该类概要功能介绍]
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
 * @author:佟广恩
 * @email:tge0503020211@163.com
 * @version 1.0
 */
public class PropertiesUtil {
	private static String path; // url

	/** 配置信息 */
	private static Properties propt = null;

	/**
	 * 
	 * Created on 2010-9-18
	 * <p>
	 * Description: [取得config.properties文件中的属性]
	 * </p>
	 * 
	 * @author:佟广恩 tge0503020211@163.com
	 * @update:[日期YYYY-MM-DD] [更改人姓名][修改说明]
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
	 * Description: [取得config.properties文件中的属性]
	 * </p>
	 * 
	 * @author:佟广恩 tge0503020211@163.com
	 * @update:[日期YYYY-MM-DD] [更改人姓名][修改说明]
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
			path = ApplicationContext.CONFIG_PROPERTIES;
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
