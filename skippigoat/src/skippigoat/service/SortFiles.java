package skippigoat.service;

import java.util.List;
import java.io.File;
import java.nio.file.FileVisitResult.*;
import skippigoat.system.Settings;

public class SortFiles
{
   public static void sort(List<SongFile> files)
   {
      File dup = new File(Settings.getHome() + System.getProperty("file.separator") + "duplicates");
      dup.mkdirs();
      for (SongFile file: files)
      {
         String folders = getStructure(file);
         File aFile = new File(file.getmPath());
         File dir = new File(Settings.getHome() + folders);
         dir.mkdirs();
         String name = file.getmPath().substring(file.getmPath().lastIndexOf(System.getProperty("file.separator")) + 1);
         String newName = dir + System.getProperty("file.separator") + name;
         File test = new File(newName);
         System.out.println(newName);
         int i = 0;
         if (newName.compareTo(file.getmPath()) != 0)
         {
            while (test.exists())
            {
               newName = (Settings.getHome() + System.getProperty("file.separator") + "duplicates" + System.getProperty("file.separator") + name).replaceAll(".", "" + i + ".");
               test = new File(newName);
               i++;
            }
            
            if (aFile.renameTo(test))
            {
                System.out.println("Successfully moved");
            }
            else
            {
                System.out.println("Failed moved");
            }
         }
      }
   }

   public static String getStructure(SongFile file)
   {
      String struct = Settings.getmFolderStructure();
      String folders = "";
      for (int i = 0; i < struct.length(); i++)
      {
         folders += System.getProperty("file.separator");
         switch (struct.substring(i,i+1))
         {
            case "a": folders+=file.getmArtist();
               break;
            case "A": folders+=file.getmArtist().substring(0,1);
               break;
            case "s": folders+=file.getmTitle();
               break;
            case "S": folders+=file.getmTitle().substring(0,1);
               break;
            case "l": folders+=file.getmAlbum();
               break;
            case "L": folders+=file.getmAlbum().substring(0,1);
               break;
            default:
               break;
         }
      }
      return folders;
   }
}