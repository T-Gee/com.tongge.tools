package mypatch.reader.impl;

import java.io.File;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Set;
import java.util.TreeSet;

import mypatch.reader.IReader;

import com.tongge.patcher.model.Patch;

public class NoneReader extends BaseReader implements IReader {

	public Patch read(LineNumberReader lReader) throws IOException {
		Patch patch = new Patch();
//		Set<File> fileVector = new TreeSet<File>();
//		while (lReader.ready()) {
//			String virtualFileName = lReader.readLine();
//			if (virtualFileName == null || virtualFileName.trim().length() == 0
//					|| virtualFileName.startsWith("#")) {
//				continue;
//			}
//			if (isSuffixRegistered(virtualFileName = virtualFileName.trim())) {
//				File trueFile = locateFile(virtualFileName);
//				fileVector.add(trueFile);
//				// �����JAVAԴ�ļ�����һ���ҵ������ļ��ŵ�fileVector��
//				if (trueFile.getName().endsWith(".java")) {
//					File javaClassFile = locateJavaClassFile(trueFile
//							.getAbsolutePath());
//					fileVector.add(javaClassFile);
//				}
//			}
//		}
		return patch;
	}

}
