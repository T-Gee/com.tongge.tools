package com.tongge.revision_control.clear_case.exception;

import com.tongge.application.AppException;

public class ClearCaseException extends AppException {
	/**
     * {�ֶι�������}
     */
    private static final long serialVersionUID = 7946670141465274846L;

    /**
     * <p>Discription:[�������Ĺ�����]</p>
     * @coustructor 
     */
    public ClearCaseException(String arg0)
    {
        super(arg0);
    }

    /**
     * <p>Discription:[�������Ĺ���������]</p>
     * @coustructor 
     */
    public ClearCaseException(Throwable arg0)
    {
        super(arg0);
    }

    /**
     * 
     * <p>Discription:[�������Ĺ���������</p>
     * @coustructor ����.
     */
    public ClearCaseException(String arg0, Throwable arg1)
    {
        super(arg0, arg1);
    }
}
