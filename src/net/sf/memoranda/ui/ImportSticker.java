package net.sf.memoranda.ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/** 
 * ImportSticker class 
 * This class handles the Importing of the Annotation objects
 * from an html document, previously exported already in Annotation form.
 * 
 * Update: Self Checked altered method with Checkstyle, FixBugs, 
 * and for defects.
 * Found checkstyle issues with indentation, naming, and grammar.
 * No Fixbugs found, issues resolved and re-checked - 2/20/2016
 */
public class ImportSticker {       
        
	       /** 
	        * ImportSticker default Constructor.
	        */
        public ImportSticker() {
            
        }
        
        /** 
         * Method HTMLStickerImport Added by Thomas Johnson.
         * For US-55, TSK-58 on 2/20/2016
         * Handles the Import of an HTML Annotation Formatted file 
         * into a new Annotation Sticker object
         * 
         * @param f
         * @return text
         */
        public String htmlAnnotationImport(File file) {
            String text = "";
            BufferedReader in;
            try {
                in = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                String line = in.readLine();
                while (line != null) {
                    text = text + line + "\n";
                    line = in.readLine();
                }
                in.close();            
            }catch (Exception e) {
                new ExceptionDialog(e, "Failed to import "+file.getPath(), "");
                return null;
            }
      
            System.out.println("Your Annotation has been successfully read");
            return text;        

        }
            
}