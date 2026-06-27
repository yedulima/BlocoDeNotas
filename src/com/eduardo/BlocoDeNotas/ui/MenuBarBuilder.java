package com.eduardo.BlocoDeNotas.ui;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class MenuBarBuilder {

    private final JMenuItem newFileItem         = new JMenuItem("Novo");
    private final JMenuItem openNewFileItem     = new JMenuItem("Nova janela");
    private final JMenuItem saveMenuItem        = new JMenuItem("Salvar");
    private final JMenuItem openMenuItem        = new JMenuItem("Abrir...");
    private final JMenuItem exitMenuItem        = new JMenuItem("Sair");
    private final JCheckBoxMenuItem lineWrapMenuItem = new JCheckBoxMenuItem("Quebra automática de linha");

    public JMenuBar build(ActionListener listener) {
        int shortcut = Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx();

        newFileItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, shortcut));
        openNewFileItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.SHIFT_MASK | InputEvent.CTRL_MASK));
        saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, shortcut));
        openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, shortcut));

        newFileItem.addActionListener(listener);
        openNewFileItem.addActionListener(listener);
        saveMenuItem.addActionListener(listener);
        openMenuItem.addActionListener(listener);
        exitMenuItem.addActionListener(listener);
        lineWrapMenuItem.addActionListener(listener);

        JMenu fileMenu   = new JMenu("Arquivo");
        JMenu editMenu   = new JMenu("Editar");
        JMenu formatMenu = new JMenu("Formatar");

        fileMenu.add(newFileItem);
        fileMenu.add(openNewFileItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(exitMenuItem);

        formatMenu.add(lineWrapMenuItem);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(formatMenu);

        return menuBar;
    }

    public JMenuItem getNewFileItem()               { return newFileItem; }
    public JMenuItem getOpenNewFileItem()           { return openNewFileItem; }
    public JMenuItem getSaveMenuItem()              { return saveMenuItem; }
    public JMenuItem getOpenMenuItem()              { return openMenuItem; }
    public JMenuItem getExitMenuItem()              { return exitMenuItem; }
    public JCheckBoxMenuItem getLineWrapMenuItem()  { return lineWrapMenuItem; }
}
