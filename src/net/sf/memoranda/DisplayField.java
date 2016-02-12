/**
 * 
 */
package net.sf.memoranda;

import javax.swing.JLabel;

/**
 * @author ggoforth
 *
 */
public interface DisplayField{
	public void setFieldName(String name);
	public String getFieldName();
	public <T> void createDataControl(T data);
	public <T> T getData();
}
