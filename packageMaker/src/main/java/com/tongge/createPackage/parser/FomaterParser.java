package com.tongge.createPackage.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.tongge.createPackage.exception.FormatException;


/**
 * 
 * Created on 2011-2-12
 * <p>Title:       tge0503020211@163.com/p>
 * <p>Description: [描述该类概要功能介绍]</p>
 * <p>Copyright:   Copyright (c) 2009</p>
 * <p>Company:     </p>
 * <p>Department:  </p>
 * @author:佟广恩
 * @email:tge0503020211@163.com
 * @version        1.0
 */
public class FomaterParser
{
    private String phonePrefix="139";//手机号码段
    
    private Pattern p = Pattern.compile(phonePrefix+"([0-9]{8})");//正则表达式
    
    private static FomaterParser instance=new FomaterParser();//实例
    /**
     * 
     *  Created on 2011-2-12 
     * <p>Description: [方法功能中文描述]</p>
     * @author:佟广恩 tge0503020211@163.com
     * @update:[日期YYYY-MM-DD] [更改人姓名][修改说明]
     * @return.
     */
    public static FomaterParser getInstance()
    {
        return instance;
    }
    /**
     * 
     * <p>Description:[构造器方法描述]</p>
     * @coustructor 方法.
     */
    private FomaterParser()
    {
        
    }
    /**
     * 
     *  Created on 2011-2-12 
     * <p>Description: [校验ccDB文件文件属性列的合法性，如果合法则拆分]</p>
     * @author:佟广恩 tge0503020211@163.com
     * @update:[日期YYYY-MM-DD] [更改人姓名][修改说明]
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
            throw new FormatException("数据格式不合法。");
        }
    }
    /**
     * 
     *  Created on 2011-2-12 
     * <p>Description: [方法功能中文描述]</p>
     * @author:佟广恩 tge0503020211@163.com
     * @update:[日期YYYY-MM-DD] [更改人姓名][修改说明]
     * @param data
     * @return.
     */
    public String revertData(int data)
    {
        return phonePrefix+StringUtils.leftPad(String.valueOf(data), 8, '0');
    }
}
