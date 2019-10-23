package tdd_3_2;

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

    public void renameAllInCurrentDirectory(String what, String to) throws IOException {
        File directory = new File(workingDirectoryPath);

        for (File file : directory.listFiles()) {
            if (file.isFile() && file.getName().contains(what)) {
                String newFileName = file.getName().replaceAll(what, to);
                File renamedFile = new File(workingDirectoryPath, newFileName);
                file.renameTo(renamedFile);
            }
        }
    }

    public void renameAllInCurrentDirectoryWithExtension(String what, String to, String ext) {
        File directory = new File(workingDirectoryPath);

        for (File file : directory.listFiles()) {
            String fileName = file.getName();
            if (file.isFile() && fileName.contains(what) && fileName.endsWith(ext)) {
                // cut off the extension so that we don't replace anything in it
                fileName = fileName.substring(0, fileName.length() - ext.length());

                // replace all and create new file name with extension
                String newFileName = fileName.replaceAll(what, to) + ext;
                File renamedFile = new File(workingDirectoryPath, newFileName);
                file.renameTo(renamedFile);
            }
        }
    }

    public void renameAllInCurrentAndSubDirectories(String what, String to) throws IOException {
        renameAllInCurrentAndSubDirectories_helper(what, to, "");
    }

    private void renameAllInCurrentAndSubDirectories_helper(String what, String to, String subdirectoryPath) throws IOException {
        String currentPath = workingDirectoryPath + subdirectoryPath;
        File directory = new File(currentPath);

        for (File file : directory.listFiles()) {
            if (file.isFile() && file.getName().contains(what)) {
                String newFileName = file.getName().replaceAll(what, to);
                File renamedFile = new File(currentPath, newFileName);
                file.renameTo(renamedFile);
            } else if (file.isDirectory()) {
                renameAllInCurrentAndSubDirectories_helper(what, to, subdirectoryPath + file.getName() + "\\");
            }
        }
    }
}