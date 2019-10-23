import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        createGUI();
    }

    private static void createGUI() {
        // Initialize frame and main panel
        JFrame frame = new JFrame ("File Renamer");
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        JPanel mainPanel = new JPanel();

        // Construct components
        JTextArea textDescriptionContainer = new JTextArea();
        JTextArea textDescription = new JTextArea (
                "Description: Rename parts of all file names in current working directory. Name part specified in the field \"rename\" will be replaced with text specified in the field \"to\".");
        JLabel labelRename = new JLabel ("Rename:");
        JLabel labelTo = new JLabel (" To:");
        JTextField textFieldTo = new JTextField ();
        JTextField textFieldRename = new JTextField ();
        JTextField textFieldExtension = new JTextField ();
        JCheckBox checkBoxTargetExtension = new JCheckBox ("Target specific file extension");
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

        // Adjust size and set layout
        mainPanel.setPreferredSize (new Dimension (385, 368));
        mainPanel.setLayout (null);

        // Add components to main panel
        mainPanel.add(textDescription);
        mainPanel.add(textDescriptionContainer);
        mainPanel.add(labelRename);
        mainPanel.add(labelTo);
        mainPanel.add(textFieldRename);
        mainPanel.add(textFieldTo);
        mainPanel.add(textFieldExtension);
        mainPanel.add(checkBoxTargetExtension);
        mainPanel.add(checkBoxSubdirectories);
        mainPanel.add(btnRename);

        // Add main panel to the frame
        frame.getContentPane().add(mainPanel);

        // Set component bounds (only needed by Absolute Positioning)
        textDescriptionContainer.setBounds (0, 0, 385, 70);
        textDescription.setBounds (10, 10, 355, 50);
        labelRename.setBounds (45, 100, 60, 25);
        labelTo.setBounds (75, 135, 30, 25);
        textFieldRename.setBounds (110, 100, 230, 25);
        textFieldTo.setBounds (110, 135, 230, 25);
        textFieldExtension.setBounds (250, 200, 90, 25);
        checkBoxTargetExtension.setBounds (55, 200, 190, 25);
        checkBoxSubdirectories.setBounds (55, 235, 165, 25);
        btnRename.setBounds (95, 315, 190, 45);

        // Show frame
        frame.pack();
        frame.setResizable(false);
        frame.setVisible (true);

        // Open frame in center of the user's screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
    }
}

