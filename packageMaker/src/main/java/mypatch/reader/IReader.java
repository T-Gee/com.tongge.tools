package mypatch.reader;

import java.io.LineNumberReader;

import com.tongge.patcher.model.Patch;

public interface IReader {
	
	Patch read(LineNumberReader lReader) throws Exception;

}
