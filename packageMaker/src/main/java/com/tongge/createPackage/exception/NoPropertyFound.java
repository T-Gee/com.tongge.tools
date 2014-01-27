package com.tongge.createPackage.exception;


public class NoPropertyFound extends PropertiesException {

	  /**
     * <p>Discription:[带参数的构造器]</p>
	 * @throws PropertiesException 
     * @coustructor 
     */
    public NoPropertyFound(String arg0) 
    {
		super("配置文件中没有这个属性:" + arg0);
    }

    /**
     * <p>Discription:[带参数的构造器重载]</p>
     * @coustructor 
     */
    public NoPropertyFound(Throwable arg0)
    {
    	super(arg0);
    }

    /**
     * 
     * <p>Discription:[带参数的构造器重载</p>
     * @coustructor 方法.
     */
    public NoPropertyFound(String arg0, Throwable arg1)
    {
        super("配置文件中没有这个属性:" + arg0, arg1);
    }

	/**
	 * <p>Description:[字段功能描述]</p>
	 */
	private static final long serialVersionUID = -8140231358823835794L;

}
