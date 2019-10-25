# File Renamer
**Tarkvara protsessid ja kvaliteet (ITB8826) Lab 2**

## Information
File Renamer is a little Java program with GUI capable of renaming parts of file names. It allows to rename files not only in current working directory, but also target specific file extensions as well as affect subfolders of current working directory.

File Renamer was developed in scope of the TalTech ITB8826 Lab 2.

## Running the program
### CAUTION: The program renames files and the changes cannot be undone. Use at your own risk.
**(Java ver. 8 is required)**
- Put files contained in *runnable/* folder to the folder where you want to rename your files.
- Run *run_FileRenamer.bat*

## Installation and usage guide

### For Intellij IDEA
- Run Intellij, click **Open** and select the *code/* folder of this repository.
- Check whether necessary libraries are added in Intellij:
	- Go to **File -> Project Structure -> Libraries**,
	- Check whether **hamcrest** and **junit** libraries added to the project.
		* If not: **"+" sign -> Java ->** Select **hamcrest** and **junit** libraries found in *code/lib/* folder **-> Add Selected**.

###### To run the program from IDE (*code/* directory is set as current working directory)
- Run *Main.java*

###### To run final tests
- Right click the *FileRenamerTest.java* under the *test/* folder
- **Run 'All Tests' with Coverage**. A Coverage window with results should appear on the right side of the editor.

#### Problems section
###### Option "Run 'All Tests' with Coverage" is greyed out / not present or you are having errors in the project
- Go to **File -> Settings... -> Plugins -> Installed**,
- Make sure you have Coverage and JUnit plugins enabled.
	- Maybe in your case you have to enable something else as well. Check whether Intellij has already asked you about it in the bottom right corner of the IDE.

###### Coverage window is hidden / did not open
- Go to **Analyze -> Show Code Coverage Data**,
- Select desired coverage results in case prompted.
		
### For Other IDE's
Make sure to add libraries contained in the *code/lib/* folder to the project. IDE-specific plugins for JUnit and code coverage must also be installed and enabled.
- Running *Main.java* will run the program (*code/* directory will be as current working directory).
- *FileRenamerTest.java* represents final tests.