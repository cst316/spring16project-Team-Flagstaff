/**
 * 
 */
package net.sf.memoranda.ui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import net.sf.memoranda.CustomField;
import net.sf.memoranda.TaskTemplateImpl;
import net.sf.memoranda.TaskTemplateManager;
import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.util.CurrentStorage;
import net.sf.memoranda.util.Local;
import net.sf.memoranda.util.Util;

import java.awt.Toolkit;
import java.awt.Dimension;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.TableView.TableRow;
import javax.swing.BorderFactory;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

/**
 * @author ggoforth
 * Dialog box added for selection and editing of a template 
 * @param <T>
 */
public class TaskTemplateDialog<T> extends JDialog {

	private ArrayList<CustomField<T>> _customFields=null;
	private String _id;

	/**
	 * Declare class level variables to keep them accessible
	 */
	private GridBagLayout gridBagLayout;
	private Border border1, border2, border3, border4, border5;
	private JTextField txtFieldName, txtTemplateName;
	private JPanel pnlMain, pnlFields, pnlFieldInfo, pnlMidBtns, pnlButtons, pnlTemplateName;
	private JTable tblFields;
	private JLabel lblAddField, lblFieldName, lblType, lblMin, lblMax, lblMessage, lblTemplateName;
	private JComboBox<String> cbxType;
	private JCheckBox chkRequired;
	private GridBagConstraints gbc_pnlMain;
	private JSpinner spnMin, spnMax;
	private JButton btnAddField, btnEditField, btnRemoveField, btnOK, btnCancel;
	private JScrollPane scrollPane;

	/**
	 * Constructor for the TaskTemplateDialog class
	 * @param Frame frame
	 * @param String title
	 * @param String id
	 */
	public TaskTemplateDialog(Frame frame, String title, String id){		
		super(frame,title,true);
		
		// Initialize list of CustomField objects in order to avoid null reference
		_customFields = new ArrayList<CustomField<T>>();
		setPreferredSize(new Dimension(600, 400));
		setResizable(false);
		setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		setTitle("Edit Template For Project Tasks");
		setIconImage(Toolkit.getDefaultToolkit().getImage(TaskTemplateDialog.class.getResource("/net/sf/memoranda/ui/resources/icons/task_active.png")));
		// Setup the GUI components 
		try{
			initDialog();
			pack();
		}catch(Exception ex){
			new ExceptionDialog(ex);
		}
		if(id.compareTo("")!=0)
		{
			getExistingTemplate(id);
		}
		setFields();
	}

