/**
 * Created on 2011-2-12
 * <p>Title:tge0503020211@163.com/p>
 * <p>Description: [描述该类概要功能介绍]</p>
 * @author:佟广恩
 * @email:tge0503020211@163.com
 * @version        1.0
 */
package mypatch;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;

import com.tongge.workspace.model.ProjectModel;
import com.tongge.workspace.model.WorkspaceModel;

/**
 * Created on 2011-2-12
 * <p>
 * Title: tge0503020211@163.com/p>
 * <p>
 * Description: [描述该类概要功能介绍]
 * </p>
 * <p>
 * Copyright: Copyright (c) 2009
 * </p>
 * <p>
 * Company: 东软软件股份有限公司
 * </p>
 * <p>
 * Department: 软件开发事业部
 * </p>
 * 
 * @author:佟广恩
 * @email:tge0503020211@163.com
 * @version 1.0
 */
public class ApplicationContext {
	public static final String CONFIG_PROPERTIES = "config.properties";
	public static final String WORK_SPACE = "workspace";
	public static final String PROJECT = "project";
	public static final String PACKAGE_DIR = "package_dir";
	public static final String PATCH_FILENAME = "patch_filename";
	public static final String PatchFile_Kind = "PatchFileKind";
	public static final String PatchFile_Kind_CLEARCASE = "ClearCase";
	public static final String PatchFile_Kind_CVS = "CVS";
	public static final String PatchFile_Kind_SVN = "SVN";
	public static final String PatchFile_Kind_NONE = "NONE";
	public static final String Need_PATCH = "NeedPatch";
	public static final String TYPE = "type";
	public static final String TYPE_CheckOut = "CheckOut";
	public static final String TYPE_Hijack = "Hijack";
	
	public static final String WORKSPACE_PROJECTS = "workspaceProjects";
	
	private final static Map<String,Object> context = new HashMap<String, Object>();
	
	public static Map<String, Object> getContext() {
		return context;
	}

	public static String FILELIST = "";

	/**
	 * 后缀集合
	 */
	public static HashSet<String> suffixSet = new HashSet<String>();
	static {
		suffixSet.add(".java");
		suffixSet.add(".class");
		suffixSet.add(".jsp");
		suffixSet.add(".jpg");
		suffixSet.add(".gif");
		suffixSet.add(".css");
		suffixSet.add(".xml");
		suffixSet.add(".inc");
		suffixSet.add(".properties");
		suffixSet.add(".js");
		suffixSet.add(".tld");
		suffixSet.add(".xls");
		suffixSet.add(".exe");
		suffixSet.add(".wsdd");
		

	}
	

}
