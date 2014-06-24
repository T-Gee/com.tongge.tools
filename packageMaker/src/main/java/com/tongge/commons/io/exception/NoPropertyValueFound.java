package com.tongge.commons.io.exception;

import com.tongge.commons.io.PropertiesUtil;

public class NoPropertyValueFound extends PropertiesException {

	  /**
     * <p>Discription:[带参数的构造器]</p>
	 * @throws PropertiesException 
     * @coustructor 
     */
    public NoPropertyValueFound(String arg0) throws NoPropertyFound
    {
		super("配置文件中没有这个属性值（" + arg0 + "：" + PropertiesUtil.getProperty(arg0));
    }

    /**
     * <p>Discription:[带参数的构造器重载]</p>
     * @coustructor 
     */
    public NoPropertyValueFound(Throwable arg0) throws NoPropertyFound
    {
    	super(arg0);
    }

    /**
     * 
     * <p>Discription:[带参数的构造器重载</p>
     * @coustructor 方法.
     */
    public NoPropertyValueFound(String arg0, Throwable arg1)throws NoPropertyFound
    {
    	super("配置文件中没有这个属性值（" + arg0 + "：" + PropertiesUtil.getProperty(arg0), arg1);
    }

	/**
	 * <p>Description:[字段功能描述]</p>
	 */
	private static final long serialVersionUID = -8140231358823835794L;

}
