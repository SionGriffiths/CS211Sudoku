package com.siongriffiths.sudokuSolver.gui;

import com.siongriffiths.sudokuSolver.control.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The ControlPanel class is a panel ontaining JButtons which facilitate control
 * of the program via user interaction with the GUI.
 *
 *@author Sion Griffiths / sig2@aber.ac.uk
 */
public class ControlPanel extends JPanel implements ActionListener{

    /**The Controller instance which allows this class to interact with the
     * rest of the system
      */
    private Controller ctrl;


    private  JButton solve;

    /**
     * Constructs a ControlPanel, a reference to the Controller instance is passed in.
     * A preferred size is set for this panel and a JButton is instantiated and configured
     *
     * @param ctrl the Controller instance
     */
    public ControlPanel(Controller ctrl){
        this.ctrl = ctrl;
        this.setPreferredSize(new Dimension(600, 150));
        solve = new JButton("Solve");
        solve.addActionListener(this);
        this.setLayout( new BorderLayout());
        this.add(solve, BorderLayout.CENTER);
    }

    @Override
    /**
     * Calls solvePuzzle via the Controller
     */
    public void actionPerformed(ActionEvent e) {
        String event = e.getActionCommand();
        if(event.equals("Solve")){
            ctrl.solvePuzzle();
        }
    }
}
