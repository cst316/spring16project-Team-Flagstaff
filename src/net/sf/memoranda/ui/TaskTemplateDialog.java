/**
 * Dialog for creating and editing task templates for individualized fields on tasks
 * @author ggoforth - Galen Goforth
 */
package net.sf.memoranda.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import net.sf.memoranda.CustomField;
import net.sf.memoranda.Project;
import net.sf.memoranda.ProjectManager;
import net.sf.memoranda.TaskTemplateImpl;
import net.sf.memoranda.TaskTemplateManager;
import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.util.CurrentStorage;
import net.sf.memoranda.util.Util;

/**
 * @author ggoforth
 * Dialog box added for selection and editing of a template 
 * @param <T>
 */
public class TaskTemplateDialog<T> extends JDialog {

	private ArrayList<CustomField<T>> _customFields=null;
	private String _id;
	private boolean _isEdit;
	private String _title;

	/**
	 * Declare class level variables to keep them accessible
	 */
	private GridBagLayout gridBagLayout;
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
		_id=id;
		_title = title;
		
		setPreferredSize(new Dimension(600, 400));
		setResizable(false);
		setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		setTitle(title);
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
			_isEdit = getExistingTemplate(id);
		}
		else{
			_isEdit=false;
		}
		setFields();
	}

	void initDialog() throws Exception{

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
		btnCancel.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				closeWindow();
			}
		});
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
	}

	protected void closeWindow() {
		this.dispose();
	}

	/**
	 * Save the newly created task template to the XML file using the TaskTemplateManager
	 * and close the dialog returning the newly created template for setting as the selected
	 * value for the new project dialog.
	 * Added by: Galen Goforth on 2/5/16
	 */
	@SuppressWarnings("unchecked")
	protected void storeTemplate() {
		boolean isValid = validateSave();
		if(isValid){
			if(_isEdit){
				ArrayList<String> useTemplate = checkUse();
				if(useTemplate.size()>0){
					String strMessage = "This template is currently used by active projects:\n";
					for(String s:useTemplate){
						strMessage+=(s+"\n");
					}
					strMessage+="This could delete content in these templates or have other unwanted effects.\n \nAre you sure you want to save?";
					if(JOptionPane.showConfirmDialog(this.getContentPane(), strMessage, 
							"Template Still in Use", JOptionPane.YES_NO_OPTION)!=JOptionPane.YES_OPTION){
						return;
					}
				}
				_title = txtTemplateName.getText();
				TaskTemplateImpl<T> tti = new TaskTemplateImpl<T>(_id,_title);
				tti.setFields(_customFields);
				TaskTemplateManager.saveTemplateChanges(tti);
			}
			else{
				ArrayList<String> lstTitles = TaskTemplateManager.getTemplateTitles();
				for(String s: lstTitles){
					if(txtTemplateName.getText().compareTo(s)==0){
						String strMessage = "The Template Title is already in use. Choose a different title to save.";
						lblTemplateName.setForeground(Color.RED);
						JOptionPane.showMessageDialog(this.getContentPane(), strMessage, "Error saving template", JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				TaskTemplateManager.createTemplate(txtTemplateName.getText(),_customFields);
				CurrentStorage.get().storeTemplateManger();
			}
			this.dispose();
		}
	}

	private ArrayList<String> checkUse() {
		@SuppressWarnings("unchecked")
		Vector<Project> projects = ProjectManager.getActiveProjects();
		ArrayList<String> useTemplate = new ArrayList<String>();
		for(int x=0;x<projects.size();x++){
			if(projects.get(x).getTaskTemplate().compareTo(_id)==0){
				useTemplate.add(projects.get(x).getTitle());
			}
		}
		return useTemplate;
	}

	/**
	 * Action event Handler for JButton btnRemoveField - Removes the field from the list
	 * @param e -> Event context
	 */
	protected void btnRemoveField_actionPerformed(ActionEvent e) {
		int x = tblFields.getSelectedRow();
		if(x!=-1){
			_customFields.remove(x);
			setFields();
		}
	}

	protected void btnEditField_actionPerformed(ActionEvent e) {
		

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
		if(!txtTemplateName.getText().isEmpty()) {
			_title = txtTemplateName.getText();
		}
		setFields();
		clearControls();
	}
	
	/**
	 * Clears the controls after a new field is added or a previous field is edited
	 */
	private void clearControls() {
		txtFieldName.setText("");
		chkRequired.setSelected(false);
		spnMin.setValue(0);
		spnMax.setValue(0);
		cbxType.setSelectedIndex(0);
		lblTemplateName.setForeground(Color.black);
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
			_title = ttl.getName();
			
			loadedSuccessfully = true;
		}catch(Exception ex){
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
		Object[] columnNames = {"Field Title", "Data Type", "Required?"};
		DefaultTableModel model = new DefaultTableModel(new Object[0][0], columnNames);
		if(_customFields!=null){			
			for(CustomField<T> c:_customFields){
				titles.add(c.getFieldName());
			}
			for (CustomField<T> fld : _customFields) {
				Object[] o = new Object[3];
				o[0] = fld.getFieldName();
				o[1] = fld.getDataType();
				o[2] = fld.isRequired();
				model.addRow(o);
			}
		}
		tblFields.setModel(model);
		if(!_title.isEmpty()){
			txtTemplateName.setText(_title);
		}
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