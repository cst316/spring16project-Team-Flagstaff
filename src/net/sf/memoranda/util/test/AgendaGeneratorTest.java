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

import junit.framework.Assert;
import net.sf.memoranda.Project;
import net.sf.memoranda.Task;
import net.sf.memoranda.TaskList;
import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.util.AgendaGenerator;
import net.sf.memoranda.util.Local;

/**
 * @author Kevin Bryant - kjbryan1
 *
 */
public class AgendaGeneratorTest {

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
	 * Test method for {@link net.sf.memoranda.util.test.AgendaGenerator#generateTasksInfo(net.sf.memoranda.Project, net.sf.memoranda.date.CalendarDate, java.util.Collection)}.
	 */
	@Test
	public void testGenerateTasksInfo() {
		//TODO: Can't instantiate Project p
		//fail("TODO: Can't instantiate Project p");
	}

	/**
	 * Test method for {@link net.sf.memoranda.util.test.AgendaGenerator#getProgress(net.sf.memoranda.TaskList)}.
	 */
	@Test
	public void testGetProgress() {
		//TODO: Can't instantiate TaskList
		//fail("TODO: Can't instanciate TaskList");
	}

	/**
	 * Test method for {@link net.sf.memoranda.util.test.AgendaGenerator#getPriorityString(int)}.
	 * Returns a string for the given priority ...
	 */
	@Test
	public void testGetPriorityString() {
		int testNormal = Task.PRIORITY_NORMAL;
		int testLowest = Task.PRIORITY_LOWEST;
		int testLow = Task.PRIORITY_LOW;
		int testHigh = Task.PRIORITY_HIGH;
		int testHighest = Task.PRIORITY_HIGH;
		
		assertTrue(AgendaGenerator.getPriorityString(testNormal).equals("<font color=\"green\">"+Local.getString("Normal")+"</font>") );
		assertTrue(AgendaGenerator.getPriorityString(testLowest).equals("<font color=\"#666699\">"+Local.getString("Lowest")+"</font>") );
		assertTrue(AgendaGenerator.getPriorityString(testLow).equals("<font color=\"#3333CC\">"+Local.getString("Low")+"</font>") );
		assertTrue(AgendaGenerator.getPriorityString(testHigh).equals("<font color=\"#FF9900\">"+Local.getString("High")+"</font>") );
		assertTrue(AgendaGenerator.getPriorityString(testHighest).equals("<font color=\"red\">"+Local.getString("Highest")+"</font>") );
	}

	/**
	 * Test method for {@link net.sf.memoranda.util.test.AgendaGenerator#generateProjectInfo(net.sf.memoranda.Project, net.sf.memoranda.date.CalendarDate, java.util.Collection)}.
	 */
	@Test
	public void testGenerateProjectInfo() {
		//TODO: Can't instantiate -- Need to crate an overall instance of the program.
		//fail("TODO: Can't instanciate -- Need to crate an overall isntance of the program.");
	}

	/**
	 * Test method for {@link net.sf.memoranda.util.test.AgendaGenerator#generateAllProjectsInfo(net.sf.memoranda.date.CalendarDate, java.util.Collection)}.
	 */
	@Test
	public void testGenerateAllProjectsInfo() {
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link net.sf.memoranda.util.test.AgendaGenerator#generateEventsInfo(net.sf.memoranda.date.CalendarDate)}.
	 */
	@Test
	public void testGenerateEventsInfo() {
		CalendarDate testDate = new CalendarDate();
		testDate = CalendarDate.today();
		CalendarDate testDate2 = new CalendarDate();
		testDate2 = CalendarDate.yesterday(); 
		assertFalse(AgendaGenerator.generateEventsInfo(testDate).equals(AgendaGenerator.generateEventsInfo(testDate2)));
	}

	/**
	 * Test method for {@link net.sf.memoranda.util.test.AgendaGenerator#generateStickers(net.sf.memoranda.date.CalendarDate)}.
	 */
	@Test
	public void testGenerateStickers() {
		CalendarDate testDate = new CalendarDate();
		testDate = CalendarDate.today();
		CalendarDate testDate2 = new CalendarDate();
		testDate2 = CalendarDate.yesterday();
		assertFalse(AgendaGenerator.generateStickers(testDate).equals(AgendaGenerator.generateStickers(testDate2)));
	}

	/**
	 * Test method for {@link net.sf.memoranda.util.test.AgendaGenerator#getAgenda(net.sf.memoranda.date.CalendarDate, java.util.Collection)}.
	 */
	@Test
	public void testGetAgenda() {
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
