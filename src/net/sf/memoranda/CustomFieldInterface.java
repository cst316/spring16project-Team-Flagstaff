/**
 * 
 */
package net.sf.memoranda;

/**
 * @author ggoforth
 * 
 * Interface for custom fields used in custom Task templates
 * This defines the interactions that the class will use
 * 
 * Created by: Galen Goforth
 * Created on: 1/29/16
 * Created for: CST316 Spring A, Arizona State University CDSE
 * 
 * Extends the Comparable interface
 * @param <T>
 */
public interface CustomFieldInterface<T>{
	
	public String getFieldName();
	public void setFieldName(String fieldName);

	public T getData();
	public void setData(T data);
	
	public int getMin();
	public void setMin(int min);
	public int getMax();
	public void setMax(int min);
	
	public boolean isRequired();
}
