/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package skippigoat.service;

import com.echonest.api.v4.EchoNestAPI;
import com.echonest.api.v4.Track;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
        
/**
 *
 * @author Schuyler
 */
// Library found here: http://repo1.maven.org/maven2/org/jaudiotagger/2.0.3/jaudiotagger-2.0.3.jar
// Documentation here: http://www.jthink.net/jaudiotagger/index.jsp
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import skippigoat.system.Settings;

/**
 * Reads, writes, and looks up the ID3 metadata related to a song
 * @author Schuyler
 */
public class SongFile 
{
    private static final EchoNestAPI echoNest;
    private String mPath;
    private String mTitle;
    private String mArtist;
    private String mAlbum;
    private String mOldPath;
    private boolean mHasID3;
    private boolean mIsUpdated;
    
    static
    {
        echoNest = new EchoNestAPI("VBGPB0PKVHZ22PEAZ");
    }
    
    /**
     * Gets the path to the file the SongFile represents
     * @return File's path
     */
    public String getmPath() 
    {
        return mPath;
    }

    /**
     * Gets the song's title
     * @return Song title
     */
    public String getmTitle() 
    {
        return mTitle;
    }

    /**
     * Gets the artist name for the song
     * @return Artist name
     */
    public String getmArtist() 
    {
        return mArtist;
    }

    /**
     * Gets the album name for the song
     * @return Album name
     */
    public String getmAlbum() 
    {
        return mAlbum;
    }

    /**
     * Gets the path to where the file was before the previous move
     * @return
     */
    public String getmOldPath() 
    {
        return mOldPath;
    }

    /**
     * Checks if the SongFile has metadata for the song
     * @return True if it does False if not
     */
    public boolean hasID3() 
    {
        return mHasID3;
    }
    
    /**
     *
     * @param mTitle
     */
    public void setmTitle(String mTitle) 
    {
        this.mTitle = mTitle;
    }

    public void setmArtist(String mArtist) 
    {
        this.mArtist = mArtist;
    }

    public void setmAlbum(String mAlbum) 
    {
        this.mAlbum = mAlbum;
    }
    
    @Override
    public String toString()
    {
        return mPath.substring(mPath.lastIndexOf(System.getProperty("file.separator")) + 1);
    }
    
    public SongFile(SongFile pSong)
    {
        mPath = pSong.mPath;
        mTitle = pSong.mTitle;
        mArtist = pSong.mArtist;
        mAlbum = pSong.mAlbum;
        mOldPath = pSong.mOldPath;
        mHasID3 = pSong.mHasID3;
        mIsUpdated = pSong.mIsUpdated;
    }
            
    
    /**
     * Builds a SongFile based off of a file
     * Checks the file for ID3 data but does not search online for the data
     * @param pPath Where the file is located
     */
    public SongFile(String pPath)
    {
        mPath = pPath;
        mOldPath = pPath;
        mIsUpdated = false;
        
        if (readData())
        {
            System.out.println("Data Read From File");
            mHasID3 = true;
        }
        else 
        {
            System.out.println("Failed Read");
            mHasID3 = false;
        }
    }

    /**
     * Searches online for ID3 data for the song
     * @return Whether or not the search was successful
     */
    public boolean searchSongData()
    {
        try
        {
            boolean gotID3;
            Track returnSong;

            File searchSong = new File(mPath);
            returnSong = echoNest.uploadTrack(searchSong, true);

            String tempTitle;
            String tempArtist;
            String tempAlbum;
            
            tempTitle = returnSong.getTitle();
            tempArtist = returnSong.getArtistName();
            tempAlbum = returnSong.getReleaseName();
            
            gotID3 = true;
            
            if (gotID3)
            {
                if (tempTitle.compareTo("") != 0)
                {
                    mTitle = tempTitle;
                }
                
                if (tempTitle.compareTo("") != 0)
                {
                    mAlbum = tempAlbum;
                }
                
                if (tempTitle.compareTo("") != 0)
                {
                    mArtist = tempArtist;
                }
            }

            mHasID3 = gotID3 || mHasID3;
            return gotID3;
        }
        catch(Exception e)
        {
            System.out.println(e);
            return false;
        }
    }
    
