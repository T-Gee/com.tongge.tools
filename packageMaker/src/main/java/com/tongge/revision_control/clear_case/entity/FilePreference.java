package com.tongge.revision_control.clear_case.entity;

import java.util.Date;

public class FilePreference {
	/**
	 * 	ð��ǰ���ļ����У����Ƴ���
	 *	ð�ź󣺵�1�Σ��á�|���֣��ļ����У�����
	 *	��2�Σ��ļ����У����ͣ�1 �ļ���2 �ļ���
	 *	��3�Σ�ʱ���
	 *	��4�Σ��ļ���С
	 *	��5�Σ�
	 *	��6�Σ�
	 *	��7�Σ��Ƿ����ļ�
	 */
	
	/**
	 * �ļ����У����Ƴ���
	 */
	private int nameLength;
	/**
	 * �ļ����У�����
	 */
	private String name;
	/**
	 * �ļ����У����ͣ�1 �ļ���2 �ļ���
	 */
	private String type;
	/**
	 * ʱ���
	 */
	private Date timeStamp;
	/**
	 * �ļ���С
	 */
	private long fileSize;
	private String unknown1;
	private String unknown2;
	/**
	 * �Ƿ����ļ�
	 */
	private boolean checkOut;
	/**
	 * �Ƿ��д
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
