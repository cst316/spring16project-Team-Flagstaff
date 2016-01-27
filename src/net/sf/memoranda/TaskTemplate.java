/**
 * TaskTemplate.java
 * Created 1.26.16 10:43pm MST
 * @author Galen Goforth - ghgofort@asu.edu
 * Arizona State University - CST 316 - online
 * 
 */

package net.sf.memoranda;

import java.util.Collection;

public interface TaskTemplate{
	
	
	/**
	 * Returns the some collection of custom fields
	 * @return Collection
	 */
	public CustomField getCustomField(int i);
	public void setCustomFields(CustomField field,int i);
}

