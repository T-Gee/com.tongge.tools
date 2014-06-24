package mypatch;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import com.tongge.application.ApplicationContext;
import com.tongge.commons.io.FileUtil;
import com.tongge.commons.lang3.StringUtils;
import com.tongge.commons.lang3.time.DateUtils;
import com.tongge.revision_control.patcher.model.Patch;
import com.tongge.revision_control.patcher.model.PatchAssort;
import com.tongge.revision_control.patcher.reader.IReader;
import com.tongge.revision_control.patcher.reader.ReaderHandler;
import com.tongge.workspace.model.ProjectModel;
import com.tongge.workspace.model.WorkspaceModel;

/**
 * 升级包制作程序
 * 
 * @author 王帅 shuai-wang@neusoft.com
 * @update 佟广恩 tge0503020211@163.com
 * @version 0.2
 */
public class MyPatch {

    private static void initApplicationContext() throws IOException {
        Properties prop = new Properties();

        try {
            prop.load(new FileInputStream(ApplicationContext.CONFIG_PROPERTIES));
        } catch (FileNotFoundException e) {
            System.err.println("未找到系统配置文件" + ApplicationContext.CONFIG_PROPERTIES + "，程序无法运行");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("配置文件" + ApplicationContext.CONFIG_PROPERTIES + "读取失败");
            e.printStackTrace();
        }
        // 加载ConfigKeyEnmu中配置的 config.properties Key-Value
        ConfigKeyEnmu[] keyEnmus = ConfigKeyEnmu.values();
        for (int i = 0; i < keyEnmus.length; i++) {
            ConfigKeyEnmu keyEnmu = keyEnmus[i];
            ApplicationContext.getContext().put(keyEnmu, (String) prop.get(keyEnmu.toString()));
        }

        // 初始化工程项目配置信息
        WorkspaceModel workspaceModel = new WorkspaceModel((String) ApplicationContext.getContext().get(
                ConfigKeyEnmu.WORK_SPACE));
        ApplicationContext.getContext().put(ApplicationContext.WORKSPACE_PROJECTS, workspaceModel.getProjects());

    }

    @Test
    public void testName() throws Exception {
        initApplicationContext();
    }

    /**
     * 主程序入口
     * 
     * @param args
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws Exception {

        /* init ApplicationContext */
        initApplicationContext();
        // UI
        // MypathFace mypathFace1=new MypathFace("整理升级包");
        // mypathFace1.setVisible(true);

        // 1. 配置后缀集合
        // 3‘ createPatch ########add by tongge##########for Clear Case
        // if (new
        // Boolean(PropertiesUtil.getProperty(ApplicationContext.Need_PATCH)).booleanValue())
        // {
        // PatchCreater.createPatch();
        // }

        // 4. 读patch.txt
        IReader reader = ReaderHandler.getInstance().getReader(
                (String) ApplicationContext.getContext().get(ConfigKeyEnmu.PATCH_FILE_KIND));
        // patch 每个工程的排序方法
        reader.setComparator(new Comparator<File>() {
            public int compare(File o1, File o2) {
                int c = o1.getName().split("\\.")[1].compareTo(o2.getName().split("\\.")[1]);
                if (c != 0) {
                    return c;
                } else {
                    return o1.getPath().compareTo(o2.getPath());
                }
            }
        });
        reader.setFileInclude((String) ApplicationContext.getContext().get(ConfigKeyEnmu.FILE_INCLUDE));
        reader.setFileExcluding((String) ApplicationContext.getContext().get(ConfigKeyEnmu.FILE_EXCLUDING));
        final Patch patch = reader.read((String) ApplicationContext.getContext().get(ConfigKeyEnmu.PATCH_FILENAME));
        // 升级包目录
        File root = getRealFolder((String) ApplicationContext.getContext().get(ConfigKeyEnmu.PACKAGE_DIR));
        // 将所有的升级文件写入列表中
        writepath(patch, root);
        // 5. 文件分类
        PatchAssort assort = new PatchAssort(patch);

