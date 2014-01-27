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
	 * <p>	��һ�У������� CC�ļ�����
			�ڶ��У��ļ�·��
			�����У�δ֪
			�����У��ļ����ļ��и�����16���ƣ�
			��5-n�У��ļ����У���ϸ����
			
			��5-n����ϸ������
				ð��ǰ���ļ����У����Ƴ���
				ð�ź󣺵�1�Σ��á�|���֣��ļ����У�����
				��2�Σ��ļ����У����ͣ�1 �ļ���2 �ļ���
				��3�Σ�ʱ���
				��4�Σ��ļ���С
				��5�Σ�
				��6�Σ�
				��7�Σ��Ƿ����ļ�
	</p>
	 * @author:١��� tge0503020211@163.com
	 * @update:[����YYYY-MM-DD] [����������][�޸�˵��]
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
            int lineNum = 0;//�м�����
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
            				throw new FormatException("���ݸ�ʽ���Ϸ���");
            			}
            			
            		} 
            	}
                lineNum++;
            }
            ccdbFile.setFilePreference((FilePreference[]) preferenceList.toArray(new FilePreference[preferenceList.size()]));
        }catch (IOException e){
            throw AppException.pack("��ȡ�ļ��쳣��", e);
        }
        finally
        {
            LineIterator.closeQuietly(lit);
        }
		return ccdbFile;
	}
	/**
	 *  Created on 2011-2-12 
	 * <p>Description: [����������������]</p>
	 * @author:١��� tge0503020211@163.com
	 * @update:[����YYYY-MM-DD] [����������][�޸�˵��]
	 * @param args.
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
