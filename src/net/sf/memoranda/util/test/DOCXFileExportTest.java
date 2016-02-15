/**
 * 
 */
package net.sf.memoranda.util.test;

import static org.junit.Assert.*;

import java.io.File;

import javax.swing.text.html.HTMLDocument;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import net.sf.memoranda.CurrentNote;
import net.sf.memoranda.util.DOCXFileExport;
import net.sf.memoranda.util.PDFFileExport;

/**
 * @author Thomas J - 2/14/2016
 * This JUnit Test class is for testing the methods and constructor of the
 * DOCXFileExport.java Class
 */
public class DOCXFileExportTest {

	
	File f = new File(System.getProperty("user.dir") + "test");
	static HTMLDocument testDoc = new HTMLDocument();
	String enc = "UTF-8";
	String template = null;
	String in = "<html>";
	String XHTML = "<xhtml>";
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link net.sf.memoranda.util.DOCXFileExport#DOCXFileExport(java.io.File, javax.swing.text.Document, net.sf.memoranda.Note, java.lang.String, boolean, java.lang.String, boolean)}.
	 */
	@Test
	public final void testDOCXFileExport() {
		DOCXFileExport testDOCXExport = new DOCXFileExport(f, testDoc, CurrentNote.get(), enc, false, template, true);
	}

	/**
	 * Test method for {@link net.sf.memoranda.util.DOCXFileExport#doExport()}.
	 */
	@Test
	public final void testDoExport() {
		DOCXFileExport testDOCXExport = new DOCXFileExport(f, testDoc, CurrentNote.get(), enc, false, template, true);
		testDOCXExport.doExport();
	}

	/**
	 * Test method for {@link net.sf.memoranda.util.DOCXFileExport#convertToXHTML(java.lang.String)}.
	 */
	@Test
	public final void testConvertToXHTML() {
		PDFFileExport.convertToXHTML(in);
	}

	/**
	 * Test method for {@link net.sf.memoranda.util.DOCXFileExport#ConvertInHTML()}.
	 * @throws Exception 
	 */
	@Test (expected=org.docx4j.org.xhtmlrenderer.util.XRRuntimeException.class)
	public final void testConvertInHTML() throws Exception {
		DOCXFileExport testDOCXExport = new DOCXFileExport(f, testDoc, CurrentNote.get(), enc, false, template, true);
		testDOCXExport.ConvertInHTML();
	}

}
