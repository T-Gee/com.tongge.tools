package com.tongge.workspace.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.junit.Assert;

public class ProjectModel {
	
	private File file;
	
	private static String CODESET = "GBK";
	
	/**
	 * 
	 * @param workspaceProjectPath �����ռ��� .metadata/.plugins/org.eclipse.core.resources/.projects Ŀ¼�µ����ļ���
	 * @throws IOException 
	 */
	public ProjectModel(String workspaceProjectPath) throws IOException {
		this(new File(workspaceProjectPath));
	}
	

	/**
	 * 
	 * @param workspaceProject �����ռ��� .metadata/.plugins/org.eclipse.core.resources/.projects Ŀ¼�µ����ļ���
	 * @throws IOException
	 */
	public ProjectModel(File workspaceProject) throws IOException {
		File location = new File(workspaceProject.getPath() + File.separator + ".location");
		if(!location.exists()){
		    String path = workspaceProject.getPath();
		    this.file = new File(path.substring(0,path.indexOf(".metadata")) + workspaceProject.getName());
		    if(!this.file.exists()){
//		        throw new FileNotFoundException("�Ҳ���������Ϣ��" + workspaceProject.getName());
		    }
		}else if(location.exists()){
			FileInputStream is = FileUtils.openInputStream(location);
			LineIterator lit = IOUtils.lineIterator(is, CODESET);
			if (lit.hasNext()) {
				String locastr = (String) lit.next();
				locastr = locastr.substring(locastr.indexOf("URI//file:") + "URI//file:".length()+1,locastr.lastIndexOf(workspaceProject.getName())+workspaceProject.getName().length());
				this.file = new File(locastr);
			}
		} 	
		else {
//			throw new FileNotFoundException("�Ҳ���������Ϣ��" + workspaceProject.getName());
		}
	}
	
	
	//��������λ��
	private String location;
	//���̰������ļ�
	private List<File> files;
	//���̰�����Դ���
	private List<String> sources;
	//�Ƿ���web����
	private boolean isWeb = true;
	//��������

	private File project;
	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public List<File> getFiles() {
		return files;
	}
	public void setFiles(List<File> files) {
		this.files = files;
	}
	public List<String> getSources() {
		return sources;
	}
	public void setSources(List<String> sources) {
		this.sources = sources;
	}
	public boolean isWeb() {
		return isWeb;
	}
	public void setWeb(boolean isWeb) {
		this.isWeb = isWeb;
	}
	
	public String getName() {
		return this.file.getName();
	}

	public String getPath() {
		return this.file.getPath();
	}
	
	
	
}
