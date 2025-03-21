//package JGUID.Util; 
import java.io.*; 
import javax.swing.JOptionPane;
import java.net.*;

/**
 * Class for reading writing to and from file
 * and other filehandling issues such as OS independent Path.
 */ 
 import java.nio.file.*;
 
 
public class GuiDFileHandler
{
  
   /**
    * Reads a file from disk sequentially and returns it as a String.
    */
    
   public static String readFromFile( String sFilename )
   {
      System.out.println("DEBUG: Running GuiDFileHandler-readFromFile() filename");     
      FileReader file = null;

     sFilename = sFilename.trim();
     String toReturn="";
     
      try
      {                  
        //Doesn't work - static
        //URL fileUrl = getClass().getResource("/"+sFilename);
        
        InputStream test = GuiDFileHandler.class.getResourceAsStream("/"+sFilename);
        
        
        /*URL fileUrl = GuiDFileHandler.class.getResource("/"+sFilename);
        
           
        
        
        URI uri = fileUrl.toURI();
        System.out.println("URI from URL: " + uri);
         
        if(Files.exists(Paths.get(uri)))
        {
            System.out.println("EXISTS");
            JOptionPane.showMessageDialog( null, sFilename+ "EXISTS!" );
            
            String actual = Files.readString(Paths.get(uri));
            System.out.println(actual);
        }
        else
        {
            System.out.println("NOPE NOT THERE");
            JOptionPane.showMessageDialog( null, sFilename+ "does not exist" );
        }
        
        InputStreamReader isr = new InputStreamReader(fileUrl.openStream());        

        JOptionPane.showMessageDialog( null, "The filename is-"+sFilename );
        */
        //toReturn = read(isr);
        toReturn = read(test);
        System.out.println("***");
        System.out.println(toReturn);
        System.out.println("***");
    }
      catch( Exception fnfe  )
      {
          JOptionPane.showMessageDialog( null, sFilename + " WITH STRING PARAMETER could not be found ","File Not Found",JOptionPane.ERROR_MESSAGE);
      }
      
      
      return toReturn;  
   }
   
   private static String read(InputStreamReader isr)
    {
      System.out.println("DEBUG: Running GuiDFileHandler-read");    
      
      String sLine = "";
      String str = "";    
      boolean eof = false;     

       if( isr != null )
      {
         try 
         {
            BufferedReader buff = new BufferedReader( isr );
            int i = 0;          

            while( !eof )
            {             
               sLine = buff.readLine();             

               if( sLine == null )
               {
                  eof = true;
               }
               else
               {
                  str = str + "\n" + sLine;
               }
            }
         }
         catch( IOException ioe )
         {
            JOptionPane.showMessageDialog( null,ioe,"Error",JOptionPane.ERROR_MESSAGE);
         }     
      }
      
      System.out.println("DEBUG END OF GuiDFileHandler-read"); 
      System.out.println("Contents"); 
      System.out.println(str); 
      System.out.println("*******"); 
      return str;

    }
   
    private static String read(InputStream is)
    {
      System.out.println("DEBUG: Running GuiDFileHandler-read");    
      
      String sLine = "";
      String str = "";    
      boolean eof = false;     

       if( is != null )
      {
         try 
         {
            BufferedReader buff = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int i = 0;          

            while( !eof )
            {             
               sLine = buff.readLine();             

               if( sLine == null )
               {
                  eof = true;
               }
               else
               {
                  str = str + "\n" + sLine;
               }
            }
         }
         catch( IOException ioe )
         {
            JOptionPane.showMessageDialog( null,ioe,"Error",JOptionPane.ERROR_MESSAGE);
         }     
      }
      
      System.out.println("DEBUG END OF GuiDFileHandler-read"); 
      System.out.println("Contents"); 
      System.out.println(str); 
      System.out.println("*******"); 
      return str;

    }
   
   
   /*private static String read(FileReader file)
    {
      System.out.println("DEBUG: Running GuiDFileHandler-read");    
      
      String sLine = "";
      String str = "";    
      boolean eof = false;     

       if( file != null )
      {
         try 
         {
            BufferedReader buff = new BufferedReader( file );
            int i = 0;          

            while( !eof )
            {             
               sLine = buff.readLine();             

               if( sLine == null )
               {
                  eof = true;
               }
               else
               {
                  str = str + "\n" + sLine;
               }
            }
         }
         catch( IOException ioe )
         {
            JOptionPane.showMessageDialog( null,ioe,"Error",JOptionPane.ERROR_MESSAGE);
         }     
      }
      return str;

    }*/
    
    public static String readFromFile(File f)
    {
      System.out.println("DEBUG: Running GuiDFileHandler-readFromFile() File");      
       
       FileReader file = null;

      try
      {                  
         file = new FileReader(f);
      }
      catch( FileNotFoundException fnfe  )
      {
          JOptionPane.showMessageDialog( null, f.getName() + " could not be found ","File Not Found",JOptionPane.ERROR_MESSAGE);
      }
        
       return read(file);
    }

   
    
    
    
    /**
    * Writes the String s to file filename.
    
    */ 
   public static void writeToFile( File filename, String s )
   {
      System.out.println("DEBUG: Running GuiDFileHandler-writeToFile()");   
      
      try 
      {
         JOptionPane.showMessageDialog( null, "Project Saved: " + filename);
         filename.createNewFile();
      }
      catch( IOException ioe )
      {
         JOptionPane.showMessageDialog( null, 
                                        "Could not create \"" + filename 
                                        + "\" are you sure you have write permission to this path ?",
                                        "Could not create file",
                                        JOptionPane.ERROR_MESSAGE );
        ioe.printStackTrace();
         return;
      }

      try 
      {
         PrintWriter out = new PrintWriter( new FileWriter( filename ) );
         out.print( s );
         out.close();
      }
      catch( Exception e )
      {
          JOptionPane.showMessageDialog( null,"could not Write File :"+e,"File Cannot be Written",JOptionPane.ERROR_MESSAGE);
      }
   }


}


