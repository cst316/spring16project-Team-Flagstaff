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
import net.sf.memoranda.util.Local;
import net.sf.memoranda.util.Util;

import java.awt.Toolkit;
import java.awt.Dimension;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
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
	
	private TaskTemplateImpl<T> tti=null;
	
	/**
	 * Declare class level variables to keep them accessible
	 */
	GridBagLayout gridBagLayout;
	Border border1, border2, border3, border4, border5;
	JTextField txtFieldName;
	JPanel pnlMain, pnlFields, pnlFieldInfo, pnlMidBtns, pnlButtons;
	JTable tblFields;
	JLabel lblAddField, lblFieldName, lblType, lblMin, lblMax, lblMessage;
	JComboBox<String> cbxType;
	JCheckBox chkRequired;
	GridBagConstraints gbc_pnlMain;
	JSpinner spnMin, spnMax;
	JButton btnAddField, btnEditField, btnRemoveField;
	
	/**
	 * Constructor for the TaskTemplateDialog class
	 * @param Frame frame
	 * @param String title
	 * @param String id
	 */
	@SuppressWarnings("unchecked")
	public TaskTemplateDialog(Frame frame, String title, String id){
		super(frame,title,true);
		setPreferredSize(new Dimension(600, 400));
		setResizable(false);
		setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		setTitle("Edit Template For Project Tasks");
		setIconImage(Toolkit.getDefaultToolkit().getImage(TaskTemplateDialog.class.getResource("/net/sf/memoranda/ui/resources/icons/task_active.png")));
		try{
			initDialog();
			pack();
		}catch(Exception ex){
			new ExceptionDialog(ex);
		}
		try{
			tti = (TaskTemplateImpl<T>)TaskTemplateManager.getTemplate(id);
		}catch(Exception e){
			Util.debug("[Debug]*** Error loading the Task Template");
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
		pnlFields.setBounds(339, 48, 245, 250);
		pnlMain.add(pnlFields);
		pnlFields.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 245, 250);
		pnlFields.add(scrollPane);
		
				tblFields = new JTable();
				scrollPane.setViewportView(tblFields);
				tblFields.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
				tblFields.setShowHorizontalLines(true);
				tblFields.setShowVerticalLines(false);	

		pnlFieldInfo = new JPanel();
		pnlFieldInfo.setBorder(new TitledBorder(null, "Field Editor", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlFieldInfo.setBounds(4, 32, 222, 273);
		pnlMain.add(pnlFieldInfo);
		pnlFieldInfo.setLayout(null);

		lblAddField = new JLabel("Add New Field:");
		lblAddField.setBounds(10, 28, 134, 26);
		lblAddField.setForeground(new Color(0, 0, 102));
		lblAddField.setFont(new Font("Dialog", Font.PLAIN, 20));
		pnlFieldInfo.add(lblAddField);

		lblFieldName = new JLabel("Field Name:");
		lblFieldName.setBounds(10, 54, 56, 14);
		pnlFieldInfo.add(lblFieldName);

		txtFieldName = new JTextField();
		txtFieldName.setBounds(24, 79, 188, 20);
		txtFieldName.setColumns(10);
		pnlFieldInfo.add(txtFieldName);

		lblType = new JLabel("Data Type:");
		lblType.setBounds(10, 110, 54, 14);
		pnlFieldInfo.add(lblType);

		cbxType = new JComboBox<String>();
		cbxType.setBounds(24, 135, 188, 20);
		cbxType.addItem("String");
		cbxType.addItem("Integer");
		cbxType.addItem("CalendarDate");
		pnlFieldInfo.add(cbxType);

		chkRequired = new JCheckBox("Required Field");
		chkRequired.setBounds(23, 228, 93, 23);
		pnlFieldInfo.add(chkRequired);

		lblMin = new JLabel("Min:");
		lblMin.setBounds(10, 207, 20, 14);
		pnlFieldInfo.add(lblMin);

		spnMin = new JSpinner();
		spnMin.setBounds(43, 201, 62, 20);
		pnlFieldInfo.add(spnMin);

		lblMax = new JLabel("Max:");
		lblMax.setBounds(115, 207, 24, 14);
		pnlFieldInfo.add(lblMax);

		spnMax = new JSpinner();
		spnMax.setBounds(147, 201, 65, 20);
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
		JButton btnCancel = new JButton("Cancel");
		JButton btnOK = new JButton("Save");
		btnOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		pnlButtons.add(btnCancel);
		pnlButtons.add(btnOK);
		pnlMain.add(pnlButtons);
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
	 * Action event Handler for JButton btnRemoveField - Removes the field from the list
	 * @param e -> Event context
	 */
	protected void btnRemoveField_actionPerformed(ActionEvent e) {
		tblFields.getSelectedRow();
		
		
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
			tti.addField(field);
		}
		setFields();
	}
	/**
	 * Refresh the fields Table when a new field added
	 */
	@SuppressWarnings("unchecked")
	private void setFields() {
		if(tti==null){
			tti = (TaskTemplateImpl<T>) TaskTemplateManager.getDefaultTemplate();
		}
		ArrayList<CustomField<T>> fields = tti.getFields();
		ArrayList<String> titles = new ArrayList<String>();
		for(CustomField<T> c:fields){
			titles.add(c.getFieldName());
		}
		Object[] columnNames = {"Field Title", "Data Type", "Required?"};
		DefaultTableModel model = new DefaultTableModel(new Object[0][0], columnNames);
        for (CustomField<T> fld : fields) {
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
	
	private static final long serialVersionUID = 1L;
}
