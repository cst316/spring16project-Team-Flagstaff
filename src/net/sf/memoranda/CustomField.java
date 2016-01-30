/**
 * @author ggoforth
 * 
 * Defines user defined fields from custom task templates
 * 
 * Created by: Galen Goforth
 * Created on: 1/29/16
 * Created for: CST316 Spring A, Arizona State University CDSE
 */

package net.sf.memoranda;

import net.sf.memoranda.date.CalendarDate;

public class CustomField<T extends Comparable<T>> implements CustomFieldInterface<T> {
	private String fieldName;
	private int id;
	private Class<?> dataType;
	private T data;
	
	public String getFieldName(){return fieldName;}
	public void setFieldName(String fieldName) {this.fieldName=fieldName;}
	
	public int getId(){return id;}
	public void setId(int id){this.id = id;}
	
	public Class<?> getDataType(){
		if (dataType==CalendarDate.class)return String.class;
		else if(dataType==Integer.class)return Integer.class;
		else if(dataType==Double.class)return Double.class;
		else return String.class;
	}
	public void setDataType(Class<?> dataType) {this.dataType = dataType;}
	
	public T getData() {return data;}
	public void setData(T data){this.data = data;}
}
