/**
 * 
 */
package net.sf.memoranda;

/**
 * @author ggoforth
 * 
 * Interface for custom fields used in custom Task templates
 * We created a separate interface so that it would be easier to add more 
 * data types or recreate this data type for different data storage later on.
 * 
 * Created by: Galen Goforth
 * Created on: 1/29/16
 * Created for: CST316 Spring A, Arizona State University CDSE
 */
public interface CustomFieldInterface <T extends Comparable<T>>{
	
	public String getFieldName();
	public void setFieldName(String fieldName);
	
	public int getId();
	public void setId(int id);
	
	public Class<?> getDataType();
	public void setDataType(Class<?> dataType);
	
	public T getData();
	public void setData(T data);
}
