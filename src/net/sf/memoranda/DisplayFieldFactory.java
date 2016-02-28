/**
 * DisplayFieldFactory.java - Used for creating specific type instances of the interface DispalyField
 * @author ggoforth - Galen Goforth - Email: ghgofort@asu.edu - 2/11/16
 */
package net.sf.memoranda;

import java.awt.GridBagLayout;

/**
 * @author ggoforth 
 * Class for implementing the 'factory design pattern' for implementations of polymorphic classes
 */
public class DisplayFieldFactory {
	public static IDisplayField createField(String fieldType){
		if(fieldType.compareTo("Integer")==0){
			return new DisplayIntegerField(new GridBagLayout());
		}else if(fieldType.compareTo("CalendarDate")==0){
			return new DisplayCalendarDateField(new GridBagLayout());
		}else{
			return new DisplayStringField(new GridBagLayout());
		}
	}

}
