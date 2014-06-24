package com.tongge.application;

/**
 * 
 * Created on 2010-9-18
 * <p>Title:       出差补助_[子系统统名]_[模块名]/p>
 * <p>Description: [描述该类概要功能介绍]</p>
 * <p>Copyright:   Copyright (c) 2009</p>
 * <p>Company:     </p>
 * <p>Department:  </p>
 * @author:佟广恩
 * @email:tge0503020211@163.com
 * @version        1.0
 */
public class AppException extends Exception
{
    /**
     * {字段功能描述}
     */
    private static final long serialVersionUID = -3618558822682089670L;

    /**
     * 
     *  Created on 2010-9-18 
     * <p>Description: [使用logicTxt,cause包装一个AppException]</p>
     * @author:佟广恩 tge0503020211@163.com
     * @update:[日期YYYY-MM-DD] [更改人姓名][修改说明]
     * @param logicTxt
     * @param cause
     * @return.
     */
    public static AppException pack(String logicTxt, Exception cause)
    {
        AppException logicException = new AppException(logicTxt, cause);
        return logicException;
    }

    /**
     * <p>Discription:[带参数的构造器]</p>
     * @coustructor 
     */
    public AppException(String arg0)
    {
        super(arg0);
        System.exit(1);
    }

    /**
     * <p>Discription:[带参数的构造器重载]</p>
     * @coustructor 
     */
    public AppException(Throwable arg0)
    {
        super(arg0);
        System.exit(1);
    }

    /**
     * 
     * <p>Discription:[带参数的构造器重载</p>
     * @coustructor 方法.
     */
    public AppException(String arg0, Throwable arg1)
    {
        super(arg0, arg1);
        System.exit(1);
    }
}
