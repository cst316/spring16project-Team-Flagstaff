package net.sf.memoranda.util;

import java.io.*;
import java.util.Date;
import java.util.regex.Pattern;
import net.sf.memoranda.util.Configuration;

import javax.swing.text.Document;
import javax.swing.text.html.HTMLDocument;

import net.sf.memoranda.Note;
import net.sf.memoranda.ui.ExceptionDialog;
import net.sf.memoranda.ui.htmleditor.AltHTMLWriter;


import org.apache.xml.serialize.Method;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.cyberneko.html.parsers.SAXParser;
import org.xml.sax.InputSource;
import org.xml.sax.Parser;
import org.xml.sax.helpers.ParserFactory;

import java.util.List;

import org.docx4j.dml.CTBlip;
import org.docx4j.openpackaging.io.SaveToZipFile;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.Part;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPart;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.openpackaging.parts.relationships.Namespaces;
import org.docx4j.openpackaging.parts.relationships.RelationshipsPart;
import org.docx4j.relationships.Relationship;
import org.docx4j.relationships.Relationships;
import org.docx4j.utils.SingleTraversalUtilVisitorCallback;
import org.docx4j.utils.TraversalUtilVisitor;
import org.docx4j.wml.Body;

import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.NumberingDefinitionsPart;


import org.docx4j.openpackaging.contenttype.ContentType;
import org.docx4j.wml.CTAltChunk;
import org.docx4j.openpackaging.parts.PartName;
import org.docx4j.jaxb.Context;
import org.docx4j.Docx4jProperties;
import org.docx4j.openpackaging.contenttype.ContentTypeManager;

import javax.xml.bind.JAXBException;
import javax.xml.bind.JAXBContext;
import org.eclipse.persistence.jaxb.JAXBContextFactory;

import org.docx4j.convert.in.xhtml.XHTMLImporterImpl;
import org.docx4j.openpackaging.exceptions.Docx4JException;

/** The DOCXFileExport Class, previously created by Thomas Johnson.
 * This class enables Notes from Memoranda to be exported in a DOCX 
 * formatted file to the directory which was last being browsed, and
 * to the entered or selected filename.
 *
 * Changes for Sprint 2: Currently this Sprint the class was moved 
 * from the "ui" directory into the "util" directory.  
 * Also lines 111-137 are new, to handle instances where different 
 * file type extensions are selected or entered.
 * Lines 204-206 and 233-255 were added back in for creating the 
 * more strict XHTML format when converting to DOCX format.
 * Line 33 was altered to reflect this new usage of XHTML also. 
 *  - Thomas J, 2/14/2016
 */
@SuppressWarnings("deprecation")
public class DOCXFileExport {
    
    String charset = "";
    File f = null;
    HTMLDocument doc;
    Note note = null;
    boolean xhtml = false;
    boolean num = false;
    String templFile = null;
    /**
     * Constructor for DOCXFileExport.
     */
    
    
    public DOCXFileExport(File f, Document doc, Note note, String charset, boolean num, String templFile, boolean xhtml) {
        this.f = f;
        this.doc = (HTMLDocument)doc;
        this.note = note;
        this.charset = charset;
        this.num = num;
        this.templFile = templFile;
        this.xhtml = xhtml;
        
        doExport();
    }
    
    
    
