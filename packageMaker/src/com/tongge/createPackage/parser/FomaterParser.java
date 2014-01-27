package com.tongge.createPackage.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.tongge.createPackage.exception.FormatException;


/**
 * 
 * Created on 2011-2-12
 * <p>Title:       tge0503020211@163.com/p>
 * <p>Description: [���������Ҫ���ܽ���]</p>
 * <p>Copyright:   Copyright (c) 2009</p>
 * <p>Company:     </p>
 * <p>Department:  </p>
 * @author:١���
 * @email:tge0503020211@163.com
 * @version        1.0
 */
public class FomaterParser
{
    private String phonePrefix="139";//�ֻ������
    
    private Pattern p = Pattern.compile(phonePrefix+"([0-9]{8})");//������ʽ
    
    private static FomaterParser instance=new FomaterParser();//ʵ��
    /**
     * 
     *  Created on 2011-2-12 
     * <p>Description: [����������������]</p>
     * @author:١��� tge0503020211@163.com
     * @update:[����YYYY-MM-DD] [����������][�޸�˵��]
     * @return.
     */
    public static FomaterParser getInstance()
    {
        return instance;
    }
    /**
     * 
     * <p>Description:[��������������]</p>
     * @coustructor ����.
     */
    private FomaterParser()
    {
        
    }
    /**
     * 
     *  Created on 2011-2-12 
     * <p>Description: [У��ccDB�ļ��ļ������еĺϷ��ԣ�����Ϸ�����]</p>
     * @author:١��� tge0503020211@163.com
     * @update:[����YYYY-MM-DD] [����������][�޸�˵��]
     * @param line
     * @return
     * @throws FormatException.
     */
    public int parseLine(String line) throws FormatException
    {
        Matcher m = p.matcher(line.trim());
        if (m.matches())
        {
            return Integer.parseInt(m.group(1));
        }
        else
        {
            throw new FormatException("���ݸ�ʽ���Ϸ���");
        }
    }
    /**
     * 
     *  Created on 2011-2-12 
     * <p>Description: [����������������]</p>
     * @author:١��� tge0503020211@163.com
     * @update:[����YYYY-MM-DD] [����������][�޸�˵��]
     * @param data
     * @return.
     */
    public String revertData(int data)
    {
        return phonePrefix+StringUtils.leftPad(String.valueOf(data), 8, '0');
    }
}
