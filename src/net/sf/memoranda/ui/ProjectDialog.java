package net.sf.memoranda.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.SpinnerDateModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.sf.memoranda.Project;
import net.sf.memoranda.ProjectManager;
import net.sf.memoranda.TaskTemplateManager;
import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.util.CurrentStorage;
import net.sf.memoranda.util.Local;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

/*$Id: ProjectDialog.java,v 1.26 2004/10/18 19:09:10 ivanrise Exp $*/
public class ProjectDialog extends JDialog {
    public boolean CANCELLED = true;
    public static boolean taskTemplateMod = false;
    boolean ignoreStartChanged = false;
    boolean ignoreEndChanged = false;
    CalendarFrame endCalFrame = new CalendarFrame();
    CalendarFrame startCalFrame = new CalendarFrame();
    GridBagConstraints gbc;
    private GridBagConstraints gbc_1;
    JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel header = new JLabel();
    JPanel centerPanel = new JPanel(new GridBagLayout());
    JLabel titleLabel = new JLabel();
    public JTextField prTitleField = new JTextField();
    JLabel sdLabel = new JLabel();
    public JSpinner startDate = new JSpinner(new SpinnerDateModel());
    JButton sdButton = new JButton();
    public JCheckBox endDateChB = new JCheckBox();
    public JSpinner endDate = new JSpinner(new SpinnerDateModel());
    JButton edButton = new JButton();
    //public JCheckBox freezeChB = new JCheckBox();
    JPanel bottomPanel = new JPanel();
    /*
     * Added by ggoforth 12/27/16 for access dialog to edit the task template per project needs
     */
    JButton btnModTask = new JButton();
    JButton okButton = new JButton();
    JButton cancelButton = new JButton();
    JPanel pnlSelectTemplate = new JPanel();
    JList<String> lstTemplateList = new JList<String>();
    JPanel pnlTaskButtons = new JPanel();
    JPanel pnlTemlateList = new JPanel();
    JScrollPane jspTemplateList = new JScrollPane(lstTemplateList);
    JButton btnAddTemplate = new JButton("Add Template");
    JButton btnRemoveTask = new JButton("Remove Template");
    
    public ProjectDialog(Frame frame, String title) {
        super(frame, title, true);
        try {
            jbInit();
            pack();
        }
        catch(Exception ex) {
            new ExceptionDialog(ex);
        }
    }

    void jbInit() throws Exception {
    	
    	setListItems("__default");
    	pnlTaskButtons.add(btnAddTemplate);
    	pnlTaskButtons.add(btnRemoveTask);
	this.setResizable(false);
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0};
        gridBagLayout.columnWeights = new double[]{1.0};
        getContentPane().setLayout(gridBagLayout);
        topPanel.setBorder(new EmptyBorder(new Insets(0, 5, 0, 5)));
        topPanel.setBackground(Color.WHITE);        
        header.setFont(new java.awt.Font("Dialog", 0, 20));
        header.setForeground(new Color(0, 0, 124));
        header.setText(Local.getString("Project"));
        //header.setHorizontalAlignment(SwingConstants.CENTER);
        header.setIcon(new ImageIcon(net.sf.memoranda.ui.ProjectDialog.class.getResource(
            "resources/icons/project48.png")));
        topPanel.add(header);
        
        centerPanel.setBorder(new EtchedBorder());
        titleLabel.setText(Local.getString("Title"));
        gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 5;
        gbc.insets = new Insets(5, 10, 5, 10);
        //gbc.anchor = GridBagConstraints.WEST;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        centerPanel.add(titleLabel, gbc);
        
