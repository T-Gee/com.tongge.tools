package com.tongge.revision_control.patcher.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.tongge.workspace.model.ProjectModel;

public class PatchFileProjectModel extends ArrayList<File> implements List<File> {

    /**
     * @Description:[×Ö¶Î¹¦ÄÜÃèÊö]
     */
    private static final long serialVersionUID = -8932378337625685273L;

    private String name ;
    
    private ProjectModel projectModel;
    
    private List<String> shortFilename = new ArrayList<String>();
    private Set<String> fullFilename = new HashSet<String>();
    @Override
    public boolean add(File o) {
        shortFilename = null;
        if (!fullFilename.add(o.getPath())) {
            return false;
        }
        return super.add(o);
    }
    
    @Override
    public boolean addAll(Collection<? extends File> c) {
        shortFilename = null;
        boolean flag = true;
        if (c!=null&&!c.isEmpty()) {
            Iterator<? extends File> it = c.iterator();
            while (it.hasNext()) {
                File file = (File) it.next();
                flag = flag & this.add(file);
            }
        }
        
        return flag;
    }
    

    public List<String> getShortFilename(){
        if (shortFilename == null) {
            shortFilename = new ArrayList<String>();
            if(!this.isEmpty()){
                Iterator<File> it = this.iterator();
                while (it.hasNext()) {
                    File file = (File) it.next();
                    if (file.getPath().startsWith(projectModel.getPath())) {
                        shortFilename.add(file.getPath().substring(projectModel.getParentFile().getPath().length()));
                    }
                    
                }
            }
            
        }
        return shortFilename;
    }

    public PatchFileProjectModel(String name) {
        this.name = name;
    }

    public PatchFileProjectModel() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProjectModel getProjectModel() {
        return projectModel;
    }

    public void setProjectModel(ProjectModel projectModel) {
        this.projectModel = projectModel;
    }

    @Override
    public boolean equals(Object o) {
        PatchFileProjectModel model = (PatchFileProjectModel)o;
        return this.getProjectModel().getPath().equals(model.getProjectModel().getPath());
    }







    
}
