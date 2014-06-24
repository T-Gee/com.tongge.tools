package com.tongge.revision_control.patcher.model;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

public class PatchAssort extends Patch  {

	public PatchAssort(Patch patch) {
	    super(patch.getPath());
		this.patch = patch;
	}

	private Map<String, Map<String, Set<File>>> assort;

	private Patch patch;

	private void assort() {
		assort = new HashMap<String, Map<String, Set<File>>>();

		Iterator<String> names = patch.getProjectNames().iterator();
		while (names.hasNext()) {
			String name = (String) names.next();
			Map<String, Set<File>> map = new HashMap<String, Set<File>>();
			Set<File> java = new HashSet<File>();
			Set<File> clazz = new HashSet<File>();
			Set<File> jsp = new HashSet<File>();
			Set<File> config = new HashSet<File>();

			Set<File> unSorted = new HashSet<File>();

			map.put("JAVA", java);
			map.put("CLASS", clazz);
			map.put("JSP", jsp);
			map.put("CONFIG", config);

			assort.put(name, map);
			Iterator<File> elems = patch.getProject(name).iterator();
			while (elems.hasNext()) {
				File elem = (File) elems.next();
				if (elem.getName().endsWith(".java")) {
					java.add(elem);
				} else if (elem.getName().endsWith(".class")) {
					clazz.add(elem);
				} else if (elem.getName().endsWith(".jsp") || elem.getName().endsWith(".jpg")
						|| elem.getName().endsWith(".gif") || elem.getName().endsWith(".css")
						|| elem.getName().endsWith(".inc") || elem.getName().endsWith(".js")) {
					jsp.add(elem);
				} else if (elem.getName().endsWith(".xml") || elem.getName().endsWith(".properties")
						|| elem.getName().endsWith(".xls") || elem.getName().endsWith(".exe")
						|| elem.getName().endsWith(".wsdd") || elem.getName().endsWith(".tld")) {
					config.add(elem);
				} else {
					unSorted.add(elem);
				}
			}

			if (!unSorted.isEmpty()) {
				Iterator<File> it = unSorted.iterator();
				System.err.println("Following files unsorted: ");
				// 在后台列出未归类文件
				for (int i = 1; it.hasNext(); i++) {
					File elem = (File) it.next();
					System.err.println(i + ". " + elem.getAbsolutePath());
				}
				System.err.println("存在未分类文件");
				System.exit(1);
			}

		}

	}

	public Set<File> getConfigFiles(String project) {
		if (assort == null) {
			assort();
		}
		if (StringUtils.isBlank(project)) {
			return assort.values().iterator().next().get("CONFIG");
		}
		project = project.substring(project.lastIndexOf(File.separator) + File.separator.length());
		return assort.get(project).get("CONFIG");
	}

	public Set<File> getJavaFiles(String project) {
		if (assort == null) {
			assort();
		}
		if (StringUtils.isBlank(project)) {
			return assort.values().iterator().next().get("JAVA");
		}
		project = project.substring(project.lastIndexOf(File.separator) + File.separator.length());
		project = project.substring(project.lastIndexOf(File.separator) + File.separator.length());
		return assort.get(project).get("JAVA");
	}

	public Set<File> getClassFiles(String project) {
		if (assort == null) {
			assort();
		}
		if (StringUtils.isBlank(project)) {
			return assort.values().iterator().next().get("CLASS");
		}
		project = project.substring(project.lastIndexOf(File.separator) + File.separator.length());
		return assort.get(project).get("CLASS");
	}

	public Set<File> getJspFiles(String project) {
		if (assort == null) {
			assort();
		}
		if (StringUtils.isBlank(project)) {
			return assort.values().iterator().next().get("JSP");
		}
		project = project.substring(project.lastIndexOf(File.separator) + File.separator.length());
		return assort.get(project).get("JSP");
	}

}
