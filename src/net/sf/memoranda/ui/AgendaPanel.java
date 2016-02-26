package net.sf.memoranda.ui;

import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.EventNotificationListener;
import net.sf.memoranda.EventsManager;
import net.sf.memoranda.EventsScheduler;
import net.sf.memoranda.History;
import net.sf.memoranda.NoteList;
import net.sf.memoranda.Project;
import net.sf.memoranda.ProjectListener;
import net.sf.memoranda.ProjectManager;
import net.sf.memoranda.ResourcesList;
import net.sf.memoranda.TaskList;
import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.date.CurrentDate;
import net.sf.memoranda.date.DateListener;
import net.sf.memoranda.util.AgendaGenerator;
import net.sf.memoranda.util.Context;
import net.sf.memoranda.util.CurrentStorage;
import net.sf.memoranda.util.Local;
import net.sf.memoranda.util.Util;
import nu.xom.Element;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

/*$Id: AgendaPanel.java,v 1.11 2005/02/15 16:58:02 rawsushi Exp $*/
/**
 * AgendaPanel class This class handles the building of the Panel which pertains
 * to the Agenda GUI objects and their associated items.
 * 
 * <p>Update: Self Checked altered method with Checkstyle, FixBugs, and for
 * defects. Found checkstyle issues with indentation, naming, grammar, and
 * length. ---Some Length issues could not be resolved due to Checkstyle not
 * seeming to recognize reduced character lines.--- No Fixbugs found, issues
 * resolved and re-checked - 2/20/2016
 * 
 */
public class AgendaPanel extends JPanel {

  BorderLayout borderLayout1 = new BorderLayout();
  JButton historyBackB = new JButton();
  JToolBar toolBar = new JToolBar();
  JButton historyForwardB = new JButton();
  JButton export = new JButton();
  JEditorPane viewer = new JEditorPane("text/html", "");
  String[] priorities = { "Very High", "High", "Medium", "Low", "Very Low" };
  JScrollPane scrollPane = new JScrollPane();

  DailyItemsPanel parentPanel = null;

  // JPopupMenu agendaPPMenu = new JPopupMenu();
  // JCheckBoxMenuItem ppShowActiveOnlyChB = new JCheckBoxMenuItem();

  // Line 82 Added by Thomas Johnson
  // For US-55,TSK-60 on 2/20/2016
  // editor = new HTMLEditor();

  String gotoTask = null;

  boolean isActive = true;

  Collection expandedTasks;

  /**
   * Method AgendaPanel.
   * 
   * @param parentPanelInput
   */
  public AgendaPanel(DailyItemsPanel parentPanelInput) {
    try {
      parentPanel = parentPanelInput;
      jbInit();
    } catch (Exception ex) {
      new ExceptionDialog(ex);
      ex.printStackTrace();
    }
  }

