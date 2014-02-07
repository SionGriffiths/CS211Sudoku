package com.siongriffiths.sudokuSolver.control;

import com.siongriffiths.sudokuSolver.gui.*;
import com.siongriffiths.sudokuSolver.model.Cell;
import com.siongriffiths.sudokuSolver.model.GameGrid;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 *  The Controller class facilitates the construction and control of the sudoku program.
 *  Controller implements the methods of interaction for most parts of the system.
 *
 * @author Sion Griffiths / sig2@aber.ac.uk
 */
public class Controller {

    /**The FileParser instance that manages the handling and parsing
     * of input files */
    private FileParser fp = new FileParser();


    /**The GameGrid instance that holds the structures and Cells that
     * are parts of the sudoku representation */
    private  GameGrid grid;

    /**The Solver instance that contains the algorithms and functionality
     * to solve a sudoku */
    private  Solver solve;
    /**The SudokuPanel, a JPanel which facilitates the expected display of a sudoku*/
    private SudokuPanel panel;
    /**The MainFrame, a JFrame which houses the GUI for this program*/
    private MainFrame mf;
    /**The TextPanel, a JPanel containing a JTextArea to allow output of status text*/
    private TextPanel tp;
    /**The TopMenu, a JMenuBar housing submenus which implement file loading and exit options*/
    private  TopMenu tm;
    /**The ControlPanel, a JPanel containing control buttons*/
    private  ControlPanel cp;


    /**
     * Constructs a Controller and instantiates instances of use classes.
     * Builds the GUI by adding child  components to parent components and setting
     * the frame visible.
     *
     * Loads a default sudoku file into the file parser. Builds the internal representation
     * of the current puzzle and sends it to the GUI
     */
    public Controller(){
        fp.setFile("book64.sud");
        panel = new SudokuPanel();
        mf = new MainFrame();
        tp = new TextPanel();
        tm = new TopMenu (this);
        cp = new ControlPanel(this);
        mf.add(panel, BorderLayout.WEST);
        mf.add(tp, BorderLayout.EAST);
        mf.add(tm, BorderLayout.NORTH);
        mf.add(cp, BorderLayout.SOUTH);
        mf.setVisible(true);

        makePuzzle();

        sendToPanel();

    }

    /**
     * Creates a new instance of Solver and starts a new Thread for it to run inside.
     * Ensures that only one Solver is active at any time.
     *
     */
    public void solvePuzzle(){

        if(solve == null){
           solve = new Solver(this);
        }

        if(!(solve.isSolveRunning())){
            Thread solver = new Thread(solve);
            solver.start();
        }
    }

    /**
     * Sends a file to the file parser and builds a new sudoku puzzle.
     * Sends the new puzzle to the GUI.
     * Clears the text in the status text panel
     * @param f the File
     */
    public void setPuzzleFile(File f){
        fp.setFile(f.getName());
        makePuzzle();
        sendToPanel();
        tp.clearText();
    }

    /**
     * Causes the file parser to parse the current file, creates a new GameGrid and builds new Cell instances
     * with values from the file. If a space character is read the corresponding Cell
     * will be populated with candidate values.
     * Calls makestructures() in GameGrid which initialises the underlying data structures
     * in the sudoku representation.
     */
    public void makePuzzle(){

        if(!fp.parseFile()){
            JOptionPane.showMessageDialog(null, "File not recognised or corrupt");

            return;
        }
        grid = new GameGrid();
        for(int i =0; i < fp.getValues().length; i++){
            for(int j = 0; j < fp.getValues().length; j++){
                grid.addCell(i,j, new Cell());
                if(fp.getValues()[i][j] == ' '){
                    grid.getGrid()[i][j].populateCandidates();
                }
                else{
                    grid.getGrid()[i][j].setValue(Character.toString(fp.getValues()[i][j]));
                }
            }
        }
        grid.makeStructures();
    }

    /**
     * Updates the display. Reads Cell values in the current Game and updates the JLabels
     * in the SudokuPanel. Enhances presentation of a Cells candidate list on the SudokuPanel
     * by removing brackets and commas. Repaint the GUI components.
     */
    public void sendToPanel(){

        for(int i = 0; i < grid.getGrid().length; i++){
            for(int j = 0; j < grid.getGrid().length; j++){
                if(!grid.getGrid()[i][j].getValue().equals("")){
                    panel.setTextOutput(i, j, grid.getGrid()[i][j].getValue());

                }
                else{
                    String temp = grid.getGrid()[i][j].getCandidates().toString();
                    temp = temp.replaceAll(",", " ").replaceAll("\\[", "").replaceAll("\\]", "");

                    panel.setTextOutput(i, j, temp);
                }


            }
        }
        mf.repaint();
    }

    /**Sends a String to the status panel
     *
     * @param str the String
     */
    public void sendToOutput(String str){

        tp.textToOutput(str + "\n");
    }

    /**
     * Returns the current game grid
     * @return the game grid
     */
    public GameGrid getGrid(){
        return grid;
    }

}
