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
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import net.sf.memoranda.util.Local;
import java.awt.Toolkit;
import java.awt.Dimension;
import javax.swing.border.TitledBorder;
import javax.swing.BorderFactory;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * @author ggoforth
 * Dialog box added for selection and editing of a template 
 */
public class TaskTemplateDialog extends JDialog {

	/**
	 * 
	 */
	JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JLabel header = new JLabel();
	JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	JButton btnCancel = new JButton();
	JButton btnOK = new JButton();
	GridBagConstraints gbc;
	Border border1, border2, border3, border4, border5;
	JPanel dialogTitlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	public boolean CANCELLED = true;
	JPanel jPanel8 = new JPanel(new GridBagLayout());
	JPanel jPanel2 = new JPanel(new GridLayout(3, 2));
	JTextField todoField = new JTextField();
	JTextField effortField = new JTextField();
	JTextField txtFieldName;
	JTextArea descriptionField = new JTextArea();
	JScrollPane descriptionScrollPane = new JScrollPane(descriptionField);

	/*
	 * Constructor for the TaskTemplateDialog class
	 */
	public TaskTemplateDialog(Frame frame, String title){
		super(frame,title,true);
		setPreferredSize(new Dimension(600, 400));
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Edit Template For Project Tasks");
		setIconImage(Toolkit.getDefaultToolkit().getImage(TaskTemplateDialog.class.getResource("/net/sf/memoranda/ui/resources/icons/task_active.png")));
		try{
			initDialog();
			pack();


		}catch(Exception ex){
			new ExceptionDialog(ex);
		}
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

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);

		JPanel pnlMain = new JPanel();
		pnlMain.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		pnlMain.setLayout(null);
		GridBagConstraints gbc_pnlMain = new GridBagConstraints();
		gbc_pnlMain.fill = GridBagConstraints.BOTH;
		gbc_pnlMain.gridx = 0;
		gbc_pnlMain.gridy = 0;
		getContentPane().add(pnlMain, gbc_pnlMain);

		JPanel pnlFields = new JPanel();
		pnlFields.setBounds(339, 48, 245, 250);
		pnlMain.add(pnlFields);
		pnlFields.setLayout(null);

		JTable tblFields = new JTable();
		tblFields.setBounds(0, 0, 245, 250);
		pnlFields.add(tblFields);
		tblFields.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		JPanel pnlFieldInfo = new JPanel();
		pnlFieldInfo.setBorder(new TitledBorder(null, "Field Editor", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlFieldInfo.setBounds(4, 32, 222, 273);
		pnlMain.add(pnlFieldInfo);
		pnlFieldInfo.setLayout(null);

		JLabel lblAddField = new JLabel("Add New Field:");
		lblAddField.setBounds(10, 28, 134, 26);
		lblAddField.setForeground(new Color(0, 0, 102));
		lblAddField.setFont(new Font("Dialog", Font.PLAIN, 20));
		pnlFieldInfo.add(lblAddField);

		JLabel lblFieldName = new JLabel("Field Name:");
		lblFieldName.setBounds(10, 54, 56, 14);
		pnlFieldInfo.add(lblFieldName);

		txtFieldName = new JTextField();
		txtFieldName.setBounds(24, 79, 188, 20);
		txtFieldName.setColumns(10);
		pnlFieldInfo.add(txtFieldName);

		JLabel lblType = new JLabel("Data Type:");
		lblType.setBounds(10, 110, 54, 14);
		pnlFieldInfo.add(lblType);

		JComboBox<?> cbxType = new JComboBox<Object>();
		cbxType.setBounds(24, 135, 188, 20);
		pnlFieldInfo.add(cbxType);

		JCheckBox chkRequired = new JCheckBox("Required Field");
		chkRequired.setBounds(23, 228, 93, 23);
		pnlFieldInfo.add(chkRequired);

		JLabel lblMin = new JLabel("Min:");
		lblMin.setBounds(10, 207, 20, 14);
		pnlFieldInfo.add(lblMin);

		JSpinner spnMin = new JSpinner();
		spnMin.setBounds(43, 201, 62, 20);
		pnlFieldInfo.add(spnMin);

		JLabel lblMax = new JLabel("Max:");
		lblMax.setBounds(115, 207, 24, 14);
		pnlFieldInfo.add(lblMax);

		JSpinner spnMax = new JSpinner();
		spnMax.setBounds(147, 201, 65, 20);
		pnlFieldInfo.add(spnMax);

		JLabel lblMessage = new JLabel("");
		lblMessage.setBounds(10, 166, 46, 14);
		pnlFieldInfo.add(lblMessage);

		JPanel pnlMidBtns = new JPanel();
		pnlMidBtns.setBounds(230, 115, 99, 91);
		pnlMain.add(pnlMidBtns);
		pnlMidBtns.setLayout(null);

		JButton btnAddField = new JButton("Add Field");
		btnAddField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnAddField_actionPerformed(e);
			}
		});
		btnAddField.setBounds(0, 0, 99, 23);
		pnlMidBtns.add(btnAddField);

		JButton btnEditField = new JButton("Edit Field");
		btnEditField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnEditField_actionPerformed(e);
			}
		});
		btnEditField.setBounds(0, 34, 99, 23);
		pnlMidBtns.add(btnEditField);

		JButton btnRemoveField = new JButton("Remove Field");
		btnRemoveField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnRemoveField_actionPerformed(e);
			}
		});
		btnRemoveField.setBounds(0, 68, 99, 23);
		pnlMidBtns.add(btnRemoveField);

		JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pnlButtons.setBounds(339, 327, 245, 33);
		JButton btnCancel = new JButton("Cancel");
		JButton btnOK = new JButton("OK");
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

	protected void btnRemoveField_actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub


	}

	protected void btnEditField_actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	protected void btnAddField_actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	private static final long serialVersionUID = 1L;

}
