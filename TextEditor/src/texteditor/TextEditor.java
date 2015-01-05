/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package texteditor;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author victor
 */

@SuppressWarnings({"serial","unused"})
public class TextEditor extends Frame {
    private Frame frame, popUpFrame;
    private Panel mainPanel, yesNoButtonsPanel;
    private TextArea mainTextArea;
    private MenuBar frameMenuBar;
    private Menu fileMenu, editMenu, helpMenu;
    private MenuItem newItem, openItem, saveItem, exitItem, copyItem, pasteItem,
    cutItem ,clearItem, aboutItem;
    private CheckboxMenuItem boldItem, italicItem;
    private FileDialog openFileDialog, saveFileDialog;
    private Button yesButton, noButton, okButton;
    private Dialog aboutDialog;
    private Label aboutLbl;
    private BufferedReader br;
    private BufferedWriter wr;
    private String copyBuffer="";
    
    
    public TextEditor() {
        frame = new Frame();
        frame.setVisible(true);
        frame.setResizable(true);
        frame.setTitle("TextEditor");
        frame.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        });
        initComponents();
        setComponentOptions();
        addComponents();
        frame.pack();
    }
    @SuppressWarnings("static-access")
    private void initComponents(){
        mainPanel = new Panel();
        mainPanel.setBackground(Color.WHITE);
        mainTextArea = new TextArea("",30,90, TextArea.SCROLLBARS_VERTICAL_ONLY);
        frameMenuBar = new MenuBar();
        fileMenu = new Menu("File");
        editMenu = new Menu("Edit");
        helpMenu = new Menu("Help");
        newItem = new MenuItem("New");
        openItem = new MenuItem("Open");
        saveItem = new MenuItem("Save");
        exitItem = new MenuItem("Exit");
        copyItem = new MenuItem("Copy");
        pasteItem = new MenuItem("Paste");
        cutItem = new MenuItem("Cut");
        clearItem = new MenuItem("Clear");
        boldItem = new CheckboxMenuItem("Bold");
        italicItem = new CheckboxMenuItem("Italic");
        aboutItem = new MenuItem("About Editor");
        openFileDialog = new FileDialog(frame, "Open File", FileDialog.LOAD);
        saveFileDialog = new FileDialog(frame, "Save File", openFileDialog.SAVE);
        popUpFrame = new Frame("Save before exit ? ");
        yesNoButtonsPanel = new Panel(new GridLayout(3,4));
        yesButton = new Button("Yes");
        noButton = new Button("No");
        aboutDialog = new Dialog(frame);
        aboutLbl = new Label("Victor Georgiev");
        okButton = new Button("OK");
    }
    
    private void setComponentOptions(){
        popUpFrame.setSize(300, 150);
        popUpFrame.setResizable(false);
        popUpFrame.setVisible(false);
        popUpFrame.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                aboutDialog.setVisible(false);
            }
        });
        aboutDialog.setSize(400,150);
        aboutDialog.setTitle("About");
        aboutDialog.setLayout(new GridBagLayout());
        aboutDialog.setResizable(false);
        aboutDialog.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e ){
                aboutDialog.setVisible(false);
            }
        });
        aboutDialog.setVisible(false);
        newItem.addActionListener(new NewMenuItemListener());
        openItem.addActionListener(new OpenMenuItemListener());
        saveItem.addActionListener(new SaveMenuItemListener());
        exitItem.addActionListener(new ExitMenuItemListener());
        yesButton.addActionListener(new YesSaveListener());
        noButton.addActionListener(new NoSaveListener());
        copyItem.addActionListener(new CopyItemListener());
        pasteItem.addActionListener(new PasteItemListener());
        boldItem.addItemListener(new TextDecorationListener());
        italicItem.addItemListener(new TextDecorationListener());
        aboutItem.addActionListener(new AboutItemListener());
        okButton.addActionListener(new OkButtonListener());
        
    }
    
    private void addComponents() {
        frame.add(mainPanel);
        frame.setMenuBar(frameMenuBar);
        frameMenuBar.add(fileMenu);
        frameMenuBar.add(editMenu);
        frameMenuBar.add(helpMenu);
        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        editMenu.add(copyItem);
        editMenu.add(pasteItem);
        editMenu.add(cutItem);
        editMenu.add(clearItem);
        editMenu.add(boldItem);
        editMenu.add(italicItem);
        helpMenu.add(aboutItem);
        mainPanel.add(mainTextArea);
        popUpFrame.add(new Label(""), BorderLayout.NORTH);
        popUpFrame.add(yesNoButtonsPanel, BorderLayout.CENTER);
        popUpFrame.add(new Label(""), BorderLayout.SOUTH);
        yesNoButtonsPanel.add(new Label(""));
        yesNoButtonsPanel.add(new Label(""));
        yesNoButtonsPanel.add(new Label(""));
        yesNoButtonsPanel.add(new Label(""));
        yesNoButtonsPanel.add(new Label(""));
        yesNoButtonsPanel.add(yesButton);
        yesNoButtonsPanel.add(noButton);
        yesNoButtonsPanel.add(new Label(""));
        yesNoButtonsPanel.add(new Label(""));
        yesNoButtonsPanel.add(new Label(""));
        yesNoButtonsPanel.add(new Label(""));
        GridBagConstraints aboutC = new GridBagConstraints();
        aboutC.gridx=0;aboutC.gridy=0;
        aboutDialog.add(aboutLbl, aboutC);
        aboutC.gridx=0;aboutC.gridy=6;
        aboutDialog.add(okButton, aboutC);
    }
    
    class OkButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            aboutDialog.setVisible(false);
        }
    }

    class AboutItemListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            aboutDialog.setVisible(false);
        }
    }
    
    class TextDecorationListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e){
            if(boldItem.getState())
                mainTextArea.setFont(mainTextArea.getFont().deriveFont(Font.BOLD));
            if(italicItem.getState())
                mainTextArea.setFont(mainTextArea.getFont().deriveFont(Font.ITALIC));
            if(boldItem.getState()&&!italicItem.getState())
                mainTextArea.setFont(mainTextArea.getFont().deriveFont(Font.BOLD));
            if(!boldItem.getState())
                mainTextArea.setFont(mainTextArea.getFont().deriveFont(Font.ITALIC));
            if(boldItem.getState()&&italicItem.getState())
                mainTextArea.setFont(mainTextArea.getFont().deriveFont(Font.BOLD+Font.ITALIC));
                    if(!boldItem.getState()&&!italicItem.getState())
                        mainTextArea.setFont(mainTextArea.getFont().deriveFont(Font.PLAIN));          
        }
    }
    
    class CutItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            copyBuffer = mainTextArea.getSelectedText();
            copyBuffer = "";
        }
    }
    
    class PasteItemListener implements ActionListener {
        public void avtionPerformed(ActionEvent e){
            if(!(mainTextArea.getSelectedText().equals(""))) {
                mainTextArea.replaceRange(copyBuffer, mainTextArea.getSelectionStart(),mainTextArea.getSelectionEnd());
            }else{
                mainTextArea.insert(copyBuffer, mainTextArea.getCaretPosition());
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
    
            
    class CopyItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            copyBuffer = mainTextArea.getSelectedText();
        }
    }
    
    class YesSaveListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            String content = mainTextArea.getText();
            saveFileDialog.setFile("*.txt");
            saveFileDialog.setVisible(true);
            String fileDir = null, file=null;
            fileDir = saveFileDialog.getFile();
            if(file!=null) {
                try {
                    wr = new BufferedWriter(new FileWriter(fileDir + file));
                    wr.write(content);
                    wr.flush();
                    wr.close();
                    System.exit(0);
                } catch (IOException e1) {
                    e1.printStackTrace();                
                }
            }
        }
    }
    
    class NoSaveListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
    
    class ExitMenuItemListener implements ActionListener {
        @Override
        public void actionPerformed (ActionEvent e) {
            if(mainTextArea.getText().equalsIgnoreCase("")) {
                System.exit(0);
            } else {
                popUpFrame.setVisible(true);
            }
        }
    }
    
    class SaveMenuItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String content = mainTextArea.getText();
            saveFileDialog.setFile("*.txt");
            saveFileDialog.setVisible(true);
            String fileDir = null, file = null;
            fileDir = saveFileDialog.getDirectory();
            file = saveFileDialog.getFile();
            try {
                wr = new BufferedWriter(new FileWriter(fileDir + file));
                wr.write(content);
                wr.flush();
                wr.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
    
    class OpenMenuItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            openFileDialog.setVisible(true);
            String line = null, fileDir = null, file = null;
            StringBuilder builder = new StringBuilder();
            fileDir = openFileDialog.getDirectory();
            file = openFileDialog.getFile();
            try {
                br = new BufferedReader(new FileReader(fileDir + file));
                while((line = br.readLine())!=null) {
                    builder.append(line + "\n");
                }
                mainTextArea.setText(builder.toString());
                br.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    class NewMenuItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            mainTextArea.getText();
            mainTextArea.setText("");
        }
    }
    
    public static void main(String[] args) {
        TextEditor editor = new TextEditor();
        // TODO code application logic here
    }
}
