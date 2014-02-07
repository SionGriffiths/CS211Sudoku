package com.siongriffiths.sudokuSolver.control;

import javax.swing.*;
import java.io.*;

/**
 * The FileParser class facilitates the reading and parsing of Sudoku files
 * @author Sion Griffiths / sig2@aber.ac.uk
 */
public class FileParser {

    /**The File instance to hold a reference to a file */
    private File file;


    /**A Buffered reader to facilitate reading of the file*/
    private BufferedReader bRead;

    /**A String to hold each line of the file, initialised to empty*/
    private String line ="";

    /**A 2 dimensional array of char, 9x9 in size, to hold the charatcers
     * read in from a file in a format helpful for representing a
     * sudoku puzzle
     */
    private char[][] output = new char[9][9];

    /**Constructs a FileParser*/
    public FileParser(){

    }

    /**
     * Instantiates a new File instance passing in a file name
     * @param fileName the file name
     */
    public void setFile(String  fileName){
        file = new File(fileName);
    }

    /**Reads the current file line by line into a string.
     * The string is split into constituent characters which are added
     * to the output array. Conducts checks to ensure only valid files are loaded.
     */
    public boolean parseFile(){
    boolean shortLine = false;
        boolean longLine = false;
        try{
            bRead = new BufferedReader(new FileReader(file));
            for(int i = 0; i < 9; i++){
                line = bRead.readLine();


                if(line.length() < 9){
                    for(i = line.length(); i < 9; i++){
                        line+=" ";
                    }
                    shortLine = true;
                }
                if(line.length() > 9){
                    longLine = true;
                }

                if(isValid(line)){
                    for(int j = 0; j < 9; j++){

                        output[i][j]=line.charAt(j);
                    }
                }else{

                   return false;
                }
            }
            bRead.close();

        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        if(shortLine){
            JOptionPane.showMessageDialog(null, "Line in file was short. May not solve.");
        }
        if(longLine){
            JOptionPane.showMessageDialog(null, "Line in file was long. May not solve.");
        }

        return true;
    }

    /**
     * Tests if a String is composed of only numerics and spaces
     * @param line the String to be tested
     * @return true if String meets constraints, false otherwise
     */
    public boolean isValid(String line){
       return (line.matches("[0-9 ]+"));
    }

    /**Returns the output array
     *
     * @return the output array
     */
    public char[][] getValues(){
        return output;
    }
}


