package skippigoat.system;

import java.io.*;

import java.net.*;

import java.util.zip.*;

/**
 * Gets resources as streams or strings.
 *
 * @author Rick Neff
 */
public class ResourceGetter
{
   /**
    * The path prefix for property-like resources.
    * The leading slash ('/') guarantees that the
    * cResourcesPathPrefix lookup will work regardless
    * of deployment scenario.
    *
    * However, for command-line invocation, the project home
    * directory, or current directory (".") if connected to
    * the project home directory, must be in the CLASSPATH.
    * That is the directory where the "resources" subdirectory
    * is located.
    */
   public static final String cResourcesPathPrefix = "/resources/properties/";

   /**
    * Encapsulates getting a resource given its string basename.
    *
    * @param pName the string basename of the resource.
    *
    * @return an input stream opened on the resource URL.
    */
   public static InputStream getResourceAsStream(String pName)
   {
      return getResourceAsStream(ResourceGetter.class, pName);
   }

   /**
    * Encapsulates getting a resource given its string basename.
    *
    * @param pClass the class of the resource (defaults to this class).
    *
    * @param pName the string basename of the resource.
    *
    * @return an input stream opened on the resource URL.
    */
   public static InputStream getResourceAsStream(Class pClass, String pName)
   {
      InputStream inputStream = null;

      try
      {
         inputStream = new URL(getResource(pClass, pName)).openStream();
      }
      catch (Exception e)
      {
         System.err.println(e);
      }

      return inputStream;
   }

   /**
    * Encapsulates getting a resource given its string basename.
    *
    * @param pName the string basename of the resource.
    *
    * @return a String external form of the resource URL.
    */
   public static String getResource(String pName)
   {
      return getResource(ResourceGetter.class, pName);
   }

   /**
    * Encapsulates getting a resource given its class and its string basename.
    *
    * @param pClass the class of the resource (defaults to this class).
    *
    * @param pName the string basename of the resource.
    *
    * @return a String external form of the resource URL.
    */
   public static String getResource(Class pClass, String pName)
   {
      String externalForm = "Sorry---Resource_Not_Found---" + pName;

      String resourceFilename = (pName.startsWith("/") ? pName :
                                 cResourcesPathPrefix + pName);
      try
      {
         externalForm = 
            pClass.getResource(resourceFilename).toExternalForm();
      }
      catch (Exception e)
      {
      }

      return externalForm;
   }

   /**
    * Extracts a file or files from a URL-retrieved web resource
    * and saves it/them to the local filesystem.
    *
    * @param pSpecification the URL specification.
    * @param pFolder the folder in which to save file(s),
                     which must already exist.
    * @param pFilenames the filenames in the URL resource.
    */
   public static void extractToFile(String pSpecification,
                                    File pFolder,
                                    String ... pFilenames)
   {
      try
      {
         URL url = new URL(pSpecification);
         if (pSpecification.endsWith(".zip"))
         {
            ZipInputStream source = new ZipInputStream(url.openStream());

            for (ZipEntry ze; (ze = source.getNextEntry()) != null;)
            {
               for (int i = 0; i < pFilenames.length; i++)
               {
                  String filename = pFilenames[i];
                  if (ze.getName().equals(filename))
                  {
                     String fileBasename =
                        filename.substring(filename.lastIndexOf('/') + 1);
                     File target = new File(pFolder, fileBasename);
                     readFromSourceAndWriteToTarget(source, target);
                  }
               }
            }
            source.close();
         }
         else if (pSpecification.endsWith(".jar") && pFilenames.length == 1)
         {
            InputStream source = url.openStream();
            File target  = new File(pFolder, pFilenames[0]);
            readFromSourceAndWriteToTarget(source, target);
         }
      }
      catch (Exception e)
      {
         System.out.println(e);
      }
   }

   /**
    * Reads from a source input stream and writes to a target file.
    *
    * @param pSource the source input stream.
    *
    * @param pTarget the target file.
    *
    * @throws Exception if something goes wrong.
    */
   private static void readFromSourceAndWriteToTarget(InputStream pSource,
                                                      File pTarget)
      throws Exception
   {
      RandomAccessFile targetFile = new RandomAccessFile(pTarget, "rw");
      byte[] bytes = new byte[0x80000];
      int numRead;

      while ((numRead = pSource.read(bytes)) != -1)
      {
         targetFile.write(bytes, 0, numRead);
      }
      targetFile.close();
   }
}
