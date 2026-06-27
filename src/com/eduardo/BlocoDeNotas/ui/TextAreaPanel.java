package com.eduardo.BlocoDeNotas.ui;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.DocumentListener;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

public class TextAreaPanel extends JPanel {

    private final JTextArea     textArea;
    private final JScrollPane   scrollPane;

    public TextAreaPanel(int width, int height) {
        this.setLayout(new BorderLayout());

        textArea = new JTextArea();
        textArea.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        textArea.setLineWrap(false);
        textArea.setWrapStyleWord(true);
        textArea.setBorder(BorderFactory.createCompoundBorder(
                textArea.getBorder(),
                BorderFactory.createEmptyBorder(3, 5, 5, 5)
        ));

        scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(width, height));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        this.add(scrollPane, BorderLayout.CENTER);
    }

    public void setLineWrap(boolean wrap) {
        textArea.setLineWrap(wrap);
        scrollPane.setHorizontalScrollBarPolicy(
                wrap ? ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
                        : ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS
        );
    }

    public String getText() {
        return textArea.getText();
    }

    public void setText(String text) {
        textArea.setText(text);
    }

    public void addDocumentListener(DocumentListener listener) {
        textArea.getDocument().addDocumentListener(listener);
    }

    public JTextArea getTextArea() {
        return textArea;
    }
}
