package com.tongge.commons.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.tongge.application.AppException;




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
public class FileDealer 
{
	/**
	 * 
	 * @param fileName
	 * @return
	 * @throws AppException 
	 */
	public boolean writePath(String fileUrl,List checkOutFile) throws AppException{
        if (fileUrl == null || fileUrl.length() == 0)
        {
            throw new IllegalArgumentException("Ŀ���ļ�·��Ϊ�գ���ȷ��·����");
        }
        File file = new File(fileUrl);
        System.out.println("Ŀ���ļ�·����" + file.getAbsolutePath());
        BufferedWriter bw=null;
        try
        {
            bw=new BufferedWriter(new  FileWriter(file));
            Iterator it = checkOutFile.iterator();
            while(it.hasNext()){
            	String msg = (String) it.next();
            	bw.write(msg.toCharArray());
            	bw.newLine();
            }
            
            bw.flush();
        }
        catch (Exception e)
        {
            throw AppException.pack("����ļ�δ�ҵ���", e);
        }
        finally
        {
            IOUtils.closeQuietly(bw);
        }
        return true;
	}
}
