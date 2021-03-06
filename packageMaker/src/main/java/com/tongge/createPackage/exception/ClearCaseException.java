package com.tongge.createPackage.exception;

public class ClearCaseException extends AppException {
	/**
     * {字段功能描述}
     */
    private static final long serialVersionUID = 7946670141465274846L;

    /**
     * <p>Discription:[带参数的构造器]</p>
     * @coustructor 
     */
    public ClearCaseException(String arg0)
    {
        super(arg0);
    }

    /**
     * <p>Discription:[带参数的构造器重载]</p>
     * @coustructor 
     */
    public ClearCaseException(Throwable arg0)
    {
        super(arg0);
    }

    /**
     * 
     * <p>Discription:[带参数的构造器重载</p>
     * @coustructor 方法.
     */
    public ClearCaseException(String arg0, Throwable arg1)
    {
        super(arg0, arg1);
    }
}
