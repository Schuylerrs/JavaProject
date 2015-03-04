/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package skippigoat.system;

import java.io.File;
import java.util.List;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

/**
 * Encapsulates the process of prompting the user to select a directory or file
 * @author Schuyler
 */
public class Prompt 
{
    private static final DirectoryChooser dChooser;
    private static final FileChooser fChooser;
    
    static
    {
        dChooser = new DirectoryChooser();
        fChooser = new FileChooser();
    }
        
    /**
     * Prompts the user to select a directory
     * 
     * @param pTitle - Title to be displayed on the prompt window
     * @return Users selected directory
     * @throws Exception if the user clicks cancel or enter without choosing a 
     *  directory
     */
    public static File getDir(String pTitle) 
            throws Exception
    {
        dChooser.setTitle(pTitle);
        File dir = dChooser.showDialog(null);
        
        if (null == dir)
        {
            throw new Exception("No Directory Chosen");
        }
        return dir;
    }
    
    /**
     * Prompts the user to select files
     * 
     * @param pTitle - Title to be displayed on the prompt window
     * @return Users selected files
     * @throws Exception if the user clicks cancel or enter without choosing anything
     */
    public static List<File> getFiles(String pTitle) 
            throws Exception
    {
        fChooser.setTitle(pTitle);
        List<File> dir = fChooser.showOpenMultipleDialog(null);
        
        if (null == dir)
        {
            throw new Exception("No Files Chosen");
        }
        return dir;
    }
    
    /**
     * Prompts the user to select a file
     * 
     * @param pTitle - Title to be displayed on the prompt window
     * @return Users selected file
     * @throws Exception if the user clicks cancel or enter without choosing anything
     */
    public static File getFile(String pTitle) 
            throws Exception
    {
        fChooser.setTitle(pTitle);
        File dir = fChooser.showOpenDialog(null);
        
        if (null == dir)
        {
            throw new Exception("No File Chosen");
        }
        return dir;
    }
}
