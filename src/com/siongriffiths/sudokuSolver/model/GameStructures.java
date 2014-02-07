package com.siongriffiths.sudokuSolver.model;


import java.util.Vector;

/**
 * The GameStructures class is used to represent the various cell-containing structures in the sudoku grid
 * providing functionality to add cell to a structure and return structures.
 *
 * GameStructures is declared abstract for the sole reason that it cannot be directly instantiated.
 *
 *@author Sion Griffiths / sig2@aber.ac.uk
 */
public abstract class GameStructures {

    /**
     * A Vector to hold the Cell instances which are members of this GameStructure
     */
    protected Vector<Cell> members;

    /**
     * Constructs a GameStructure and initialises a new member list
     */
    protected GameStructures(){

        members = new Vector<Cell>();
    }

    /**
     * Adds a Cell to the member list
     * @param c the Cell
     */
    public void addMembers(Cell c){

        members.add(c);
    }

    /**
     * Returns a list of Cells that are members of this structure
     * @return the list of Cells that are members of this structure
     */
    public Vector<Cell> getMembers(){
        return members;
    }











}
