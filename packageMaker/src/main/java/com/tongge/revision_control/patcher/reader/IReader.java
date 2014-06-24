package com.tongge.revision_control.patcher.reader;

import java.io.File;
import java.util.Comparator;

import com.tongge.revision_control.patcher.model.Patch;

public interface IReader {

    Patch read(String path) throws Exception;
    
    Patch read(String path,String include,String excluding) throws Exception;
    
    void setComparator(Comparator<File> c);
    
    void setFileInclude(String fileInclude);
    
    void setFileExcluding(String fileExcluding);

}
