/**
 * DisplayStringField.java
 * @author ghgoforth - Galen Goforth 2/15/16
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
 *
 */
public class DisplayStringField extends JPanel implements IDisplayField{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel lblName;
	private JTextArea txtData;
	
	
	public DisplayStringField(LayoutManager layout) {
		super(layout);
		
		GridBagConstraints cs = new GridBagConstraints();
	    cs.anchor = GridBagConstraints.CENTER;
	    cs.insets = new Insets(5, 5, 5, 5);
		lblName = new JLabel();
		lblName.setPreferredSize(new Dimension(125,20));
		cs.gridx=0;
		cs.gridy=0;
		this.add(lblName, cs);
		txtData = new JTextArea();
		txtData.setPreferredSize(new Dimension(100, 20));
		cs.gridx=1;
		txtData.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		this.add(txtData, cs);	
	}

	@Override
	public void setFieldName(String name) {
		lblName.setText(name);
	}

	@Override
	public String getFieldName() {
		return lblName.getText();
	}

	@Override
	public <T> void createDataControl(T data) {
		txtData.setText(data.toString());		
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getData() {
		return (T) txtData.getText();
	}
}