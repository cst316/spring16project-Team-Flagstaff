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
 * ExportSticker class 
 * This class handles the Exporting of the Annotation objects
 * in text and html document formats
 */
public class ExportSticker {

        
        /*public static Document _doc = null;
        static Element _root = null;

        static {
                CurrentStorage.get().openEventsManager();
                if (_doc == null) {
                        _root = new Element("eventslist");
/*                        _root.addNamespaceDeclaration("jnevents", NS_JNEVENTS);
                        _root.appendChild(
                                new Comment("This is JNotes 2 data file. Do not modify.")); */
/*                        _doc = new Document(_root);
                } else
                        _root = _doc.getRootElement();

        }*/
        public ExportSticker() {
        }
        
        /** 
         * Method exportTXT Added by Thomas Johnson
         * For US-55, TSK-59 on 2/20/2016
         * Handles exporting of Annotation object as a Text document
         * 
         * @param f, sticker
         * @return result
         */
        public boolean exportTXT(File f, String sticker){
        	
        	if(f.getName().indexOf(".tx") == -1)
			{
				String dir = f.getPath();
				String ext = ".txt";

				String nfile = dir + ext;
	
				f = new File(nfile);                    	
			}
                boolean result = true;
                String fs = System.getProperty("file.separator");
                
                
                String nohtml = sticker.toString().replaceAll("\\<.*?>","");
                    
                try {
                //File file = new File(this.name+"."+txt);
                    
                    
                        FileWriter fwrite=new FileWriter(f,false);
                
                        fwrite.write(nohtml);
                            
                        fwrite.close();
                        JOptionPane.showMessageDialog(null,Local.getString("Text Document created with success in: " + f.getAbsolutePath()));
                
                
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,Local.getString("We failed to create your Text document =(..."));
        }        
                
                        
            return result;
        }
        
        /** 
         * Method exportHTML Added by Thomas Johnson
         * For US-55, TSK-60 on 2/20/2016
         * Handles exporting of Annotation object as an HTML document
         * 
         * @param f, sticker
         * @return result
         */
        public boolean exportHTML(File f, String sticker){
        	
        	if(f.getName().indexOf(".htm") == -1)
			{
				String dir = f.getPath();
				String ext = ".html";

				String nfile = dir + ext;
	
				f = new File(nfile);                    	
			}
        	
            boolean result = true;
            String fs = System.getProperty("file.separator");
                
            try {
            //File file = new File(this.name+"."+txt);
                
                
                    FileWriter fwrite=new FileWriter(f,false);
            
                    fwrite.write(sticker);
                        
                    fwrite.close();
                    JOptionPane.showMessageDialog(null,Local.getString("HTML Document created with success in: " + f.getAbsolutePath()));
            
            
            } catch (IOException e) {
            	e.printStackTrace();
       	JOptionPane.showMessageDialog(null,Local.getString("We failed to create your HTML document =(..."));
            }        
            
                    
            return result;
        }
        
        /*public static String getStickers() {
                String result ="";
                Elements els = _root.getChildElements("sticker");
                for (int i = 0; i < els.size(); i++) {
                        Element se = els.get(i);
                        m.put(se.getAttribute("id").getValue(), se.getValue());
                }
                return m;
        }*/
   
}