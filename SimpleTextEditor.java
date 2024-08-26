import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class SimpleTextEditor extends JFrame implements ActionListener {
    // Components of the editor
    JTextArea textArea;
    JScrollPane scrollPane;
    JMenuBar menuBar;
    JMenu fileMenu, editMenu, formatMenu;
    JMenuItem newFile, openFile, saveFile, exitFile;
    JMenuItem boldText, italicText, underlineText, changeFont;

    // Constructor to initialize the editor
    public SimpleTextEditor() {
        // Set the title of the editor
        setTitle("Simple Text Editor");

        // Set the size of the editor
        setSize(600, 400);

        // Create the text area where the text will be entered
        textArea = new JTextArea();
        scrollPane = new JScrollPane(textArea);
        add(scrollPane);

        // Create the menu bar and its components
        menuBar = new JMenuBar();

        // File menu
        fileMenu = new JMenu("File");
        newFile = new JMenuItem("New");
        openFile = new JMenuItem("Open");
        saveFile = new JMenuItem("Save");
        exitFile = new JMenuItem("Exit");

        newFile.addActionListener(this);
        openFile.addActionListener(this);
        saveFile.addActionListener(this);
        exitFile.addActionListener(this);

        fileMenu.add(newFile);
        fileMenu.add(openFile);
        fileMenu.add(saveFile);
        fileMenu.addSeparator();
        fileMenu.add(exitFile);

        // Edit menu (for future enhancements like Undo/Redo)
        editMenu = new JMenu("Edit");

        // Format menu
        formatMenu = new JMenu("Format");
        boldText = new JMenuItem("Bold");
        italicText = new JMenuItem("Italic");
        underlineText = new JMenuItem("Underline");
        changeFont = new JMenuItem("Change Font");

        boldText.addActionListener(this);
        italicText.addActionListener(this);
        underlineText.addActionListener(this);
        changeFont.addActionListener(this);

        formatMenu.add(boldText);
        formatMenu.add(italicText);
        formatMenu.add(underlineText);
        formatMenu.add(changeFont);

        // Add menus to the menu bar
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(formatMenu);

        // Set the menu bar to the frame
        setJMenuBar(menuBar);

        // Set the default close operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Make the editor visible
        setVisible(true);
    }

    // Action handler for menu items
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "New":
                textArea.setText("");
                break;
            case "Open":
                JFileChooser fileChooser = new JFileChooser();
                int option = fileChooser.showOpenDialog(this);
                if (option == JFileChooser.APPROVE_OPTION) {
                    try {
                        File file = fileChooser.getSelectedFile();
                        BufferedReader reader = new BufferedReader(new FileReader(file));
                        textArea.read(reader, null);
                        reader.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                break;
            case "Save":
                JFileChooser saveFileChooser = new JFileChooser();
                int saveOption = saveFileChooser.showSaveDialog(this);
                if (saveOption == JFileChooser.APPROVE_OPTION) {
                    try {
                        File file = saveFileChooser.getSelectedFile();
                        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                        textArea.write(writer);
                        writer.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                break;
            case "Exit":
                System.exit(0);
                break;
            case "Bold":
                textArea.setFont(textArea.getFont().deriveFont(Font.BOLD));
                break;
            case "Italic":
                textArea.setFont(textArea.getFont().deriveFont(Font.ITALIC));
                break;
            case "Underline":
                // Underline functionality can be complex, requires styled text support
                break;
            case "Change Font":
                String fontName = JOptionPane.showInputDialog(this, "Enter Font Name:");
                int fontSize = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Font Size:"));
                textArea.setFont(new Font(fontName, Font.PLAIN, fontSize));
                break;
        }
    }

    // Main method to start the application
    public static void main(String[] args) {
        new SimpleTextEditor();
    }
}
