/**
 * 
 */
package net.sf.memoranda;

import javax.swing.JPanel;

/**
 * @author ggoforth
 *
 */
public class DisplayStringField extends JPanel implements DisplayField{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String _name;
	private String _data;
	
	
	@Override
	public void setFieldName(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getFieldName() {
		return _name;
	}

	@Override
	public <T> void createDataControl(T data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T> T getData() {
		// TODO Auto-generated method stub
		return null;
	}
}
