package com.tongge.application;

/**
 * 
 * Created on 2010-9-18
 * <p>Title:       �����_[��ϵͳͳ��]_[ģ����]/p>
 * <p>Description: [���������Ҫ���ܽ���]</p>
 * <p>Copyright:   Copyright (c) 2009</p>
 * <p>Company:     </p>
 * <p>Department:  </p>
 * @author:١���
 * @email:tge0503020211@163.com
 * @version        1.0
 */
public class AppException extends Exception
{
    /**
     * {�ֶι�������}
     */
    private static final long serialVersionUID = -3618558822682089670L;

    /**
     * 
     *  Created on 2010-9-18 
     * <p>Description: [ʹ��logicTxt,cause��װһ��AppException]</p>
     * @author:١��� tge0503020211@163.com
     * @update:[����YYYY-MM-DD] [����������][�޸�˵��]
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
     * <p>Discription:[�������Ĺ�����]</p>
     * @coustructor 
     */
    public AppException(String arg0)
    {
        super(arg0);
        System.exit(1);
    }

    /**
     * <p>Discription:[�������Ĺ���������]</p>
     * @coustructor 
     */
    public AppException(Throwable arg0)
    {
        super(arg0);
        System.exit(1);
    }

    /**
     * 
     * <p>Discription:[�������Ĺ���������</p>
     * @coustructor ����.
     */
    public AppException(String arg0, Throwable arg1)
    {
        super(arg0, arg1);
        System.exit(1);
    }
}