	void initDialog() throws Exception{
		border1 = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		border2 = BorderFactory.createEtchedBorder(Color.white, 
				new Color(142, 142, 142));
		border3 = new TitledBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0), 
				Local.getString("Task Template"), TitledBorder.LEFT, TitledBorder.BELOW_TOP);
		border4 = BorderFactory.createEmptyBorder(0, 5, 0, 5);
		border5 = BorderFactory.createEtchedBorder(Color.white, 
				new Color(178, 178, 178));

		gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);

		pnlMain = new JPanel();
		pnlMain.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		pnlMain.setLayout(null);
		gbc_pnlMain = new GridBagConstraints();
		gbc_pnlMain.fill = GridBagConstraints.BOTH;
		gbc_pnlMain.gridx = 0;
		gbc_pnlMain.gridy = 0;
		getContentPane().add(pnlMain, gbc_pnlMain);

		pnlFields = new JPanel();
		pnlFields.setBounds(339, 11, 245, 287);
		pnlMain.add(pnlFields);
		pnlFields.setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 245, 287);
		pnlFields.add(scrollPane);

		tblFields = new JTable();
		scrollPane.setViewportView(tblFields);
		tblFields.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		tblFields.setShowHorizontalLines(true);
		tblFields.setShowVerticalLines(false);	

		pnlFieldInfo = new JPanel();
		pnlFieldInfo.setBorder(new TitledBorder(null, "Field Editor", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlFieldInfo.setBounds(4, 48, 222, 312);
		pnlMain.add(pnlFieldInfo);
		pnlFieldInfo.setLayout(null);

		lblAddField = new JLabel("Add New Field:");
		lblAddField.setBounds(10, 17, 134, 26);
		lblAddField.setForeground(new Color(0, 0, 102));
		lblAddField.setFont(new Font("Dialog", Font.BOLD, 14));
		pnlFieldInfo.add(lblAddField);

		lblFieldName = new JLabel("Field Name:");
		lblFieldName.setBounds(10, 54, 76, 26);
		pnlFieldInfo.add(lblFieldName);

		txtFieldName = new JTextField();
		txtFieldName.setBounds(24, 79, 188, 20);
		txtFieldName.setColumns(10);
		pnlFieldInfo.add(txtFieldName);

		lblType = new JLabel("Data Type:");
		lblType.setBounds(10, 110, 76, 26);
		pnlFieldInfo.add(lblType);

		cbxType = new JComboBox<String>();
		cbxType.setBounds(24, 135, 188, 20);
		cbxType.addItem("String");
		cbxType.addItem("Integer");
		cbxType.addItem("CalendarDate");
		pnlFieldInfo.add(cbxType);

		chkRequired = new JCheckBox("Required Field");
		chkRequired.setBounds(10, 247, 93, 23);
		pnlFieldInfo.add(chkRequired);

		lblMin = new JLabel("Min:");
		lblMin.setBounds(10, 179, 46, 26);
		pnlFieldInfo.add(lblMin);

		spnMin = new JSpinner();
		spnMin.setBounds(24, 204, 62, 20);
		pnlFieldInfo.add(spnMin);

		lblMax = new JLabel("Max:");
		lblMax.setBounds(120, 179, 46, 26);
		pnlFieldInfo.add(lblMax);

		spnMax = new JSpinner();
		spnMax.setBounds(130, 204, 65, 20);
		pnlFieldInfo.add(spnMax);

		lblMessage = new JLabel("");
		lblMessage.setBounds(10, 166, 46, 14);
		pnlFieldInfo.add(lblMessage);

		pnlMidBtns = new JPanel();
		pnlMidBtns.setBounds(230, 115, 99, 91);
		pnlMain.add(pnlMidBtns);
		pnlMidBtns.setLayout(null);

		btnAddField = new JButton("Add Field");
		btnAddField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnAddField_actionPerformed(e);
			}
		});
		btnAddField.setBounds(0, 0, 99, 23);
		pnlMidBtns.add(btnAddField);

		btnEditField = new JButton("Edit Field");
		btnEditField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnEditField_actionPerformed(e);
			}
		});
		btnEditField.setBounds(0, 34, 99, 23);
		pnlMidBtns.add(btnEditField);

		btnRemoveField = new JButton("Remove Field");
		btnRemoveField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnRemoveField_actionPerformed(e);
			}
		});
		btnRemoveField.setBounds(0, 68, 99, 23);
		pnlMidBtns.add(btnRemoveField);

		pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pnlButtons.setBounds(339, 327, 245, 33);
		btnCancel = new JButton("Cancel");
		btnOK = new JButton("Save");
		btnOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				storeTemplate();
			}
		});
		pnlButtons.add(btnCancel);
		pnlButtons.add(btnOK);
		pnlMain.add(pnlButtons);

		pnlTemplateName = new JPanel();
		pnlTemplateName.setBounds(4, 11, 283, 26);
		pnlMain.add(pnlTemplateName);
		pnlTemplateName.setLayout(null);

		txtTemplateName = new JTextField();
		txtTemplateName.setBounds(122, 0, 161, 20);
		pnlTemplateName.add(txtTemplateName);
		txtTemplateName.setColumns(10);

		lblTemplateName = new JLabel("Template Name:");
		lblTemplateName.setForeground(new Color(0, 0, 102));
		lblTemplateName.setFont(new Font("Dialog", Font.BOLD, 14));
		lblTemplateName.setBounds(0, -1, 129, 20);
		pnlTemplateName.add(lblTemplateName);
		/*topPanel.setBorder(new EmptyBorder(new Insets(0, 5, 0, 5)));
	        topPanel.setBackground(Color.WHITE);        
	        header.setFont(new java.awt.Font("Dialog", 0, 20));
	        header.setForeground(new Color(0, 0, 124));
	        header.setText(Local.getString("Task Template"));
	        //header.setHorizontalAlignment(SwingConstants.CENTER);

		 * Needs to be changed to a new icon for Task Template

	        header.setIcon(new ImageIcon(net.sf.memoranda.ui.ProjectDialog.class.getResource(
	            "resources/icons/project48.png")));
	        topPanel.add(header);*/


	}

	/**
	 * Save the newly created task template to the XML file using the TaskTemplateManager
	 * and close the dialog returning the newly created template for setting as the selected
	 * value for the new project dialog.
	 * Added by: Galen Goforth on 2/5/16
	 */
	protected void storeTemplate() {
		boolean isValid = validateSave();
		if(isValid){
			TaskTemplateManager.createTemplate(txtTemplateName.getText(),_customFields);
			CurrentStorage.get().storeTemplateManger();
			this.dispose();
		}
	}

	/**
	 * Action event Handler for JButton btnRemoveField - Removes the field from the list
	 * @param e -> Event context
	 */
	protected void btnRemoveField_actionPerformed(ActionEvent e) {
		((DefaultTableModel)tblFields.getModel()).removeRow(tblFields.getSelectedRow());
	}

	protected void btnEditField_actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
	/**
	 * Action handler method for action event from the 'Add Field' JButton -> btnAddField
	 * @param e -> event context
	 */
	@SuppressWarnings("unchecked")
	protected void btnAddField_actionPerformed(ActionEvent e) {
		if(validateFields()){
			CustomField<T> field = null;
			if(cbxType.getSelectedIndex()==1){
				field = (CustomField<T>) new CustomField<Integer>(txtFieldName.getText(), chkRequired.isSelected(), new Integer(0));
			}else if(cbxType.getSelectedIndex()==2){
				field = (CustomField<T>) new CustomField<CalendarDate>(txtFieldName.getText(), chkRequired.isSelected(), new CalendarDate());
			}else{
				field = (CustomField<T>) new CustomField<String>(txtFieldName.getText(), chkRequired.isSelected(), "");
			}
			_customFields.add(field);
		}
		setFields();
	}
	
	/**
	 * Gets an existing TaskTemplate for editing and populates the GUI controls from template values
	 * @param id
	 */
	private boolean getExistingTemplate(String id){
		boolean loadedSuccessfully = false;
		try{
			@SuppressWarnings("unchecked")
			TaskTemplateImpl<T> ttl = (TaskTemplateImpl<T>)TaskTemplateManager.getTemplate(id);
			_customFields = ttl.getFields();
			loadedSuccessfully = false;
		}
		catch(Exception ex){
			Util.debug("***[Debug] There was an error loading the selected Template:\n"
					+ ex.getLocalizedMessage().toString());
		}
		return loadedSuccessfully;
	}
	
	/**
	 * Refresh the fields Table when a new field added
	 */
	private void setFields() {
		ArrayList<String> titles = new ArrayList<String>();
		if(_customFields!=null){
			
		}
		for(CustomField<T> c:_customFields){
			titles.add(c.getFieldName());
		}
		Object[] columnNames = {"Field Title", "Data Type", "Required?"};
		DefaultTableModel model = new DefaultTableModel(new Object[0][0], columnNames);
		for (CustomField<T> fld : _customFields) {
			Object[] o = new Object[3];
			o[0] = fld.getFieldName();
			o[1] = fld.getDataType();
			o[2] = fld.isRequired();
			model.addRow(o);
		}
		tblFields.setModel(model);
	}
	/**
	 * Helper method for field validation before adding a new field to the template
	 * @return boolean -> True if valid
	 */
	private boolean validateFields(){
		boolean isValid = false;
		Color color = Color.RED;
		if(!txtFieldName.getText().isEmpty()){
			isValid = true;
		}
		else{
			lblFieldName.setForeground(color);
			txtFieldName.grabFocus();
		}
		return isValid;
	}
	/**
	 * Validates the data in the Template design before attempting to save
	 * @return boolean isValid
	 */
	private boolean validateSave() {
		String strMessage;
		boolean isValid = true;
		if(txtTemplateName.getText().isEmpty() || txtTemplateName.getText().length()<3){
			isValid=false;
			strMessage = "The Template Title must be at least 3 characters.";
			lblTemplateName.setForeground(Color.RED);
			JOptionPane.showMessageDialog(this.getContentPane(), strMessage, "Error saving template", JOptionPane.ERROR_MESSAGE);
		}
		return isValid;
	}

	private static final long serialVersionUID = 1L;
}