    /**
     * Looks at the file for ID3 data
     * @return Whether or not the search was successful
     */
    private boolean readData() 
    {
        boolean gotID3;

        String tempTitle;
        String tempArtist;
        String tempAlbum;
    
        try 
        {
            AudioFile f = AudioFileIO.read(new File(mPath));
            Tag tag = f.getTag();
            
            tempAlbum = tag.getFirst(FieldKey.ALBUM);
            tempTitle = tag.getFirst(FieldKey.TITLE);
            tempArtist = tag.getFirst(FieldKey.ARTIST);
        } 
        catch (Exception ex) 
        {
            return false;
        }
        
        gotID3 = true;

        if (gotID3)
        {
            if (tempTitle.compareTo("") != 0)
            {
                mTitle = tempTitle;
                gotID3 = true;
            }
            else
            {
                gotID3 = false;
            }

            if (tempTitle.compareTo("") != 0)
            {
                mAlbum = tempAlbum;
                gotID3 = true;
            }
            else
            {
                gotID3 = false;
            }
            
            if (tempTitle.compareTo("") != 0)
            {
                mArtist = tempArtist;
                gotID3 = true;
            }
            else
            {
                gotID3 = false;
            }
        }

        mHasID3 = gotID3 || mHasID3;
        return gotID3;
    }
    
    /**
     * Writes the ID3 tag data to the file
     * @param pForce - Force the function to write the data regardless of 
     * whether or not new ID3 data is present
     */
    public void writeData(boolean pForce)
    {
        if (mHasID3 == false)
        {
            return;
        }
        
        if (mIsUpdated || pForce)
        {
            try 
            {
                File file = new File(mPath);
                AudioFile f = AudioFileIO.read(file);
                Tag tag = f.getTag();
                tag.setField(FieldKey.ARTIST,mArtist);
                tag.setField(FieldKey.TITLE,mTitle);
                tag.setField(FieldKey.ALBUM,mAlbum);
                AudioFileIO.write(f);
            } 
            catch (FileNotFoundException ex) 
            {
                System.out.println("No File Found At " + mPath);
            } 
            catch (IOException ex) 
            {
                System.out.println("Error when writting to file: " + mPath);
            } 
            catch (CannotReadException ex) 
            {
                Logger.getLogger(SongFile.class.getName()).log(Level.SEVERE, null, ex);
            } 
            catch (TagException ex) 
            {
                Logger.getLogger(SongFile.class.getName()).log(Level.SEVERE, null, ex);
            } 
            catch (ReadOnlyFileException ex) 
            {
                Logger.getLogger(SongFile.class.getName()).log(Level.SEVERE, null, ex);
            } 
            catch (InvalidAudioFrameException ex) 
            {
                Logger.getLogger(SongFile.class.getName()).log(Level.SEVERE, null, ex);
            } 
            catch (CannotWriteException ex) 
            {
                Logger.getLogger(SongFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Renames the file based on given input
     * @param newName - The new name for the file (Without the extension)
     */
    public void rename(String newName)
    {    
        String newPath = mPath.substring(0,mPath.lastIndexOf(System.getProperty("file.separator")) + 1);
        String ext = mPath.substring(mPath.lastIndexOf("."));
        File file = new File(mPath);
        
        newPath += newName + ext;
        System.out.println("Attempting to rename: \n" + mPath + " to " + newPath);
        File file2 = new File(newPath);         
        if (!file.exists())
        {
            System.out.println("File doesn't exist");
        }
                    
        if (file2.exists()) 
        {
            System.out.println("A file already exists with this name");
        }
        
        if (file.renameTo(file2)) 
        {
            System.out.println("Success!");
            mOldPath = mPath;
            mPath = newPath;
        }
        else
        {
            System.out.println("Failed");
        }
    }
    
    /**
     * Moves the song to a given location if possible
     * TODO: Make this work
     * @param pPath - Where to move the song to
     * @return Whether or not the move was successful
     */
    public boolean move(String pPath)
    {
        return true;
    }

    /**
     * Copies data from passed in SongFile
     * @param pCopy - SongFile to copy
     */
    public void copy(SongFile pCopy)
    {
        this.mAlbum = pCopy.mAlbum;
        this.mArtist = pCopy.mArtist;
        this.mTitle = pCopy.mTitle;
        this.mHasID3 = pCopy.mHasID3;
        this.mIsUpdated = pCopy.mIsUpdated;
        this.mPath = pCopy.mPath;
        this.mOldPath = pCopy.mOldPath;
    }
    
    /**
     * Formats the name based on the metadata and the names
     * 
     * @return Suggested name based on metadata
     */
    public String getName()
    {
       String struct = Settings.getmFileNameStructure();
       String name = "";
       for (int i = 0; i < struct.length(); i++)
       {
          switch (struct.substring(i,i+1))
          {
             case "a": name += mArtist;
                break;
             case "A": name += mArtist.substring(0,1);
                break;
             case "s": name += mTitle;
                     break;
             case "S": name += mTitle.substring(0,1);
                break;
             case "l": name += mAlbum;
                break;
             case "L": name += mAlbum.substring(0,1);
                break;
             default:
                break;
          }
          name+="-";
       }
       name = name.substring(0,name.length()-1);
       return name;
    }
}
