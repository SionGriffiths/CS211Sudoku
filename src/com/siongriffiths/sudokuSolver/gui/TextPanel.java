package com.siongriffiths.sudokuSolver.gui;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;

/**
 * TextPanel is a simple JPanel implementation containing a JtextArea. It provides an area on the screen for status
 * text to be displayed.
 * @author Sion Griffiths / sig2@aber.ac.uk
 *
 */
public class TextPanel extends JPanel {

    private JTextArea output = new JTextArea();
    private JScrollPane scroll =new JScrollPane(output);
    private DefaultCaret caret = (DefaultCaret) output.getCaret();



    public TextPanel(){
        this.setLayout(null);
        scroll.setBounds(3,3, 240, 550);
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        this.setPreferredSize(new Dimension(250, 600));
        this.add(scroll);

    }

    public void textToOutput(String str){
        output.append(str);

    }

    public void clearText(){
        output.setText("");
    }

}
