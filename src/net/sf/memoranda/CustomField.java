/**
 * 
 */
package net.sf.memoranda;

import java.util.HashMap;
/**
 * @author ggoforth
 *
 */
public class CustomField{
	// Maps the string stored in the xml to an integer value that is used in a case statement to determine data type
	private static final HashMap<Integer, String> typeMap = new HashMap<Integer, String>(){
		{
			put(1,"Integer");
			put(2, "String");
			put(3, "DateTime");
			put(4, "US Dollars");
		}
	};
	// Set the class level variables
	private int id;
	private String dataType;
	private String fieldName;
	private Object fieldData;
	
	// Set the accessors and mutators
	public String getDataType(){return dataType;}
	public void setDataTyep(String dataType){this.dataType = dataType;}
	
	public String getFieldName(){return fieldName;}
	public void setFieldName(String fieldName){this.fieldName = fieldName;}
	
	public int getId(){return id;}
	public void setId(int id){this.id = id;}
	
	//private void <T> setField(<T> T fieldData){this.fieldData = fieldData;}
	
}
