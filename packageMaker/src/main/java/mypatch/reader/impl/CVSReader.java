package mypatch.reader.impl;

import java.io.File;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Set;
import java.util.TreeSet;

import mypatch.reader.IReader;

import com.tongge.patcher.model.Patch;

public class CVSReader extends BaseReader implements IReader {

	public Patch read(LineNumberReader lReader) throws IOException {
		Patch patch = new Patch();
//		Set fileVector = new TreeSet();
//		while (lReader.ready()) {
//			String virtualFileName = lReader.readLine();
//			if (virtualFileName.startsWith("RCS file: ")) { // �������Ϊ�����ļ���Ϣ
//				virtualFileName = virtualFileName.substring("RCS file: ".length());// ��ͷ
//				if (virtualFileName.endsWith(",v")) // ��βȥβ
//					virtualFileName = virtualFileName.substring(0, virtualFileName.length() - 2);
//				if (isSuffixRegistered(virtualFileName)) {
//					File trueFile = locateFile(virtualFileName);
//					fileVector.add(trueFile);
//					// �����JAVAԴ�ļ�����һ���ҵ������ļ��ŵ�fileVector��
//					if (trueFile.getName().endsWith(".java")) {
//						File javaClassFile = locateJavaClassFile(trueFile.getAbsolutePath());
//						fileVector.add(javaClassFile);
//					}
//				}
//			}
//		}

		return patch;
	}

}
