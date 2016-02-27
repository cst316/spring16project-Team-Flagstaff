package net.sf.memoranda.util;

import java.io.IOException;

import com.itextpdf.text.DocumentException;


/** 
 * PDFHandler Interface  
 * This class exists to be Implemented for handling the creation of PDF
 * documents, given XHTML and file Strings.
 * 
 * <p>Update: Self Checked altered method with Checkstyle, FixBugs, 
 * and for defects.
 * Found checkstyle issues with indentation, naming, and grammar.
 * No Fixbugs found, issues resolved and re-checked - 2/26/2016
 *
 *@author Thomas Johnson
 */
public interface IPdfHandler {

  /** Method to convert XHTML formatted String of Note content to PDF File.
  *   @param xhtml (String)
  */
  void xhtmlToPdf(String xhtml) throws IOException, DocumentException;

  /* Method to create PDF Document
  * @param file (String)
  * @param xhtml (String)
  */
  void createPdf(String file, String xhtml) throws IOException, DocumentException;

}