package net.sf.memoranda.ui;

import java.io.*;
import java.nio.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JOptionPane;

import net.sf.memoranda.EventsManager;
import net.sf.memoranda.util.CurrentStorage;
import net.sf.memoranda.util.Local;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;

/** 
 * ExportSticker class. 
 * This class handles the Exporting of the Annotation objects
 * in text and html document formats
 * 
 * Update: Self Checked altered method with Checkstyle, FixBugs, 
 * and for defects.
 * Found checkstyle issues with indentation, naming, grammar, 
 * and code line length.
 * No Fixbugs found, issues resolved and re-checked - 2/20/2016
 *
 * --Implemented Singleton design pattern 2/25/2016--
 */
public class ExportSticker {

      //new ExportSticker object created, from which instances will
	   //be retrieved.
	   private static ExportSticker exportSticker = new ExportSticker();

	   /** 
	   * ExportSticker default Constructor.
	   * 
	   * --Changed to private for Singleton design pattern 2/25/2016--
	   */
       private ExportSticker() {
         
       }
     
      /** 
	   * ExportSticker getInstance method
	   * This method retrieves an instance of the current 
	   * ExportSticker object.
	   * 
	   * @return exportSticker
	   */
      public static ExportSticker getInstance( ) {
         return exportSticker;
      }        
        /** 
         * Method exportTXT Added by Thomas Johnson.
         * For US-55, TSK-59 on 2/20/2016
         * Handles exporting of Annotation object as a Text document
         *
         * --2/25/2016 Added boolean test parameter for silencing 
         *   message dialog during a JUnit test--
         * 
         * @param f, sticker
         * @return result
         */
        public boolean exportText(File file, String sticker, boolean test){
        	
        	if(file.getName().indexOf(".tx") == -1){
				String dir = file.getPath();
				String ext = ".txt";

				String nfile = dir + ext;
	
				file = new File(nfile);                    	
			}
                boolean result = true;              
                
                String nohtml = sticker.toString().replaceAll("\\<.*?>","");
                    
                try {
                    
                    
                        FileWriter fwrite=new FileWriter(file,false);
                
                        fwrite.write(nohtml);
                            
                        fwrite.close();
                        
                        if(test == false){
                        JOptionPane.showMessageDialog(null,Local.
                        		getString("Text Document created with success in: " 
                        				+ file.getAbsolutePath()));
                        }
                
                
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,Local.
            		getString("We failed to create your Text document =(..."));
        }        
                
                        
            return result;
        }
        
        /** 
         * Method exportHTML Added by Thomas Johnson.
         * For US-55, TSK-60 on 2/20/2016
         * Handles exporting of Annotation object as an HTML document
         *
         * --2/25/2016 Added boolean test parameter for silencing 
         *   message dialog during a JUnit test--
         * 
         * @param f, sticker
         * @return result
         */
        public boolean exportHtml(File file, String sticker, boolean test){
        	
        	if(file.getName().indexOf(".htm") == -1){
				String dir = file.getPath();
				String ext = ".html";

				String nfile = dir + ext;
	
				file = new File(nfile);                    	
			}
        	
            boolean result = true;
                
            try {
                
                    FileWriter fwrite=new FileWriter(file,false);
            
                    fwrite.write(sticker);
                        
                    fwrite.close();
                    
                    if(test == false){
                    JOptionPane.showMessageDialog(null,Local.
                    		getString("HTML Document created with success in: " 
                    				+ file.getAbsolutePath()));
                    }
            
            
            } catch (IOException e) {
            	e.printStackTrace();
       	JOptionPane.showMessageDialog(null,Local.
       			getString("We failed to create your HTML document =(..."));
            }        
            
                    
            return result;
        }
   
}