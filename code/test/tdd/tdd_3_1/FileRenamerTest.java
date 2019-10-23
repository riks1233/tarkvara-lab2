package tdd.tdd_3_1;

import org.junit.Assert;
import org.junit.Test;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.Scanner;

public class FileRenamerTest {
    // In tests we suppose no files with such filenames were created in current working directory by user

    private static String DIR_PATH = String.valueOf(FileSystems.getDefault().getPath(".").toAbsolutePath());

    @Test
    public void testRenameAllInCurrentDirectory() {
        // Create a test file, rename it with function that is tested, delete everything that was produced
        File testFile = null;
        File renamedFile = null;
        try {
            // Create a test file

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
            System.out.println(DIR_PATH);
        } catch (Exception e1) {
            testFile.delete();
            renamedFile.delete();
            Assert.fail(e1.getMessage());
        }
    }

    @Test
    public void testRenameAllInCurrentDirectoryWithExtension() throws IOException {
        // Create 2 test files with same name but different extensions.
        // Rename equal file name parts with function that is tested, assert and delete everything that was produced
        File testFile1 = null;
        File testFile2 = null;
        File newTestFile1 = null;
        File oldTestFile2 = null;
        try {

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


        } catch (Exception e1) {
            testFile1.delete();
            testFile2.delete();
            newTestFile1.delete();
            oldTestFile2.delete();
            Assert.fail(e1.getMessage());
        }
    }

    @Test
    public void testRenameAllInCurrentAndSubDirectories(){
        // Create 1 test file in current directory and 1 test file in test subdirectory
        // Rename equal file name parts with function that is tested, assert and delete everything that was produced
        File testFile1 = null;
        File testFile2 = null;
        File newTestFile1 = null;
        File newTestFile2 = null;
        File testDirectory = null;
        try {
            // File 1
            String testFile1Name = "testfile1.txt";
            testFile1 = createTestFile(testFile1Name);
            String expectedLastFile1Line = "Test File 1 Contents";
            putInFileAsLastLine(testFile1, expectedLastFile1Line);

            // Directory + File 2
            String testDirectoryName = "testdirectory";
            testDirectory = new File(DIR_PATH + testDirectoryName + "\\");
            testDirectory.mkdir();
            String testFile2Name = "testfile2.txt";
            testFile2 = createTestFile(testDirectoryName + "\\", testFile2Name);
            String expectedLastFile2Line = "Test File 2 Contents";
            putInFileAsLastLine(testFile2, expectedLastFile2Line);

            // Rename
            FileRenamer.renameAllInCurrentAndSubDirectories("testfile", "rambotest");

            // Check if files exist
            String newTestFile1Name = "rambotest1.txt";
            String newTestFile2Name = "rambotest2.txt";
            newTestFile1 = new File(DIR_PATH, newTestFile1Name);
            newTestFile2 = new File(DIR_PATH + testDirectoryName + "\\", newTestFile2Name);

            // Test file names to be desired
            if (!newTestFile1.exists()) {
                throw new IOException("Renamed file 1 does not exit");
            }
            if (!newTestFile2.exists()) {
                throw new IOException("Renamed file 2 does not exit");
            }

            // Record actual last line
            String actualLastFile1Line = getLastFileLine(newTestFile1);
            String actualLastFile2Line = getLastFileLine(newTestFile2);
            // Test actual last line to be the same as the recorded
            Assert.assertTrue(actualLastFile1Line.equals(expectedLastFile1Line));
            Assert.assertTrue(actualLastFile2Line.equals(expectedLastFile2Line));
            // Delete the files and directory
            newTestFile1.delete();
            newTestFile2.delete();
            testDirectory.delete();

        } catch (Exception e1) {
            testFile1.delete();
            testFile2.delete();
            newTestFile1.delete();
            newTestFile2.delete();
            testDirectory.delete();
            Assert.fail(e1.getMessage());
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

    public File createTestFile(String directory, String name) throws IOException {
        File file = new File(DIR_PATH + directory, name);
        file.createNewFile();
        return file;
    }

    public void putInFileAsLastLine(File file, String line) throws IOException {
        FileWriter writer = new FileWriter(file);
        writer.write("\n" + line);
        writer.close();
    }
}
