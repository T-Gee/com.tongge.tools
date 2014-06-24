package com.tongge.revision_control.patcher.model;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Patch extends File {
    
    
    public Patch(String pathname) {
        super(pathname);
    }
    /**
     * @Description:[×Ö¶Î¹¦ÄÜÃèÊö]
     */
    private static final long serialVersionUID = -2440000551519542324L;

	private Map<String,PatchFileProjectModel> map = new HashMap<String,PatchFileProjectModel>();
	
	public void addProject(String name){
	    PatchFileProjectModel model = new PatchFileProjectModel(name);
		map.put(name, model);
	}
	public void removeProject(String name){
		map.remove(name);
	}
	public Set<String> getProjectNames(){
		return map.keySet();
	}
	public PatchFileProjectModel getProject(String name){
		return map.get(name);
	}
	
	public void addFile(String name,File file){
	    PatchFileProjectModel model = map.get(name);
	    model.add(file);
	}
    public Collection<PatchFileProjectModel> getProjects() {
        return map.values();
    }
}
