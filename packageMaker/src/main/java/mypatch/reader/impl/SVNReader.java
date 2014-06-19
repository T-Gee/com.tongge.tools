package mypatch.reader.impl;

import java.io.File;
import java.io.LineNumberReader;
import java.util.Map;
import java.util.Set;

import mypatch.ApplicationContext;
import mypatch.reader.IReader;

import com.tongge.patcher.model.Patch;
import com.tongge.workspace.model.ProjectModel;

public class SVNReader extends BaseReader implements IReader {

	@SuppressWarnings("unchecked")
	public Patch read(LineNumberReader lReader) throws Exception {
		
		Patch patch = new Patch();
		Set<File> project = null;
		ProjectModel projectModel = null;
		while (lReader.ready()) {
			String virtualFileName = lReader.readLine();
			if (virtualFileName.startsWith("#P ")) {
				String projectName = virtualFileName.substring(3);
				patch.addProject(projectName);
				project = patch.getProject(projectName);
				projectModel = ((Map<String, ProjectModel>)ApplicationContext.getContext().get(ApplicationContext.WORKSPACE_PROJECTS)).get(projectName);

			}
			if (virtualFileName.startsWith("Index: ")) { // 如果本行为有用文件信息
				virtualFileName = virtualFileName.substring("Index: ".length());// 掐头
				if (isSuffixRegistered(virtualFileName)) {
					File trueFile = locateFile(projectModel, virtualFileName);
					project.add(trueFile);
				}
			}
		}
		return patch;
	}

}
