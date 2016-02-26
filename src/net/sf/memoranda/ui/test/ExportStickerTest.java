package net.sf.memoranda.ui.test;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import net.sf.memoranda.ui.ExportSticker;

/**
 * JUnit Test Class to test the functionality of the previously
 * modified methods in ExportSticker.java to export Annotation
 * Stickers as Text and HTMl file types.
 * 
 * Self Checked altered method with Checkstyle, FixBugs, 
 * and for defects.
 * Found Checkstyle issues with order, naming, and grammar.
 * No Fixbugs found, issues resolved and re-checked - 2/25/2016
 * Checked for code smells, none found.
 * 
 * @author Thomas Johnson
 * Created 2/25/2016
 */
public class ExportStickerTest {

	File testFile = new File(System.getProperty("user.dir") + "testFile");
	String testSticker = "<div style=" 
			+ " \"background-color:#FFFF00;font-size:20;color:#0;\" " 
				+ ">February 20, 2016 9:59 PM<br></div>";
	ExportSticker testExportSticker = ExportSticker.getInstance();
	
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
	 * Test method for {@link net.sf.memoranda.ui.
	 * ExportSticker#exportText(java.io.File, java.lang.String)}.
	 */
	@Test
	public final void testExportText() {
		assertTrue(testExportSticker.exportText(testFile, testSticker, true));
	}

	/**
	 * Test method for {@link net.sf.memoranda.ui.
	 * ExportSticker#exportHtml(java.io.File, java.lang.String)}.
	 */
	@Test
	public final void testExportHtml() {
		assertTrue(testExportSticker.exportHtml(testFile, testSticker, true));
	}

}
