/**
 * DisplayIntegerField.java -- Defines the class DisplayIntegerField
 * @author ggoforth - Galen Goforth - Email: ghgofort@asu.edu - 2/12/16
 */
package net.sf.memoranda;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.LayoutManager;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;

/**
 * @author ggoforth
 * DisplayIntegerField class is a custom panel that has a JTextArea and a JLabel built in for displaying an integer type data. 
 * The DisplayField interface is implemented so that this field can be dynamically added to meet the needs of an XML
 * defined task template.
 */
public class DisplayIntegerField extends JPanel implements DisplayField {
	/**
	 * Default Serial ID
	 */
	private static final long serialVersionUID = 1L;
	private JLabel lblName = null;
	private JTextArea txtData = null;

	/**
	 * @param gridBagLayout 
	 * 
	 */
	public DisplayIntegerField(LayoutManager layout) {
		super(layout);
		GridBagConstraints cs = new GridBagConstraints();
	    cs.anchor = GridBagConstraints.CENTER;
	    cs.insets = new Insets(0,0,5,0);
		lblName = new JLabel();
		lblName.setPreferredSize(new Dimension(125,20));
		cs.gridx=0;
		cs.gridy=0;
		this.add(lblName, cs);
		cs.insets = new Insets(0,0,0,0);
		txtData = new JTextArea();
		txtData.setPreferredSize(new Dimension(100, 20));
		txtData.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		cs.gridx=1;
		this.add(txtData, cs);	
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
		txtData.setText((String)data.toString());
	}

	/* (non-Javadoc)
	 * @see net.sf.memoranda.DisplayField#getData()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getData() {
		return (T) txtData.getText();
	}

}
