package com.tongge.createPackage.entity;

public class CCdbFile {
	/**
	 * ��һ�У������� CC�ļ�����
	 * �ڶ��У��ļ�·��
	 * �����У�δ֪
	 * �����У��ļ����ļ��и�����16���ƣ�
	 * ��5-n�У��ļ����У���ϸ����
	 */
	
	/**
	 * ������ CC�ļ�����
	 */
	private String type;
	/**
	 * �ļ�·��
	 */
	private String path;
	/**
	 * δ֪
	 */
	private String unKnown1;
	/**
	 * �ļ����ļ��и�����16���ƣ�
	 */
	private String fileCount;
	/**
	 * �ļ����У���ϸ����
	 */
	private FilePreference []filePreference;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getUnKnown1() {
		return unKnown1;
	}
	public void setUnKnown1(String unKnown1) {
		this.unKnown1 = unKnown1;
	}
	public String getFileCount() {
		return fileCount;
	}
	public void setFileCount(String fileCount) {
		this.fileCount = fileCount;
	}
	public FilePreference []getFilePreference() {
		return filePreference;
	}
	public void setFilePreference(FilePreference []filePreference) {
		this.filePreference = filePreference;
	}
	
	
	
}
