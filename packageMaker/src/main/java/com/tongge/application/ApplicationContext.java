/**
 * Created on 2011-2-12
 * <p>Title:tge0503020211@163.com/p>
 * <p>Description: [���������Ҫ���ܽ���]</p>
 * @author:١���
 * @email:tge0503020211@163.com
 * @version        1.0
 */
package com.tongge.application;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * Created on 2014��6��21��
 * <p>Title		 : �ƶ���άϵͳ_[��ϵͳͳ��]_[ģ����]</p>
 * <p>Description: [���������Ҫ���ܽ���]</p>
 * @author		 : ١��� tongguangen@ultrapower.com.cn
 * @version      : 1.0
 */
/*--------------------------------------------------------------
 *�޸������� 1) [�޸�ʱ��] [�޸���] [�޸�˵��]��
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
