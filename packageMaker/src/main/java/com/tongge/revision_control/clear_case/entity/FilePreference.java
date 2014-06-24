package com.tongge.revision_control.clear_case.entity;

import java.util.Date;

public class FilePreference {
	/**
	 * 	冒号前：文件（夹）名称长度
	 *	冒号后：第1段（用“|”分）文件（夹）名称
	 *	第2段：文件（夹）类型：1 文件，2 文件夹
	 *	第3段：时间戳
	 *	第4段：文件大小
	 *	第5段：
	 *	第6段：
	 *	第7段：是否检出文件
	 */
	
	/**
	 * 文件（夹）名称长度
	 */
	private int nameLength;
	/**
	 * 文件（夹）名称
	 */
	private String name;
	/**
	 * 文件（夹）类型：1 文件，2 文件夹
	 */
	private String type;
	/**
	 * 时间戳
	 */
	private Date timeStamp;
	/**
	 * 文件大小
	 */
	private long fileSize;
	private String unknown1;
	private String unknown2;
	/**
	 * 是否检出文件
	 */
	private boolean checkOut;
	/**
	 * 是否可写
	 */
	private boolean canWrite;
	
	
	
	public boolean isCanWrite() {
		return canWrite;
	}
	public void setCanWrite(boolean canWrite) {
		this.canWrite = canWrite;
	}
	public int getNameLength() {
		return nameLength;
	}
	public void setNameLength(int nameLength) {
		this.nameLength = nameLength;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public long getFileSize() {
		return fileSize;
	}
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
	public String getUnknown1() {
		return unknown1;
	}
	public void setUnknown1(String unknown1) {
		this.unknown1 = unknown1;
	}
	public String getUnknown2() {
		return unknown2;
	}
	public void setUnknown2(String unknown2) {
		this.unknown2 = unknown2;
	}
	public boolean isCheckOut() {
		return checkOut;
	}
	public void setCheckOut(boolean checkOut) {
		this.checkOut = checkOut;
	}
	public Date getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	
}