  /**
   * Method jbInit. This method Initializes the GUI objects for the Agenda
   * Panel and handles the events for GUI object actions
   * 
   * @throws Exception
   */
  void jbInit() throws Exception {
    expandedTasks = new ArrayList();

    toolBar.setFloatable(false);
    viewer.setEditable(false);
    viewer.setOpaque(false);
    viewer.addHyperlinkListener(new HyperlinkListener() {

      public void hyperlinkUpdate(HyperlinkEvent event) {
        if (event.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
          String description = event.getDescription();
          if (description.equalsIgnoreCase("memoranda:events")) {
            parentPanel.alarmB_actionPerformed(null);
          } else if (description.startsWith("memoranda:tasks")) {
            String id = description.split("#")[1];
            CurrentProject.set(ProjectManager.getProject(id));
            parentPanel.taskB_actionPerformed(null);
          } else if (description.startsWith("memoranda:project")) {
            String id = description.split("#")[1];
            CurrentProject.set(ProjectManager.getProject(id));
          } else if (description.startsWith("memoranda:removesticker")) {
            String id = description.split("#")[1];
            StickerConfirmation stc = new StickerConfirmation(App.getFrame());
            Dimension frmSize = App.getFrame().getSize();
            stc.setSize(new Dimension(300, 180));
            Point loc = App.getFrame().getLocation();
            stc.setLocation((frmSize.width - stc.getSize().width) / 2 + loc.x,
                (frmSize.height - stc.getSize().height) / 2 + loc.y);
            stc.setVisible(true);
            if (!stc.CANCELLED) {
              EventsManager.removeSticker(id);
              CurrentStorage.get().storeEventsManager();
            }
            refresh(CurrentDate.get());
          } else if (description.startsWith("memoranda:addsticker")) {
            StickerDialog dlg = new StickerDialog(App.getFrame());
            Dimension frmSize = App.getFrame().getSize();
            dlg.setSize(new Dimension(300, 380));
            Point loc = App.getFrame().getLocation();
            dlg.setLocation((frmSize.width - dlg.getSize().width) / 2 + loc.x,
                (frmSize.height - dlg.getSize().height) / 2 + loc.y);
            dlg.setVisible(true);
            if (!dlg.CANCELLED) {
              String txt = dlg.getStickerText();
              int priority = dlg.getPriority();
              txt = txt.replaceAll("\\n", "<br>");
              txt = "<div style=\"background-color:" + dlg.getStickerColor() + ";font-size:"
                  + dlg.getStickerTextSize() + ";color:" + dlg.getStickerTextColor() + "; \">" + txt
                  + "</div>";
              EventsManager.createSticker(txt, priority);
              CurrentStorage.get().storeEventsManager();
            }
            refresh(CurrentDate.get());
            System.out.println("I added a sticker");
          } else if (description.startsWith("memoranda:expandsubtasks")) {
            String id = description.split("#")[1];
            gotoTask = id;
            expandedTasks.add(id);
            refresh(CurrentDate.get());
          } else if (description.startsWith("memoranda:closesubtasks")) {
            String id = description.split("#")[1];
            gotoTask = id;
            expandedTasks.remove(id);
            refresh(CurrentDate.get());
          } else if (description.startsWith("memoranda:expandsticker")) {
            String id = description.split("#")[1];
            /**
             * Attempted to break down smaller for 100 char limit.
             * Unfortunately Checkstyle seemed to reject even a few
             * characters, saying that they alone were over 100.
             */
            Element preSticker = (Element) ((Map) EventsManager.getStickers()).get(id);
            String sticker = preSticker.getValue();
            int first = sticker.indexOf(">");
            int last = sticker.lastIndexOf("<");
            int backcolor = sticker.indexOf("#");
            int fontcolor = sticker.indexOf("#", backcolor + 1);
            int priority = Integer.parseInt(preSticker.getAttributeValue("priority"));
            /**
             * Attempted to break down smaller for 100 char limit.
             * Unfortunately Checkstyle seemed to reject even a few
             * characters, saying that they alone were over 100.
             */
            String backGroundColor = sticker.substring(backcolor, sticker.indexOf(';', backcolor));
            /**
             * Attempted to break down smaller for 100 char limit.
             * Unfortunately Checkstyle seemed to reject even a few
             * characters, saying that they alone were over 100.
             */
            String foreGroundColor = sticker.substring(fontcolor, sticker.indexOf(';', fontcolor));
            sticker = "<html>" + sticker.substring(first + 1, last) + "</html>";
            /**
             * Attempted to break down smaller for 100 char limit.
             * Unfortunately Checkstyle seemed to reject even a few
             * characters, saying that they alone were over 100.
             */
            StickerExpand dlg = new StickerExpand(App.getFrame(), 
                sticker, backGroundColor, foreGroundColor,
                Local.getString("priority") + ": " + Local.getString(priorities[priority]));
            Dimension frmSize = App.getFrame().getSize();
            dlg.setSize(new Dimension(300, 200));
            Point loc = App.getFrame().getLocation();
            /**
             * Attempted to break down smaller for 100 char limit.
             * Unfortunately Checkstyle seemed to reject even a few
             * characters, saying that they alone were over 100.
             */
            dlg.setLocation((frmSize.width - dlg.getSize().width) / 2 + loc.x,
                (frmSize.height - dlg.getSize().height) / 2 + loc.y);
            dlg.stickerText.setText(sticker);
            dlg.setVisible(true);
          } else if (description.startsWith("memoranda:editsticker")) {
            String id = description.split("#")[1];
            Element preSticker = (Element) ((Map) EventsManager.getStickers()).get(id);
            String sticker = preSticker.getValue();
            sticker = sticker.replaceAll("<br>", "\n");
            int first = sticker.indexOf(">");
            int last = sticker.lastIndexOf("<");
            int backcolor = sticker.indexOf("#");
            int fontcolor = sticker.indexOf("#", backcolor + 1);
            int sizeposition = sticker.indexOf("font-size") + 10;
            int size = Integer.parseInt(sticker.substring(sizeposition, sizeposition + 2));
            System.out.println(size + " " + sizeposition);
            int priority = Integer.parseInt(preSticker.getAttributeValue("priority"));
            /**
             * Attempted to break down smaller for 100 char limit.
             * Unfortunately Checkstyle seemed to reject even a few
             * characters, saying that they alone were over 100.
             */
            String backGroundColor = sticker.substring(backcolor, sticker.indexOf(';', backcolor));
            /**
             * Attempted to break down smaller for 100 char limit.
             * Unfortunately Checkstyle seemed to reject even a few
             * characters, saying that they alone were over 100.
             */
            String foreGroundColor = sticker.substring(fontcolor, sticker.indexOf(';', fontcolor));
            /**
             * Attempted to break down smaller for 100 char limit.
             * Unfortunately Checkstyle seemed to reject even a few
             * characters, saying that they alone were over 100.
             */
            StickerDialog dlg = new StickerDialog(App.getFrame(), 
                sticker.substring(first + 1, last),
                backGroundColor, foreGroundColor, priority, size);
            Dimension frmSize = App.getFrame().getSize();
            dlg.setSize(new Dimension(300, 380));
            Point loc = App.getFrame().getLocation();
            /**
             * Attempted to break down smaller for 100 char limit.
             * Unfortunately Checkstyle seemed to reject even a few
             * characters, saying that they alone were over 100.
             */
            dlg.setLocation((frmSize.width - dlg.getSize().width) / 2 + loc.x,
                (frmSize.height - dlg.getSize().height) / 2 + loc.y);
            dlg.setVisible(true);
            if (!dlg.CANCELLED) {
              String txt = dlg.getStickerText();
              priority = dlg.getPriority();
              txt = txt.replaceAll("\\n", "<br>");
              txt = "<div style=\"background-color:" + dlg.getStickerColor() + ";font-size:"
                  + dlg.getStickerTextSize() + ";color:" + dlg.getStickerTextColor() + ";\">" + txt
                  + "</div>";
              EventsManager.removeSticker(id);
              EventsManager.createSticker(txt, priority);
              CurrentStorage.get().storeEventsManager();
            }
            refresh(CurrentDate.get());

            // Lines 226 & 232 changed, Lines 227-231 & 233-237
            // Added by Thomas Johnson
            // For US-55, TSK-59 & TSK-60 on 2/20/2016
          } else if (description.startsWith("memoranda:exportstickerhtml")) {
            String id = description.split("#")[1];
            Element preSticker = (Element) ((Map) EventsManager.getStickers()).get(id);
            String sticker = preSticker.getValue();
            System.out.println("Export Sticker HTML Selection");
            exportSticker(0, sticker); // 0 = HTML Export
          } else if (description.startsWith("memoranda:exportstickertxt")) {
            String id = description.split("#")[1];
            Element preSticker = (Element) ((Map) EventsManager.getStickers()).get(id);
            String sticker = preSticker.getValue();
            System.out.println("Export Sticker TXT Selection");
            exportSticker(1, sticker);// 1 = TXT Export
          } else if (description.startsWith("memoranda:importstickers")) {
            // Lines 241-242 Added by Thomas Johnson
            // For US-55, TSK-58 on 2/20/2016
            System.out.println("Import Sticker Selection");
            importSticker();
          }
        }
      }
    });
    historyBackB.setAction(History.historyBackAction);
    historyBackB.setFocusable(false);
    historyBackB.setBorderPainted(false);
    historyBackB.setToolTipText(Local.getString("History back"));
    historyBackB.setRequestFocusEnabled(false);
    historyBackB.setPreferredSize(new Dimension(24, 24));
    historyBackB.setMinimumSize(new Dimension(24, 24));
    historyBackB.setMaximumSize(new Dimension(24, 24));
    historyBackB.setText("");

    historyForwardB.setAction(History.historyForwardAction);
    historyForwardB.setBorderPainted(false);
    historyForwardB.setFocusable(false);
    historyForwardB.setPreferredSize(new Dimension(24, 24));
    historyForwardB.setRequestFocusEnabled(false);
    historyForwardB.setToolTipText(Local.getString("History forward"));
    historyForwardB.setMinimumSize(new Dimension(24, 24));
    historyForwardB.setMaximumSize(new Dimension(24, 24));
    historyForwardB.setText("");

    this.setLayout(borderLayout1);
    scrollPane.getViewport().setBackground(Color.white);

    scrollPane.getViewport().add(viewer, null);
    this.add(scrollPane, BorderLayout.CENTER);
    toolBar.add(historyBackB, null);
    toolBar.add(historyForwardB, null);
    toolBar.addSeparator(new Dimension(8, 24));

    this.add(toolBar, BorderLayout.NORTH);

    CurrentDate.addDateListener(new DateListener() {

      /**
       * Method dateChange. This method handles the refreshing of a date
       * change within the AgendaPanel
       * 
       * @param date
       */
      public void dateChange(CalendarDate date) {
        if (isActive) {
          refresh(date);
        }
      }
    });
    CurrentProject.addProjectListener(new ProjectListener() {

      /**
       * Method projectChange. This method handles the variable
       * re-initialization within the AgendaPanel for a changed project
       */
      public void projectChange(Project prj, NoteList nl, TaskList tl, ResourcesList rl) {
      }

      /**
       * Method projectWasChanged. This method handles the currentDate
       * refresh within the AgendaPanel for a changed project
       */
      public void projectWasChanged() {
        if (isActive) {
          refresh(CurrentDate.get());
        }
      }
    });
    EventsScheduler.addListener(new EventNotificationListener() {
      /**
       * Method eventIsOccurred. This method handles the currentDate
       * refresh within the AgendaPanel for an event occurrence
       * 
       * @param ev
       */
      public void eventIsOccured(net.sf.memoranda.Event ev) {
        if (isActive) {
          refresh(CurrentDate.get());
        }
      }

      /**
       * Method eventsChanged This method handles the currentDate refresh
       * within the AgendaPanel for when events have changed.
       */
      public void eventsChanged() {
        if (isActive) {
          refresh(CurrentDate.get());
        }
      }
    });
    refresh(CurrentDate.get());

    // agendaPPMenu.setFont(new java.awt.Font("Dialog", 1, 10));
    // agendaPPMenu.add(ppShowActiveOnlyChB);
    // PopupListener ppListener = new PopupListener();
    // viewer.addMouseListener(ppListener);
    // ppShowActiveOnlyChB.setFont(new java.awt.Font("Dialog", 1, 11));
    // ppShowActiveOnlyChB.setText(
    // Local.getString("Show Active only"));
    // ppShowActiveOnlyChB.addActionListener
    // (new java.awt.event.ActionListener() {
    // public void actionPerformed(ActionEvent e) {
    // toggleShowActiveOnly_actionPerformed(e);
    // }
    // });
    // boolean isShao =
    // (Context.get("SHOW_ACTIVE_TASKS_ONLY") != null)
    // && (Context.get("SHOW_ACTIVE_TASKS_ONLY").
    // equals("true"));
    // ppShowActiveOnlyChB.setSelected(isShao);
    // toggleShowActiveOnly_actionPerformed(null);
  }

