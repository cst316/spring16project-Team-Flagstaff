package net.sf.memoranda.util;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.Base64;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.AbstractImageProvider;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;
 
import java.io.*;
import java.util.Date;
import java.util.regex.Pattern;

import javax.swing.text.html.HTMLDocument;

import org.apache.xml.serialize.Method;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.cyberneko.html.parsers.SAXParser;
import org.xml.sax.InputSource;

import net.sf.memoranda.Note;
import net.sf.memoranda.ui.ExceptionDialog;
import net.sf.memoranda.ui.htmleditor.AltHTMLWriter;


/** The PDFFileExport Class, is a new class created by Thomas Johnson.
 * This class enables Notes from Memoranda to be exported in a PDF 
 * formatted file to the directory which was last being browsed, and
 * to the entered or selected filename. - Thomas J, 2/14/2016
 */
@SuppressWarnings("deprecation")
public class PDFFileExport {

    String charset = "";
    File f = null;
    HTMLDocument doc;
    Note note = null;
    boolean xhtml = false;
    boolean num = false;
    String templFile = null;
    /**
     * Constructor for PDFFileExport.
     */
    public PDFFileExport(File f, javax.swing.text.Document doc, Note note, String charset, boolean num, String templFile, boolean xhtml) {
        this.f = f;
        this.doc = (HTMLDocument)doc;
        this.note = note;
        this.charset = charset;
        this.num = num;
        this.templFile = templFile;
        this.xhtml = xhtml;
        doExport();
    }
    
    /* Method to handle and correct extensions possibly given upon file creation execution
     */
    private void doExport() {
        try {
                    //Added to fix the file if there was no extention given
					//------------------------------------------------------
        	if(f.getName().indexOf(".htm")  == -1)
			{
				String dir = f.getPath();
				String ext = ".xhtml";
				String nfile = dir + ext;
	
				f = new File(nfile);                    	
			}
			else if(f.getName().indexOf(".doc")  == -1)
			{
				String dir = f.getPath();
				String ext = ".xhtml";
				String nfile = dir + ext;
	
				f = new File(nfile);                    	
			}
			else if(f.getName().indexOf(".pdf")  == -1)
			{
				String dir = f.getPath();
				String ext = ".xhtml";
				String nfile = dir + ext;
	
				f = new File(nfile);                    	
			}
			else if(f.getName().indexOf(".xhtm")  == -1)
			{
				String dir = f.getPath();
				String ext = ".xhtml";
				String nfile = dir + ext;
	
				f = new File(nfile);                    	
			}
					//------------------------------------------------------
					//End appendage
                    
                    if (charset != null) {
					} else {
					}
                    XHTMLToPDF(applyTemplate());
                }
                catch (Exception ex) {
                    new ExceptionDialog(ex, "Cannot export file "+f.getPath(), null);
                }
        }
	 
     /*Method to retrieve the templated HTML String
     * @param temlF (String)
     * @return t (String)
     */
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
	    String t = (String)Configuration.get("DEFAULT_EXPORT_TEMPLATE");
	    if ((t != null) && (t.length() > 0))
	        return t;
		return "<html>\n<head>\n@METACHARSET@\n<title>@TITLE@ - @PROJECT@</title>\n</head>\n<body>\n@CONTENT@\n</body>\n</html>";
	 }
     
     /*Method to apply a template for the content of the HTML representation of the Note data
     * @return templ (String)
     */
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
     
     /*Method to retrieve the main body of the Note
     * @return text (String)
     */
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
     
     /*Method to convert HTML formatted String to XHTML format
     * @Param in (String)
     */
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
     
     /* Internal Class to handle Images residing in the Note, of XHTML format
     * @return Image
     */
     class Base64ImageProvider extends AbstractImageProvider {
    	 
         @Override
         public Image retrieve(String src) {
             int pos = src.indexOf("base64,");
             try {
                 if (src.startsWith("data") && pos > 0) {
                     byte[] img = Base64.decode(src.substring(pos + 7));
                     return Image.getInstance(img);
                 }
                 else {
                     return Image.getInstance(src);
                 }
             } catch (BadElementException ex) {
                 return null;
             } catch (IOException ex) {
                 return null;
             }
         }
  
         @Override
         public String getImageRootPath() {
             return null;
         }
     }
     
     /* Method to convert XHTML formatted String of Note content to PDF File
     * @param XHTML (String)
     */
     public void XHTMLToPDF(String XHTML) throws IOException, DocumentException{
    	 
    	 String tempinputfilepath = f.getAbsolutePath();
	     String inputfilepath = tempinputfilepath.replace("\\", "/");;

	     String outputFilePath = inputfilepath.replace(".xhtml", ".pdf");
	     
	     System.out.print("Output File:" + outputFilePath);
	     
    	 File file = new File(outputFilePath);
         file.getParentFile().mkdirs();
         createPdf(outputFilePath, XHTML);
     }
  
     /* Method to create PDF Document
     * @param file (String)
     * @param XHTML (String)
     */
     public void createPdf(String file, String XHTML) throws IOException, DocumentException {
  
         Document document = new Document();
         PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
         document.open();
  
         // CSS
         CSSResolver cssResolver =
                 XMLWorkerHelper.getInstance().getDefaultCssResolver(true);
  
         // HTML
         HtmlPipelineContext htmlContext = new HtmlPipelineContext(null);
         htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());
         htmlContext.setImageProvider(new Base64ImageProvider());
  
         // Pipelines
         PdfWriterPipeline pdf = new PdfWriterPipeline(document, writer);
         HtmlPipeline html = new HtmlPipeline(htmlContext, pdf);
         CssResolverPipeline css = new CssResolverPipeline(cssResolver, html);
  
         // XML Worker
         XMLWorker worker = new XMLWorker(css, true);
         XMLParser p = new XMLParser(worker);
         p.parse(new ByteArrayInputStream(XHTML.getBytes()));
  
         document.close();
     }
}
