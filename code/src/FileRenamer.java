import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class FileRenamer {

    public static String DIR_PATH = Paths.get("").toAbsolutePath().toString() + "\\";
    public String workingDirectoryPath;

    // Default initialization
    FileRenamer() {
        workingDirectoryPath = DIR_PATH;
    }

    // Functionality for tests
    FileRenamer(String workingDirectoryPath){
        this.workingDirectoryPath = workingDirectoryPath;
    }

    public int renameAllInCurrentDirectory(String what, String to) throws IOException {
        if (what.equals("")) throw new IOException("A required parameter was an empty string");
        File directory = new File(workingDirectoryPath);
        int filesRenamed = 0;

        for (File file : directory.listFiles()) {
            if (file.isFile() && file.getName().contains(what)) {
                String newFileName = file.getName().replaceAll(what, to);
                File renamedFile = new File(workingDirectoryPath, newFileName);
                file.renameTo(renamedFile);
                filesRenamed++;
            }
        }
        return filesRenamed;
    }

    public int renameAllInCurrentDirectoryWithExtension(String what, String to, String ext) throws IOException {
        if (what.equals("") || ext.equals("")) throw new IOException("A required parameter was an empty string");
        File directory = new File(workingDirectoryPath);
        int filesRenamed = 0;

        for (File file : directory.listFiles()) {
            String fileName = file.getName();
            if (file.isFile() && fileName.contains(what) && fileName.endsWith(ext)) {
                // cut off the extension so that we don't replace anything in it
                fileName = fileName.substring(0, fileName.length() - ext.length());

                // replace all and create new file name with extension
                String newFileName = fileName.replaceAll(what, to) + ext;
                File renamedFile = new File(workingDirectoryPath, newFileName);
                file.renameTo(renamedFile);
                filesRenamed++;
            }
        }
        return filesRenamed;
    }

    public int renameAllInCurrentAndSubDirectories(String what, String to) throws IOException {
        if (what.equals("")) throw new IOException("A required parameter was an empty string");
        return renameAllInCurrentAndSubDirectories_helper(what, to, "");
    }

    private int renameAllInCurrentAndSubDirectories_helper
            (String what, String to, String subdirectoryPath) throws IOException {
        String currentPath = workingDirectoryPath + subdirectoryPath;
        File directory = new File(currentPath);
        int filesRenamed = 0;

        for (File file : directory.listFiles()) {
            if (file.isFile() && file.getName().contains(what)) {
                String newFileName = file.getName().replaceAll(what, to);
                File renamedFile = new File(currentPath, newFileName);
                file.renameTo(renamedFile);
                filesRenamed++;
            } else if (file.isDirectory()) {
                filesRenamed += renameAllInCurrentAndSubDirectories_helper(what, to, subdirectoryPath + file.getName() + "\\");
            }
        }
        return filesRenamed;
    }

    public int renameAllInCurrentAndSubDirectoriesWithExtension
            (String what, String to, String ext) throws IOException {
        if (what.equals("") || ext.equals("")) throw new IOException("A required parameter was an empty string");
        return renameAllInCurrentAndSubDirectoriesWithExtension_helper(what, to, ext, "");
    }

    private int renameAllInCurrentAndSubDirectoriesWithExtension_helper
            (String what, String to, String ext, String subdirectoryPath) throws IOException {
        String currentPath = workingDirectoryPath + subdirectoryPath;
        File directory = new File(currentPath);
        int filesRenamed = 0;

        for (File file : directory.listFiles()) {
            String fileName = file.getName();
            if (file.isFile() && fileName.contains(what) && fileName.endsWith(ext)) {
                fileName = fileName.substring(0, fileName.length() - ext.length());

                String newFileName = fileName.replaceAll(what, to) + ext;
                File renamedFile = new File(currentPath, newFileName);
                file.renameTo(renamedFile);
                filesRenamed++;
            } else if (file.isDirectory()) {
                filesRenamed += renameAllInCurrentAndSubDirectoriesWithExtension_helper(what, to, ext, subdirectoryPath + file.getName() + "\\");
            }
        }
        return filesRenamed;
    }
}