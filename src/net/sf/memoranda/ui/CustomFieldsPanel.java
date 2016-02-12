/**
 * CustomFieldPanal.java
 * Added by @author ggoforth -> Galen Goforth 
 * ASURiteId: ghgofort
 * 
 * Added on 2/9/16, 12:59am
 */
package net.sf.memoranda.ui;

import java.awt.LayoutManager;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerDateModel;

import net.sf.memoranda.CustomField;
import net.sf.memoranda.DisplayField;
import net.sf.memoranda.date.CalendarDate;

/**
 * @author ggoforth
 * @param <T>
 *
 */
public class CustomFieldsPanel<T> extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<CustomField<T>> customFields=null;
	private ArrayList<DisplayField> customPanels=null;	
	
	/**
	 * Constructor to add all of our fields to the panel
	 */
	public CustomFieldsPanel() {
		customFields = new ArrayList<CustomField<T>>();

	}
	/**
	 * Add a field to the panel
	 * @param field
	 */
	public void addField(CustomField<T> field){
		// Add the label for the field
		
		
		if(field.getData().getClass() == String.class){
			if(field.getData()!=null){
				field.dataToString();
			}
		}else if(field.getData().getClass()== Integer.class){
			
		}else{
			
		}
	}
	
	/**
	 * @param layout
	 */
	public CustomFieldsPanel(LayoutManager layout) {
		super(layout);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param isDoubleBuffered
	 */
	public CustomFieldsPanel(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param layout
	 * @param isDoubleBuffered
	 */
	public CustomFieldsPanel(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the customField
	 */
	public ArrayList<CustomField<T>> getCustomFields() {
		return customFields;
	}

	/**
	 * @param customField the customField to set
	 */
	public void setCustomFields(ArrayList<CustomField<T>> customFields) {
		this.customFields = customFields;
	}
	/**
	 * @return the customPanels
	 */
	public ArrayList<DisplayField> getCustomPanels() {
		return customPanels;
	}
	/**
	 * @param customPanels the customPanels to set
	 */
	public void setCustomPanels(ArrayList<DisplayField> customPanels) {
		this.customPanels = customPanels;
	}

}