    public void doExport() {
        try {
                    //FileWriter fw = new FileWriter(f);
        			Writer fw;
					//Added to fix the file if there was no extention given
					//jcscoobyrs 17-Nov-2003 at 09:08:55
					//------------------------------------------------------
					if(f.getName().indexOf(".htm")  == -1)
					{
						String dir = f.getPath();
						String ext = ".xhtml";
						//String ps = System.getProperty("file.separator");
						String nfile = dir + ext;
			
						f = new File(nfile);                    	
					}
					else if(f.getName().indexOf(".doc")  == -1)
					{
						String dir = f.getPath();
						String ext = ".xhtml";
						//String ps = System.getProperty("file.separator");
						String nfile = dir + ext;
			
						f = new File(nfile);                    	
					}
					else if(f.getName().indexOf(".pdf")  == -1)
					{
						String dir = f.getPath();
						String ext = ".xhtml";
						//String ps = System.getProperty("file.separator");
						String nfile = dir + ext;
			
						f = new File(nfile);                    	
					}
					else if(f.getName().indexOf(".xhtm")  == -1)
					{
						String dir = f.getPath();
						String ext = ".xhtml";
						//String ps = System.getProperty("file.separator");
						String nfile = dir + ext;
			
						f = new File(nfile);                    	
					}
					//------------------------------------------------------
					//End appendage
					
					if (charset != null)    
                        fw = new OutputStreamWriter(new FileOutputStream(f), charset);
                    else
                        fw = new FileWriter(f);
                    fw.write(applyTemplate());
                    fw.flush();
                    fw.close();
                    
                    ConvertInHTML();
                }
                catch (Exception ex) {
                    new ExceptionDialog(ex, "Cannot export file "+f.getPath(), null);
                }
        }
	 
     
	 private String getTemplateString(String templF) {		
	    if ((templF != null) && (templF.length() >0)) {
    		File f = new File(templF);			
			String text = "";	
			try {
				BufferedReader fr = new BufferedReader(new InputStreamReader(new FileInputStream(f)));		
				String s = fr.readLine();
				while (s != null) {				
					text = text + s;
					s = fr.readLine();
				}
				fr.close();
			}
			catch (Exception ex) {
				new ExceptionDialog(ex, "Cannot read template file from "+templF, null);
			}	
			if (text.length() > 0)
				return text;
		}
	    String t = (String) Configuration.get("DEFAULT_EXPORT_TEMPLATE");
	    if ((t != null) && (t.length() > 0))
	        return t;
		return "<html>\n<head>\n@METACHARSET@\n<title>@TITLE@ - @PROJECT@</title>\n</head>\n<body>\n@CONTENT@\n</body>\n</html>";
	 }
     
	 private String applyTemplate() {
        String body = getNoteBody();        
		String title = note != null? note.getTitle() : "";
		String id = note != null? note.getId() : "";
		String project = note != null? note.getProject().getTitle() : "";
		String date = note != null? note.getDate().getFullDateString() : "";
		String now = new Date().toString();
		String templ = getTemplateString(templFile);
		templ = templ.replaceAll("@CONTENT@", body);
		templ = templ.replaceAll("@TITLE@", title);
		templ = templ.replaceAll("@ID@", id);
		templ = templ.replaceAll("@PROJECT@", project);
		templ = templ.replaceAll("@DATE@", date);
		templ = templ.replaceAll("@NOW@", now);
		if ((charset != null) && (charset.length() >0))
		    templ = templ.replaceAll("@METACHARSET@", "<meta http-equiv=\"Content-Type\" content=\"text/html; charset="
                        + charset + "\" >");
		
		if (xhtml)
			System.out.print("converting to XHTML");
		    templ = convertToXHTML(templ);
		
		return templ;
     }
     
     private String getNoteBody() {
        String text = "";
        StringWriter sw = new StringWriter();
        AltHTMLWriter writer = new AltHTMLWriter(sw, doc, charset, num);
        try {
            writer.write();
            sw.flush();
            sw.close();
        }
        catch (Exception ex) {
            new ExceptionDialog(ex);
        }
        text = sw.toString();
        text = Pattern
                .compile("<body(.*?)>", java.util.regex.Pattern.DOTALL
                        + java.util.regex.Pattern.CASE_INSENSITIVE).split(text)[1];
        text = Pattern
                .compile("</body>", java.util.regex.Pattern.DOTALL
                        + java.util.regex.Pattern.CASE_INSENSITIVE).split(text)[0];
        return text;
     }
     
