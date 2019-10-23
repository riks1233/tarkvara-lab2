import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.regex.Pattern;

public class Main {

    private static FileRenamer fileRenamer;
    private static boolean targetExtension;
    private static boolean affectSubdirectories;

    public static void main(String[] args) {
        fileRenamer = new FileRenamer();
        createApplication();
    }

    private static void createApplication() {
        // Initialize frame and main panel
        JFrame frame = new JFrame ("File Renamer");
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        JPanel mainPanel = new JPanel();

        // Construct components
        JTextArea textDescriptionContainer = new JTextArea();
        JTextArea textDescription = new JTextArea (
                "Description: Rename parts of all file names in current working directory. Name part specified in the field \"Rename\" will be replaced with text specified in the field \"To\".");
        JLabel labelRename = new JLabel ("Rename:");
        JLabel labelTo = new JLabel (" To:");
        JLabel labelExtensionExample = new JLabel ("eg. 'txt', 'pdf' etc.");
        JLabel labelExtensionDot = new JLabel (".");
        JLabel labelResultMessage = new JLabel ();
        JTextField textFieldRename = new JTextField ();
        JTextField textFieldTo = new JTextField ();
        JTextField textFieldExtension = new JTextField ();
        JCheckBox checkBoxExtension = new JCheckBox ("Target files with extension");
        JCheckBox checkBoxSubdirectories = new JCheckBox ("Affect subdirectories");
        JButton btnRename = new JButton ("Rename");

        // Set components properties
        textDescriptionContainer.setEnabled(false);
        textDescription.setEnabled (false);
        textDescription.setDisabledTextColor(Color.BLACK);
        textDescription.setLineWrap(true);
        textDescription.setWrapStyleWord(true);
        textFieldExtension.setEnabled(false);
        textFieldExtension.setDisabledTextColor(Color.LIGHT_GRAY);
        labelResultMessage.setHorizontalAlignment(JLabel.CENTER);

        // Adjust size and set layout
        mainPanel.setPreferredSize (new Dimension (385, 368));
        mainPanel.setLayout (null);

        // Add components to main panel
        mainPanel.add(textDescription);
        mainPanel.add(textDescriptionContainer);
        mainPanel.add(labelRename);
        mainPanel.add(labelTo);
        mainPanel.add(labelExtensionExample);
        mainPanel.add(labelExtensionDot);
        mainPanel.add(labelResultMessage);
        mainPanel.add(textFieldRename);
        mainPanel.add(textFieldTo);
        mainPanel.add(textFieldExtension);
        mainPanel.add(checkBoxExtension);
        mainPanel.add(checkBoxSubdirectories);
        mainPanel.add(btnRename);

        // Add main panel to the frame
        frame.getContentPane().add(mainPanel);

        // Set component bounds (only needed by Absolute Positioning)
        textDescriptionContainer.setBounds (-5, 0, 400, 70);
        textDescription.setBounds (10, 10, 355, 50);
        labelRename.setBounds (45, 100, 60, 25);
        labelTo.setBounds (75, 135, 30, 25);
        labelExtensionExample.setBounds (250, 175, 100, 25);
        labelExtensionDot.setBounds (250, 205, 100, 25);
        labelResultMessage.setBounds (10, 290, 365, 25);
        textFieldRename.setBounds (110, 100, 230, 25);
        textFieldTo.setBounds (110, 135, 230, 25);
        textFieldExtension.setBounds (255, 200, 85, 25);
        checkBoxExtension.setBounds (55, 200, 190, 25);
        checkBoxSubdirectories.setBounds (55, 235, 165, 25);
        btnRename.setBounds (95, 315, 190, 45);

        // Add component listeners
        // Affect subdirectories checkbox listener
        checkBoxSubdirectories.addItemListener(e -> {
            if (checkBoxSubdirectories.isSelected()) {
                affectSubdirectories = true;
            } else {
                affectSubdirectories = false;
            }
        });

        // Target extension checkbox listener
        checkBoxExtension.addItemListener(e -> {
            if (checkBoxExtension.isSelected()) {
                textFieldExtension.setEnabled(true);
                targetExtension = true;
            } else {
                textFieldExtension.setEnabled(false);
                targetExtension = false;
            }
        });

        // Target extension textfield listener
        textFieldExtension.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (! Pattern.matches("[\\p{L}|0-9]", Character.toString(e.getKeyChar())) ) {
                    e.consume();
                }
            }
            @Override
            public void keyPressed(KeyEvent e) {
            }
            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        // Button action listener
        btnRename.addActionListener(e -> {
            labelResultMessage.setText("");
            try {
                String what = textFieldRename.getText();
                String to = textFieldTo.getText();
                String ext = textFieldExtension.getText();

                if (what.equals("") || to.equals("") || (targetExtension && ext.equals(""))) {
                    throw new Exception("A field was left empty");
                }

                ext = "." + ext;

                int filesRenamed = -1;
                if (targetExtension && affectSubdirectories) {
                    filesRenamed = fileRenamer.renameAllInCurrentAndSubDirectoriesWithExtension(what, to, ext);
                } else if (!targetExtension && affectSubdirectories) {
                    filesRenamed = fileRenamer.renameAllInCurrentAndSubDirectories(what, to);
                } else if (targetExtension && !affectSubdirectories) {
                    filesRenamed = fileRenamer.renameAllInCurrentDirectoryWithExtension(what, to, ext);
                } else {
                    filesRenamed = fileRenamer.renameAllInCurrentDirectory(what, to);
                }

                if (filesRenamed <= 0) {
                    throw new Exception("No files were renamed");
                }
                labelResultMessage.setForeground(new Color(0, 153, 0));
                labelResultMessage.setText("Success! Files renamed: " + Integer.toString(filesRenamed));
            } catch (Exception e1) {
                labelResultMessage.setForeground(new Color(255, 0, 0));
                labelResultMessage.setText("Error: " + e1.getMessage());
            }
        });

        // Setup and open frame in center of the user's screen
        frame.pack();
        frame.setResizable(false);
        frame.setVisible (true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
    }
}

