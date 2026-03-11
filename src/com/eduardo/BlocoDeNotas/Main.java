package com.eduardo.BlocoDeNotas;

import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JMenu;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main extends JFrame implements ActionListener {

    // JFrame
    private final JFrame frame = this;
    private final int FRAME_WIDTH = 1050;
    private final int FRAME_HEIGHT = 750;
    private File documentFile = null;
    private boolean isDocumentSaved = true;
    private String fileTitle = "Sem título";

    // MenuBar
    private final JMenuBar menuBar = new JMenuBar();
    private final JMenu fileMenu = new JMenu("Arquivo");
    private final JMenu editMenu = new JMenu("Editar");
    private final JMenu formatMenu = new JMenu("Formatar");

    private final JMenuItem openNewFileItem = new JMenuItem("Abrir janela");
    private final JMenuItem saveMenuItem = new JMenuItem("Salvar");
    private final JMenuItem openMenuItem = new JMenuItem("Abrir...");
    private final JMenuItem exitMenuItem = new JMenuItem("Sair");

    private final JCheckBoxMenuItem isLineWrapMenuItem = new JCheckBoxMenuItem("Quebra automática de linha");

    // Components
    private final JTextArea textArea = new JTextArea();
    private String initialTextArea = "";
    private JScrollPane scrollPane;

    // Vars
    private boolean isLineWrap = false;

    Main() {
        updateDocumentTitle(fileTitle);
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setMinimumSize(new Dimension(300, 400));
        this.setLayout(new BorderLayout());

        ImageIcon icon = new ImageIcon("src/com/eduardo/BlocoDeNotas/images/bloco_de_notas.png");
        this.setIconImage(icon.getImage());

        textArea.setText(initialTextArea);
        textArea.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        textArea.setLineWrap(isLineWrap);
        textArea.setWrapStyleWord(true);

        scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        this.add(scrollPane, BorderLayout.CENTER);

        /* MenuBar */

        openNewFileItem.addActionListener(this);
        saveMenuItem.addActionListener(this);
        openMenuItem.addActionListener(this);
        exitMenuItem.addActionListener(this);

        isLineWrapMenuItem.addActionListener(this);

        fileMenu.add(openNewFileItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(exitMenuItem);

        formatMenu.add(isLineWrapMenuItem);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(formatMenu);

        /* /MenuBar */

        this.setJMenuBar(menuBar);
        this.setVisible(true);

        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                documentChange();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentChange();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentChange();
            }

            public void documentChange() {
                if (!textArea.getText().equals(initialTextArea)) {
                    isDocumentSaved = false;
                    updateDocumentTitle();
                } else {
                    isDocumentSaved = true;
                    updateDocumentTitle(fileTitle);
                }
            }
        });

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                documentClosing();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == openNewFileItem) {
            new Main();
        }
        else if (e.getSource() == saveMenuItem) {
            saveDocument();
        }
        else if (e.getSource() == openMenuItem) {
            openDocument();
        }
        else if (e.getSource() == exitMenuItem) {
            documentClosing();
        }
        else if (e.getSource() == isLineWrapMenuItem) {
            this.isLineWrap = !this.isLineWrap;
            this.textArea.setLineWrap(this.isLineWrap);
            scrollPane.setHorizontalScrollBarPolicy(
                    isLineWrap ? ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER :
                            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS
            );
        }
    }

    private void saveDocument() {
        if (this.documentFile == null) {
            saveAsDocument();
        } else {
            writeDocument(this.documentFile);
        }
    }

    private void saveAsDocument() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("."));

        int response = fileChooser.showSaveDialog(this);

        if (response == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            if (!file.getName().toLowerCase().endsWith(".txt")) {
                file = new File(file.getAbsolutePath() + ".txt");
            }

            writeDocument(file);
        }
    }

    private void writeDocument(File file) {
        try (PrintWriter fileOut = new PrintWriter(file)) {
            fileOut.print(this.textArea.getText());

            this.initialTextArea = this.textArea.getText();
            this.fileTitle = file.getName();
            this.documentFile = file;
            this.isDocumentSaved = true;

            updateDocumentTitle(fileTitle);
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar arquivo: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void openDocument() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("."));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
        fileChooser.setFileFilter(filter);

        int response = fileChooser.showOpenDialog(this);

        if (response == JFileChooser.APPROVE_OPTION) {
            File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
            this.textArea.setText("");
            StringBuilder content = new StringBuilder();

            try (Scanner fileIn = new Scanner(file)) {
                while (fileIn.hasNextLine()) {
                    String line = fileIn.nextLine();
                    content.append(line).append('\n');
                }

                String fullText = content.toString();
                this.textArea.setText(fullText);
                this.initialTextArea = fullText;

                this.fileTitle = file.getName();
                this.documentFile = file;
                this.isDocumentSaved = true;
                this.setTitle(fileTitle + " - Bloco de notas");

                updateDocumentTitle(this.fileTitle);
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao abrir arquivo: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    private void updateDocumentTitle() {
        String fileName = this.fileTitle;

        int index = fileName.lastIndexOf(".");

        if (index != -1) {
            fileName = fileName.substring(0, index);
        }

        this.setTitle((!isDocumentSaved ? "*" : "") + fileName + " - Bloco de notas");
    }

    private void updateDocumentTitle(String title) {
        String fileName = title;

        int index = fileName.lastIndexOf(".");

        if (index != -1) {
            fileName = fileName.substring(0, index);
        }

        this.setTitle((!isDocumentSaved ? "*" : "") + fileName + " - Bloco de notas");
    }

    private void documentClosing() {
        if (!isDocumentSaved) {
            String[] options = {"Salvar", "Não Salvar", "Cancelar"};
            int response = JOptionPane.showOptionDialog(
                    this, "Deseja salvar as alterações em " + fileTitle + "?",
                    "Bloco de notas", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, 0
            );

            if (response == 0) {
                saveDocument();
            }
            else if (response == 1) {
                this.dispose();
            }

            return;
        }

        this.dispose();
    }

    public static void main(String[] args) {
        new Main();
    }
}
