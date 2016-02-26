/**
 * TaskTemplateManager.java
 * @author ggoforth -> Galen Goforth - Email: ghgofort@asu.edu
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
		if(fields==null) {
			fields = new ArrayList<CustomField<T>>();
		}
		for(int x=0;x<fields.size();x++){
			Element child = new Element("customField");
			Element type = new Element("dataType");
			type.appendChild(fields.get(x).getDataType());
			Element name = new Element("fieldName");
			name.appendChild(fields.get(x).getFieldName());
			child.appendChild(type);
			child.appendChild(name);
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
		if(_nameMap.containsKey(name)) {
			String id = _nameMap.get(name);
			return getTemplate(id);
		}else {
			// Returns null if the name cannot be found in the name map
			return null;
		}
		
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
		Element c = null;
		Elements elements = _root.getChildElements();
		int size = elements.size();
		int x =0;
		while(x<size){
			c = elements.get(x);
			String eleId = c.getAttributeValue("id");
			if(eleId.compareTo(id)!=0){	
				x++;
			}
			else{
				d=c;
				x=size;
			}
		}
		if(d==null){
			return null;
		}
		String name = d.getAttributeValue("name");
		//fields
		tt = new TaskTemplateImpl<T>(id, name);
		Elements children = d.getChildElements("customField");
		for(int y=0;y<d.getChildElements("customField").size();y++){
			Element e = children.get(y);
			String fname="", min="", max="", req="", type="";
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
			if(e.getFirstChildElement("dataType").getValue()!=null);
			type=e.getFirstChildElement("dataType").getValue();
			if(type.compareToIgnoreCase("Integer")==0){
				int d1 = 0;  // This is the default value.  It could be set to change from program
				try{
					//d1 = Integer.parseInt(data);   // Change it with this value that can be stored in XML with template
				}catch(NumberFormatException ex){
					Util.debug("Number Format Exception Handled...Integer field set to 0");
				}
				cf = new CustomField<Integer>(fname, isReq, d1);
			}else if(type.compareToIgnoreCase("CalendarDate")==0){
				CalendarDate cd = new CalendarDate();
				cf = new CustomField<CalendarDate>(fname, isReq, cd);
			}else{
				cf = new CustomField<String>(fname, isReq, "");
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
				_nameMap.remove(d.getAttribute("name").getValue());
				d.removeChildren();
				d.detach();
				x=elements.size()-1; 	// skip to the end of the loop
			}
			x++;
		}		
	}

	/**
	 * Static Method for Saving changes to a Task Template
	 * @param <T>
	 * @param template
	 */
	public static <T> void saveTemplateChanges(TaskTemplateImpl<T> template){
		String id = template.getId();
		Element d = null;
		Elements elements = _root.getChildElements();
		int x=0;
		while(x<elements.size()){
			d = elements.get(x);
			x++;
			if(d.getAttribute("id").getValue().compareTo(id)==0){
				if(d.getAttribute("name").getValue().compareTo(template.getName())!=0){
					_nameMap.remove(d.getAttribute("name").getValue());
					_nameMap.put(template.getName(), id);
					d.getAttribute("name").setValue(template.getName());
				}
				
				d.removeChildren();
				ArrayList<CustomField<T>> fields = template.getFields();
				for(int y=0;y<fields.size();y++){
					Element child = new Element("customField");
					Element type = new Element("dataType");
					type.appendChild(fields.get(y).getDataType());
					Element name = new Element("fieldName");
					name.appendChild(fields.get(y).getFieldName());
					child.appendChild(type);
					child.appendChild(name);
					d.appendChild(child);
				}
			}
		}
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
