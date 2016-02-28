package net.sf.memoranda.util.test;

import java.io.*;

import javax.swing.text.html.HTMLDocument;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.itextpdf.text.DocumentException;

import net.sf.memoranda.CurrentNote;
import net.sf.memoranda.util.PDFFileExport;
import net.sf.memoranda.util.IPdfHandler;

/**
 * @author Thomas J - 2/14/2016
 * This JUnit Test class is for testing the methods and constructor of the
 * PDFFileExport.java Class
 */
public class PDFFileExportTest {
	
	File f = new File(System.getProperty("user.dir") + "test");
	static HTMLDocument testDoc = new HTMLDocument();
	String enc = "UTF-8";
	String template = "";
	String in = "<html>";
	String XHTML = "<xhtml>";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link net.sf.memoranda.util.PDFFileExport#PDFFileExport(java.io.File, javax.swing.text.Document, net.sf.memoranda.Note, java.lang.String, boolean, java.lang.String, boolean)}.
	 */
	//Dead Store bug is a false positive, testPDFExport is the test object being created
	@SuppressWarnings("unused")
	@Test
	public final void testPDFFileExport() {
		IPdfHandler testPDFExport = new PDFFileExport(f, testDoc, CurrentNote.get(), enc, false, template, true);
	}

	/**
	 * Test method for {@link net.sf.memoranda.util.PDFFileExport#convertToXHTML()}.
	 */
	@Test
	public final void testConvertToXHTML() {
		PDFFileExport.convertToXHTML(in);
	}

	/**
	 * Test method for {@link net.sf.memoranda.util.PDFFileExport#XHTMLToPDF()}.
	 */
	@Test (expected=com.itextpdf.text.ExceptionConverter.class)
	public final void testXHTMLToPDF() throws IOException, DocumentException {
		IPdfHandler testXHTMLToPDF = new PDFFileExport(f, testDoc, CurrentNote.get(), enc, false, template, true);
		testXHTMLToPDF.xhtmlToPdf(XHTML);
	}

	/**
	 * Test method for {@link net.sf.memoranda.util.PDFFileExport#createPdf()}.
	 */
	@Test (expected=com.itextpdf.text.ExceptionConverter.class)
	public final void testCreatePdf() throws IOException, DocumentException {
		IPdfHandler testCreatePDF = new PDFFileExport(f, testDoc, CurrentNote.get(), enc, false, template, true);
		testCreatePDF.createPdf(f.getAbsolutePath(), XHTML);
	}

}
