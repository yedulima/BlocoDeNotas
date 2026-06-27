package com.eduardo.BlocoDeNotas;

import com.eduardo.BlocoDeNotas.ui.MainFrame;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame(null));
    }
}
