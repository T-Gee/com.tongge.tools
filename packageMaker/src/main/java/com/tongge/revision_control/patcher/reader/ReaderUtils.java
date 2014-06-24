package com.tongge.revision_control.patcher.reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.lang3.StringUtils;

import com.tongge.application.ApplicationContext;
import com.tongge.workspace.model.ProjectModel;

public class ReaderUtils {

    public static File locateFile(ProjectModel file, String virtualFileName) throws FileNotFoundException,
            UnsupportedEncodingException {
        String trueFileName = virtualFileName.replace('/', File.separatorChar);
        trueFileName = file.getPath() + File.separatorChar + trueFileName;

        File trueFile = new File(trueFileName);
        if (!trueFile.exists()) {
            System.err.println("Couldn't find file: " + trueFile.getAbsolutePath());
            throw new FileNotFoundException();
        }
        return trueFile;
    }

}
