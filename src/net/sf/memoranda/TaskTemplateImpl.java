/**
 * net.sf.memoranda.TaskTemplateImpl.java
 * @author ggoforth -> Galen Goforth
 * ASURiteId: ghgofort - Email: ghgofort@asu.edu
 * Last updated by: Galen Goforth
 * Last updated on: 2/7/16
 */
package net.sf.memoranda;

import java.util.ArrayList;

/**
 * @author ggoforth
 * @param <T>
 *
 */
public class TaskTemplateImpl<T> implements ITaskTemplate<T> {
	private String id;
	String name;
	private ArrayList<CustomField<T>> fields;

	/**
	 * Constructor takes with 2 arguments
	 * @param String id
	 * @param String name
	 */
	public TaskTemplateImpl(String id, String name){
		this.id = id;
		this.name = name;
		fields = new ArrayList<CustomField<T>>();
	}
	/**
	 * Returns the id for the TaskTemplate object
	 * @return
	 * @see net.sf.memoranda.ITaskTemplate#getId()
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
	public void setFields(ArrayList fields) {
		this.fields=fields;
	}

	/**
	 * adds a CustomField<T> to the custom fields list of the Task Template 
	 * @param field
	 */
	public void addField(CustomField field) {
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

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String name) {
		this.name=name;		
	}

}