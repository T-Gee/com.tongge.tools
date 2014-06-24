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
 * ��������������
 * 
 * @author ��˧ shuai-wang@neusoft.com
 * @update ١��� tge0503020211@163.com
 * @version 0.2
 */
public class MyPatch {

    private static void initApplicationContext() throws IOException {
        Properties prop = new Properties();

        try {
            prop.load(new FileInputStream(ApplicationContext.CONFIG_PROPERTIES));
        } catch (FileNotFoundException e) {
            System.err.println("δ�ҵ�ϵͳ�����ļ�" + ApplicationContext.CONFIG_PROPERTIES + "�������޷�����");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("�����ļ�" + ApplicationContext.CONFIG_PROPERTIES + "��ȡʧ��");
            e.printStackTrace();
        }
        // ����ConfigKeyEnmu�����õ� config.properties Key-Value
        ConfigKeyEnmu[] keyEnmus = ConfigKeyEnmu.values();
        for (int i = 0; i < keyEnmus.length; i++) {
            ConfigKeyEnmu keyEnmu = keyEnmus[i];
            ApplicationContext.getContext().put(keyEnmu, (String) prop.get(keyEnmu.toString()));
        }

        // ��ʼ��������Ŀ������Ϣ
        WorkspaceModel workspaceModel = new WorkspaceModel((String) ApplicationContext.getContext().get(
                ConfigKeyEnmu.WORK_SPACE));
        ApplicationContext.getContext().put(ApplicationContext.WORKSPACE_PROJECTS, workspaceModel.getProjects());

    }

    @Test
    public void testName() throws Exception {
        initApplicationContext();
    }

    /**
     * ���������
     * 
     * @param args
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws Exception {

        /* init ApplicationContext */
        initApplicationContext();
        // UI
        // MypathFace mypathFace1=new MypathFace("����������");
        // mypathFace1.setVisible(true);

        // 1. ���ú�׺����
        // 3�� createPatch ########add by tongge##########for Clear Case
        // if (new
        // Boolean(PropertiesUtil.getProperty(ApplicationContext.Need_PATCH)).booleanValue())
        // {
        // PatchCreater.createPatch();
        // }

        // 4. ��patch.txt
        IReader reader = ReaderHandler.getInstance().getReader(
                (String) ApplicationContext.getContext().get(ConfigKeyEnmu.PATCH_FILE_KIND));
        // patch ÿ�����̵����򷽷�
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
        // ������Ŀ¼
        File root = getRealFolder((String) ApplicationContext.getContext().get(ConfigKeyEnmu.PACKAGE_DIR));
        // �����е������ļ�д���б���
        writepath(patch, root);
        // 5. �ļ�����
        PatchAssort assort = new PatchAssort(patch);

        // 6. ������������
        File pack_template = new File("������ģ��");
        String dest_dir_name = root.getPath();
        File dest_dir_file = new File(dest_dir_name);
        // ���������ж��������Ϣʱ�����ɶ����
        Iterator<String> projects = patch.getProjectNames().iterator();
        while (projects.hasNext()) {

            String projectName = (String) projects.next();
            if (patch.getProjectNames().size() >= 2) {
                dest_dir_file = new File(dest_dir_name + File.separator + projectName);
            }
            FileUtils.copyDirectory(pack_template, dest_dir_file);
            // 7. ���ƹ����ļ���д��˵��.txt��
            ProjectModel projectModel = ((Map<String, ProjectModel>) ApplicationContext.getContext().get(
                    ApplicationContext.WORKSPACE_PROJECTS)).get(projectName);
            writeReadmeFile(dest_dir_file, projectModel, "JAVAԴ�ļ�", assort.getJavaFiles(projectName));
            writeReadmeFile(dest_dir_file, projectModel, "���ļ�", assort.getClassFiles(projectName));
            writeReadmeFile(dest_dir_file, projectModel, "JSP�ļ�", assort.getJspFiles(projectName));
            writeReadmeFile(dest_dir_file, projectModel, "�����ļ�", assort.getConfigFiles(projectName));
            // 8.ɾ������Ŀ¼�����磺����������в�����JAVA�ļ�����ô�Ͳ����������б�����JAVA�ļ����͡����ļ�������Ŀ¼��
            removeUnUsedFolder(dest_dir_file);

        }
        // ��patch�ļ�copy���������ļ���rootĿ¼��
        FileUtils.copyFile(patch, new File(root + File.separator + patch.getName()));
        // 9. ����Դ����������λ��������Ŀ¼
        Runtime.getRuntime().exec("cmd /C explorer.exe " + root.getPath());
    }

    private static File getRealFolder(String package_dir) throws IOException {
        String dateStr = DateUtils.getDateStr("yyyyMMddHHmmss");
        InetAddress addr = InetAddress.getLocalHost();   
        String hostName=addr.getHostName().toString(); //��ȡ�������������
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
     * ���ƹ����ļ���д��˵��.txt��
     * 
     * @param workspaceProject
     *            ��Ӧ�Ĺ�������
     * @param fileName
     * @param patch
     * @param subtypeFileList
     *            �����ļ��б�
     * @param dest_dir_name
     * @param subfolder
     *            �����������һ����ʱ���ò�����Ϊ�գ�һ��Ϊ��������
     * @throws IOException
     */
    private static void writeReadmeFile(File dest_dir_file, ProjectModel workspaceProject, String subfolder,
            Set<File> subtypeFileList) throws IOException {
        FileWriter writer = null;
        File readme = new File(dest_dir_file.getPath() + File.separator + subfolder + File.separator + "˵��.txt");
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
     * Description: [�Ƴ�ĳЩ�ļ����У�]
     * </p>
     * 
     * @author:١��� tge0503020211@163.com
     * @update:[����YYYY-MM-DD] [����������][�޸�˵��]
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
