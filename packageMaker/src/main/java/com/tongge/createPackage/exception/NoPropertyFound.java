package com.tongge.createPackage.exception;


public class NoPropertyFound extends PropertiesException {

	  /**
     * <p>Discription:[�������Ĺ�����]</p>
	 * @throws PropertiesException 
     * @coustructor 
     */
    public NoPropertyFound(String arg0) 
    {
		super("�����ļ���û���������:" + arg0);
    }

    /**
     * <p>Discription:[�������Ĺ���������]</p>
     * @coustructor 
     */
    public NoPropertyFound(Throwable arg0)
    {
    	super(arg0);
    }

    /**
     * 
     * <p>Discription:[�������Ĺ���������</p>
     * @coustructor ����.
     */
    public NoPropertyFound(String arg0, Throwable arg1)
    {
        super("�����ļ���û���������:" + arg0, arg1);
    }

	/**
	 * <p>Description:[�ֶι�������]</p>
	 */
	private static final long serialVersionUID = -8140231358823835794L;

}
