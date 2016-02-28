package net.sf.memoranda.ui.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JLabel;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import net.sf.memoranda.ui.ExportSticker;
import net.sf.memoranda.ui.ImportSticker;

/**
 * JUnit Test Class to test the functionality of the previously
 * modified methods in ImportSticker.java to import Annotation
 * Stickers from previously exported HTML files.
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
public class ImportStickerTest {

	static File testFile = new File(System.getProperty("user.dir") + "testFile.html");
	static String testSticker = "<div style=" 
			+ " \"background-color:#FFFF00;font-size:20;color:#0;\" " 
				+ ">February 20, 2016 9:59 PM<br></div>";
	ImportSticker testImportSticker = ImportSticker.getInstance();
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		try {
			FileWriter fileWriter = new FileWriter(testFile);
			fileWriter.write(testSticker);
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
	 * ImportSticker#htmlAnnotationImport(java.io.File)}.
	 */
	@Test
	public final void testHtmlAnnotationImport() {
		assertEquals(testImportSticker.htmlAnnotationImport
				(testFile).replaceAll("[\n\r]", ""), testSticker);
	}

}