     public static String convertToXHTML(String in) {       
         SAXParser parser = new SAXParser();
         InputSource source;
         OutputFormat outputFormat = new OutputFormat();
         try {
             //parser.setProperty("http://cyberneko.org/html/properties/default-encoding", charset);
             parser.setProperty("http://cyberneko.org/html/properties/names/elems", "lower");
             outputFormat.setOmitDocumentType(true);
             outputFormat.setOmitXMLDeclaration(true);
             outputFormat.setMethod(Method.XHTML);
             outputFormat.setIndenting(true);            
             StringReader sr = new StringReader(in);
             StringWriter sw = new StringWriter();
             source = new InputSource(sr);
             parser.setContentHandler(new XMLSerializer(sw, outputFormat));
             parser.parse(source);
             return sw.toString();
         }
         catch (Exception ex) {
            new ExceptionDialog(ex);
         }
         return null;
     }

    
    public void ConvertInHTML() throws Exception{

    	BasicConfigurator.configure();
    	ContentTypeManager ctm = new ContentTypeManager();
    	
    	        String tempinputfilepath = f.getAbsolutePath();
    	        String inputfilepath = tempinputfilepath.replace("\\", "/");;
    	        System.out.println("Input File Path: " + inputfilepath);
    	        String tempbaseURL = "file:///" + f.getParent();
    	        String baseURL = tempbaseURL.replace("\\", "/");;
    	        System.out.println("Base URL: " + baseURL);
    	        
    	        //System.out.println("user.dir: " + (System.getProperty("user.dir")));
    	        
    	        boolean saveImages = true;
    			boolean saveResultingDoc = true;
    	        
    	        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.createPackage();

    	        NumberingDefinitionsPart ndp = new NumberingDefinitionsPart();
    	        wordMLPackage.getMainDocumentPart().addTargetPart(ndp);
    	        ndp.unmarshalDefaultNumbering(); 
    	        
    	        XHTMLImporterImpl xHTMLImporter = new XHTMLImporterImpl(wordMLPackage);
    	        xHTMLImporter.setHyperlinkStyle("Hyperlink");

    	        wordMLPackage.getMainDocumentPart().getContent().addAll(xHTMLImporter.convert(new File(inputfilepath), null));
    	        
    	        MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
    	        RelationshipsPart relsPart = documentPart.getRelationshipsPart();
    			Relationships rels = relsPart.getRelationships();
    			List<Relationship> relsList = rels.getRelationship();
    	        
    			for (Relationship r : relsList) {

    				System.out.println(r.getTargetMode());
    				if ( r.getType().equals( Namespaces.IMAGE )
    						&& (r.getTargetMode()==null
    								|| r.getTargetMode().equalsIgnoreCase("internal"))) {
    					
    					String target = r.getTarget();
    					System.out.println("target: " + target);
    					
    					if (saveImages) {
    						
    						File f = new File(baseURL + "/" + target);
    						if (f.exists()) {
    							System.out.println("Overwriting existing object: " + f.getPath() );
    						} else if ( !f.getParentFile().exists() ) {
    							System.out.println("creating " + f.getParentFile().getAbsolutePath() );
    							f.getParentFile().mkdirs();
    						}					
    						
    						Part p = relsPart.getPart(r);
    						FileOutputStream fos = new FileOutputStream( f );
    						((BinaryPart)p).writeDataToOutputStream(fos);
    						fos.close();
    						
    					}
    					r.setTargetMode("External");
    					
    				}			
    			}
    			
    			org.docx4j.wml.Document wmlDocumentEl = (org.docx4j.wml.Document)documentPart.getJaxbElement();
    			Body body =  wmlDocumentEl.getBody();
    							
    			SingleTraversalUtilVisitorCallback imageVisitor 
    				= new SingleTraversalUtilVisitorCallback(
    						new TraversalUtilBlipVisitor());
    			imageVisitor.walkJAXBElements(body);
    			
    			
    	        
    	        String fileName = (inputfilepath).replace(".xhtml", "");
    	        
    	        File saveFile = new File(fileName + ".docx");

    	        wordMLPackage.save(saveFile);
    	        
    	        boolean bool = false;
    	        
    	        bool = f.delete();
    	      
    	  }
    		
    	}


     
	 
