package com.tongge.workspace.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jdom.Element;
import org.junit.Test;

import com.tongge.revision_control.patcher.reader.ReaderUtils;
import com.tongge.workspace.model.ProjectModel;

public class ProjectUtils {
    
    
    @Test
    public void testName() throws Exception {
        String a = "src/main/com/ultrapower/mams/webgis/manager/YHWorkOrderImpl.java";
        if (a.matches("^src[\\\\\\/]main[\\\\\\/].*$")) {
            System.out.println();
        }
    }
    
    
    public static List<File> getClassFile(ProjectModel projectModel, String line) throws FileNotFoundException,
            UnsupportedEncodingException {
        if(line.startsWith(File.separator)){
            line = line.substring(1);
        }
        if (line.endsWith("xml")) {
            System.out.println(line);
        }else if (line.endsWith("java")){
            System.out.println(line);
        }
        List<File> result = new ArrayList<File>();
        Iterator<Element> classesto = projectModel.getClassesto().iterator();
        while (classesto.hasNext()) {
            
            Element element = classesto.next();
            String ePath = element.getAttributeValue("path");
            String output = element.getAttributeValue("output");
            String eExcluding = element.getAttributeValue("excluding");
            boolean flag = false;
            if (StringUtils.isNotBlank(eExcluding)) {
                String[] split = eExcluding.split("\\|");
                for (int i = 0; i < split.length; i++) {
                    if (line.matches("^"+ePath + "[\\\\\\/]" + split[i].replaceAll("[\\\\\\/]", "") + "[\\\\\\/].*$")) {
                        flag = true;
                        break;
                    }
                }
            }
            if (flag) {
                continue;
            }
            if (line.replaceAll("[\\\\\\/]", "").startsWith(ePath.replaceAll("[\\\\\\/]", ""))) {
                if (line.endsWith("xml")) {
                    System.out.println(line);
                }else if (line.endsWith("java")){
                    System.out.println(line);
                }
                String clazzPath = output + line.substring(ePath.length());
                if (clazzPath.endsWith(".java")) {
                    final String filenameWithoutPostfix = clazzPath.substring(0, clazzPath.lastIndexOf(".java"));
                    clazzPath = filenameWithoutPostfix + ".class";
                    
                    final File clazz = ReaderUtils.locateFile(projectModel, clazzPath);
                    result.add(clazz);
                    //查找内部类
                    File[] listFiles = clazz.getParentFile().listFiles(new FileFilter() {

                        public boolean accept(File file) {
                            if (file.getPath().endsWith(".class")) {
                                return file.getName().startsWith(clazz.getName().split("\\.")[0] + "$");
                            }
                            return false;
                        }
                    });
                    result.addAll( Arrays.asList(listFiles));
                } else {
                    final File clazz = ReaderUtils.locateFile(projectModel, clazzPath);
                    result.add(clazz);
                }
                break;
            }
        }
        return result;
    }
}
