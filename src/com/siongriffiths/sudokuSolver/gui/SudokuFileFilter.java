package com.siongriffiths.sudokuSolver.gui;

import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * The SudokuFileFilter is an implementation of FileFilter specifically for the sudoku solving program.
 * It's function is to constrain file choices to .sud files.
 *@author Sion Griffiths / sig2@aber.ac.uk
 */

public class SudokuFileFilter extends FileFilter {

    /**
     * Returns true if the file meets the file extension constraints
     * @param f the file
     * @return if file meets constraints, false otherwise
     */
    @Override
    public boolean accept(File f) {

      String path = f.getAbsolutePath().toLowerCase();

      if(f.isDirectory()){
          return true;
      }
      if(path.endsWith(".sud")){
          return true;
      }

       return false;
    }

    /**
     * Returns a description of allowed files
     * @return a description of allowed files
     */
    @Override
    public String getDescription() {
        return ".sud files";
    }
}
