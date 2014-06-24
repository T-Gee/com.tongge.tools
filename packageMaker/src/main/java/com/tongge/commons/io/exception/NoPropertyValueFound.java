package com.tongge.commons.io.exception;

import com.tongge.commons.io.PropertiesUtil;

public class NoPropertyValueFound extends PropertiesException {

	  /**
     * <p>Discription:[�������Ĺ�����]</p>
	 * @throws PropertiesException 
     * @coustructor 
     */
    public NoPropertyValueFound(String arg0) throws NoPropertyFound
    {
		super("�����ļ���û���������ֵ��" + arg0 + "��" + PropertiesUtil.getProperty(arg0));
    }

    /**
     * <p>Discription:[�������Ĺ���������]</p>
     * @coustructor 
     */
    public NoPropertyValueFound(Throwable arg0) throws NoPropertyFound
    {
    	super(arg0);
    }

    /**
     * 
     * <p>Discription:[�������Ĺ���������</p>
     * @coustructor ����.
     */
    public NoPropertyValueFound(String arg0, Throwable arg1)throws NoPropertyFound
    {
    	super("�����ļ���û���������ֵ��" + arg0 + "��" + PropertiesUtil.getProperty(arg0), arg1);
    }

	/**
	 * <p>Description:[�ֶι�������]</p>
	 */
	private static final long serialVersionUID = -8140231358823835794L;

}