        // 6. 创建空升级包
        File pack_template = new File("升级包模板");
        String dest_dir_name = root.getPath();
        File dest_dir_file = new File(dest_dir_name);
        // 升级包中有多个工程信息时，生成多个包
        Iterator<String> projects = patch.getProjectNames().iterator();
        while (projects.hasNext()) {

            String projectName = (String) projects.next();
            if (patch.getProjectNames().size() >= 2) {
                dest_dir_file = new File(dest_dir_name + File.separator + projectName);
            }
            FileUtils.copyDirectory(pack_template, dest_dir_file);
            // 7. 复制工程文件，写“说明.txt”
            ProjectModel projectModel = ((Map<String, ProjectModel>) ApplicationContext.getContext().get(
                    ApplicationContext.WORKSPACE_PROJECTS)).get(projectName);
            writeReadmeFile(dest_dir_file, projectModel, "JAVA源文件", assort.getJavaFiles(projectName));
            writeReadmeFile(dest_dir_file, projectModel, "类文件", assort.getClassFiles(projectName));
            writeReadmeFile(dest_dir_file, projectModel, "JSP文件", assort.getJspFiles(projectName));
            writeReadmeFile(dest_dir_file, projectModel, "配置文件", assort.getConfigFiles(projectName));
            // 8.删除多余目录。例如：如果升级包中不包含JAVA文件，那么就不在升级包中保留“JAVA文件”和“类文件”两个目录。
            removeUnUsedFolder(dest_dir_file);

        }
        // 将patch文件copy到升级包文件夹root目录下
        FileUtils.copyFile(patch, new File(root + File.separator + patch.getName()));
        // 9. 打开资源管理器，定位至升级包目录
        Runtime.getRuntime().exec("cmd /C explorer.exe " + root.getPath());
    }

    private static File getRealFolder(String package_dir) throws IOException {
        String dateStr = DateUtils.getDateStr("yyyyMMddHHmmss");
        InetAddress addr = InetAddress.getLocalHost();   
        String hostName=addr.getHostName().toString(); //获取本机计算机名称
        String username = StringUtils.nvl(ApplicationContext.getContext().get(ConfigKeyEnmu.USERNAME),hostName);
        String description = StringUtils.nvl(ApplicationContext.getContext().get(ConfigKeyEnmu.DESCRIPTION),
                "Description");
        File file = new File(package_dir.replaceAll("(?i)\\[sysdate\\]", dateStr)
                .replaceAll("(?i)\\[username\\]", username).replaceAll("(?i)\\[description\\]", description));
        if (!file.exists()) {
            file.mkdir();
        }
        return file;
    }

    private static File writepath(Patch patch, File realFolder) throws IOException {
        String charset = (String) ApplicationContext.getContext().get(ConfigKeyEnmu.CHARSET);
        File filelist = new File(realFolder.getPath() + File.separator + "filelist.txt");
        if (!filelist.exists()) {
            filelist.createNewFile();
        }
        FileOutputStream outputStream = FileUtils.openOutputStream(filelist, true);
        Iterator<String> names = patch.getProjectNames().iterator();
        while (names.hasNext()) {
            String name = (String) names.next();
            IOUtils.writeLines(patch.getProject(name).getShortFilename(), "\r\n", outputStream, charset);
        }
        outputStream.close();
        return filelist;
    }

    /**
     * 复制工程文件，写“说明.txt”
     * 
     * @param workspaceProject
     *            对应的工程名称
     * @param fileName
     * @param patch
     * @param subtypeFileList
     *            分类文件列表
     * @param dest_dir_name
     * @param subfolder
     *            如果与多个工程一起打包时，该参数不为空，一般为工程名称
     * @throws IOException
     */
    private static void writeReadmeFile(File dest_dir_file, ProjectModel workspaceProject, String subfolder,
            Set<File> subtypeFileList) throws IOException {
        FileWriter writer = null;
        File readme = new File(dest_dir_file.getPath() + File.separator + subfolder + File.separator + "说明.txt");
        writer = new FileWriter(readme);
        Iterator<File> it = subtypeFileList.iterator();
        while (it.hasNext()) {
            File elem = (File) it.next();
            String sourcePath = elem.getAbsolutePath().substring(workspaceProject.getPath().length());
            FileUtils.copyFile(elem, new File(dest_dir_file.getPath() + File.separator + subfolder + File.separator
                    + sourcePath));
            String filePath = workspaceProject.getName() + File.separator + sourcePath;
            writer.write(filePath + "\r\n");
            writer.flush();
        }
        writer.close();
    }

    // private static void writeReadmeFile(ProjectModel workspaceProject, String
    // fileName, Set<File> subtypeFileList,
    // String dest_dir_name, String subfolder) throws IOException {
    // FileWriter writer = null;
    // File readme = new File(fileName);
    // writer = new FileWriter(readme);
    // FileWriter writer2 = new FileWriter(ApplicationContext.FILELIST, true);
    // Iterator<File> it = subtypeFileList.iterator();
    // while (it.hasNext()) {
    // File elem = (File) it.next();
    // String sourcePath =
    // elem.getAbsolutePath().substring(workspaceProject.getPath().length());
    // FileUtils
    // .copyFile(elem, new File(dest_dir_name + File.separator + subfolder +
    // File.separator + sourcePath));
    // String filePath = workspaceProject.getName() + File.separator +
    // sourcePath;
    // writer.write(filePath + "\r\n");
    // writer.flush();
    // writer2.write(filePath + "\r\n");
    // writer2.flush();
    // }
    // writer.close();
    // writer2.close();
    // }

    private static Set<String> removFolder = new HashSet<String>();
    static {
        removFolder.add(".svn");
        removFolder.add("CVS");
    }

    /**
     * 
     * Created on 2011-5-24
     * <p>
     * Description: [移除某些文件（夹）]
     * </p>
     * 
     * @author:佟广恩 tge0503020211@163.com
     * @update:[日期YYYY-MM-DD] [更改人姓名][修改说明]
     * @param dest_dir
     *            .
     */
    private static void removeUnUsedFolder(File dest_dir) {
        File[] files = dest_dir.listFiles();
        for (int i = 0, n = files.length; i < n; i++) {
            File file = files[i];
            if (removFolder.contains(file.getName())) {
                FileUtil.deleteDirectory(file.getPath());
            } else if (!file.isFile()) {
                removeUnUsedFolder(file);
            }
        }
    }
}
