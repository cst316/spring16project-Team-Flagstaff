/**
 * 
 */
package net.sf.memoranda.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import net.sf.memoranda.TaskTemplate;
import net.sf.memoranda.TaskTemplateImpl;
import net.sf.memoranda.TaskTemplateManager;
import net.sf.memoranda.CustomField;

/**
 * @author ggoforth
 * Unit tests for the TaskTemplateManager class
 */
public class TaskTemplateManagerTest {
@SuppressWarnings({ "rawtypes", "unused" })
private static ArrayList<CustomField> arrayCustom = null;
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
	 * @param <T>
	 * @throws java.lang.Exception
	 */
	@Before
	public <T> void setUp() throws Exception {
		ArrayList<CustomField<T>> arrayCustom = new ArrayList<CustomField<T>>();
		for(int x = 0;x<4;x++) {
			String templateName = "my template name "+x;
			@SuppressWarnings("unchecked")
			CustomField<T> cf = new CustomField<T>(templateName, false, (T) "Test String Data");
			arrayCustom.add(cf);
		}
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link net.sf.memoranda.TaskTemplateManager#createTemplate(java.lang.String, java.lang.String, java.util.ArrayList)}.
	 * @param <T>
	 */
	@SuppressWarnings("unchecked")
	@Test
	public <T> void testCreateTemplateStringStringArrayListOfCustomFieldOfT() {
		TaskTemplate<T> ttl = null;
		String testId = "TestId";
		String templateName = "templName";
		ttl = (TaskTemplate<T>)TaskTemplateManager.createTemplate(testId, templateName, arrayCustom);
		
		/* Method needs to be finished*/
		
		assertTrue(true);
	}

	/**
	 * Test method for {@link net.sf.memoranda.TaskTemplateManager#createTemplate(java.lang.String, java.util.ArrayList)}.
	 */
	@Test
	public void testCreateTemplateStringArrayListOfCustomFieldOfT() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link net.sf.memoranda.TaskTemplateManager#createTemplate(java.lang.String)}.
	 */
	@Test
	public void testCreateTemplateString() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link net.sf.memoranda.TaskTemplateManager#getTemplateTitles()}.
	 */
	@Test
	public void testGetTemplateTitles() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link net.sf.memoranda.TaskTemplateManager#getDefaultTemplate()}.
	 */
	@Test
	public void testGetDefaultTemplate() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link net.sf.memoranda.TaskTemplateManager#getTemplateFromName(java.lang.String)}.
	 */
	@Test
	public void testGetTemplateFromName() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link net.sf.memoranda.TaskTemplateManager#getIdFromName(java.lang.String)}.
	 */
	@Test
	public void testGetIdFromName() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link net.sf.memoranda.TaskTemplateManager#getTemplate(java.lang.String)}.
	 */
	@Test
	public void testGetTemplate() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link net.sf.memoranda.TaskTemplateManager#removeTemplate(java.lang.String)}.
	 */
	@Test
	public void testRemoveTemplate() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link net.sf.memoranda.TaskTemplateManager#saveTemplateChanges(net.sf.memoranda.TaskTemplateImpl)}.
	 */
	@Test
	public void testSaveTemplateChanges() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link net.sf.memoranda.TaskTemplateManager#addTemplateListener(net.sf.memoranda.TaskTemplateListener)}.
	 */
	@Test
	public void testAddTemplateListener() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link net.sf.memoranda.TaskTemplateManager#getTemplateListeners()}.
	 */
	@Test
	public void testGetTemplateListeners() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link net.sf.memoranda.TaskTemplateManager#addNotify(java.lang.String)}.
	 */
	@Test
	public void testAddNotify() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link net.sf.memoranda.TaskTemplateManager#removeNotify(java.lang.String)}.
	 */
	@Test
	public void testRemoveNotify() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link net.sf.memoranda.TaskTemplateManager#modNotify(java.lang.String)}.
	 */
	@Test
	public void testModNotify() {
		fail("Not yet implemented");
	}

}
