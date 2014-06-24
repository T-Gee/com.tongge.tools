package com.tongge.workspace.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import mypatch.ConfigKeyEnmu;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.StringUtils;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.tongge.application.ApplicationContext;

public class ProjectModel {

    private File file;

    private Set<Element> classesto = new HashSet<Element>();
    
    
    public File getParentFile(){
        return this.file.getParentFile();
    }

    /**
     * 
     * @param workspaceProjectPath 工作空间内 .metadata/.plugins/org.eclipse.core.resources/.projects 目录下的子文件夹
     * @throws IOException 
     */
    public ProjectModel(String workspaceProjectPath) throws IOException {
        this(new File(workspaceProjectPath));
    }

    /**
     * 
     * @param workspaceProject 工作空间内 .metadata/.plugins/org.eclipse.core.resources/.projects 目录下的子文件夹
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    public ProjectModel(File workspaceProject) throws IOException {
        String charset = (String) ApplicationContext.getContext().get(ConfigKeyEnmu.CHARSET);
        File location = new File(workspaceProject.getPath() + File.separator + ".location");
        if (!location.exists()) {
            String path = workspaceProject.getPath();
            this.file = new File(path.substring(0, path.indexOf(".metadata")) + workspaceProject.getName());
            if (!this.file.exists()) {
                // throw new FileNotFoundException("找不到工程信息：" +
                // workspaceProject.getName());
            }
        } else if (location.exists()) {
            FileInputStream is = FileUtils.openInputStream(location);
            LineIterator lit = IOUtils.lineIterator(is, charset);
            if (lit.hasNext()) {
                String locastr = (String) lit.next();
                locastr = locastr.substring(locastr.indexOf("URI//file:") + "URI//file:".length() + 1,
                        locastr.lastIndexOf(workspaceProject.getName()) + workspaceProject.getName().length());
                this.file = new File(locastr);
            }
        } else {
            // throw new FileNotFoundException("找不到工程信息：" +
            // workspaceProject.getName());
        }
        File _class = new File(this.file.getPath() + File.separator + ".classpath");
        if(!_class.exists()){
            return;
        }
        SAXBuilder builder = new SAXBuilder();
        try {
            Document doc = builder.build(_class);
            Element root = doc.getRootElement();
            Iterator<Element> children = root.getChildren("classpathentry").iterator();
            String defaultOutput = null;
            while (children.hasNext()) {
                Element entry = children.next();
                String kind = entry.getAttributeValue("kind");
                String path = entry.getAttributeValue("path");
                if ("output".equals(kind)) {
                    defaultOutput = path;
                    break;
                }
            }
            children = root.getChildren("classpathentry").iterator();
            while (children.hasNext()) {
                Element entry = children.next();
                String kind = entry.getAttributeValue("kind");
                String path = entry.getAttributeValue("path");
                String output = entry.getAttributeValue("output");
//                excluding
                if("src".equals(kind)){
                    if(StringUtils.isBlank(output)){
                        entry.setAttribute(new Attribute("output", defaultOutput));
                    }
                    classesto.add(entry);
                }
            }
        } catch (JDOMException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // 工程所在位置
    private String location;
    // 工程包含的文件
    private List<File> files;
    // 工程包含的源码包
    private List<String> sources;
    // 是否是web工程
    private boolean isWeb = true;
    // 工程名称

    private File project;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public List<String> getSources() {
        return sources;
    }

    public void setSources(List<String> sources) {
        this.sources = sources;
    }

    public boolean isWeb() {
        return isWeb;
    }

    public void setWeb(boolean isWeb) {
        this.isWeb = isWeb;
    }

    public String getName() {
        return this.file.getName();
    }

    public String getPath() {
        return this.file.getPath();
    }

    public Set<Element> getClassesto() {
        return classesto;
    }

}
