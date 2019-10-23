package tdd.tdd_2_1;

import org.junit.Assert;
import org.junit.Test;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.Scanner;

public class FileRenamerTest {

    private static String DIR_PATH = String.valueOf(FileSystems.getDefault().getPath(".").toAbsolutePath());

    @Test
    public void testRenameAllInCurrentDirectory() {
        // Create a test file, rename it with function that is tested, delete everything that was produced
        File testFile = null;
        File renamedFile = null;
        try {
            // Create a test file
            // (suppose no file with such filename was created in current working directory by user)
            String testFileName = "testfile.txt";
            testFile = createTestFile(testFileName);

            // Record expected last line
            String expectedLastFileLine = "Test File Contents";
            putInFileAsLastLine(testFile, expectedLastFileLine);

            // Rename
            FileRenamer.renameAllInCurrentDirectory("testfile", "rambotest");

            // Check if new file exists
            String newTestFileName = "rambotest.txt";
            renamedFile = new File(DIR_PATH, newTestFileName);

            // Test file name to be desired
            if (!renamedFile.exists()) {
                throw new IOException("Renamed file does not exit");
            }
            // Record actual last line
            String actualLastFileLine = getLastFileLine(renamedFile);
            // Test actual last line to be the same as the recorded
            Assert.assertTrue(actualLastFileLine.equals(expectedLastFileLine));
            // Delete the files
            testFile.delete();
            renamedFile.delete();
        } catch (IOException e1) {
            try {
                testFile.delete();
                renamedFile.delete();
            } catch (Exception e2) {
                // No files to delete
            } finally {
                Assert.fail(e1.getMessage());
            }
        }
    }

    @Test
    public void testRenameAllInCurrentDirectoryWithExtension() throws IOException {
        File testFile1 = null;
        File testFile2 = null;
        File newTestFile1 = null;
        File oldTestFile2 = null;
        try {
            // Create 2 test files with same name but different extensions.
            // Rename equal file name parts with function that is tested, assert and delete everything that was produced
            String testFile1Name = "testfile.txt";
            String testFile2Name = "testfile.mp3";
            testFile1 = createTestFile(testFile1Name);
            testFile2 = createTestFile(testFile2Name);
            String expectedLastFile1Line = "Test File 1 Contents";
            putInFileAsLastLine(testFile1, expectedLastFile1Line);
            String expectedLastFile2Line = "Test File 2 Contents";
            putInFileAsLastLine(testFile2, expectedLastFile2Line);

            // Rename
            FileRenamer.renameAllInCurrentDirectoryWithExtension("testfile", "rambotest", ".txt");

            // Check if new file that had to be renamed exists
            // Check if old file that had not to be renamed exists
            String newTestFile1Name = "rambotest.txt";
            newTestFile1 = new File(DIR_PATH, newTestFile1Name);
            oldTestFile2 = new File(DIR_PATH, testFile2Name);

            // Test file names to be desired
            if (!newTestFile1.exists()) {
                throw new IOException("Renamed file does not exit");
            }
            if (!oldTestFile2.exists()) {
                throw new IOException("Old file does not exit");
            }

            // Record actual last line
            String actualLastFile1Line = getLastFileLine(newTestFile1);
            String actualLastFile2Line = getLastFileLine(oldTestFile2);
            // Test actual last line to be the same as the recorded
            Assert.assertTrue(actualLastFile1Line.equals(expectedLastFile1Line));
            Assert.assertTrue(actualLastFile2Line.equals(expectedLastFile2Line));
            // Delete the files
            testFile2.delete();
            newTestFile1.delete();


        } catch (IOException e1) {
            try {
                testFile1.delete();
                testFile2.delete();
                newTestFile1.delete();
                oldTestFile2.delete();
            } catch (Exception e2) {
                // No files to delete
            } finally {
                Assert.fail(e1.getMessage());
            }
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
        File file = new File(DIR_PATH, name);
        file.createNewFile();
        return file;
    }

    public void putInFileAsLastLine(File file, String line) throws IOException {
        FileWriter writer = new FileWriter(file);
        writer.write("\n" + line);
        writer.close();
    }
}
