/**
 * 
 */
package net.sf.memoranda;

import java.util.ArrayList;
import java.util.Hashtable;

import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.util.CurrentStorage;
import net.sf.memoranda.util.Local;
import net.sf.memoranda.util.Util;
import nu.xom.Attribute;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;

/**
 * @author ggoforth -> Galen Goforth on 2/1/16
 *
 * Handles IO operations for the Task Templates 
 */
public class TaskTemplateManager {
	public static Document _doc=null;
	static Element _root = null;
	static ArrayList<TaskTemplateListener> _templateListeners=new ArrayList<TaskTemplateListener>();
	
	 /* 
	 * Hashtable of "task" XOM elements for mapping the names of the templates to the template id
	 * (ID => element) 
	 */
	private static Hashtable<String, String> _nameMap; 
	
	static {
		_nameMap = new Hashtable<String, String>();
		init();
	}

	/**
	 * Helper method for initiating the needed class members
	 */
	public static void init(){
		CurrentStorage.get().openTemplateManger();
		if (_doc == null) {
			_root = new Element("templateList");
			_doc = new Document(_root);
			createTemplate("__default", Local.getString("Default Template"), null);
		}
		else{
			_root = _doc.getRootElement();
			Elements elements = _root.getChildElements("taskTemplate");
			if(elements!=null){
				for(int x=0;x<elements.size();x++){
					_nameMap.put((elements.get(x).getAttributeValue("name")!=null)?elements.get(x).getAttributeValue("name"): "NAME...ERROR", elements.get(x).getAttributeValue("id"));
				}
			}
		}
	}

	/**
	 * Creates a TaskTemplate in Local Storage and then returns an instance of the
	 * TaskTemplate class that represents the new template
	 * 
	 * @param id
	 * @param templateName
	 * @param fields
	 * @return
	 */
	public static <T> TaskTemplate<T> createTemplate(String id, String templateName, ArrayList<CustomField<T>> fields) {
		Element el = new Element("taskTemplate");
		el.addAttribute(new Attribute("id", id));
		el.addAttribute(new Attribute("name", templateName));
		// Loop through the CustomFields and add the values to the XML element for the task template
		for(int x=0;x<fields.size();x++){
			Element child = new Element("customField");
			Element type = new Element("dataType");
			type.appendChild(fields.get(x).getDataType());
			Element name = new Element("fieldName");
			name.appendChild(fields.get(x).getFieldName());
			Element data = new Element("data");
			name.appendChild(fields.get(x).dataToString());
			child.appendChild(type);
			child.appendChild(name);
			child.appendChild(data);
			el.appendChild(child);
		}
		_root.appendChild(el);
		TaskTemplate<T> tt = new TaskTemplateImpl<T>(id, templateName);
		tt.setFields(fields);
		_nameMap.put(templateName, id);
		return tt;
	}
	/**
	 * Creates a TaskTemplate in Local Storage and then returns an instance of the
	 * TaskTemplate class that represents the new template
	 * 
	 * @param templateName
	 * @param fields
	 * @return TaskTemplate<T>
	 */
	public static <T> TaskTemplate<T> createTemplate(String templateName, ArrayList<CustomField<T>> fields) {
		TaskTemplate<T> tt = createTemplate(Util.generateId(),templateName,fields);
		return tt;
	}

	/**
	 * Creates a TaskTemplate in Local Storage and then returns an instance of the
	 * TaskTemplate class that represents the new template
	 * 
	 * @param templateName
	 * @return TaskTemplate<T>
	 */
	public static <T> TaskTemplate<T> createTemplate(String templateName) {
		ArrayList<CustomField<T>> fields = new ArrayList<CustomField<T>>();
		TaskTemplate<T> tt = createTemplate(Util.generateId(),templateName, fields);
		return tt;
	}	

	/**
	 * Returns a list of the template names for display
	 * @return ArrayList<String> : A list of title names
	 */
	public static ArrayList<String> getTemplateTitles(){
		ArrayList<String> names = new ArrayList<String>();
		Elements elements = _root.getChildElements();
		for(int x=0;x<elements.size();x++){
			names.add(elements.get(x).getAttributeValue("name"));
		}
		return names;
	}

	/**
	 * Returns the default object of type: TaskTemplate from XML storage
	 * @return TaskTemplate
	 */
	public static <T> TaskTemplate<T> getDefaultTemplate(){
		TaskTemplate<T> ttDefault=null;
		ttDefault = getTemplate("__default");
		if(ttDefault==null){
			ArrayList<CustomField<T>> fields = new ArrayList<CustomField<T>>();
			String title = "__default";
			ttDefault = createTemplate(title, "Default Template", fields);
		}
		return ttDefault;
	}

	/**
	 * Returns an object that implements the TaskTemplate interface from the given name
	 * @param String name
	 * @return TaskTemplate 
	 */
	public static <T> TaskTemplate<T> getTemplateFromName(String name){
		String id = _nameMap.get(name);
		return getTemplate(id);
	}

