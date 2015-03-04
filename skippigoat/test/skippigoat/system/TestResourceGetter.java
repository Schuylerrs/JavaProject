package skippigoat.system;

import java.io.*;

import org.testng.annotations.*;

/**
 * Tests ResourceGetter functionality.
 * In the process, gets some needed resources!
 *
 * @author Rick Neff
 */
public class TestResourceGetter
{
   /**
    * Holds a Shell object.
    */
   private Shell mShell;

   /**
    * Holds a File folder object.
    */
   private File mFolder;

   /**
    * Constructs a TestResourceGetter instance.
    */
   public TestResourceGetter()
   {
      // intentionally left empty
   }

   /**
    * Sets up each test by creating a new Shell object.
    */
   @BeforeMethod
   public void setUp()
   {
      mShell = new Shell(true);
      String folderName = System.getenv("PROJECTHOME") + "/lib";
      mFolder = new File(folderName);
      try
      {
         mShell.mkdir(mFolder);
      }
      catch (Exception e)
      {
         System.out.println(e);
      }
   }

   /**
    * Tests getting the first dependency resources.
    */
   @Test(timeOut = 7000)
   public void gettingFirstDependency()
   {
      System.out.print("Getting first dependency...");
      String spec = "     http://repo1.maven.org/maven2/org/jaudiotagger/2.0.3/";
      String filename = "jaudiotagger-2.0.3.jar";
      File file = new File(mFolder, filename);
      if (file.exists())
      {
         System.out.print("already " );
      }
      else
      {
         ResourceGetter.extractToFile(spec + filename, mFolder, filename);
      }
      assert (file.exists());
      System.out.println("done.");
   }

   /**
    * Tests getting the second dependency resources.
    */
   @Test(timeOut = 12000)
   public void gettingSecondDependencies()
   {
      System.out.print("Getting second dependencies...");
      String spec = "https://jen-api.googlecode.com/files/jEN-4.x.r.zip";
      String fileName = "jEN-4.x.r/lib/";
      String fileName1 = fileName + "jEN.jar";
      String fileName2 = fileName + "json_simple-1.1.jar";

      String file1BaseName = fileName1.substring(fileName1.lastIndexOf('/') + 1);
      String file2BaseName = fileName2.substring(fileName2.lastIndexOf('/') + 1);
      File file1 = new File(mFolder, file1BaseName);
      File file2 = new File(mFolder, file2BaseName);
      if (file2.exists())
      {
         System.out.print("already " );
      }
      else
      {
         ResourceGetter.extractToFile(spec, mFolder, fileName1, fileName2);
      }
      assert (file1.exists());
      assert (file2.exists());
      System.out.println("done.");
   }
}

