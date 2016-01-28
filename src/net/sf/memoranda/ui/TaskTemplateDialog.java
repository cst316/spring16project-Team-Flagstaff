/**
 * 
 */
package net.sf.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

/**
 * @author ggoforth
 *
 */
public class TaskTemplateDialog extends JDialog {

	/**
	 * 
	 */
	  JPanel mPanel = new JPanel(new BorderLayout());
	    JPanel areaPanel = new JPanel(new BorderLayout());
	    JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	    JButton cancelB = new JButton();
	    JButton okB = new JButton();
	    Border border1;
	    Border border2;
	    JPanel dialogTitlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    JLabel header = new JLabel();
	    public boolean CANCELLED = true;
	    JPanel jPanel8 = new JPanel(new GridBagLayout());
	    Border border3;
	    Border border4;
//	    Border border5;
//	    Border border6;
	    JPanel jPanel2 = new JPanel(new GridLayout(3, 2));
	    JTextField todoField = new JTextField();
	    
	    // added by rawsushi
	    JTextField effortField = new JTextField();
	    JTextArea descriptionField = new JTextArea();
	    JScrollPane descriptionScrollPane = new JScrollPane(descriptionField);
	    
//	    Border border7;
	    Border border8;
	    
	    
	private static final long serialVersionUID = 1L;

}
