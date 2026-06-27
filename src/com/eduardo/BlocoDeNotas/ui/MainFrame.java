package com.eduardo.BlocoDeNotas.ui;

import com.eduardo.BlocoDeNotas.controller.DocumentController;
import com.eduardo.BlocoDeNotas.io.DocumentIO;
import com.eduardo.BlocoDeNotas.model.DocumentState;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame extends JFrame implements ActionListener {

    private static final int FRAME_WIDTH  = 1050;
    private static final int FRAME_HEIGHT = 750;

    private final DocumentState      state;
    private final DocumentController controller;
    private final TextAreaPanel      textAreaPanel;
    private final MenuBarBuilder     menuBarBuilder;

    public MainFrame(JFrame parent) {
        state          = new DocumentState();
        textAreaPanel  = new TextAreaPanel(FRAME_WIDTH, FRAME_HEIGHT);
        menuBarBuilder = new MenuBarBuilder();
        controller     = new DocumentController(
                state,
                new DocumentIO(),
                this,
                textAreaPanel.getTextArea(),
                this::updateTitle
        );

        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setLocationRelativeTo(parent);
        this.setMinimumSize(new Dimension(300, 400));
        this.setLayout(new BorderLayout());

        ImageIcon icon = new ImageIcon("src/com/eduardo/BlocoDeNotas/images/bloco_de_notas.png");
        this.setIconImage(icon.getImage());

        this.add(textAreaPanel, BorderLayout.CENTER);
        this.setJMenuBar(menuBarBuilder.build(this));

        textAreaPanel.addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e)  { controller.onTextChanged(textAreaPanel.getText()); }
            @Override public void removeUpdate(DocumentEvent e)  { controller.onTextChanged(textAreaPanel.getText()); }
            @Override public void changedUpdate(DocumentEvent e) { controller.onTextChanged(textAreaPanel.getText()); }
        });

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                controller.handleClose();
            }
        });

        updateTitle();
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == menuBarBuilder.getNewFileItem()) {
            controller.newDocument();
            new MainFrame(null);
        } else if (src == menuBarBuilder.getOpenNewFileItem()) {
            new MainFrame(this);
        } else if (src == menuBarBuilder.getSaveMenuItem()) {
            controller.save();
        } else if (src == menuBarBuilder.getOpenMenuItem()) {
            controller.open();
        } else if (src == menuBarBuilder.getExitMenuItem()) {
            controller.handleClose();
        } else if (src == menuBarBuilder.getLineWrapMenuItem()) {
            textAreaPanel.setLineWrap(menuBarBuilder.getLineWrapMenuItem().isSelected());
        }
    }

    public void updateTitle() {
        String name = state.getTitle();
        int dot = name.lastIndexOf(".");
        if (dot != -1) {
            name = name.substring(0, dot);
        }
        this.setTitle((state.isSaved() ? "" : "*") + name + " - Bloco de notas");
    }
}