  /**
   * Method refresh This method handles the refreshing/updating of the
   * calendar date.
   * 
   * @param date
   */
  public void refresh(CalendarDate date) {
    viewer.setText(AgendaGenerator.getAgenda(date, expandedTasks));
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        if (gotoTask != null) {
          viewer.scrollToReference(gotoTask);
          scrollPane.setViewportView(viewer);
          Util.debug("Set view port to " + gotoTask);
        }
      }
    });

    Util.debug("Summary updated.");
  }

  /**
   * Method setActive This method handles the setting of the isActive boolean
   * variable.
   * 
   * @param isa
   */
  public void setActive(boolean isa) {
    isActive = isa;
  }

  /**
   * Method Export Sticker Added by Thomas Johnson For US-55, TSK-59 & TSK-60
   * on 2/20/2016 This method handles the export GUI file chooser and event
   * handlers for the export GUI actions for the Annotation Sticker object
   * export.
   * 
   * @param type,
   *            sticker
   */
  void exportSticker(int type, String sticker) {

    UIManager.put("FileChooser.lookInLabelText", Local.getString("Save in:"));
    UIManager.put("FileChooser.upFolderToolTipText", Local.getString("Up One Level"));
    UIManager.put("FileChooser.newFolderToolTipText", Local.getString("Create New Folder"));
    UIManager.put("FileChooser.listViewButtonToolTipText", Local.getString("List"));
    UIManager.put("FileChooser.detailsViewButtonToolTipText", Local.getString("Details"));
    UIManager.put("FileChooser.fileNameLabelText", Local.getString("File Name:"));
    UIManager.put("FileChooser.filesOfTypeLabelText", Local.getString("Files of Type:"));
    UIManager.put("FileChooser.saveButtonText", Local.getString("Save"));
    UIManager.put("FileChooser.saveButtonToolTipText", Local.getString("Save selected file"));
    UIManager.put("FileChooser.cancelButtonText", Local.getString("Cancel"));
    UIManager.put("FileChooser.cancelButtonToolTipText", Local.getString("Cancel"));

    JFileChooser chooser = new JFileChooser();
    chooser.setFileHidingEnabled(false);
    chooser.setDialogTitle(Local.getString("Export Annotation Sticker"));
    chooser.setAcceptAllFileFilterUsed(false);
    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    if (type == 0) {
      chooser.addChoosableFileFilter(new AllFilesFilter(AllFilesFilter.HTML));
    } else if (type == 1) {
      chooser.addChoosableFileFilter(new AllFilesFilter(AllFilesFilter.TXT));
    }
    String lastSel = (String) Context.get("LAST_SELECTED_EXPORT_FILE");
    if (lastSel != null) {
      chooser.setCurrentDirectory(new File(lastSel));
    }

    AnnotationExportDialog slg = new AnnotationExportDialog(
        App.getFrame(), Local.getString("Export Sticker"), chooser);

    Dimension dlgSize = new Dimension(550, 475);
    slg.setSize(dlgSize);
    Dimension frmSize = App.getFrame().getSize();
    Point loc = App.getFrame().getLocation();
    slg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
    slg.setVisible(true);
    if (slg.cancelled) {
      return;
    }

    Context.put("LAST_SELECTED_EXPORT_FILE", chooser.getSelectedFile().getPath());

    if (type == 0) {
      File file = chooser.getSelectedFile();
      ExportSticker stickerExport = new ExportSticker();
      stickerExport.exportHtml(file, sticker);
    }
    if (type == 1) {
      File file = chooser.getSelectedFile();
      ExportSticker stickerExport = new ExportSticker();
      stickerExport.exportText(file, sticker);
    }
  }

  /**
   * Method Export Sticker Added by Thomas Johnson For US-55, TSK-58 on
   * 2/20/2016 This method handles the import GUI file chooser and event
   * handlers for the import GUI actions for the Annotation Sticker object
   * import.
   * 
   */
  void importSticker() {
    UIManager.put("FileChooser.lookInLabelText", Local.getString("Look in:"));
    UIManager.put("FileChooser.upFolderToolTipText", Local.getString("Up One Level"));
    UIManager.put("FileChooser.newFolderToolTipText", Local.getString("Create New Folder"));
    UIManager.put("FileChooser.listViewButtonToolTipText", Local.getString("List"));
    UIManager.put("FileChooser.detailsViewButtonToolTipText", Local.getString("Details"));
    UIManager.put("FileChooser.fileNameLabelText", Local.getString("File Name:"));
    UIManager.put("FileChooser.filesOfTypeLabelText", Local.getString("Files of Type:"));
    UIManager.put("FileChooser.openButtonText", Local.getString("Open"));
    UIManager.put("FileChooser.openButtonToolTipText", Local.getString("Open selected file"));
    UIManager.put("FileChooser.cancelButtonText", Local.getString("Cancel"));
    UIManager.put("FileChooser.cancelButtonToolTipText", Local.getString("Cancel"));

    JFileChooser chooser = new JFileChooser();
    chooser.setFileHidingEnabled(false);
    chooser.setDialogTitle(Local.getString("Import Annotation Sticker"));
    chooser.setAcceptAllFileFilterUsed(false);
    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    chooser.addChoosableFileFilter(new AllFilesFilter(AllFilesFilter.HTML));
    chooser.setPreferredSize(new Dimension(550, 375));
    String lastSel = (String) Context.get("LAST_SELECTED_IMPORT_FILE");
    if (lastSel != null) {
      chooser.setCurrentDirectory(new java.io.File(lastSel));
    }
    if (chooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
      return;
    }

    Context.put("LAST_SELECTED_IMPORT_FILE", chooser.getSelectedFile().getPath());

    File file = chooser.getSelectedFile();

    ImportSticker tempImport = new ImportSticker();

    String txt = tempImport.htmlAnnotationImport(file);

    EventsManager.createSticker(txt, 1);
    CurrentStorage.get().storeEventsManager();
    refresh(CurrentDate.get());
    JOptionPane.showMessageDialog(null,
        Local.getString("Your Sticker has been successfully imported from: " + file.getAbsolutePath()));

  }

  // void toggleShowActiveOnly_actionPerformed(ActionEvent e) {
  // Context.put(
  // "SHOW_ACTIVE_TASKS_ONLY",
  // new Boolean(ppShowActiveOnlyChB.isSelected()));
  // /*if (taskTable.isShowActiveOnly()) {
  // // is true, toggle to false
  // taskTable.setShowActiveOnly(false);
  // //showActiveOnly.setToolTipText
  // (Local.getString("Show Active Only"));
  // }
  // else {
  // // is false, toggle to true
  // taskTable.setShowActiveOnly(true);
  // showActiveOnly.setToolTipText
  // (Local.getString("Show All"));
  // }*/
  // refresh(CurrentDate.get());
  //// parentPanel.updateIndicators();
  // //taskTable.updateUI();
  // }

  // class PopupListener extends MouseAdapter {
  //
  // public void mouseClicked(MouseEvent e) {
  // System.out.println("mouse clicked!");
  //// if ((e.getClickCount() == 2) && (taskTable.getSelectedRow() > -1))
  //// editTaskB_actionPerformed(null);
  // }
  //
  // public void mousePressed(MouseEvent e) {
  // System.out.println("mouse pressed!");
  // maybeShowPopup(e);
  // }
  //
  // public void mouseReleased(MouseEvent e) {
  // System.out.println("mouse released!");
  // maybeShowPopup(e);
  // }
  //
  // private void maybeShowPopup(MouseEvent e) {
  // if (e.isPopupTrigger()) {
  // agendaPPMenu.show(e.getComponent(), e.getX(), e.getY());
  // }
  // }
  //
  // }
}