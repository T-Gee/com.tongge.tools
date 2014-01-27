package com.tongge.createPackage.entity;

public class CCdbFile {
	/**
	 * 第一行：估计是 CC文件类型
	 * 第二行：文件路径
	 * 第三行：未知
	 * 第四行：文件及文件夹个数（16进制）
	 * 第5-n行：文件（夹）详细描述
	 */
	
	/**
	 * 估计是 CC文件类型
	 */
	private String type;
	/**
	 * 文件路径
	 */
	private String path;
	/**
	 * 未知
	 */
	private String unKnown1;
	/**
	 * 文件及文件夹个数（16进制）
	 */
	private String fileCount;
	/**
	 * 文件（夹）详细描述
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
