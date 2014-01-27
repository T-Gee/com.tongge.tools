package com.tongge.createPackage.clear_case;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import com.tongge.createPackage.entity.CCdbFile;
import com.tongge.createPackage.entity.FilePreference;
import com.tongge.createPackage.exception.AppException;
import com.tongge.createPackage.exception.FormatException;

public class ClearCaseUtil {

	/**
	 * 
	 *  Created on 2011-2-12 
	 * <p>Description: [read CCdbFile]</p>
	 * <p>	第一行：估计是 CC文件类型
			第二行：文件路径
			第三行：未知
			第四行：文件及文件夹个数（16进制）
			第5-n行：文件（夹）详细描述
			
			第5-n行详细描述：
				冒号前：文件（夹）名称长度
				冒号后：第1段（用“|”分）文件（夹）名称
				第2段：文件（夹）类型：1 文件，2 文件夹
				第3段：时间戳
				第4段：文件大小
				第5段：
				第6段：
				第7段：是否检出文件
	</p>
	 * @author:佟广恩 tge0503020211@163.com
	 * @update:[日期YYYY-MM-DD] [更改人姓名][修改说明]
	 * @param dbFile
	 * @throws AppException 
	 * @return.
	 */
	public static CCdbFile readCCdbFile(File dbFile) throws AppException {
		CCdbFile ccdbFile = new CCdbFile();
		List preferenceList = new ArrayList();
        LineIterator lit = null;
        try
        {
            lit = FileUtils.lineIterator(dbFile);
            int lineNum = 0;//行计数器
            while (lit.hasNext())
            {
                String msg = lit.nextLine();
            	switch(lineNum){
            		case 0: ccdbFile.setType(msg);break;
            		case 1: ccdbFile.setPath(msg);break;
            		case 2: break;
            		case 3: break;
            		default:{
            			try{
	            			String []str = msg.split(":");
	            			String []per = str[1].split("\\|");
	            			FilePreference prefer = new FilePreference();
	            			prefer.setNameLength(Integer.valueOf(str[0],16).intValue());
	            			prefer.setName(per[0]);
	            			prefer.setType("1".equals(per[1])? "file" : "folder");
	            			prefer.setTimeStamp(new Date(Long.valueOf(per[2],16).longValue()));
	            			prefer.setFileSize(Long.valueOf(per[3],16).longValue());
	            			prefer.setUnknown1(per[4]);
	            			prefer.setUnknown2(per[5]);
	            			prefer.setCheckOut("1".equals(per[6])? true : false);
	            			prefer.setCanWrite(false);
	            			preferenceList.add(prefer);
            			}catch(Exception e){
            				e.printStackTrace();
            				throw new FormatException("数据格式不合法。");
            			}
            			
            		} 
            	}
                lineNum++;
            }
            ccdbFile.setFilePreference((FilePreference[]) preferenceList.toArray(new FilePreference[preferenceList.size()]));
        }catch (IOException e){
            throw AppException.pack("读取文件异常。", e);
        }
        finally
        {
            LineIterator.closeQuietly(lit);
        }
		return ccdbFile;
	}
	/**
	 *  Created on 2011-2-12 
	 * <p>Description: [方法功能中文描述]</p>
	 * @author:佟广恩 tge0503020211@163.com
	 * @update:[日期YYYY-MM-DD] [更改人姓名][修改说明]
	 * @param args.
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
