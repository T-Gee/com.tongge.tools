package mypatch.reader.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import mypatch.ApplicationContext;

import org.apache.commons.lang3.StringUtils;

import com.tongge.workspace.model.ProjectModel;

public class BaseReader {

	

	/**
	 * 根据文件后缀判断是否为已注册的可以装包的文件类型
	 * 
	 * @param line
	 *            文件全名
	 * @return
	 */
	protected boolean isSuffixRegistered(String line) {
		String suffix = "";
		try {
			if (line != null && line.indexOf(".") > -1) {
				suffix = line.substring(line.lastIndexOf('.'));
			} else {
				System.out.println("不符合要求的路径:" + line);
				return false;
			}

			if (ApplicationContext.suffixSet.contains(suffix))
				return true;
			System.out.println("不符合要求的路径:" + line);
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("不符合要求的路径:" + line);
			return false;
		}

	}
	
	protected File locateFile(ProjectModel file, String virtualFileName)
			throws FileNotFoundException, UnsupportedEncodingException {
		String trueFileName = virtualFileName.replace('/', File.separatorChar);
		trueFileName = file.getPath() + File.separatorChar + trueFileName;
		
		
		File trueFile = new File(trueFileName);
		if (!trueFile.exists()) {
			System.err.println("Couldn't find file: "
					+ trueFile.getAbsolutePath());
			throw new FileNotFoundException();
		}
		return trueFile;
	}
	
	protected File locateJavaClassFile(String virtualFileName)
			throws FileNotFoundException {
		String trueFileName = StringUtils.replace(virtualFileName, "src",
				"EAPDomain" + File.separator + "WEB-INF" + File.separator
						+ "classes");
		trueFileName = StringUtils.replace(trueFileName, ".java", ".class");
		File trueFile = new File(trueFileName);
		if (!trueFile.exists()) {
			System.err.println("Couldn't find file: "
					+ trueFile.getAbsolutePath());
			throw new FileNotFoundException();
		}
		return trueFile;
	}
}
