package com.siongriffiths.sudokuSolver.model;

import java.util.Vector;

/**
 *The GameGrid class represents the board and initialises the structures and cells which populate it.
 * Allocating individual Cells to their correct Row, Column and Block.
 *
 *@author Sion Griffiths / sig2@aber.ac.uk
 */
public class GameGrid {

    /**
     * Default size for arrays - 9 is the standard structure length for sudoku
     */
    private static final int DEFAULT_SIZE = 9;

    /**
     * A 2d array of Cell which holds all Cell instances in the grid
     */
    private Cell[][] grid;

    /**
     * A Vector to hold a list of all the GameStructures in the grid
     */
    private Vector<GameStructures> allStructures;

    /**
     * An array to hold all Row structures
     */
    private Row[] rows;

    /**
     * An array to hold all Column structures
     */
    private Column[] columns;

    /**
     * An array to hold all Block structures
     */
    private Block[] blocks;

    /**
     * Constructs the GameGrid. Initialises the Cell array using DEFAUL_SIZE
     * Initialises the structure list
     */
    public GameGrid(){
        grid = new Cell[DEFAULT_SIZE][DEFAULT_SIZE];
        allStructures = new Vector<GameStructures>();
    }

    /**
     * Adds a Cell to the Cell grid
     * @param x the x index
     * @param y the y index
     * @param cell the Cell
     */
    public void addCell(int x, int y, Cell cell){
        grid[x][y] = cell;
    }

    /**
     * Returns the grid of Cells
     * @return the 2d array of Cells
     */
    public Cell[][] getGrid(){
        return grid;
    }

    /**
     * Creates the structures in the grid. Initialises the lists to contain
     * structures of each type and allocates Cells to each one.
     */
    public void makeStructures(){

        rows = new Row[DEFAULT_SIZE];
        columns = new Column[DEFAULT_SIZE];
        blocks = new Block[DEFAULT_SIZE];


        for(int i = 0; i < DEFAULT_SIZE; i ++){
            Row row = new Row();
            Column column = new Column();
            Block block = new Block();

            for(int j = 0; j <  DEFAULT_SIZE; j++){
                row.addMembers(grid[i][j]);
                column.addMembers(grid[j][i]);

            }
            rows[i] = row;
            columns[i] = column;
            blocks[i] = block;
            allStructures.add(rows[i]);
            allStructures.add(columns[i]);
            allStructures.add(blocks[i]);

        }

        int blockNumber = 0;
        int blockCounter = 0;

        for(int i = 0; i < DEFAULT_SIZE; i++){

            if(i % 3 == 0) {
                if(i!= 0){
                    blockCounter +=3;
                    blockNumber = blockCounter;
                }
            }
            for(int j = 0; j < DEFAULT_SIZE; j++){
                if(j % 3 == 0) {
                    if(j!=0){
                        blockNumber += 1;
                    }
                }
                blocks[blockNumber].addMembers(grid[i][j]);
            }
            blockNumber = blockCounter;
        }


    }

    /**
     * Returns the list of Rows
     * @return the list of Rows
     */
    public Row[] getRows(){
        return rows;
    }

    /**
     * Returns the list of Columns
     * @return the list of Columns
     */
    public Column[] getColumns(){
        return columns;
    }

    /**
     * Returns the list of Blocks
     * @return the list of Blocks
     */
    public Block[] getBlocks(){
        return blocks;
    }

    /**
     * Returns the Block that contains a specific Cell
     * @param c the Cell
     * @return the Block which contains the passed Cell
     */
    public Block getBlockByCell(Cell c){
        Block retBlock = null;
        for(int i = 0; i < blocks.length; i++){
            if(blocks[i].getMembers().contains(c)){
                retBlock = blocks[i];
            }
        }
        return retBlock;
    }

    /**
     * Returns a list of all structures in the grid
     * @return a list of all structures in the grid
     */
    public Vector<GameStructures> getStructures(){
        return allStructures;
    }




}
