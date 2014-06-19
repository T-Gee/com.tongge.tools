package com.tongge.workspace;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.tongge.workspace.model.ProjectModel;
import com.tongge.workspace.model.WorkspaceModel;

public class WorkspaceUtilsTest {

	@Test
	public void testGetProjects() throws Exception {
		try {
			new WorkspaceModel(null);
		} catch (Exception e) {
			assertEquals("输入参数 不能为空", e.getMessage());
		}
		
		try {
			new WorkspaceModel("D:\\workspace\\ultrapower\\workspace\\");
		} catch (Exception e) {
			assertEquals("输入的工作空间不存在", e.getMessage());
		}
		try {
			new WorkspaceModel("F:\\workspace\\ultrapower\\workspace\\.metadata\\.plugins");
		} catch (Exception e) {
			assertEquals("输入的工作空间不合法", e.getMessage());
		}
		
		
		
		
		
	}
	
	@Test
	public void testGetProjects2() throws Exception {
		Map<String, ProjectModel> projects = new WorkspaceModel("F:\\workspace\\ultrapower\\workspace\\").getProjects();
		Set<String> keys = projects.keySet();
		Iterator<String> it = keys.iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			System.out.println(key + ":\t\t" + projects.get(key).getPath());
			
		}
		
	}
	

}
