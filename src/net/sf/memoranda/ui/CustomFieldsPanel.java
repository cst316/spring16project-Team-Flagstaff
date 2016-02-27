package net.sf.memoranda.ui;

/**
 * CustomFieldPanal.java
 * Added by @author ggoforth -> Galen Goforth 
 * ASURiteId: ghgofort
 * 
 * <p>Added on 2/9/16, 12:59am
 */

import net.sf.memoranda.CustomField;
import net.sf.memoranda.IDisplayField;
import net.sf.memoranda.DisplayFieldFactory;
import net.sf.memoranda.date.CalendarDate;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.io.InvalidClassException;

import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * Class CustomFieldsPanel.
 * 
 * @author ggoforth
 * @param <T>
 *
 */
public class CustomFieldsPanel extends JPanel {

  private static final long serialVersionUID = 1L;
  private ArrayList<IDisplayField> customPanels = null;  

  /**
   * Constructor to add all of our fields to the panel.
   */
  public CustomFieldsPanel() {
    customPanels = new ArrayList<IDisplayField>();
  }
  
  /**
   * Add a field to the panel.
   * 
   * @param <T>
   * @param customField
   */
  public <T> void addField(CustomField<T> customField, int xIndex, int yIndex) 
          throws InvalidClassException {
    IDisplayField newField = null;
    GridBagConstraints cs = new GridBagConstraints();
    cs.anchor = GridBagConstraints.BOTH;
    cs.insets = new Insets(3, 2, 3, 2);
    cs.gridx = xIndex;
    cs.gridy = yIndex;
    if (customField.getData() != null) {
      if (customField.getData().getClass() == CalendarDate.class) {
        newField = DisplayFieldFactory.createField("CalendarDate");
        newField.createDataControl(customField.getData());
      } else if (customField.getData().getClass() == Integer.class) {
        newField = DisplayFieldFactory.createField("Integer");
        newField.createDataControl(customField.getData());
      } else {
        newField = DisplayFieldFactory.createField("String");
        newField.createDataControl(customField.getData());
      }
    } else {
      throw new InvalidClassException("There is a field in the XML template that is not a"
          + " supported data type.\n"
          + "Fix the XML Template for this project or select a different template.\n"
          + "CustomFieldsPanel.java: 62/naddField(CustomField<T> customField) parameter" 
          + " type not supported\n"
          + "Parameter type= " + customField.getClass().toGenericString());  
    }
    newField.setFieldName(customField.getFieldName());
    customPanels.add(newField);
    this.add((Component) newField, cs);
    // Render the changes
    this.revalidate();
    this.repaint();
  }
  
  /**
   * Removes the indicated field from the Panel.
   * 
   * @param name
   * @return
   */
  public boolean removeField(String name) {
    
    // Still needs to be implemented
    
    boolean isFound = false;
    return isFound;
  }

  /**
   * Method CustomFieldsPanel.
   * 
   * @param layout
   */
  public CustomFieldsPanel(LayoutManager layout) {
    super(layout);
    customPanels = new ArrayList<IDisplayField>();
  }

  /**
   * Method CustomFieldsPanel.
   * 
   * @param isDoubleBuffered
   */
  public CustomFieldsPanel(boolean isDoubleBuffered) {
    super(isDoubleBuffered);
    customPanels = new ArrayList<IDisplayField>();
  }

  /**
   * Method CustomFieldPanel.
   * 
   * @param layout
   * @param isDoubleBuffered
   */
  public CustomFieldsPanel(LayoutManager layout, boolean isDoubleBuffered) {
    super(layout, isDoubleBuffered);
    customPanels = new ArrayList<IDisplayField>();
  }
  
  /**
   * Adds the custom fields to the panel from an array list of CustomField types.
   * 
   * @param <T>
   * @param fld
   */
  public <T> void fillPanel(ArrayList<CustomField<T>> fld) {
    this.removeAll();
    int column = fld.size() / 2;
    for (int x = 0;x < fld.size();x++) {
      try {
        if (x < column) {
          addField(fld.get(x),0,x );
        } else {
          addField(fld.get(x),1,(x - (column + 1)));
        }
      } catch (InvalidClassException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Method getCustomPanels.
   * 
   * @return the customPanels
   */
  public ArrayList<IDisplayField> getCustomPanels() {
    return customPanels;
  }
  
  /**
   * @param customPanels the customPanels to set.
   */
  public void setCustomPanels(ArrayList<IDisplayField> customPanels) {
    this.customPanels = customPanels;
  }
  
  /**
   * Sets the data in the field with the name given.
   * 
   * @param fieldName
   * @param data
   */
  public <T> void setFieldData(String fieldName, T data) {
    if (customPanels != null) {
      int x = 0;
      boolean isFound = false;
      while (x < customPanels.size() && !isFound) {
        if (customPanels.get(x).getFieldName().compareTo(fieldName) == 0) {
          customPanels.get(x).createDataControl(data);
          isFound = true;
        }
        x++;
      }
    }
  }

}