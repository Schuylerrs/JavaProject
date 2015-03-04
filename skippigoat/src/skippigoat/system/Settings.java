package skippigoat.system;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * This is a class for storing the settings for the end user.
 * It is used this was so that it can be serialized and stored as a file.
 * @author Schuyler
 */
public class Settings 
{   
    /**
    * The path to the main music directory
    */
    private static String mHome;
    
    /**
    * String representing how the file names are formated 
    */
    private static String mFileNameStructure;
    
    /**
    * String representing how the folders are structured
    */
    private static String mFolderStructure;
    
   /**
    * File extensions available to choose from
    */
    private static final List<String> mExtensions;
    
    /**
    * Does the program run a search when it first starts up
    */
    private static boolean mPullOnStart;
    
    /**
    * Does the program automatically rename the songs as they are pulled in
    */
    private static boolean mFormatOnPull;
    
    static
    {
        mExtensions = new ArrayList<>();
    }
    
    /**
    * Loads the file from a set location based on the OS
    */
    public static void Load()
    {
        Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, "Loading Settings");
        String[] exts;
        Preferences prefs = Preferences.userRoot();
        mPullOnStart = prefs.getBoolean("PullOnStart", false);
        mFormatOnPull = prefs.getBoolean("FormatOnPull", false);
        mHome = prefs.get("HomePath", System.getProperty("User.home") + System.getProperty("file.separator") + "Music");
        mFolderStructure = prefs.get("FolderStruct","al");
        mFileNameStructure = prefs.get("FileNameStruct", "as");
        exts = (prefs.get("Extensions", "mp3:mp4:flac:wav")).split(":");
        //exts = ("mp3:mp4:flac:wav").split(":");
        for(String ext : exts)
        {
            System.out.println(ext);
            mExtensions.add(ext);
        }
    }
    
    /**
    * Saves the file to a set location based on the OS
    */
    public static void Save()
    {
        String exts = "";
        Preferences prefs = Preferences.userRoot();
        prefs.putBoolean("PullOnStart", mPullOnStart);
        prefs.putBoolean("FormatOnPull", mFormatOnPull);
        prefs.put("HomePath", mHome);
        for (String ext : mExtensions)
        {
            exts += ":" + ext;
        }
        prefs.put("Extensions", exts);
    }
    
    /**
    * Returns a string containing the path to the main music directory
    * @return mHome
    */
    public static String getHome()
    {
        return mHome;
    }
    
    /**
    * Sets the "Home" path
    * @param pHome Path to the new home directory
    */
    public static void setHome(String pHome)
    {
        Path tempPath = Paths.get(pHome);
        // Make sure it is a valid path
        if (Files.exists(tempPath)) 
        {
            mHome = pHome;
        }
        else
        {
            System.out.println("ERROR: Invalid Home Path!");
        }
    }
    
    /**
    * Returns the type of file extensions that the user has to choose from
    * @return List<String> Extensions
    */
    public static List<String> getExtensions()
    {
        return mExtensions;
    }

    /**
     * Removes 
     * @param ext
     */
    public static void removeExtension(String ext)
    {
        mExtensions.remove(ext);
    }
    
    /**
    * Adds a file extensions to the list of file extensions
    * @param pExtension - List of extensions to search for
    */
    public static void addExtension(String pExtension)
    {
        if (mExtensions.contains(pExtension) == false)
            mExtensions.add(pExtension);
    }

    /**
     * Sets the format for how song files are auto-formated
     * @param mFileNameStructure - String of characters representing different 
     * metadata to use for file name
     */
    public static void setmFileNameStructure(String mFileNameStructure) 
    {
        Settings.mFileNameStructure = mFileNameStructure;
    }
    
    /**
     * Gets the format for how song files are auto-formated
     * @return mFileNameStructure
     */
    public static String getmFileNameStructure() 
    {
        return mFileNameStructure;
    }

    /**
     * Sets the key which defines how folders are set up
     * @param mFolderStructure - How the folders are structured when files are sorted
     */
    public static void setmFolderStructure(String mFolderStructure) 
    {
        Settings.mFolderStructure = mFolderStructure;
    }
    
    /**
     * Gets the key which defines how folders are set up
     * @return mFolderStructure
     */
    public static String getmFolderStructure() 
    {
        return mFolderStructure;
    }
    
    /**
    * Tells the program whether or not to pull from the default directories 
    * each time the program starts up
    * @return mPullOnStart
    */
    public static boolean doesPullOnStart()
    {
        return mPullOnStart;
    }
    
    /**
    * Sets whether or not the program should pull each time it starts 
    * @param pPullOnStart
    */
    public static void setPullOnStart(boolean pPullOnStart)
    {
        mPullOnStart = pPullOnStart;
    }
    
    /**
    * Tells the program whether or not format/style the filenames of the songs
    * automatically when they are pulled in
    * @return Whether or not the program will format songs on pull
    */
    public static boolean doesFormatOnPull()
    {
        return mFormatOnPull;
    }
    
    /**
    * Sets whether or not the program should format the filenames when they are
    * pulled in
    * @param pFormatOnPull - Whether or not the program will format songs on pull
    */    
    public static void setFormatOnPull(boolean pFormatOnPull)
    {
        mFormatOnPull = pFormatOnPull;
    }
}
