package com.tongge.patcher.model;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Patch {
	
	private Map<String,Set<File>> map = new HashMap<String, Set<File>>();
	
	public void addProject(String name){
		map.put(name, new HashSet<File>());
	}
	public void removeProject(String name){
		map.remove(name);
	}
	public Set<String> getProjectNames(){
		return map.keySet();
	}
	public Set<File> getProject(String name){
		return map.get(name);
	}
	
	public void addFile(String name,File file){
		map.get(name).add(file);
	}
}
