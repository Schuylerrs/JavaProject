package skippigoat.system;

import java.io.*;

import org.testng.annotations.*;

/**
 * Tests Shell functionality.
 *
 * @author Brother Neff
 */
public class TestShell
{
   /**
    * Holds a Shell object.
    */
   private Shell mShell;

   /**
    * Constructs a TestShell instance.
    */
   public TestShell()
   {
      // intentionally left empty
   }

   /**
    * Sets up each test by creating a new Shell object.
    */
   @BeforeMethod
   public void setUp()
   {
      mShell = new Shell(false);
   }

   /**
    * Tests that invoking the simplest shell command works.
    */
   @Test(timeOut = 1000)
   public void echoCommand()
   {
      ByteArrayOutputStream bStream = new ByteArrayOutputStream();
      mShell.setOutputStream(bStream);
      String[] echoCommand = {"echo", "does this work?"};
      assert (mShell.command(echoCommand) == 0);      
      String output = bStream.toString();
      assert (output.equals(echoCommand[1] +
                            System.getProperty("line.separator")));
   }

   /**
    * Tests that invoking a more complicated shell command works.
    */
   @Test(timeOut = 1000)
   public void invokingShellCommand()
   {
      ByteArrayOutputStream bStream = new ByteArrayOutputStream();
      mShell.setOutputStream(bStream);
      mShell.setInput("2^340%341\nquit\n");
      String[] bcCommand = {"bc"};
      assert (mShell.command(bcCommand) == 0);
      String output = bStream.toString();
      assert (output.equals("1" + System.getProperty("line.separator")));
   }
}
