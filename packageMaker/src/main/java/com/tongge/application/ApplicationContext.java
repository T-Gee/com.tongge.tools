/**
 * Created on 2011-2-12
 * <p>Title:tge0503020211@163.com/p>
 * <p>Description: [描述该类概要功能介绍]</p>
 * @author:佟广恩
 * @email:tge0503020211@163.com
 * @version        1.0
 */
package com.tongge.application;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * Created on 2014年6月21日
 * <p>Title		 : 移动代维系统_[子系统统名]_[模块名]</p>
 * <p>Description: [描述该类概要功能介绍]</p>
 * @author		 : 佟广恩 tongguangen@ultrapower.com.cn
 * @version      : 1.0
 */
/*--------------------------------------------------------------
 *修改履历： 1) [修改时间] [修改人] [修改说明]；
 *--------------------------------------------------------------
 */
public class ApplicationContext {
    public static final String CONFIG_PROPERTIES = "config.properties";
    public static final String TYPE_CheckOut = "CheckOut";
    public static final String TYPE_Hijack = "Hijack";

    public static final String WORKSPACE_PROJECTS = "workspaceProjects";

    private final static Map<Object, Object> context = new HashMap<Object, Object>();

    public static Map<Object, Object> getContext() {
        return context;
    }

}
