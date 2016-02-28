package net.sf.memoranda.ui.test;

import static org.junit.Assert.*;

import java.awt.event.ActionEvent;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import net.sf.memoranda.ui.AppFrame;

/**
 * JUnit Test Class to test the functionality of the previously
 * modified methods in AppFrame.java to access external web links
 * using the default system web browser
 * 
 * Self Checked altered method with Checkstyle, FixBugs, 
 * and for defects.
 * Found Checkstyle issues with naming and grammar.
 * No Fixbugs found, issues resolved and re-checked - 2/25/2016
 * Checked for code smells, none found.
 * 
 * @author Thomas Johnson
 * Created 2/25/2016
 *
 */
public class WebLinksTest {

	ActionEvent event = new ActionEvent(0, 0, null);
	AppFrame testAppFrame = new AppFrame();
	
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
	 * AppFrame#jMenuHelpBug_actionPerformed(java.awt.event.ActionEvent)}.
	 */
	@Test
	public final void testJMenuHelpBug_actionPerformed() {
		assertTrue(testAppFrame.helpBugActionPerformed(event, true));
	}

	/**
	 * Test method for {@link net.sf.memoranda.ui.AppFrame#
	 * jMenuHelpWeb_actionPerformed(java.awt.event.ActionEvent)}.
	 */
	@Test
	public final void testJMenuHelpWeb_actionPerformed() {
		assertTrue(testAppFrame.helpWebActionPerformed(event, true));
	}

	/**
	 * Test method for {@link net.sf.memoranda.ui.AppFrame#
	 * jMenuHelpGuide_actionPerformed(java.awt.event.ActionEvent)}.
	 */
	@Test
	public final void testJMenuHelpGuide_actionPerformed() {
		assertTrue(testAppFrame.helpGuideActionPerformed(event, true));
	}

}
