package tdd_1_2;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileRenamer {

    public static void renameAllInCurrentDirectory(String what, String to) throws IOException {
        String directoryPath =  String.valueOf(FileSystems.getDefault().getPath(".").toAbsolutePath());
        File directory = new File(directoryPath);
        List<String> filenames = new ArrayList<>();

        for (File file : directory.listFiles()) {
            if (file.isFile() && file.getName().contains(what)) {
                String newFileName = file.getName().replaceAll(what, to);
                File renamedFile = new File(directoryPath, newFileName);
                file.renameTo(renamedFile);
            }
        }
    }
}