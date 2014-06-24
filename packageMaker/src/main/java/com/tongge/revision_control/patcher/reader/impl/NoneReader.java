package com.tongge.revision_control.patcher.reader.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import mypatch.ConfigKeyEnmu;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;

import com.tongge.application.ApplicationContext;
import com.tongge.revision_control.patcher.model.Patch;
import com.tongge.revision_control.patcher.model.PatchFileProjectModel;
import com.tongge.revision_control.patcher.reader.IReader;
import com.tongge.revision_control.patcher.reader.ReaderUtils;
import com.tongge.workspace.model.ProjectModel;
import com.tongge.workspace.utils.ProjectUtils;

/**
 * 
 * Created on 2014年6月22日
 * <p>Title		 : 移动代维系统_[子系统统名]_[模块名]</p>
 * <p>Description: [只有文件列表的patch文件]</p>
 * @author		 : 佟广恩 tongguangen@ultrapower.com.cn
 * @version      : 1.0
 */
/*--------------------------------------------------------------
 *修改履历： 1) [修改时间] [修改人] [修改说明]；
 *--------------------------------------------------------------
 */
public class NoneReader extends BaseReader implements IReader {

    @SuppressWarnings("unchecked")
    public Patch read(String path) throws Exception {
        String charset = (String) ApplicationContext.getContext().get(ConfigKeyEnmu.CHARSET);
        final Patch patch = new Patch(path);
        if (!patch.exists()) {
            throw new FileNotFoundException("the patch not found：" + path);
        }
        LineIterator lineIterator = IOUtils.lineIterator(FileUtils.openInputStream(patch), charset);
        PatchFileProjectModel project = null;
        ProjectModel projectModel = null;
        String projectName = null;
        while (lineIterator.hasNext()) {
            String line = (String) lineIterator.next();
            if (line.startsWith(File.separator)) {
                line = line.substring(1);
            }

            String newProjectName = line.substring(0, line.indexOf(File.separator));
            if (newProjectName != null && !newProjectName.equals(projectName)) {
                projectName = newProjectName;
                patch.addProject(projectName);
                project = patch.getProject(projectName);
                projectModel = ((Map<String, ProjectModel>) ApplicationContext.getContext().get(
                        ApplicationContext.WORKSPACE_PROJECTS)).get(projectName);
                project.setProjectModel(projectModel);
            }

            //
            line = line.substring(line.indexOf(File.separator));
            if (isSuffixRegistered(line) && line.endsWith("")) {
                File trueFile = ReaderUtils.locateFile(projectModel, line);
                project.add(trueFile);
            }
            assert projectModel != null;
            List<File> clazzes = ProjectUtils.getClassFile(projectModel, line);
            if (clazzes != null && !clazzes.isEmpty()) {
                project.addAll(clazzes);
            }

        }
        // sort
        Iterator<PatchFileProjectModel> it = patch.getProjects().iterator();
        while (it.hasNext()) {
            List<File> listFiles = it.next();
            if (listFiles != null && !listFiles.isEmpty()) {
                Collections.sort(listFiles, comparator);
            }

        }
        return patch;
    }

}
