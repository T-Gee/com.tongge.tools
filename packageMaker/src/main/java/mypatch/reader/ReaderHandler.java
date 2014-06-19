package mypatch.reader;

import mypatch.ApplicationContext;
import mypatch.reader.impl.CVSReader;
import mypatch.reader.impl.NoneReader;
import mypatch.reader.impl.SVNReader;

import com.tongge.createPackage.exception.NoPropertyFound;
import com.tongge.createPackage.exception.PropertiesException;

public class ReaderHandler {

	private ReaderHandler() {
	}

	private static ReaderHandler factory = new ReaderHandler();

	public static ReaderHandler getInstance() {
		return factory;
	}

	public IReader getReader(String key) throws NoPropertyFound,
			PropertiesException {
		if (ApplicationContext.PatchFile_Kind_CVS.equalsIgnoreCase(key)) {
			return new CVSReader();
		} else if (ApplicationContext.PatchFile_Kind_SVN.equalsIgnoreCase(key)) {
			return new SVNReader();
		} else if (ApplicationContext.PatchFile_Kind_CLEARCASE.equalsIgnoreCase(key)
				|| ApplicationContext.PatchFile_Kind_NONE.equalsIgnoreCase(key)) {
			return new NoneReader();
		} else {
			return null;
		}
	}

}
