package com.tongge.createPackage.exception;

/**
 * 
 * Created on 2011-2-17
 * <p>Title:       tge0503020211@163.com/p>
 * <p>Description: [描述该类概要功能介绍]</p>
 * <p>Copyright:   Copyright (c) 2009</p>
 * <p>Company:     </p>
 * <p>Department:  </p>
 * @author:佟广恩
 * @email:tge0503020211@163.com
 * @version        1.0
 */
public class FormatException extends AppException
{

    /**
     * {字段功能描述}
     */
    private static final long serialVersionUID = 7946670141465274846L;

    /**
     * <p>Discription:[带参数的构造器]</p>
     * @coustructor 
     */
    public FormatException(String arg0)
    {
        super(arg0);
    }

    /**
     * <p>Discription:[带参数的构造器重载]</p>
     * @coustructor 
     */
    public FormatException(Throwable arg0)
    {
        super(arg0);
    }

    /**
     * 
     * <p>Discription:[带参数的构造器重载</p>
     * @coustructor 方法.
     */
    public FormatException(String arg0, Throwable arg1)
    {
        super(arg0, arg1);
    }

}
