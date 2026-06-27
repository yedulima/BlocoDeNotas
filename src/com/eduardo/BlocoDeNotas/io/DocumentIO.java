package com.eduardo.BlocoDeNotas.io;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class DocumentIO {
    public String readFile(File file) throws FileNotFoundException {
        StringBuilder content = new StringBuilder();

        try (Scanner fileIn = new Scanner(file)) {
            while (fileIn.hasNextLine()) {
                content.append(fileIn.nextLine()).append('\n');
            }
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao abrir arquivo: " + ex.getMessage());
            ex.printStackTrace();
        }

        return content.toString();
    }

    public void writeFile(File file, String content) throws FileNotFoundException {
        try (PrintWriter fileOut = new PrintWriter(file)) {
            fileOut.print(content);
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar arquivo: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
