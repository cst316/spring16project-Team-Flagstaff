/**
 * DisplayField.java -- Interface that defines the interactions of the DisplayField type.  These fields are used to display
 * CustomField types from the user defined task templates.
 * 
 * @author ggoforth - Galen Goforth - Email: ghgofort@asu.edu - 2/12/16
 */
package net.sf.memoranda;

import javax.accessibility.Accessible;

/**
 * @author ggoforth
 * Interface for Generic type display fields that have a label and a field.
 */
public interface DisplayField extends Accessible{
	/**
	 * Set the label text for the field name
	 * @param name
	 */
	public void setFieldName(String name);
	/**
	 * Returns the text in the field name label
	 * @return
	 */
	public String getFieldName();
	/**
	 * Sets the data in the given Field
	 * @param data
	 */
	public <T> void createDataControl(T data);
	/**
	 * Returns the data in the given field
	 * @return
	 */
	public <T> T getData();
}
