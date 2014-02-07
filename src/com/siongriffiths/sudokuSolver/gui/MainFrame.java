package com.siongriffiths.sudokuSolver.gui;

import javax.swing.*;

/**
 * The MainFrame class is a simple implementation of a JFrame
 * @author Sion Griffiths / sig2@aber.ac.uk
 */
public class MainFrame extends JFrame{


    /**
     * Constructs a MainFrame object, sets size, location and close operation.
     */
    public MainFrame(){
        super("SADAKAH!");
        this.setSize(1000,750);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        this.setVisible(true);
    }



}
