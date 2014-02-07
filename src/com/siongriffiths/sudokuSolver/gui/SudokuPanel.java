package com.siongriffiths.sudokuSolver.gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * The SudokuPanel class is a sudoku specific implementation of a JPanel.
 * Facilitates the display of the puzzle in a manner consistent with sudoku.
 *
 * @author Sion Griffiths / sig2@aber.ac.uk
 */
public class SudokuPanel extends JPanel {

    /**
     * Default size for arrays - 9 is the standard structure length for sudoku
     */
    private static final int DEFAULT_SIZE = 9;

    /**
     * A 2d array of JLabels to hold all the cell values in the puzzle
     */
    private JLabel[][] cells;

    /**
     * A boolean flag used to control the painting of of JLabels
     */
    private boolean paint;

    /**
     * A Font used to control display of cell values
     */
    private Font big = new Font("Dialog", Font.PLAIN, 30);

    /**
     * A Font used to control the display of cell candidates
     */
    private Font small = new Font("Dialog", Font.PLAIN, 8);

    /**
     * A Border instance used to add a coloured border to the JLabels
      */
    private Border border = BorderFactory.createLineBorder(new Color(138, 168, 186));

    /**
     * Constructs a SudokuPanel, sets panel size adds a layout manager.
     * Initialises the JLabel array at DEFAULT_SIZE
     *
     */
    public SudokuPanel(){


        this.setPreferredSize(new Dimension(700, 600));
        cells = new JLabel[DEFAULT_SIZE][DEFAULT_SIZE];

        this.setLayout(new GridLayout(DEFAULT_SIZE,DEFAULT_SIZE));
        makePanel();

    }

    /**
     * Initialises JLabels adding borders and controls their background colour based
     * on their position on the grid.
     */
    public void makePanel(){
        paint = true;
        for (int i = 0; i < DEFAULT_SIZE; i++) {
            for (int j = 0; j < DEFAULT_SIZE; j++) {
                cells[i][j] = new JLabel();
                cells[i][j].setOpaque(true);
                cells[i][j].setBorder(border);

                paintCell(i, j);
                if ((j + 1) % 3 == 0) {
                    paint = !paint;
                }


                this.add(cells[i][j]);
            }

            paint = (i + 1) / 3 != 1;
            /*
            if ((i + 1) / 3 == 1) {
                paint = false;
            } else {
                paint = true;
            } */
        }
    }

    /**
     * Sets the background colour of a JLabel based on it's coordinates
     * in the grid
     * @param x the X position of the JLabel
     * @param y the Y position of the JLabel
     */
    private void paintCell(int x, int y) {
        if (paint) {
            cells[x][y].setBackground(new Color(200, 200, 200));
        }
    }

    /**
     * Sets the text to be displayed on each label and sets size of the text based on
     * number of characters to display. Small text size if more than 1 character.
     *
     * @param x the x position of the JLabel
     * @param y the y position of the JLabel
     * @param text the text to be displayed on the label
     */
    public void setTextOutput(int x, int y, String text){
        if(text.length() > 1){
            cells[x][y].setFont(small);
        }
        else{
            cells[x][y].setFont(big);
            cells[x][y].setHorizontalAlignment(JTextField.CENTER);
        }
        cells[x][y].setText(text);

    }

}
