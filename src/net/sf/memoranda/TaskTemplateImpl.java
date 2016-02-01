/**
 * 
 */
package net.sf.memoranda;

import java.util.ArrayList;

/**
 * @author ggoforth
 * @param <T>
 *
 */
public class TaskTemplateImpl<T> implements TaskTemplate<T> {

	private String id;
	String name;
	private ArrayList<CustomField<T>> fields;
	
	public TaskTemplateImpl(String id, String name){
		this.id = id;
		this.name = name;
	}
	
	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id=id;
	}

	@Override
	public ArrayList<CustomField<T>> getFields() {
		return fields;
	}

	@Override
	public void setFields(ArrayList<CustomField<T>> fields) {
		this.fields=fields;
		
	}

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
	public void removeField(int index) {
		fields.remove(index);
		
	}

	public boolean saveTemplate(TaskTemplateImpl<T> taskTemp){
		boolean success = false;
		//********Save to XML storage using TaskTemplateManager********
		TaskTemplateManager.removeTemplate(id);
		TaskTemplateManager.createTemplate(id);
		try{
			
			success=true;
			
		}catch(Exception e){
			
		}
		return success;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub		
	}
}
