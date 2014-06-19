package com.tongge.workspace.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class WorkspaceModel {

	private Map<String,ProjectModel> projects = new HashMap<String,ProjectModel>();

	private File model;

	public WorkspaceModel(String path) throws IOException {
		model = new File(path);
		if (StringUtils.isBlank(path)) {
			throw new FileNotFoundException("������� ����Ϊ��");
		}
		File workspace = new File(path);
		if (!workspace.exists()) {
			throw new FileNotFoundException("����Ĺ����ռ䲻����");
		}

		File metadata = new File(path = path + "/.metadata/.plugins");
		if (!metadata.exists()) {
			throw new FileNotFoundException("����Ĺ����ռ䲻�Ϸ�");
		}

		File projectsf = new File(path = path + "/org.eclipse.core.resources/.projects");
		if (!projectsf.exists()) {
			throw new FileNotFoundException("�ռ���û�й��̵���");
		}

		File[] folders = projectsf.listFiles();
		for (int i = 0; i < folders.length; i++) {
			File project = folders[i];
			projects.put(project.getName(), new ProjectModel(project));
		}

	}

	public Map<String, ProjectModel> getProjects() {
		return projects;
	}

	public void setProjects(Map<String, ProjectModel> projects) {
		this.projects = projects;
	}
}
