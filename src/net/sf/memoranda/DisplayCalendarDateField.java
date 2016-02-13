/**
 * DisplayDateTimeField.java : Class that implements the DisplayField interface and serves as an implementation of a generic control
 * for the data type Date. It can is used to display the custom fields from the Task templates which have an undetermined type
 * until they are read from the XML template file.
 * 
 * @author ggoforth -> Galen Goforth -- Email: ghgofort@asu.ed -- 2/11/16
 */
package net.sf.memoranda;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;

/**
 * @author ggoforth
 */
public class DisplayCalendarDateField extends JPanel implements DisplayField {
	/**
	 * Default Serial ID
	 */
	private static final long serialVersionUID = 1L;
	private JLabel lblName;
	private JSpinner spnDate=null;
	/**
	 * 
	 */
	public DisplayCalendarDateField(LayoutManager layout){
		super(layout);
		GridBagConstraints cs = new GridBagConstraints();
	    cs.anchor = GridBagConstraints.WEST;
	    cs.insets = new Insets(5, 5, 5, 5);
		lblName = new JLabel();
		cs.gridx=0;
		cs.gridy=0;
		this.add(lblName, cs);
		spnDate = new JSpinner(new SpinnerDateModel());
		cs.gridx=1;
		this.add(spnDate, cs);	
	}

	/* (non-Javadoc)
	 * @see net.sf.memoranda.DisplayField#setFieldName(java.lang.String)
	 */
	@Override
	public void setFieldName(String name) {
		lblName.setText(name);
	}

	/* (non-Javadoc)
	 * @see net.sf.memoranda.DisplayField#getFieldName()
	 */
	@Override
	public String getFieldName() {
		return lblName.getText();
	}

	/* (non-Javadoc)
	 * @see net.sf.memoranda.DisplayField#createDataControl(java.lang.Object)
	 */
	@Override
	public <T> void createDataControl(T data) {
		if(data!=null){
			spnDate.getModel().setValue((Date)data);
		}
		
	}

	/* (non-Javadoc)
	 * @see net.sf.memoranda.DisplayField#getData()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getData() {
		return (T) spnDate.getModel().getValue();
	}

}
