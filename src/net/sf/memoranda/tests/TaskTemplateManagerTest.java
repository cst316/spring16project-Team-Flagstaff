/**
 * 
 */
package net.sf.memoranda.tests;

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
import net.sf.memoranda.util.Util;
import net.sf.memoranda.CustomField;

/**
 * @author ggoforth
 * Unit tests for the TaskTemplateManager class
 * @param <T>
 */
public class TaskTemplateManagerTest<T> {

	// Declare class members
	private ArrayList<CustomField<T>> arrayCustom = null;
	private TaskTemplate<T> ttl = null;
	private String testId = Util.generateId();
	private String templateName = "templName";
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
		// Create an instance of the ArrayList<CustomField<T>> and populate it with 4 CustomFields
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
	@SuppressWarnings({ "unchecked", "hiding" })
	@Test
	public <T> void testCreateTemplateStringStringArrayListOfCustomFieldOfT() {
		ttl = TaskTemplateManager.createTemplate(testId, templateName, arrayCustom);
		boolean success = (ttl!=null)? true:false;
		assertTrue(success);
		if(ttl.getFields().size()!=4) {
			success = false;
		}
	}

	/**
	 * Test method for {@link net.sf.memoranda.TaskTemplateManager#createTemplate(java.lang.String, java.util.ArrayList)}.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testCreateTemplateStringArrayListOfCustomFieldOfT() {
		ttl = TaskTemplateManager.createTemplate(templateName, arrayCustom);
		boolean success = (ttl!=null)? true:false;
		if(ttl.getFields().size()!=4) {
			success = false;
		}
		assertTrue(success);
	}

	/**
	 * Test method for {@link net.sf.memoranda.TaskTemplateManager#createTemplate(java.lang.String)}.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testCreateTemplateString() {
		ttl = TaskTemplateManager.createTemplate(templateName);
		boolean success = (ttl!=null)? true:false;
		assertTrue(success);
	}

	/**
	 * Test method for {@link net.sf.memoranda.TaskTemplateManager#getTemplateTitles()}.
	 */
	@Test
	public void testGetTemplateTitles() {
		for(int x=0;x<3;x++) {
			
		}
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
