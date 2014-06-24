package com.tongge.revision_control.patcher.reader;

import com.tongge.commons.io.exception.NoPropertyFound;
import com.tongge.commons.io.exception.PropertiesException;
import com.tongge.revision_control.patcher.reader.impl.CVSReader;
import com.tongge.revision_control.patcher.reader.impl.NoneReader;
import com.tongge.revision_control.patcher.reader.impl.SVNReader;
import com.tongge.revision_control.patcher.utils.PatchEnum;

public class ReaderHandler {

    private ReaderHandler() {
    }

    private static ReaderHandler factory = new ReaderHandler();

    public static ReaderHandler getInstance() {
        return factory;
    }

    public IReader getReader(String key) throws NoPropertyFound, PropertiesException {
        if (PatchEnum.CVS == PatchEnum.valueOf(key)) {
            return new CVSReader();
        } else if (PatchEnum.SVN == PatchEnum.valueOf(key)) {
            return new SVNReader();
        } else if (PatchEnum.CLEARCASE == PatchEnum.valueOf(key) || PatchEnum.NONE == PatchEnum.valueOf(key)) {
            return new NoneReader();
        } else {
            return null;
        }
    }

}
