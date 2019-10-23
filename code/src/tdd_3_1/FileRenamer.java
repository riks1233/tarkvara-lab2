package tdd_3_1;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.List;

public class FileRenamer {

    private static String DIR_PATH = String.valueOf(FileSystems.getDefault().getPath(".").toAbsolutePath());

    public static void renameAllInCurrentDirectory(String what, String to) throws IOException {
        File directory = new File(DIR_PATH);
        List<String> filenames = new ArrayList<>();

        for (File file : directory.listFiles()) {
            if (file.isFile() && file.getName().contains(what)) {
                String newFileName = file.getName().replaceAll(what, to);
                File renamedFile = new File(DIR_PATH, newFileName);
                file.renameTo(renamedFile);
            }
        }
    }

    public static void renameAllInCurrentDirectoryWithExtension(String what, String to, String ext) {
        File directory = new File(DIR_PATH);
        List<String> filenames = new ArrayList<>();

        for (File file : directory.listFiles()) {
            String fileName = file.getName();
            if (file.isFile() && fileName.contains(what) && fileName.endsWith(ext)) {
                // cut off the extension so that we don't replace anything in it
                fileName = fileName.substring(0, fileName.length() - ext.length());

                // replace all and create new file name with extension
                String newFileName = fileName.replaceAll(what, to) + ext;
                File renamedFile = new File(DIR_PATH, newFileName);
                file.renameTo(renamedFile);
            }
        }
    }

    public static void renameAllInCurrentAndSubDirectories(String what, String to) throws IOException {

    }
}