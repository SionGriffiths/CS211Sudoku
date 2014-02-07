package com.siongriffiths.sudokuSolver.model;

import java.util.HashSet;

/**
 * The Cell class represents a single 'cell' in a sudoku puzzle.
 * It holds a value and a set of candidate values with methods to
 * facilitate setting and returning of these values.
 *
 * @author Sion Griffiths / sig2@aber.ac.uk
 */
public class Cell {

    /** String to hold the value of a Cell initialised to empty**/
    private String value = "";
    /** A set to hold candidate values for a Cell**/
    private HashSet<String> candidates;

    /**Costructs a cell and initialises an empty candidate set*/
    public Cell(){

        candidates = new HashSet<String>();
    }

    /**Populates the candidate set with Strings representing
     * the values 1 to 9
     */
    public void populateCandidates(){
        candidates.add("1");
        candidates.add("2");
        candidates.add("3");
        candidates.add("4");
        candidates.add("5");
        candidates.add("6");
        candidates.add("7");
        candidates.add("8");
        candidates.add("9");
    }

    /**
     * Returns the candidate set for this Cell
     *
     * @return the candidate set for this Cell
     */
    public HashSet<String> getCandidates(){
        return candidates;
    }

    /**
     * Removes an entry from the candidate set
     *
     * @param s the String representing the value to be removed
     */
    public void remove(String s){
        candidates.remove(s);
    }

    /**
     * Sets the value of this Cell
     *
     * @param value the value to be set for this Cell
     */
    public void setValue(String value){
        this.value = value;
    }

    /**
     * Returns the value of this Cell
     * @return the value of this Cell
     */
    public String getValue(){
        return value;
    }




}
