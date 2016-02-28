/**
 * 
 */
package net.sf.memoranda.util.test;

import static org.junit.Assert.*;

import java.awt.event.ActionListener;
import java.util.Vector;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import net.sf.memoranda.util.CurrentStorage;
import net.sf.memoranda.util.FileStorage;
import net.sf.memoranda.util.IStorage;

/**
 * @author Kevin Bryant - kjbryan1
 *
 */
public class CurrentStorageTest {
	
	private static IStorage testStorage;
	private static Vector testActionListeners;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	    testStorage = new FileStorage();
	    testActionListeners = new Vector();
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
	 * Test method for {@link net.sf.memoranda.util.test.CurrentStorage#get()}.
	 * Gets 'Storage' type
	 */
	@Test
	public void testGet() {
		IStorage temp = new FileStorage();
		assertTrue( temp == testStorage);
		assertTrue( testStorage == CurrentStorage.get() );
	}

	/**
	 * Test method for {@link net.sf.memoranda.util.test.CurrentStorage#set(net.sf.memoranda.util.test.IStorage)}.
	 * Sets 'Storage' type
	 */
	@Test
	public void testSet() {
		IStorage temp = testStorage;
		CurrentStorage.set(temp);
		assertTrue(temp == testStorage);
	}

	/**
	 * Test method for {@link net.sf.memoranda.util.test.CurrentStorage#addChangeListener(java.awt.event.ActionListener)}.
	 */
	@Test
	public void testAddChangeListener() {
		//TODO: cannot instanciate an actionListener
		//fail("TODO: cannot instanciate an actionListener");
	}

	/**
	 * Test method for {@link net.sf.memoranda.util.test.CurrentStorage#getChangeListeners()}.
	 */
	@Test
	public void testGetChangeListeners() {
		//TODO: cannot instanciate an actionListener
		//fail("TODO: cannot instanciate an actionListener");
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
