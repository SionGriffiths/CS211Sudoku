package com.siongriffiths.sudokuSolver.gui;

import com.siongriffiths.sudokuSolver.control.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A simple JMenuBar implementation, consists of 2 submenus and a JFileChooser
 * Facilitates the choosing of files to be loaded into the program and
 * allows user to exit via the menu.
 * @author Sion Griffiths / sig2@aber.ac.uk
 */
public class TopMenu extends JMenuBar implements ActionListener{

    /**
     * The controller instance to pass a file reference into the program
     */
    private Controller ctrl;

    /**
     * The file menu
     */
    private JMenu file = new JMenu("File");
    /**
     * The exit and load submenus
     */
    private JMenuItem load, exit;
    /**
     * The file chooser
     */
    private JFileChooser chooser;

    /**
     * Constructs a TopMenu, instantiates submenus, sets their labels and adds them
     * to the main file menu. Adds ActionListerners to allow user interaction with
     * the menu.
     * @param ctrl the Controller
     */
    public TopMenu(Controller ctrl){
        this.ctrl = ctrl;
        load = new JMenuItem("Load");
        exit = new JMenuItem("Exit");
        file.add(load);
        file.add(exit);
        load.addActionListener(this);
        exit.addActionListener(this);
        this.add(file);



    }


    @Override
    /**
     * Performs actions based on user interaction with specific menu items
     *
     *
     */
    public void actionPerformed(ActionEvent e) {

        String event = e.getActionCommand();


        if(event.equals("Exit")){
            System.exit(0);
        }
        if(event.equals("Load")){
            chooser = new JFileChooser();
            chooser.setAcceptAllFileFilterUsed(false);  //do not allow 'all files' option
            chooser.setFileFilter(new SudokuFileFilter());

            int chosen = chooser.showOpenDialog(this);
            if(chosen == JFileChooser.APPROVE_OPTION){
                //load the file
                ctrl.setPuzzleFile(chooser.getSelectedFile());

            }
        }

    }
}

