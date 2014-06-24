/**
 * Created on 2011-2-12
 * <p>Title:tge0503020211@163.com/p>
 * <p>Description: [���������Ҫ���ܽ���]</p>
 * @author:١���
 * @email:tge0503020211@163.com
 * @version        1.0
*/
package com.tongge.revision_control.clear_case;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import mypatch.ConfigKeyEnmu;

import com.tongge.application.AppException;
import com.tongge.application.ApplicationContext;
import com.tongge.commons.io.FileDealer;
import com.tongge.commons.io.FileUtil;
import com.tongge.commons.io.exception.NoPropertyValueFound;
import com.tongge.revision_control.clear_case.entity.CCdbFile;
import com.tongge.revision_control.clear_case.entity.FilePreference;
import com.tongge.revision_control.clear_case.exception.ClearCaseException;

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
public class PatchCreater {

    /**
     * ����CC�ļ��ص㽫Ҫ���������������ļ������б�
     *  Created on 2011-2-12 
     * <p>Description: [����������������]</p>
     * @author:١��� tge0503020211@163.com
     * @throws AppException 
     * @update:[����YYYY-MM-DD] [����������][�޸�˵��].
     */
    public static void createPatch() throws AppException {
        FileDealer fileDealer = new FileDealer();
        // ��ȡ�����ļ���Ϣ
        String patchFileName = (String) ApplicationContext.getContext().get(ConfigKeyEnmu.PATCH_FILENAME);
        String workSpace = (String) ApplicationContext.getContext().get(ConfigKeyEnmu.WORK_SPACE);
        String project = null;// (String)ApplicationContext.getContext().get(ConfigKeyEnmu.PROJECT);
        String patchType = (String) ApplicationContext.getContext().get(ConfigKeyEnmu.TYPE);

        // �õ������·��projectPath
        String projectPath = workSpace.concat(project);
        // �õ����е��ļ�fileList
        List fileList = FileUtil.getAllFile(projectPath);// CheckOut
        List OutFile = null;

        if (ApplicationContext.TYPE_CheckOut.equalsIgnoreCase(patchType)) {
            // ��������.copyarea.db�ļ�dbFiles
            List dbFiles = FileUtil.getOneKindFiles(fileList, ".*\\.copyarea\\.db$");
            if (dbFiles.isEmpty()) {
                throw new ClearCaseException("ָ���Ĺ�������û��ClearCase�ļ���*.db��");
            }
            /* �鵽���е��Ѽ�����ļ�checkOutFile */
            OutFile = getCheckOutFile(dbFiles);
            if (OutFile.isEmpty()) {
                throw new ClearCaseException("ָ���Ĺ�������û���ļ������");
            }
        } else if (ApplicationContext.TYPE_Hijack.equalsIgnoreCase(patchType)) {
            /* �鵽���еķ�ֻ���ļ� */
            OutFile = getCanWriteFile(fileList);
        } else {
            throw new NoPropertyValueFound(patchType);
        }
        // ����path
        OutFile = doposePath(OutFile, project);
        // ȥ�������ļ�
        OutFile = exceptFile(OutFile, new String[0]);
        // createPath
        fileDealer.writePath(patchFileName, OutFile);

    }

    private static List exceptFile(List checkOutFile, String[] exceptFiles) {
        List newList = new ArrayList();
        if (exceptFiles == null || exceptFiles.length == 0) {
            return checkOutFile;
        }
        return newList;
    }

    /**
     * 
     *  Created on 2011-2-12 
     * <p>Description: [��URl����ɿ��������������ʽ ģ��cvs]</p>
     * @author:١��� tge0503020211@163.com
     * @update:[����YYYY-MM-DD] [����������][�޸�˵��]
     * @param checkOutFile<File>
     * @return.
     */
    private static List doposePath(List checkOutFile, String project) {
        Iterator it = checkOutFile.iterator();
        List newList = new ArrayList();
        while (it.hasNext()) {
            String url = (String) it.next();
            url = url.substring(url.indexOf(project) + project.length());
            newList.add(url);
        }
        return newList;
    }

    /**
     * 
     *  Created on 2011-2-12 
     * <p>Description: [�鵽���е��Ѽ�����ļ�checkOutFile]</p>
     * @author:١��� tge0503020211@163.com
     * @update:[����YYYY-MM-DD] [����������][�޸�˵��]
     * @param dbFiles
     * @throws AppException 
     * @return.
     */
    private static List getCheckOutFile(List dbFiles) throws AppException {
        Iterator it = dbFiles.iterator();
        List checkOutFile = new ArrayList();
        while (it.hasNext()) {
            File file = (File) it.next();
            CCdbFile dbFile = ClearCaseUtil.readCCdbFile(file);
            FilePreference[] filePreference = dbFile.getFilePreference();
            for (int i = 0; i < filePreference.length; i++) {
                if (filePreference[i].isCheckOut()) {
                    checkOutFile.add(dbFile.getPath() + "\\" + filePreference[i].getName());
                }
            }
        }
        return checkOutFile;
    }

    /**
     * 
     *  Created on 2011-2-12 
     * <p>Description: [�鵽���еķ�ֻ���ļ�]</p>
     * @author:١��� tge0503020211@163.com
     * @update:[����YYYY-MM-DD] [����������][�޸�˵��]
     * @param dbFiles
     * @return
     * @throws AppException.
     */
    private static List getCanWriteFile(List fileList) throws AppException {
        Iterator it = fileList.iterator();
        List canWriteFile = new ArrayList();
        while (it.hasNext()) {
            File file = (File) it.next();
            if (file.canWrite() && file.isFile() && !file.getPath().endsWith("\\.class")
                    && file.getPath().indexOf("\\.") > 0) {
                canWriteFile.add(file.getAbsolutePath());
            }
        }
        return canWriteFile;
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
