/**
 * @author ggoforth
 * 
 * Class for User defined fields from custom task templates. 
 * 
 */

package net.sf.memoranda;

import java.util.IllegalFormatException;

import net.sf.memoranda.date.CalendarDate;
/**
 * CustomField class holds values that define custom fields that are added to a task template
 * The variable names are the same as the names in the XML Schema for data storage
 * 
 * @author ggoforth -> Galen Goforth
 *
 * @param <T> Generic Type
 */
public class CustomField<T> implements CustomFieldInterface<T>{
	private String fieldName;		
	private int minValue;
	private int maxValue;
	private boolean isRequired;
	private int index;
	private T data;

	/**
	 * Constructor
	 * @param fieldName, String for the name of the custom field
	 * @param isRequired, boolean -> is this a required field?
	 * @param data, <T> -> is this a required field
	 */
	public CustomField(String fieldName, boolean isRequired, T data){
		this.fieldName = fieldName;
		this.isRequired=isRequired;
		this.data=data;
		this.minValue = 0;
		this.maxValue = 0;
	}

	/**
	 * Returns a String name for the data type.
	 * @return String -> name of data type
	 */
	public String getDataType(){
		String r="";
		if(data.getClass()==String.class) r="String";
		else if(data.getClass()==Integer.class)r="Integer";
		else if(data.getClass()==CalendarDate.class)r="CalendarDate";
		return r;
	}
	
	
	/**
	 * Method returns String representation from the data member
	 * @return String 
	 */
	public String dataToString(){
		String r="";
		try{
			if(this.data.getClass() == String.class)
				r= (String)this.data;
			else if(this.data.getClass() == Integer.class)
				r= Integer.toString((Integer) this.data);
			else if(this.data.getClass()==CalendarDate.class)r= this.data.toString();
			else r = this.data.toString();
		}catch(IllegalFormatException | ClassCastException e){
			System.out.print(
					"[Debug] IllegalFormatException \n[Debug]"+e.getLocalizedMessage());
		}
		return r;
	}
		
	/**
	 * Gets the name of the CustomField Object
	 * @return fieldName String
	 */
	public String getFieldName(){return fieldName;}
	/**
	 * Sets the name of the CustomField object
	 * @param fieldName String
	 */
	public void setFieldName(String fieldName) {this.fieldName=fieldName;}
	/**
	 * Gets the data member of a CustomField object
	 * @return data T
	 */
	public T getData() {return data;}
	/**
	 * Set the data member of the CustomField
	 * @param data T
	 */
	public void setData(T data){this.data = data;}

	/**
	 * If there is a minimum value set for this CustomField then this returns it.  
	 *  ***NOTE: if the data type is String then this represents the minimum # of characters that can be used
	 * @return int
	 */
	public int getMin() {return minValue;}
	/**
	 * If the instance has a min value than this is what that value is set to.
	 * NOTE: if the data type is String then this represents the min # of characters that can be used
	 * @param int
	 */
	public void setMin(int min) {minValue = min;}

	/**
	 * If there is a maximum value set for this CustomField then this returns it.  
	 *  ***NOTE: if the data type is String then this represents the max # of characters that can be used
	 * @return int
	 */
	public int getMax() {return maxValue;}
	/**
	 * If the instance has a max value than this is what that value is set to.
	 *  ***NOTE: if the data type is String then this represents the max # of characters that can be used
	 * @return int
	 */
	public void setMax(int max) {this.maxValue = max;}
	/**
	 * @return boolean
	 */
	public boolean isRequired() {return isRequired;}

}