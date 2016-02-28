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

import net.sf.memoranda.ITaskTemplate;
import net.sf.memoranda.TaskTemplateImpl;
import net.sf.memoranda.ITaskTemplateListener;
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
	@SuppressWarnings("rawtypes")
	private static ITaskTemplate ttl = null;
	private static ArrayList<String> testIds = new ArrayList<String>();
	private static ArrayList<String> testNames = new ArrayList<String>();
	private static String templateName = "templName";
	boolean addListen = true;
	boolean removeListen = true;
	boolean changeListen = true;
	String notifyMessage = "";	
	
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
	@SuppressWarnings("unchecked")
	@Before
	public <T> void setUp() throws Exception {
		// Create an instance of the ArrayList<CustomField<T>> and populate it with 4 CustomFields
		for(int x = 0;x<4;x++) {
			String name = "testTemplate"+x;
			String id = Util.generateId();
			// Add the name and id to arrays for testing the accessor methods for id and name
			testNames.add(name);
			testIds.add(id);
			
		}
		// Add a listener for testing the observer pattern 
		TaskTemplateManager.addTemplateListener(new ITaskTemplateListener(){

			@Override
			public void TaskTemplateChanged(String id) {
				// set static variables to check against in unit tests
				changeListen = true;
				notifyMessage = id;
			}

			@Override
			public void TaskTemplateAdded(String id) {
				addListen = true;
				notifyMessage = id;
			}

			@Override
			public void TaskTemplateRemoved(String id) {
				removeListen = true;
				notifyMessage = id;
			}

		});
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		// Remove the templates that we have created for the testing purposes
		for(String id: testIds) {
			if(TaskTemplateManager.getTemplate(id)!=null) {
				TaskTemplateManager.removeTemplate(id);
			}
		}
	}

	/**
	 * Test method for {@link net.sf.memoranda.TaskTemplateManager#createTemplate(java.lang.String, java.lang.String, java.util.ArrayList)}.
	 * @param <T>
	 */
	@SuppressWarnings({ "hiding", "unchecked" })
	@Test
	public <T> void testCreateTemplateStringStringArrayListOfCustomFieldOfT() {
	// Create a field array for the template
		ArrayList<CustomField<T>> arrayCustom = new ArrayList<CustomField<T>>();
		String id = Util.generateId();
		testIds.add(id);
		testNames.add(templateName);
		for(int x = 0;x<4;x++) {
			String templateName = "templateName"+x;
			@SuppressWarnings("unchecked")
			CustomField<T> cf = new CustomField<T>(templateName, false, (T) "Test String Data");
			arrayCustom.add(cf);
		}
		ttl = TaskTemplateManager.createTemplate(id, templateName, arrayCustom);
		boolean success = (ttl!=null)? true:false;
		if(ttl.getFields().size()!=4) {
			success = false;
		}
		assertTrue(success);
	}

	/**
	 * Test method for {@link net.sf.memoranda.TaskTemplateManager#createTemplate(java.lang.String, java.util.ArrayList)}.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testCreateTemplateStringArrayListOfCustomFieldOfT() {
		// Create a field array for the template
		ArrayList<CustomField<T>> arrayCustom = new ArrayList<CustomField<T>>();
		for(int x = 0;x<4;x++) {
			String templateName = "templateName"+x;
			@SuppressWarnings("unchecked")
			CustomField<T> cf = new CustomField<T>(templateName, false, (T) "Test String Data");
			arrayCustom.add(cf);
		}
		testNames.add(templateName);
		try {
			setUp();
		} catch (Exception e) {
			e.printStackTrace();
		}
		ttl = TaskTemplateManager.createTemplate(templateName, arrayCustom);
		boolean success = (ttl!=null)? true:false;
		if(ttl.getFields().size()!=4) {
			success = false;
		}else {
			testIds.add(ttl.getId());
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
		testIds.add(ttl.getId());
		assertTrue(success);
	}

	/**
	 * Test method for {@link net.sf.memoranda.TaskTemplateManager#getTemplateTitles()}.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testGetTemplateTitles() {
		ArrayList<String> getTitles = TaskTemplateManager.getTemplateTitles();
		boolean hasTitles = (!getTitles.isEmpty());
		assertTrue(hasTitles);
	}

	/**
	 * Test method for {@link net.sf.memoranda.TaskTemplateManager#getDefaultTemplate()}.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testGetDefaultTemplate() {
		ttl = TaskTemplateManager.getDefaultTemplate();
		boolean success = false;
		if(ttl.getName().compareTo("Default Template")==0) {
			success = true;
		}
		assertTrue(success);
	}

	/**
	 * Test method for {@link net.sf.memoranda.TaskTemplateManager#getTemplateFromName(java.lang.String)}.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testGetTemplateFromName() {
		TaskTemplateManager.createTemplate("New Template");
		boolean success = true;
		ttl = TaskTemplateManager.getTemplateFromName("New Template");
		if(ttl==null) {
			success = false;
		}else {
			ttl = TaskTemplateManager.getTemplateFromName("NameDoesntExist");
			if(ttl!=null) {
				success = false;
			}
		}
		assertTrue(success);
	}

	/**
	 * Test method for {@link net.sf.memoranda.TaskTemplateManager#getIdFromName(java.lang.String)}.
	 */
	@Test
	public void testGetIdFromName() {
		boolean success = true;
		String name = "testName";
		ttl = TaskTemplateManager.createTemplate(name);
		ttl = TaskTemplateManager.getTemplateFromName(name);
		if(ttl==null) {
			success = false;
		}
		assertTrue(success);
	}

	/**
	 * Test method for {@link net.sf.memoranda.TaskTemplateManager#getTemplate(java.lang.String)}.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testGetTemplate() {
		boolean success = true;
		ttl = TaskTemplateManager.createTemplate("testName");
		String id = ttl.getId();
		ttl = TaskTemplateManager.getTemplate(id);
		if(ttl==null) {
			success = false;
		}
		assertTrue(success);
	}

	/**
	 * Test method for {@link net.sf.memoranda.TaskTemplateManager#removeTemplate(java.lang.String)}.
	 */
	@Test
	public void testRemoveTemplate() {
		boolean success = true;
		for(String id: testIds) {
			TaskTemplateManager.removeTemplate(id);
		}
		for(String id: testIds) {
			ttl = TaskTemplateManager.getTemplate(id);
			if(TaskTemplateManager.getTemplate(id)!=null) {
				success = false;
			}
		}
		assertTrue(success);
	}

	/**
	 * Test method for {@link net.sf.memoranda.TaskTemplateManager#saveTemplateChanges(net.sf.memoranda.TaskTemplateImpl)}.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testSaveTemplateChanges() {
		boolean success = true;
		testNames = new ArrayList<String>();
		for(int x=0;x<testIds.size();x++) {
				ttl = TaskTemplateManager.getTemplate(testIds.get(x));
				if(ttl!=null) {
					ttl.addField(new CustomField<T>(templateName+x, false, (T) "Test String Data"));
					testNames.remove(ttl.getName());
					ttl.setName("modified"+x);
					testNames.add("modified"+x);
					TaskTemplateManager.saveTemplateChanges((TaskTemplateImpl) ttl);
				}
		}
		for(int x=0;x<testNames.size();x++) {
			ttl = TaskTemplateManager.getTemplateFromName(testNames.get(x));
			if(ttl!=null) {
				// if the template is not null then we check to see if the field was added successfully 
				arrayCustom = ttl.getFields();
				boolean newFieldFound = false;
				for(int y=0;y<arrayCustom.size();y++) {
					if(arrayCustom.get(y).getFieldName().equals(templateName)) {
						newFieldFound = true;
					}
				}
				if(!newFieldFound) {
					success = false;
				}
			}else {
				success = false;
			}
		}
		assertTrue(success);
	}

	/**
	 * Test method for {@link net.sf.memoranda.TaskTemplateManager#addTemplateListener(net.sf.memoranda.ITaskTemplateListener)}.
	 * Test method for {@link net.sf.memoranda.TaskTemplateManager#getTemplateListeners()}.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@Test
	public void testAddGetTemplateListener() {
		// Get the subscribed listener list
		ArrayList<ITaskTemplateListener> testListeners = TaskTemplateManager.getTemplateListeners();
		int count = testListeners.size();
		boolean success = true;
		// Add a listener for testing the observer pattern
		TaskTemplateManager.addTemplateListener(new ITaskTemplateListener(){

			@Override
			public void TaskTemplateChanged(String id) {
				Util.debug("[DEBUG] Task Template Changed listener reached");
				changeListen = true;
			}

			@Override
			public void TaskTemplateAdded(String id) {
				Util.debug("[DEBUG] Task Template Added listener reached");
				addListen = true;
			}

			@Override
			public void TaskTemplateRemoved(String id) {
				Util.debug("[DEBUG] Task Template Removed listener reached");
				removeListen = true;
			}
		});
		// Get the subscribed listener list again and make sure new item was added
		testListeners = TaskTemplateManager.getTemplateListeners();
		if(count != (testListeners.size()-1)) {
			success = false;
		}
		assertTrue(success);
	}

	/**
	 * Test method for {@link net.sf.memoranda.TaskTemplateManager#addNotify(java.lang.String)}.
	 */
	@Test
	public void testAddNotify() {
		String test = "Test Add";
		TaskTemplateManager.addNotify(test);
		assertTrue(addListen);
		assertTrue(notifyMessage.compareTo(test)==0);
	}

	/**
	 * Test method for {@link net.sf.memoranda.TaskTemplateManager#removeNotify(java.lang.String)}.
	 */
	@Test
	public void testRemoveNotify() {
		String test = "Test Add";
		TaskTemplateManager.removeNotify(test);
		assertTrue(addListen);
		assertTrue(notifyMessage.compareTo(test)==0);
	}

	/**
	 * Test method for {@link net.sf.memoranda.TaskTemplateManager#modNotify(java.lang.String)}.
	 */
	@Test
	public void testModNotify() {
		String test = "Test Add";
		TaskTemplateManager.modNotify(test);
		if(notifyMessage.compareTo(test)!=0) {
			addListen = false;
		}
		assertTrue(addListen);
	}
}
