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
 */
public class ExportSticker {

        public ExportSticker() {
        }
        
        /** 
         * Method exportTXT Added by Thomas Johnson.
         * For US-55, TSK-59 on 2/20/2016
         * Handles exporting of Annotation object as a Text document
         * 
         * @param f, sticker
         * @return result
         */
        public boolean exportText(File file, String sticker){
        	
        	if(file.getName().indexOf(".tx") == -1){
				String dir = file.getPath();
				String ext = ".txt";

				String nfile = dir + ext;
	
				file = new File(nfile);                    	
			}
                boolean result = true;
                String fs = System.getProperty("file.separator");
                
                
                String nohtml = sticker.toString().replaceAll("\\<.*?>","");
                    
                try {
                    
                    
                        FileWriter fwrite=new FileWriter(file,false);
                
                        fwrite.write(nohtml);
                            
                        fwrite.close();
                        JOptionPane.showMessageDialog(null,Local.
                        		getString("Text Document created with success in: " 
                        				+ file.getAbsolutePath()));
                
                
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
         * @param f, sticker
         * @return result
         */
        public boolean exportHtml(File file, String sticker){
        	
        	if(file.getName().indexOf(".htm") == -1){
				String dir = file.getPath();
				String ext = ".html";

				String nfile = dir + ext;
	
				file = new File(nfile);                    	
			}
        	
            boolean result = true;
            String fs = System.getProperty("file.separator");
                
            try {
                
                    FileWriter fwrite=new FileWriter(file,false);
            
                    fwrite.write(sticker);
                        
                    fwrite.close();
                    JOptionPane.showMessageDialog(null,Local.
                    		getString("HTML Document created with success in: " 
                    				+ file.getAbsolutePath()));
            
            
            } catch (IOException e) {
            	e.printStackTrace();
       	JOptionPane.showMessageDialog(null,Local.
       			getString("We failed to create your HTML document =(..."));
            }        
            
                    
            return result;
        }
   
}