        //prTitleField.setPreferredSize(new Dimension(270, 20));
        gbc = new GridBagConstraints();
        gbc.gridwidth = 5;
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 10, 5, 0);
        //gbc.anchor = GridBagConstraints.EAST;
        gbc.anchor = GridBagConstraints.CENTER;
        centerPanel.add(prTitleField, gbc);
        
        sdLabel.setText(Local.getString("Start date"));
        sdLabel.setPreferredSize(new Dimension(70, 20));
        sdLabel.setMinimumSize(new Dimension(70, 20));
        sdLabel.setMaximumSize(new Dimension(70, 20));
        gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.insets = new Insets(5, 10, 10, 10);
        centerPanel.add(sdLabel, gbc);

        startDate.setPreferredSize(new Dimension(80, 20));
        startDate.setLocale(Local.getCurrentLocale());
		//Added by (jcscoobyrs) on 17-Nov-2003 at 14:24:43 PM
		//---------------------------------------------------
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf = (SimpleDateFormat)DateFormat.getDateInstance(DateFormat.SHORT);
		startDate.setEditor(new JSpinner.DateEditor(startDate, 
			sdf.toPattern()));
		//---------------------------------------------------
        startDate.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                if (ignoreStartChanged) return;
                ignoreStartChanged = true;
                Date sd = (Date) startDate.getModel().getValue();
                if (endDate.isEnabled()) {
                  Date ed = (Date) endDate.getModel().getValue();
                  if (sd.after(ed)) {
                    startDate.getModel().setValue(ed);
                    sd = ed;
                  }
                }
                startCalFrame.cal.set(new CalendarDate(sd));
                ignoreStartChanged = false;
            }
        });
        gbc = new GridBagConstraints();
        gbc.gridx = 1; gbc.gridy = 2;
        gbc.insets = new Insets(5, 0, 10, 5);
        centerPanel.add(startDate, gbc);
        
        sdButton.setMinimumSize(new Dimension(20, 20));
        sdButton.setPreferredSize(new Dimension(20, 20));
        sdButton.setIcon(new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/calendar.png")));
        sdButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sdButton_actionPerformed(e);
            }
        });
        gbc = new GridBagConstraints();
        gbc.gridx = 2; gbc.gridy = 2;
        gbc.insets = new Insets(5, 0, 10, 25);
        gbc.anchor = GridBagConstraints.WEST;
        centerPanel.add(sdButton, gbc);
        
        endDateChB.setForeground(Color.gray);
        endDateChB.setText(Local.getString("End date"));
        endDateChB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                endDateChB_actionPerformed(e);
            }
        });
        gbc = new GridBagConstraints();
        gbc.gridx = 3; gbc.gridy = 2;
        gbc.insets = new Insets(5, 0, 10, 5);
        gbc.anchor = GridBagConstraints.WEST;
        centerPanel.add(endDateChB, gbc);
        
        endDate.setEnabled(false);
        endDate.setPreferredSize(new Dimension(80, 20));
        endDate.setLocale(Local.getCurrentLocale());
		//Added by (jcscoobyrs) on 17-Nov-2003 at 14:24:43 PM
		//---------------------------------------------------
		endDate.setEditor(new JSpinner.DateEditor(endDate, 
			sdf.toPattern()));
		//---------------------------------------------------
        endDate.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                if (ignoreEndChanged) return;
                ignoreEndChanged = true;
                Date sd = (Date) startDate.getModel().getValue();
                Date ed = (Date) endDate.getModel().getValue();
                if (sd.after(ed)) {
                    endDate.getModel().setValue(sd);
                    ed = sd;
                }
                endCalFrame.cal.set(new CalendarDate(ed));
                ignoreEndChanged = false;
            }
        });
        //((JSpinner.DateEditor) endDate.getEditor()).setLocale(Local.getCurrentLocale());
        gbc = new GridBagConstraints();
        gbc.gridx = 4; gbc.gridy = 2;
        gbc.insets = new Insets(5, 0, 10, 5);
        gbc.anchor = GridBagConstraints.WEST;
        centerPanel.add(endDate, gbc);
        
        edButton.setEnabled(false);
        edButton.setMinimumSize(new Dimension(20, 20));
        edButton.setMaximumSize(new Dimension(20, 20));
        edButton.setPreferredSize(new Dimension(20, 20));
        edButton.setIcon(new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/calendar.png")));
        edButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                edButton_actionPerformed(e);
            }
        });
        gbc = new GridBagConstraints();
        gbc.gridx = 5; gbc.gridy = 2;
        gbc.insets = new Insets(5, 0, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        centerPanel.add(edButton, gbc);
        
        bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        
        okButton.setMaximumSize(new Dimension(100, 25));
        okButton.setMinimumSize(new Dimension(100, 25));
        okButton.setPreferredSize(new Dimension(100, 25));
        okButton.setText(Local.getString("Ok"));
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                okButton_actionPerformed(e);
            }
        });
        this.getRootPane().setDefaultButton(okButton);
        cancelButton.setMaximumSize(new Dimension(100, 25));
        cancelButton.setMinimumSize(new Dimension(100, 25));
        cancelButton.setPreferredSize(new Dimension(100, 25));
        cancelButton.setText(Local.getString("Cancel"));
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancelButton_actionPerformed(e);
            }
        });
        bottomPanel.add(okButton);
        bottomPanel.add(cancelButton);
        
        gbc_1 = new GridBagConstraints();
        gbc_1.insets = new Insets(0, 0, 5, 0);
        gbc_1.gridx = 0; gbc_1.gridy = 0;
        gbc_1.fill = GridBagConstraints.HORIZONTAL;
        getContentPane().add(topPanel, gbc_1);
        
        gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.insets = new Insets(5, 5, 5, 0);
        getContentPane().add(centerPanel, gbc);
        
        GridBagConstraints gbc_pnlSelectTemplate = new GridBagConstraints();
        gbc_pnlSelectTemplate.insets = new Insets(0, 0, 5, 0);
        gbc_pnlSelectTemplate.fill = GridBagConstraints.BOTH;
        gbc_pnlSelectTemplate.gridx = 0;
        gbc_pnlSelectTemplate.gridy = 2;
        getContentPane().add(pnlSelectTemplate, gbc_pnlSelectTemplate);
        pnlSelectTemplate.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        //pnlTemlateList.add(lstTemplateList);
        lstTemplateList.setBackground(new Color(255, 255, 255));
        lstTemplateList.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        lstTemplateList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lstTemplateList.setSize(150,250 );
        pnlSelectTemplate.add(pnlTaskButtons);
        pnlTaskButtons.add(btnModTask);
        
        // Modify task button
        btnModTask.setMaximumSize(new Dimension(200, 25));
        btnModTask.setMinimumSize(new Dimension(200, 25));
        btnModTask.setPreferredSize(new Dimension(150, 25));
        btnModTask.setText("Modify Task Template");
        FlowLayout flowLayout = (FlowLayout) pnlTemlateList.getLayout();
        flowLayout.setAlignOnBaseline(true);
        
        pnlSelectTemplate.add(pnlTemlateList);
        btnModTask.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	modTaskButton_actionPerformed(e);
            }
        });
        
        gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.insets = new Insets(5, 0, 0, 0);
        gbc.anchor = GridBagConstraints.EAST;
        getContentPane().add(bottomPanel, gbc);
    
        startCalFrame.cal.addSelectionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (ignoreStartChanged)
                    return;
                startDate.getModel().setValue(startCalFrame.cal.get().getCalendar().getTime());
            }
        });
        endCalFrame.cal.addSelectionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (ignoreEndChanged)
                    return;
                endDate.getModel().setValue(endCalFrame.cal.get().getCalendar().getTime());
            }
        });
    }
    /**
     * Event handler for Modify Task Template button
     * @param e
     */
    void modTaskButton_actionPerformed(ActionEvent e) {
		this.setVisible(false);
    	newTaskTemplate();
		
	}

	void okButton_actionPerformed(ActionEvent e) {
        CANCELLED = false;
        this.dispose();
    }
    
    void cancelButton_actionPerformed(ActionEvent e) {
        this.dispose();
    }
    
    void endDateChB_actionPerformed(ActionEvent e) {
        endDate.setEnabled(endDateChB.isSelected());
        edButton.setEnabled(endDateChB.isSelected());
        if (endDateChB.isSelected()) {
            endDateChB.setForeground(Color.BLACK);
            endDate.getModel().setValue(startDate.getModel().getValue());
        }
        else endDateChB.setForeground(Color.GRAY);
    }
    
    void sdButton_actionPerformed(ActionEvent e) {
        //startCalFrame.setLocation(sdButton.getLocation());
        startCalFrame.setLocation(0, 0);
        startCalFrame.setSize((this.getContentPane().getWidth() / 2), 
            this.getContentPane().getHeight());
        this.getLayeredPane().add(startCalFrame);
        startCalFrame.setTitle(Local.getString("Start date"));
        startCalFrame.show();
    }
    
    void edButton_actionPerformed(ActionEvent e) {
        endCalFrame.setLocation((this.getContentPane().getWidth() / 2),0);
        endCalFrame.setSize((this.getContentPane().getWidth() / 2), 
            this.getContentPane().getHeight());
        this.getLayeredPane().add(endCalFrame);
        endCalFrame.setTitle(Local.getString("End date"));
        endCalFrame.show();
    }
    
    /**
     * Displays an instance of the dialog box for creating a task template
     * @author ggofort -> Galen Goforth 1/28/16
     */
    public void newTaskTemplate(){
    	TaskTemplateDialog ttd = new TaskTemplateDialog(null, "", "");
    	Dimension dlgSize = ttd.getSize();
    	Dimension frmSize = App.getFrame().getSize();
    	Point point = App.getFrame().getLocation();
    	ttd.setLocation((frmSize.width-dlgSize.width)/2 +point.x,(frmSize.height - dlgSize.height) / 2 + point.y);
    	// set the static variable to true to avoid any conflicting edits of the templates
    	taskTemplateMod=true;
    	ttd.addWindowListener(new WindowAdapter(){
    		public void windowClosed(WindowEvent e){
    			ttd_windowClosed(e);
    		}
    	});
    	ttd.setVisible(true);
    }

	public static void newProject() {
        ProjectDialog dlg = new ProjectDialog(null, Local.getString("New project"));
        Dimension dlgSize = dlg.getSize();
        //dlg.setSize(dlgSize);
        Dimension frmSize = App.getFrame().getSize();
        Point loc = App.getFrame().getLocation();
        dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
        dlg.setVisible(true);
        if (dlg.CANCELLED)
            return;
        String title = dlg.prTitleField.getText();
        CalendarDate startD = new CalendarDate((Date) dlg.startDate.getModel().getValue());
        CalendarDate endD = null;
        if (dlg.endDateChB.isSelected())
            endD = new CalendarDate((Date) dlg.endDate.getModel().getValue());
        Project prj = ProjectManager.createProject(title, startD, endD);
        /*if (dlg.freezeChB.isSelected())
            prj.freeze();*/
        CurrentStorage.get().storeProjectManager();
    }
	
	public void setListItems(String selectedId){
		ArrayList<String> titles = TaskTemplateManager.getTemplateTitles();
    	DefaultListModel<String> listModel = new DefaultListModel<String>();
    	for(String s:titles){
    		if(s.compareToIgnoreCase(selectedId)!=0)
    			listModel.addElement(s);
    		else
    			listModel.add(0, s);
    	}
    	lstTemplateList.setSelectedIndex(0);
    	
    	lstTemplateList.setModel(listModel);
	}
	
	protected void ttd_windowClosed(WindowEvent e) {
		
		this.setVisible(true);
	}
}
