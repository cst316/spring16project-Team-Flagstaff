/**
 * TaskTemplate.java
 * Created 1.26.16 10:43pm MST
 * @author Galen Goforth - ghgofort@asu.edu
 * Arizona State University - CST 316 - online
 * 
 */

package net.sf.memoranda;

public interface TaskTemplate{
	
	
	/**
	 * Returns the some collection of custom fields
	 * @return Collection
	 */
	public <T extends Comparable<T>> CustomField<T> getCustomField(int i);
	public <T extends Comparable<T>> void setCustomField(CustomField<T> field,int i);
}

