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
//			if (virtualFileName.startsWith("RCS file: ")) { // 如果本行为有用文件信息
//				virtualFileName = virtualFileName.substring("RCS file: ".length());// 掐头
//				if (virtualFileName.endsWith(",v")) // 有尾去尾
//					virtualFileName = virtualFileName.substring(0, virtualFileName.length() - 2);
//				if (isSuffixRegistered(virtualFileName)) {
//					File trueFile = locateFile(virtualFileName);
//					fileVector.add(trueFile);
//					// 如果是JAVA源文件，则一并找到其类文件放到fileVector里
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
