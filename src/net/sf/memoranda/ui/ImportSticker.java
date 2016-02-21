package net.sf.memoranda.ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import net.sf.memoranda.util.Local;

/** 
 * ImportSticker class 
 * This class handles the Importing of the Annotation objects
 * from an html document, previously exported already in Annotation form.
 */
public class ImportSticker {       
        
	   /** 
	    * ImportSticker default Constructor
	    */
        public ImportSticker() {
            
        }
        
        /** 
         * Method HTMLStickerImport Added by Thomas Johnson
         * For US-55, TSK-58 on 2/20/2016
         * Handles the Import of an HTML Annotation Formatted file 
         * into a new Annotation Sticker object
         * 
         * @param f
         * @return text
         */
        public String HTMLAnnotationImport(File f) {
            String text = "";
            BufferedReader in;
            try {
                in = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
                String line = in.readLine();
                while (line != null) {
                    text = text + line + "\n";
                    line = in.readLine();
                }
                in.close();            
            }
            catch (Exception e) {
                new ExceptionDialog(e, "Failed to import "+f.getPath(), "");
                return null;
            }
      
            System.out.println("Your Annotation has been successfully read");
            return text;        

        }
            
}