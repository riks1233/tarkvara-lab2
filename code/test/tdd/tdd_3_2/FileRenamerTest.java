package tdd.tdd_3_2;

import org.junit.Assert;
import org.junit.Test;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public class FileRenamerTest {
    // In tests we suppose that testingDirectory directory exists and run everything in this directory

    private static String TESTING_DIR_PATH = Paths.get("").toAbsolutePath().toString() + "\\testingDirectory\\";

    @Test
    public void testRenameAllInCurrentDirectory() throws IOException {
        // Create a test file, rename it with function that is tested, delete everything that was produced
        try {
            // Create a test file
            String testFileName = "testfile.txt";
            File testFile = createTestFile(testFileName);

            // Record expected last line
            String expectedLastFileLine = "Test File Contents";
            putInFileAsLastLine(testFile, expectedLastFileLine);

            // Rename
            FileRenamer fileRenamer = new FileRenamer(TESTING_DIR_PATH);
            fileRenamer.renameAllInCurrentDirectory("testfile", "rambotest");

            // Check if renamed file exists
            String renamedTestFileName = "rambotest.txt";
            File renamedFile = new File(TESTING_DIR_PATH, renamedTestFileName);

            // Test file name to be desired
            if (!renamedFile.exists()) {
                clearTestingDirectory();
                throw new AssertionError("Renamed file does not exit");
            }
            // Record actual last line
            String actualLastFileLine = getLastFileLine(renamedFile);
            // Delete the files
            clearTestingDirectory();
            // Test actual last line to be the same as the recorded
            Assert.assertEquals(actualLastFileLine, expectedLastFileLine);
        } catch (Exception e1) {
            clearTestingDirectory();
            throw e1;
        }
    }

    @Test
    public void testRenameAllInCurrentDirectoryWithExtension() throws IOException {
        // Create 2 test files with same name but different extensions.
        // Rename equal file name parts with function that is tested, assert and delete everything that was produced
        try {

            String testFile1Name = "testfile.txt";
            String testFile2Name = "testfile.mp3";
            File testFile1 = createTestFile(testFile1Name);
            File testFile2 = createTestFile(testFile2Name);
            String expectedLastFile1Line = "Test File 1 Contents";
            putInFileAsLastLine(testFile1, expectedLastFile1Line);
            String expectedLastFile2Line = "Test File 2 Contents";
            putInFileAsLastLine(testFile2, expectedLastFile2Line);

            // Rename
            FileRenamer fileRenamer = new FileRenamer(TESTING_DIR_PATH);
            fileRenamer.renameAllInCurrentDirectoryWithExtension("testfile", "rambotest", ".txt");

            // Check if renamed file exists
            // Check if old file that had not to be renamed exists
            String renamedTestFile1Name = "rambotest.txt";
            File renamedTestFile1 = new File(TESTING_DIR_PATH, renamedTestFile1Name);
            File oldTestFile2 = new File(TESTING_DIR_PATH, testFile2Name);

            // Test file names to be desired
            if (!renamedTestFile1.exists()) {
                clearTestingDirectory();
                throw new AssertionError("Renamed file does not exit");
            }
            if (!oldTestFile2.exists()) {
                clearTestingDirectory();
                throw new AssertionError("Old file does not exit");
            }

            // Record actual last line
            String actualLastFile1Line = getLastFileLine(renamedTestFile1);
            String actualLastFile2Line = getLastFileLine(oldTestFile2);
            // Delete the files
            clearTestingDirectory();
            // Test actual last line to be the same as the recorded
            Assert.assertEquals(actualLastFile1Line, expectedLastFile1Line);
            Assert.assertEquals(actualLastFile2Line, expectedLastFile2Line);
        } catch (Exception e1) {
            clearTestingDirectory();
            throw e1;
        }
    }

    @Test
    public void testRenameAllInCurrentAndSubDirectories() throws IOException {
        // Create main test directory (testdir1) in which to operate and put another test directory (testdir2) in it
        // Create 1 test file in current directory and 1 test file in a test directory
        // Rename equal file name parts with function that is tested, assert and delete everything that was produced
        try {
            // File 1
            String testFile1Name = "testfile1.txt";
            File testFile1 = createTestFile(testFile1Name);
            String expectedLastFile1Line = "Test File 1 Contents";
            putInFileAsLastLine(testFile1, expectedLastFile1Line);

            // Directory + File 2
            String testDirectoryName = "testdirectory";
            File testDirectory = new File(TESTING_DIR_PATH + testDirectoryName + "\\");
            testDirectory.mkdir();
            String testFile2Name = "testfile2.txt";
            File testFile2 = createTestFile(testDirectoryName + "\\", testFile2Name);
            String expectedLastFile2Line = "Test File 2 Contents";
            putInFileAsLastLine(testFile2, expectedLastFile2Line);

            // Rename
            FileRenamer fileRenamer = new FileRenamer(TESTING_DIR_PATH);
            fileRenamer.renameAllInCurrentAndSubDirectories("testfile", "rambotest");

            // Check if files exist
            String renamedTestFile1Name = "rambotest1.txt";
            String renamedTestFile2Name = "rambotest2.txt";
            File renamedTestFile1 = new File(TESTING_DIR_PATH, renamedTestFile1Name);
            File renamedTestFile2 = new File(TESTING_DIR_PATH + testDirectoryName + "\\", renamedTestFile2Name);

            // Test file names to be desired
            if (!renamedTestFile1.exists()) {
                throw new AssertionError("Renamed file 1 does not exit");
            }
            if (!renamedTestFile2.exists()) {
                throw new AssertionError("Renamed file 2 does not exit");
            }

            // Record actual last lines
            String actualLastFile1Line = getLastFileLine(renamedTestFile1);
            String actualLastFile2Line = getLastFileLine(renamedTestFile2);
            // Delete the files and directory
            clearTestingDirectory();
            // Test actual last line to be the same as the recorded
            Assert.assertEquals(actualLastFile1Line, expectedLastFile1Line);
            Assert.assertEquals(actualLastFile2Line, expectedLastFile2Line);
        } catch (Exception e1) {
            clearTestingDirectory();
            throw e1;
        }
    }

    private String getLastFileLine(File file) throws IOException{
        Scanner sc = new Scanner(file);
        String lastFileLine = "";
        while (sc.hasNextLine()){
            lastFileLine = sc.nextLine();
        }
        sc.close();
        return lastFileLine;
    }

    private File createTestFile(String name) throws IOException {
        File file = new File(TESTING_DIR_PATH, name);
        file.createNewFile();
        return file;
    }

    private File createTestFile(String directory, String name) throws IOException {
        File file = new File(TESTING_DIR_PATH + directory, name);
        file.createNewFile();
        return file;
    }

    private void putInFileAsLastLine(File file, String line) throws IOException {
        FileWriter writer = new FileWriter(file);
        writer.write("\n" + line);
        writer.close();
    }

    private void clearTestingDirectory() {
        clearTestingDirectory_helper("");
    }

    private void clearTestingDirectory_helper(String subdirectory){
        File testingDirectory = new File(TESTING_DIR_PATH + subdirectory);
        for (File file : testingDirectory.listFiles()) {
            if (file.isDirectory()) {
                clearTestingDirectory_helper(subdirectory + file.getName() + "\\");
            }
            file.delete();
        }
    }
}
