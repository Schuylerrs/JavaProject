import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.nio.file.FileVisitResult.*;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes; 

public class PrintFiles
   extends SimpleFileVisitor<Path>
{
   public List mList = new ArrayList();
   public FileVisitResult visitFile(Path file, BasicFileAttributes attr)
   {
      if (mList.contains(file.toString().substring(file.toString().lastIndexOf('.'))))
      {
         System.out.println(file.toString().substring(file.toString().lastIndexOf('/') + 1,file.toString().length()));
         File aFile = new File(file.toString());
         File dir = new File("/home/sto11038/cs246/test/b");
         dir.mkdir();
         aFile.renameTo(new File("/home/sto11038/cs246/test/b/" + file.toString().substring((file.toString().lastIndexOf('/') + 1),file.toString().length())));
      }
      else
      {
         System.out.println(file.toString() + " is not an mp3");
      }
      return FileVisitResult.CONTINUE;
   }


   public static void main(String[] args)
   {
      try
      {
         Path startingDir = Paths.get("/home/sto11038/cs246/test");
         PrintFiles pf = new PrintFiles();
         pf.mList.add(".mp3");
         pf.mList.add(".mp4");
         Files.walkFileTree(startingDir, pf);
      }
      catch (Exception e)
      {
         System.out.println("Why am I here?");
      }
   }
}
   