/**
 * CustomFieldPanal.java
 * Added by @author ggoforth -> Galen Goforth 
 * ASURiteId: ghgofort
 * 
 * Added on 2/9/16, 12:59am
 */
package net.sf.memoranda.ui;

import java.awt.LayoutManager;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import net.sf.memoranda.CustomField;
import net.sf.memoranda.date.CalendarDate;

/**
 * @author ggoforth
 * @param <T>
 *
 */
public class CustomFieldPanel<T> extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CustomField<T> customField = null;
	
	/**
	 * Constructor to add all of our fields to the panel
	 */
	public CustomFieldPanel() {
		
	}

	/**
	 * @param layout
	 */
	public CustomFieldPanel(LayoutManager layout) {
		super(layout);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param isDoubleBuffered
	 */
	public CustomFieldPanel(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param layout
	 * @param isDoubleBuffered
	 */
	public CustomFieldPanel(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the customField
	 */
	public CustomField<T> getCustomField() {
		return customField;
	}

	/**
	 * @param customField the customField to set
	 */
	public void setCustomField(CustomField<T> customField) {
		this.customField = customField;
	}

}
