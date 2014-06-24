/**
 * Created on 2011-2-12
 * <p>Title:tge0503020211@163.com/p>
 * <p>Description: [描述该类概要功能介绍]</p>
 * @author:佟广恩
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
 * <p>Description: [描述该类概要功能介绍]</p>
 * <p>Copyright:   Copyright (c) 2009</p>
 * <p>Company:     </p>
 * <p>Department:  </p>
 * @author:佟广恩
 * @email:tge0503020211@163.com
 * @version        1.0
 */
public class PatchCreater {

    /**
     * 根据CC文件特点将要导出做升级包的文件做成列表
     *  Created on 2011-2-12 
     * <p>Description: [方法功能中文描述]</p>
     * @author:佟广恩 tge0503020211@163.com
     * @throws AppException 
     * @update:[日期YYYY-MM-DD] [更改人姓名][修改说明].
     */
    public static void createPatch() throws AppException {
        FileDealer fileDealer = new FileDealer();
        // 获取配置文件信息
        String patchFileName = (String) ApplicationContext.getContext().get(ConfigKeyEnmu.PATCH_FILENAME);
        String workSpace = (String) ApplicationContext.getContext().get(ConfigKeyEnmu.WORK_SPACE);
        String project = null;// (String)ApplicationContext.getContext().get(ConfigKeyEnmu.PROJECT);
        String patchType = (String) ApplicationContext.getContext().get(ConfigKeyEnmu.TYPE);

        // 得到具体的路径projectPath
        String projectPath = workSpace.concat(project);
        // 得到所有的文件fileList
        List fileList = FileUtil.getAllFile(projectPath);// CheckOut
        List OutFile = null;

        if (ApplicationContext.TYPE_CheckOut.equalsIgnoreCase(patchType)) {
            // 查找所有.copyarea.db文件dbFiles
            List dbFiles = FileUtil.getOneKindFiles(fileList, ".*\\.copyarea\\.db$");
            if (dbFiles.isEmpty()) {
                throw new ClearCaseException("指定的工作区内没有ClearCase文件（*.db）");
            }
            /* 查到所有的已检出的文件checkOutFile */
            OutFile = getCheckOutFile(dbFiles);
            if (OutFile.isEmpty()) {
                throw new ClearCaseException("指定的工作区内没有文件检出！");
            }
        } else if (ApplicationContext.TYPE_Hijack.equalsIgnoreCase(patchType)) {
            /* 查到所有的非只读文件 */
            OutFile = getCanWriteFile(fileList);
        } else {
            throw new NoPropertyValueFound(patchType);
        }
        // 整理path
        OutFile = doposePath(OutFile, project);
        // 去掉例外文件
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
     * <p>Description: [将URl处理成可以用于输出的形式 模拟cvs]</p>
     * @author:佟广恩 tge0503020211@163.com
     * @update:[日期YYYY-MM-DD] [更改人姓名][修改说明]
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
     * <p>Description: [查到所有的已检出的文件checkOutFile]</p>
     * @author:佟广恩 tge0503020211@163.com
     * @update:[日期YYYY-MM-DD] [更改人姓名][修改说明]
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
     * <p>Description: [查到所有的非只读文件]</p>
     * @author:佟广恩 tge0503020211@163.com
     * @update:[日期YYYY-MM-DD] [更改人姓名][修改说明]
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
