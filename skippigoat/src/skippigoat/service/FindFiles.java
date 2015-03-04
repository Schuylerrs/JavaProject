package skippigoat.service;

import java.util.List;
import java.util.ArrayList;
import java.nio.file.FileVisitResult.*;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;


/********************************************************
* Find files looks at every file in a file tree and checks
* if they are of one of the types in mList
*******************************************************/
public class FindFiles
   extends SimpleFileVisitor<Path>
{
   private List fileTypes = new ArrayList();
   private List files = new ArrayList();

   public FindFiles()
   {
      fileTypes = null;
      files = null;
   }

   public FindFiles(List pFileTypes)
   {
      fileTypes = pFileTypes;
   }
   
   /*******************************************************************
    * VisitFile visits each file in the file tree and outputs if they
    * match one of the file types in mList
    *
    * @param file this is the file that is being checked
    * @param attr this stores the basic attributes of file
    * @return this is the result which is always to continue until the
    *         end of the file tree
    ******************************************************************/
   @Override
   public FileVisitResult visitFile(Path file, BasicFileAttributes attr)
   {
      if (fileTypes.contains(file.toString().substring(file.toString().lastIndexOf('.') + 1)))
      {
         files.add(file.toString());
         System.out.println(file.toString());
      }
      else
      {
         System.out.println(file.toString() + " is not a music file");
      }
      return FileVisitResult.CONTINUE;
   }

   /*******************************************************************
    * SetFileTypes is a basic setter function for fileTypes
    *
    * @param pList is the list that is copied to fileTypes
    ******************************************************************/
   public void setFileTypes(List pList)
   {
      fileTypes = pList;
   }

   /*******************************************************************
    * GetFileTypes returns fileTypes
    * @return returns fileTypes
    ******************************************************************/
   public List getFileTypes()
   {
      return fileTypes;
   }

   public void setFiles(List pList)
   {
      files = pList;
   }

    /**
     *
     * @return
     */
    public List getFiles() 
   {
       return files;
   }
}