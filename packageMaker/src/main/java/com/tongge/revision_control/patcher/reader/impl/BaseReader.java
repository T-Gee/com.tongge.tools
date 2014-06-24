package com.tongge.revision_control.patcher.reader.impl;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import com.tongge.application.ApplicationContext;
import com.tongge.revision_control.patcher.model.Patch;

public abstract class BaseReader {
    
    protected Comparator<File> comparator ;
    
    public void setComparator(Comparator<File> comparator) {
        this.comparator = comparator;
    }
    protected String fileInclude;
    protected String fileExcluding;
	

	/**
	 * 根据文件后缀判断是否为已注册的可以装包的文件类型
	 * 
	 * @param line
	 *            文件全名
	 * @return
	 */
	protected boolean isSuffixRegistered(String line) {
//	    Pattern p = Pattern.compile(fileInclude.r);
//	    Matcher m = p.matcher("");
	    
	    
	    
	    
		String suffix = "";
		try {
			if (line != null && line.indexOf(".") > -1) {
				suffix = line.substring(line.lastIndexOf('.'));
			} else {
				System.out.println("不符合要求的路径:" + line);
				return false;
			}
			if (line.indexOf("applicationContext-allbean-hibernate.xml")>0) {
                System.out.println();
            }
			if (fileInclude.contains(suffix)){
			    String[] excluding = fileExcluding.split(",");
	            for (int i = 0; i < excluding.length; i++) {
	                String reg = excluding[i].replaceAll("\\.", "\\\\.");
	                reg = "^" + reg.replaceAll("\\*", ".*") +"$";
	                if (line.matches(reg)) {
	                    return false;
                    }
	            }
	            return true;
			}
			System.out.println("不符合要求的路径:" + line);
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("不符合要求的路径:" + line);
			return false;
		}
	}
	public abstract Patch read(String path) throws Exception;
	
	public Patch read(String path, String include, String excluding) throws Exception{
        this.fileInclude = include;
        this.fileExcluding = excluding;
        return read(path);
    }
    
    public void setFileInclude(String fileInclude) {
        this.fileInclude = fileInclude;
    }
    public void setFileExcluding(String fileExcluding) {
        this.fileExcluding = fileExcluding;
    }
}
