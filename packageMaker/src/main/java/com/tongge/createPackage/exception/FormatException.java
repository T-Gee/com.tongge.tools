package com.tongge.createPackage.exception;

/**
 * 
 * Created on 2011-2-17
 * <p>Title:       tge0503020211@163.com/p>
 * <p>Description: [���������Ҫ���ܽ���]</p>
 * <p>Copyright:   Copyright (c) 2009</p>
 * <p>Company:     </p>
 * <p>Department:  </p>
 * @author:١���
 * @email:tge0503020211@163.com
 * @version        1.0
 */
public class FormatException extends AppException
{

    /**
     * {�ֶι�������}
     */
    private static final long serialVersionUID = 7946670141465274846L;

    /**
     * <p>Discription:[�������Ĺ�����]</p>
     * @coustructor 
     */
    public FormatException(String arg0)
    {
        super(arg0);
    }

    /**
     * <p>Discription:[�������Ĺ���������]</p>
     * @coustructor 
     */
    public FormatException(Throwable arg0)
    {
        super(arg0);
    }

    /**
     * 
     * <p>Discription:[�������Ĺ���������</p>
     * @coustructor ����.
     */
    public FormatException(String arg0, Throwable arg1)
    {
        super(arg0, arg1);
    }

}
