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
public interface TaskTemplate<T> {
	public String getId();
	public void setId(String id);
	
	public String getName();
	public void setName(String name);
	
	public ArrayList<CustomField<T>> getFields();
	public void setFields(ArrayList<CustomField<T>> fields);
	 
	public void addField(CustomField<T> field);
	public CustomField<T> getField(int index);
	public void removeField(String fieldId);
}
