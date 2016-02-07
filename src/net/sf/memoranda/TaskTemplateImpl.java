/**
 * net.sf.memoranda.TaskTemplateImpl.java
 * @author ggoforth -> Galen Goforth
 * ASURiteId: ghgofort - Email: ghgofort@asu.edu
 * Last updated by: Galen Goforth
 * Last updated on: 2/7/16
 */
package net.sf.memoranda;

import java.util.ArrayList;
import java.util.Hashtable;

import nu.xom.Document;
import nu.xom.Element;

/**
 * @author ggoforth
 * @param <T>
 *
 */
public class TaskTemplateImpl<T> implements TaskTemplate<T> {
	private String id;
	String name;
	private ArrayList<CustomField<T>> fields;
	
	/**
	 * Constructor taking 3 parameters for root element
	 * @param Element root -> root element from XML 
	 */
	public TaskTemplateImpl(){
	}
	/**
	 * Constructor takes with 2 arguments
	 * @param String id
	 * @param String name
	 */
	public TaskTemplateImpl(String id, String name){
		this.id = id;
		this.name = name;
	}
	/**
	 * Returns the id for the TaskTemplate object
	 * @return
	 * @see net.sf.memoranda.TaskTemplate#getId()
	 */
	@Override
	public String getId() {
		return id;
	}
	/**
	 * Sets the id for the Task Template
	 * @param id
	 */
	@Override
	public void setId(String id) {
		this.id=id;
	}
	/**
	 * Returns the list of the custom fields from the template
	 * @return
	 */
	@Override
	public ArrayList<CustomField<T>> getFields() {
		return fields;
	}
	/**
	 * Sets the list of custom fields
	 * @param fields
	 */
	@Override
	public void setFields(ArrayList<CustomField<T>> fields) {
		this.fields=fields;
	}
	/**
	 * adds a CustomField<T> to the custom fields list of the Task Template 
	 * @param field
	 */
	@Override
	public void addField(CustomField<T> field) {
		fields.add(field);
		
	}
	/**
	 * Returns the CustomField object at the given index
	 */
	public CustomField<T> getField(int index) {
		CustomField<T> field = fields.get(index);
		return field;
	}
	
	@Override
	public void removeField(String index) {
		fields.remove(index);
		
	}

	public boolean saveTemplate(TaskTemplateImpl<T> taskTemp){
		boolean success = false;
		//********Save to XML storage using TaskTemplateManager********
		TaskTemplateManager.saveTemplateChanges(this.id);
		try{
			
			success=true;
			
		}catch(Exception e){
			
		}
		return success;
	}
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String name) {
		this.name=name;		
	}
}
