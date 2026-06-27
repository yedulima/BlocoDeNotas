package com.eduardo.BlocoDeNotas.controller;

import com.eduardo.BlocoDeNotas.io.DocumentIO;
import com.eduardo.BlocoDeNotas.model.DocumentState;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import java.io.File;
import java.io.FileNotFoundException;

public class DocumentController {

    private final DocumentState state;
    private final DocumentIO    io;
    private final JFrame        parent;
    private final JTextArea     textArea;
    private final TitleUpdateCallback titleUpdateCallback;

    public interface TitleUpdateCallback {
        void updateTitle();
    }

    public DocumentController(DocumentState state, DocumentIO io, JFrame parent, JTextArea textArea, TitleUpdateCallback titleUpdateCallback) {
        this.state = state;
        this.io = io;
        this.parent = parent;
        this.textArea = textArea;
        this.titleUpdateCallback = titleUpdateCallback;
    }

    public void newDocument() {
        handleClose();
        state.setFile(null);
        state.setTitle("Sem título");
        state.setInitialText("");
        state.setSaved(true);
        textArea.setText("");
        titleUpdateCallback.updateTitle();
    }

    public void save() {
        if (state.getFile() == null) {
            saveAs();
        } else {
            writeDocument(state.getFile());
        }
    }

    public void saveAs() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("."));

        int response = fileChooser.showSaveDialog(parent);

        if (response == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            if (!file.getName().toLowerCase().endsWith(".txt")) {
                file = new File(file.getAbsolutePath() + ".txt");
            }

            writeDocument(file);
        }
    }

    public void writeDocument(File file) {
        try {
            io.writeFile(file, textArea.getText());

            state.setInitialText(textArea.getText());
            state.setTitle(file.getName());
            state.setFile(file);
            state.setSaved(true);

            titleUpdateCallback.updateTitle();
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(parent, "Erro ao salvar arquivo: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void open() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("."));
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Text files", "txt"));

        int response = fileChooser.showOpenDialog(parent);

        if (response == JFileChooser.APPROVE_OPTION) {
            File file = new File(fileChooser.getSelectedFile().getAbsolutePath());

            try {
                String content = io.readFile(file);

                textArea.setText(content);
                state.setInitialText(content);
                state.setTitle(file.getName());
                state.setFile(file);
                state.setSaved(true);

                titleUpdateCallback.updateTitle();
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(parent, "Erro ao abrir arquivo: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    public void handleClose() {
        if (!state.isSaved()) {
            String[] options = {"Salvar", "Não Salvar", "Cancelar"};
            int response = JOptionPane.showOptionDialog(
                    null,
                    "Deseja salvar as alterações em " + state.getTitle() + "?",
                    "Bloco de notas",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    options,
                    0
            );

            if (response == 0) {
                save();
            } else if (response == 2 || response == -1) {
                return;
            }
        }

        parent.dispose();
    }

    public void onTextChanged(String currentText) {
        if (!currentText.equals(state.getInitialText())) {
            state.setSaved(false);
        } else {
            state.setSaved(true);
        }

        titleUpdateCallback.updateTitle();
    }
}