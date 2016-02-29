/**
 * DisplayDateTimeField.java : Class that implements the DisplayField interface and serves as an implementation of a generic control
 * for the data type Date. It can is used to display the custom fields from the Task templates which have an undetermined type
 * until they are read from the XML template file.
 * 
 * @author ggoforth -> Galen Goforth -- Email: ghgofort@asu.ed -- 2/11/16
 */

package net.sf.memoranda;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.ui.CalendarFrame;


/**
 * @author ggoforth
 */
public class DisplayCalendarDateField extends JPanel implements IDisplayField {
	/**
	 * Default Serial ID
	 */
	private static final long serialVersionUID = 1L;
	private JLabel lblName;
	private JSpinner spnDate=null;
	private CalendarFrame calFrame = new CalendarFrame();
	private JLayeredPane layeredPane;
	private JButton btnSetDate;
	private JPanel pnlContainer;
	private boolean stateChanged=false;

	/**
	 * 
	 */
	public DisplayCalendarDateField(LayoutManager layout){
		super(layout);
		init();
	}

	/**
	 * Initiates our GUI components that are additional to the 
	 * original setup
	 */
	private void init() {
		this.setPreferredSize(new Dimension(200, 30));
		layeredPane = new JLayeredPane();
		layeredPane.setSize(200, 30);
		layeredPane.setPreferredSize(new Dimension(200,30));
		this.add(layeredPane);
		pnlContainer = new JPanel();
		pnlContainer.setLayout(new GridBagLayout());
		pnlContainer.setBounds(0, 0, 200, 30);
		btnSetDate = new JButton();
		GridBagConstraints cs = new GridBagConstraints();
		cs.anchor = GridBagConstraints.CENTER;
		cs.insets = new Insets(5, 5, 5, 5);
		lblName = new JLabel();
		cs.gridx=0;
		cs.gridy=0;
		pnlContainer.add(lblName, cs);
		cs.gridx=1;
		Date dtDefault = new Date();
		spnDate = new JSpinner(new SpinnerDateModel(dtDefault,null,null,Calendar.DAY_OF_WEEK));
		spnDate.setBorder(BorderFactory.createEtchedBorder());
		spnDate.setPreferredSize(new Dimension(80, 24));
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf = (SimpleDateFormat)DateFormat.getDateInstance(DateFormat.SHORT);
		spnDate.setEditor(new JSpinner.DateEditor(spnDate, sdf.toPattern())); 
		pnlContainer.add(spnDate, cs);
		spnDate.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (stateChanged) {
					return;
				}
				stateChanged = true;
				// it's an ugly hack so that the spinner can increase day by day
				SpinnerDateModel sdm = new SpinnerDateModel((Date)spnDate
						.getModel().getValue(),null,null,Calendar.DAY_OF_WEEK);
				spnDate.setModel(sdm);
				Date ed = (Date) spnDate.getModel().getValue();
				calFrame.cal.set(new CalendarDate(ed));
				stateChanged = false;
			}
		});
		btnSetDate.setMinimumSize(new Dimension(24, 24));
		btnSetDate.setPreferredSize(new Dimension(24, 24));
		btnSetDate.setText("");
		btnSetDate.setIcon(
				new ImageIcon(net.sf.memoranda.ui.AppFrame.class
						.getResource("resources/icons/calendar.png")));
		btnSetDate.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnSetDate_actionPerformed(e);
			}
		});
		pnlContainer.add(btnSetDate);
		layeredPane.add(pnlContainer, new Integer(1), 0);
		calFrame.cal.addSelectionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				spnDate_actionPerformed(e);
			}
		});
	}
	
	void spnDate_actionPerformed(ActionEvent e) {
		spnDate.getModel().setValue(calFrame.cal.get().getCalendar().getTime());
		this.setPreferredSize(new Dimension(200, 30));
		layeredPane.setPreferredSize(new Dimension(200,30));
		calFrame.hide();
	}

	void btnSetDate_actionPerformed(ActionEvent e) {
		calFrame.setLocation(btnSetDate.getLocation());
		this.setPreferredSize(new Dimension(200, 230));
		layeredPane.setPreferredSize(new Dimension(200,230));
		calFrame.setBounds(0,0,200, 200);
		layeredPane.add(calFrame, new Integer(2), 0);
		calFrame.show();
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
		Date date = new Date();
		if(data!=null){
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat();
				dateFormat = (SimpleDateFormat)DateFormat.getDateInstance(DateFormat.SHORT);
				date = dateFormat.parse(data.toString());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			spnDate.getModel().setValue(date);
		}

	}

	/* (non-Javadoc)
	 * @see net.sf.memoranda.DisplayField#getData()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getData() {
		CalendarDate sd = new CalendarDate((Date) spnDate.getModel().getValue());
		return (T) sd;
	}

}