	/**
	 * Returns the id lookup from the name to id map
	 * @param name
	 * @return 
	 */
	public static String getIdFromName(String name){
		return _nameMap.get(name);
	}

	/**
	 * Returns an object that implements the TaskTemplate interface from the given id.
	 * TaskTemplateImpl implements the TaskTemplate interface and is the intended return type for the generic
	 * 
	 * @param String id
	 * @return TaskTemplate 
	 */
	@SuppressWarnings("unchecked")
	public static <T> TaskTemplate<T> getTemplate(String id){
		TaskTemplate<T> tt = null;
		Element d = null;
		Elements elements = _root.getChildElements();

		for(int x =0;x<elements.size();x++){
			if(elements.get(x).getAttribute("id").getValue().equals(id))
				d = elements.get(x);
			x++;
		}
		String name = d.getAttributeValue("name");
		ArrayList<CustomField<T>> fields = new ArrayList<CustomField<T>>();
		//fields
		tt = new TaskTemplateImpl<T>(id, name);
		Elements children = d.getChildElements("customField");
		int intSize1 = d.getChildCount();
		int intSize2 = d.getChildElements("customField").size();
		for(int y=0;y<d.getChildElements("customField").size();y++){
			Element e = children.get(y);
			String fname="", min="", max="", req="", data="", type="";
			boolean isReq =(req.compareToIgnoreCase("true")==0)? true:false;
			CustomField<?> cf=null;
			if(e.getFirstChildElement("fieldName").getValue()!=null)
				fname = e.getFirstChildElement("fieldName").getValue();
			if(e.getAttributeValue("minValue")!=null)
				min = e.getAttributeValue("minValue");
			if(e.getAttributeValue("maxValue")!=null)
				max = e.getAttributeValue("maxValue");
			if(e.getAttributeValue("isRequired")!=null)
				req = e.getAttributeValue("isRequired");
			if(e.getFirstChildElement("data").getValue()!=null)
				data = e.getFirstChildElement("data").getValue();
			if(e.getFirstChildElement("dataType").getValue()!=null);
			type=e.getFirstChildElement("dataType").getValue();
			if(type.compareToIgnoreCase("Integer")==0){
				int d1 = 0;
				try{
					d1 = Integer.parseInt(data);
				}catch(NumberFormatException ex){
					Util.debug("Number Format Exception Handled...Integer field set to 0");
				}
				cf = new CustomField<Integer>(fname, isReq, d1);
			}else if(type.compareToIgnoreCase("CalendarDate")==0){
				CalendarDate cd = new CalendarDate(data);
				cf = new CustomField<CalendarDate>(fname, isReq, cd);
			}else{
				cf = new CustomField<String>(fname, isReq, data);
			}

			tt.addField((CustomField<T>) cf);
		}
		return tt;
	}

	/**
	 * Removes the task template from local storage
	 * @param id
	 */
	public static void removeTemplate(String id){
		Element d = null;
		Elements elements = _root.getChildElements();
		int x=0;
		while(x<elements.size()){
			if(elements.get(x).getAttribute("id").getValue().equals(id)){
				d = elements.get(x);
				x = elements.size()-1;
			}
			x++;
		}
		d.removeChildren();
		d.detach();
	}

	/**
	 * Static Method for Saving changes to a Task Template
	 * @param <T>
	 */
	public static <T> void saveTemplateChanges(String id){
		@SuppressWarnings("unchecked")
		TaskTemplateImpl<T> tti = (TaskTemplateImpl<T>) getTemplate(id); 
		//ttDefault = getTemplate("__default");
	}

	/**
	 * Adds a TaskTemplateListener to the list of methods to be notified if there are changes made to the task templates list in storage
	 * @param listener
	 */
	public static void addTemplateListener(TaskTemplateListener listener){
		_templateListeners.add(listener);
	}

	/**
	 * Returns a list of the methods to be notified if there are changes made to the task templates list in storage
	 * @return
	 */
	public static ArrayList<TaskTemplateListener> getTemplateListeners(){
		return _templateListeners;
	}

	/**
	 * Notify the template listener methods that there was a new template added to the template list
	 * @param newId
	 */
	public static void addNotify(String newId){
		for (int i = 0; i < _templateListeners.size(); i++) {
			_templateListeners.get(i).TaskTemplateAdded(newId);         
		}
	}

	/**
	 * Notify the template listener methods that there was a template removed from the list
	 * @param removeId
	 */
	public static void removeNotify(String removedId){
		for (int i = 0; i < _templateListeners.size(); i++) {
			_templateListeners.get(i).TaskTemplateRemoved(removedId);         
		}
	}

	/**
	 * Notify the template listener methods that there was a change to a template in the list
	 * @param modId
	 */
	public static void modNotify(String modId){
		for (int i = 0; i < _templateListeners.size(); i++) {
			_templateListeners.get(i).TaskTemplateChanged(modId);         
		}
	}

	// *******************************************************************************************
	// ---------------------------   Private Methods Below Here   --------------------------------
	// *******************************************************************************************

	/**
	 * Create the hash table for efficient lookup of templates from name
	 */
	private static void setTable(){

	}
}
