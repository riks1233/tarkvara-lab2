package tdd_1_2;

import org.junit.Assert;
import org.junit.Test;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.Scanner;

public class FileRenamerTest {

    @Test
    public void testRenameAllInCurrentDirectory() {
        // Create a test file, rename it with function that is tested, delete everything that was produced
        try {
            // Create a test file
            // (suppose no file with such filename was created in current working directory by user)
            String directoryPath = String.valueOf(FileSystems.getDefault().getPath(".").toAbsolutePath());
            String testFileName = "testfile.txt";
            File testFile = createTestFile(testFileName);

            // Record expected last line
            String expectedLastFileLine = "Test File Contents";
            Scanner sc = new Scanner(testFile);
            // If the file somehow already exists (for ex. from previous test)
            if (sc.hasNextLine()) {
                expectedLastFileLine = getLastFileLine(testFile);
            } else {
                FileWriter writer = new FileWriter(testFile);
                writer.write("\n" + expectedLastFileLine);
                writer.close();
            }
            sc.close();

            // Rename
            FileRenamer.renameAllInCurrentDirectory("testfile", "rambotest");

            // Check if new file exists
            String newTestFileName = "rambotest.txt";
            File renamedFile = new File(directoryPath, newTestFileName);
            // Test file name to be desired
            if (!renamedFile.exists()) {
                testFile.delete();
                throw new IOException("Renamed file does not exit");
            }
            // Record actual last line
            String actualLastFileLine = getLastFileLine(renamedFile);
            // Test actual last line to be the same as the recorded
            System.out.println(actualLastFileLine + " : " + expectedLastFileLine);
            Assert.assertTrue(actualLastFileLine.equals(expectedLastFileLine));
            testFile.delete();
            renamedFile.delete();
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        }
    }

    public String getLastFileLine(File file) throws IOException{
        Scanner sc = new Scanner(file);
        String lastFileLine = "";
        while (sc.hasNextLine()){
            lastFileLine = sc.nextLine();
        }
        sc.close();
        return lastFileLine;
    }

    public File createTestFile(String name) throws IOException {
        String directoryPath = String.valueOf(FileSystems.getDefault().getPath(".").toAbsolutePath());
        File file = new File(directoryPath, name);
        file.createNewFile();
        return file;
    }
}
