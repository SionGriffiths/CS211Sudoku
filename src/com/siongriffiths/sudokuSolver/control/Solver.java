package com.siongriffiths.sudokuSolver.control;


import com.siongriffiths.sudokuSolver.model.*;


import java.util.HashSet;
import java.util.Stack;
import java.util.Vector;

/**
 * The Solver class implements algorithms which assist in solving sudoku puzzles based on cartain
 * patterns in the sudoku grid.
 * Implements Runnable to allow algorithms to execute in their own thread.
 *
 *@author Sion Griffiths / sig2@aber.ac.uk
 */
public class Solver implements Runnable{

    /**
     * The Controller instance which allows integration with the control of the program
     */
    private Controller ctrl;
    /**
     * The GameGrid instance which holds all GameStructures and Cells
     */
    private GameGrid g;
    private boolean solveRunning = false;

    /**
     * Constructs a Solver.
     * @param ctrl the Controller
     */
    public Solver(Controller ctrl){
        this.ctrl = ctrl;

    }


    /**
     * Point of entry when a Thread passed this class is started.
     * Allows the Solver to run in its own thread of execution
     *
     */
    @Override
    public void run() {
        solve();
    }

    /**
     *Sequentially calls the algorithms defined in Solver until the puzzle is solved
     * or a solution cannot be found.
     * Passes a GameGrid instance to all algorithms.
     *
     */
    public void solve(){
        solveRunning = true;
        g = ctrl.getGrid();

        //Add delay so that each step is visible.
        while(!isSolved(g)){

            try{
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            if(!checkSolvedCell(g)){
                if(!eliminateCandidates(g)){
                    if(!hiddenSingle(g)){
                        if(!nakedPair(g)){
                            if(!nakedTriples(g)){
                                if(!pointingPair(g)){
                                    ctrl.sendToOutput("PUZZLE NOT SOLVED");

                                    break;
                                }
                            }
                        }
                    }
                }
            }
            ctrl.sendToPanel();
        }
        solveRunning = false;

    }

    /**
     * Checks if a solution has been found by checking if all Cells in the GameGrid have a set value.
     * Outputs a message on success.
     * @param g the GameGrid
     * @return - false if board not solved, true if solved
     */
    public boolean isSolved(GameGrid g){
        for(Row r : g.getRows()){
            for(Cell c : r.getMembers()){
                if((c.getValue().equals(""))){

                    return false;
                }
            }
        }
        ctrl.sendToOutput("SOLVED");
        validateSolution(g);
        return true;
    }

    /**Validates the found solution for a sudoku by ensuring that each structure in the grid
     * has Cells containing all values from 1-9 and no duplicates
     * @param g the GameGrid
     */
    public void validateSolution(GameGrid g){
        int count =0;
        for(GameStructures gs : g.getStructures()){
            HashSet<String> checkValues = new HashSet<String>();
            for(Cell c : gs.getMembers()){
                checkValues.add(c.getValue());

            }

            if(checkValues.size() == 9){
                count ++;
            }
        }
        if(count == 27){
            ctrl.sendToOutput("Solution is valid");
        }
        else{
            System.out.println("Solution INVALID " + count);
        }
    }

    /**
     * Searches through each Cell in every row and sets that Cells value
     * if it only has 1 possible candidate value.
     * @param g the Gamegrid
     * @return true if a Cell value if set, false if not.
     */
    public boolean checkSolvedCell(GameGrid g){
        for(Row r : g.getRows()){
            for(Cell c : r.getMembers()){
                if(isCellSolved(c)){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isCellSolved(Cell c){
        if(c.getCandidates().size() == 1){
            String temp = c.getCandidates().toString();
            temp = temp.replaceAll(",", " ").replaceAll("\\[", "").replaceAll("\\]", "");

            c.setValue(temp);
            c.getCandidates().clear();
            ctrl.sendToOutput("Cell Solved");
            return true;
        }
        return false;
    }

    /**
     * Searches through each GameStructure for a Cell with a set value.
     * Adds those values to a set and removes those values from the other
     * Cells in the same structure.
     * @param g the Gamegrid
     * @return true if a candidate has been removed, false otherwise.
     */
    public boolean eliminateCandidates(GameGrid g){


        for(GameStructures gs : g.getStructures()){
            Vector<String> toEliminate = new Vector<String>();
            for(Cell c : gs.getMembers()){
                if(!c.getValue().equals("")){
                    toEliminate.add(c.getValue());
                }
            }

            for(Cell c : gs.getMembers()){
                for (String s : toEliminate) {
                    if (c.getCandidates().contains(s)) {
                        c.remove(s);

                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Searches each Cell in each structure of the grid for a candidate value that does not
     * appear elsewhere in that structure. If found that unique candidate value is set as the
     * sole candidate value of that Cell
     * @param g the Gamegrid
     * @return true if a Cell meeting constraints is found, false otherwise.
     */
    public boolean hiddenSingle(GameGrid g){
        for(GameStructures gs : g.getStructures()){

            for(int i = 0; i < gs.getMembers().size(); i++){
                Stack<Cell> candidateStack = new Stack<Cell>();

                for(Cell c : gs.getMembers()){
                    if(c.getCandidates().contains(Integer.toString(i+1))){
                        candidateStack.push(c);
                    }
                }

                if(candidateStack.size() == 1){
                    Cell valueSetCell = candidateStack.pop();
                    valueSetCell.getCandidates().clear();
                    valueSetCell.getCandidates().add(Integer.toString(i+1));
                    ctrl.sendToOutput("Hidden Single Found");

                    return true;
                }
            }
        }


        return false;
    }

    /**
     * Searches through each structure for 2 cells with the same 2 candidate values and no others.
     * Removes those candidate values from the other cells in the structure
     * @param g the Gamegrid
     * @return true if a pair is found, false otherwise.
     */
    public boolean nakedPair(GameGrid g){
        boolean found = false;
        for(GameStructures gs : g.getStructures()){

            Vector<Cell> pair = new Vector<Cell>();
            HashSet<String> toRemove = new HashSet<String>();

            for(Cell c : gs.getMembers()){
                if(c.getCandidates().size() == 2){
                    pair.add(c);
                }
            }

            if(pair.size() > 1){
                for(Cell c : pair){
                    for(Cell d : pair){
                        if(c != d){
                            //check candidates are equal and add to set to remove
                            if(c.getCandidates().equals(d.getCandidates())){
                                toRemove.addAll(d.getCandidates());
                                for(Cell e : gs.getMembers()){
                                    if((e != c) && (e != d)){

                                        //remove the candidates of the pair from rest of structure
                                        for(String s : toRemove){
                                            if(e.getCandidates().contains(s)){
                                                e.getCandidates().remove(s);
                                                found = true;

                                            }
                                        }

                                        if(found){
                                            ctrl.sendToOutput("Naked Pair Found");
                                            found = false;
                                            return true;

                                        }

                                    }

                                }
                                toRemove.clear();
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    /**
     * Searches through Row and Column structures for 2 Cells that occupy the same Row/Column and also
     * the same Block. If a candidate value is shared between those 2 Cells and appears no where else
     * within their Block, that candidate value is then removed from the other Cells in the Row/Column
     * @param g the GameGrid
     * @return true if a pair is found, false otherwise.
     */

    public boolean pointingPair(GameGrid g){

        for(GameStructures gs : g.getStructures()){
            if(!(gs instanceof Block)){

                for(Cell c : gs.getMembers()){
                    for(Cell d : gs.getMembers()){
                        if(d != c){
                            //Check that pair occupy the same block
                            if(g.getBlockByCell(d).getMembers().contains(c)){
                                if(c.getCandidates().size() > 1){
                                    if(d.getCandidates().size() > 1){
                                        HashSet<String> toRemove = new HashSet<String>();

                                        for(String s : d.getCandidates()){
                                            if(c.getCandidates().contains(s)){
                                                toRemove.addAll(d.getCandidates());
                                                for(Cell e : g.getBlockByCell(c).getMembers()){
                                                    if(e != c && e != d){
                                                        toRemove.removeAll(e.getCandidates());
                                                    }
                                                }

                                            }
                                        }
                                        if(!toRemove.isEmpty()){
                                            for(Cell f : gs.getMembers()){
                                                if(f.getCandidates().size() > 1){
                                                    if(f != c && f != d){
                                                        for(String s : toRemove){
                                                            if(f.getCandidates().contains(s)){
                                                                f.getCandidates().removeAll(toRemove);
                                                                ctrl.sendToOutput("Pointing Pair Found");
                                                                toRemove.clear();
                                                                return true;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                }

            }
        }
        return false;
    }

    /**
     * Searches each structure for 3 cells, which share a combination of 3 candidate values.
     * If found those candidate values can be removed from the other cells in the structure.
     * @param g the GameGrid
     * @return true if 3 Cells meeting constrains are found, false otherwise.
     */
    public boolean nakedTriples(GameGrid g){

        for(GameStructures gs : g.getStructures()){
            Vector<Cell> tripleCells = new Vector<Cell>();



            for(Cell c : gs.getMembers()){

                if((c.getCandidates().size() > 1) && (c.getCandidates().size() <= 3)){
                    tripleCells.add(c);
                }
            }

            if(tripleCells.size() >= 3){

                HashSet<String> toRemove = new HashSet<String>();
                HashSet<String> temp = new HashSet<String>();
                HashSet<String> temp2;
                Vector<Cell> triple = new Vector<Cell>();

                //Using the fact HashSets only allow unique values - add contents of temp and temp2 together to check if candidates to remove fit constraints
                for(Cell c : tripleCells){
                    temp.clear();
                    temp.addAll(c.getCandidates());
                    for(Cell d : tripleCells){
                        temp2 = new HashSet<String>();
                        if(d != c){
                            temp2.addAll(temp);
                            temp2.addAll(d.getCandidates());
                            if(temp2.size() <= 3){
                                if(!(triple.contains(d))){
                                    triple.add(d);
                                    toRemove.addAll(d.getCandidates());
                                }
                            }
                        }
                    }
                }

                if(triple.size() == 3){
                    if(toRemove.size() ==3){
                        for(Cell c : gs.getMembers()){
                            if(c.getCandidates().size() > 1){
                                if(!(triple.contains(c))){
                                    for(String s : toRemove){
                                        if(c.getCandidates().contains(s)){

                                            c.getCandidates().removeAll(toRemove);
                                            ctrl.sendToOutput("Naked Triple Found");
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }


        return false;
    }



    public boolean isSolveRunning(){
        return solveRunning;
    }

}

