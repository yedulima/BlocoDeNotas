package com.eduardo.BlocoDeNotas.model;

import java.io.File;

public class DocumentState {

    private File    file            = null;
    private String  title           = "Sem título";
    private boolean saved           = true;
    private String  initialText     = "";

    // Getters

    public File getFile() { return file; }

    public String getTitle() { return title; }

    public boolean isSaved() { return saved; }

    public String getInitialText() { return initialText; }

    // Setters

    public void setFile(File file) {
        this.file = file;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    public void setInitialText(String text) {
        this.initialText = text;
    }
}