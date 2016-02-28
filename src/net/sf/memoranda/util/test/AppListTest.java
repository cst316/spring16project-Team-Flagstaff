/**
 * 
 */
package net.sf.memoranda.util.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import net.sf.memoranda.util.AppList;

/**
 * @author Kevin Bryant - kjbryan1
 *
 */
public class AppListTest {

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
	 * Test method for {@link net.sf.memoranda.util.test.AppList#getPlafCode(java.lang.String)}.
	 */
	@Test
	public void testGetPlafCode() {
		String windows;
		String linux;
		String solaris;
		if (System.getProperty("os.name").contains("windows")){
			windows = System.getProperty("os.name");
			assertTrue(windows.equals(AppList.getPlafCode(System.getProperty("os.name"))));
		}
		if (System.getProperty("os.name").contains("linux")){
			linux = System.getProperty("os.name");
			assertTrue(linux.equals(AppList.getPlafCode(System.getProperty("os.name"))));
		}
		if (System.getProperty("os.name").contains("solaris")){
			solaris = System.getProperty("os.name");
			assertTrue(solaris.equals(AppList.getPlafCode(System.getProperty("os.name"))));
		}
	}

	/**
	 * Test method for {@link net.sf.memoranda.util.test.AppList#AppList(Element)}.
	 */
	@Test
	public void testAppList() {
		
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link net.sf.memoranda.util.test.AppList#getCommandLine(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testGetCommandLine() {
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link net.sf.memoranda.util.test.AppList#getCommand(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testGetCommand() {
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link net.sf.memoranda.util.test.AppList#getFindPath(java.lang.String)}.
	 */
	@Test
	public void testGetFindPath() {
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link net.sf.memoranda.util.test.AppList#setFindPath(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testSetFindPath() {
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link net.sf.memoranda.util.test.AppList#getExec(java.lang.String)}.
	 */
	@Test
	public void testGetExec() {
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link net.sf.memoranda.util.test.AppList#setExec(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testSetExec() {
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link net.sf.memoranda.util.test.AppList#getCommandLinePattern(java.lang.String)}.
	 */
	@Test
	public void testGetCommandLinePattern() {
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link net.sf.memoranda.util.test.AppList#setCommandLinePattern(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testSetCommandLinePattern() {
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link net.sf.memoranda.util.test.AppList#addApp(java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testAddApp() {
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link net.sf.memoranda.util.test.AppList#addOrReplaceApp(java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testAddOrReplaceApp() {
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link net.sf.memoranda.util.test.AppList#getBrowserExec()}.
	 */
	@Test
	public void testGetBrowserExec() {
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link net.sf.memoranda.util.test.AppList#setBrowserExec(java.lang.String)}.
	 */
	@Test
	public void testSetBrowserExec() {
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link java.lang.Object#Object()}.
	 */
	@Test
	public void testObject() {
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link java.lang.Object#getClass()}.
	 */
	@Test
	public void testGetClass() {
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link java.lang.Object#hashCode()}.
	 */
	@Test
	public void testHashCode() {
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link java.lang.Object#equals(java.lang.Object)}.
	 */
	@Test
	public void testEquals() {
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link java.lang.Object#clone()}.
	 */
	@Test
	public void testClone() {
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link java.lang.Object#toString()}.
	 */
	@Test
	public void testToString() {
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link java.lang.Object#notify()}.
	 */
	@Test
	public void testNotify() {
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link java.lang.Object#notifyAll()}.
	 */
	@Test
	public void testNotifyAll() {
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link java.lang.Object#wait(long)}.
	 */
	@Test
	public void testWaitLong() {
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link java.lang.Object#wait(long, int)}.
	 */
	@Test
	public void testWaitLongInt() {
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link java.lang.Object#wait()}.
	 */
	@Test
	public void testWait() {
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link java.lang.Object#finalize()}.
	 */
	@Test
	public void testFinalize() {
		//fail("Not yet implemented");
	}

}
