package net.sf.memoranda.ui;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFileChooser;

import net.sf.memoranda.util.Local;

import javax.swing.plaf.basic.BasicFileChooserUI;

/**
 * @author  Thomas Johnson - 2/20/2016
 * Class AnnotationExportDialog created for US-55, TSK-59 & TSK-60
 * This class creates a Dialog box for the Export of an Annotation
 * Sticker object
 * 
 */
public class AnnotationExportDialog extends javax.swing.JDialog {
    
    /**
	 * Initialized Variables
	 */
	private static final long serialVersionUID = 1L;
	public boolean CANCELLED = true;
    
    
    /** 
     * Constructor for AnnotationExportDialog 
     * Creates a new fileChooser object and initializes dialog components
     * 
     * @param parent, title, chooser
     */
    public AnnotationExportDialog(java.awt.Frame parent, String title, JFileChooser chooser) {
        super(parent, title, true);
        fileChooser = chooser;
        initComponents();
    }
    
    /** 
     * Method initComponents 
     * Method to initialize dialog GUI components
     * 
     */
    private void initComponents() {//GEN-BEGIN:initComponents
    	System.out.println("StickerExportDialog Init");
        jPanel2 = new javax.swing.JPanel();
        okB = new javax.swing.JButton();
        cancelB = new javax.swing.JButton();
        filePanel = new javax.swing.JPanel();
        //fileChooser = new javax.swing.JFileChooser();
        optionsPanel = new javax.swing.JPanel();
        
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setModal(true);
        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        okB.setText(Local.getString("Save"));
        okB.addActionListener(new java.awt.event.ActionListener() {
        	/** 
             * Method actionPerformed
             * Handles ok_b button action performed event
             * 
             * @param e
             */
            public void actionPerformed(ActionEvent e) {
              okB_actionPerformed(e);
              if (fileChooser.getUI() instanceof BasicFileChooserUI)	
      	      {
      		      BasicFileChooserUI ui = (BasicFileChooserUI)fileChooser.getUI();	
      		      ui.getApproveSelectionAction().actionPerformed(e);			
      	      }
            }
         });
        okB.setPreferredSize(new java.awt.Dimension(90, 25));
        okB.addActionListener(new java.awt.event.ActionListener() {
        	/** 
             * Method actionPerformed
             * Handles ok_b button post action event
             * 
             * @param e
             */
            public void actionPerformed(ActionEvent e) {
                CANCELLED = false;
                dispose();
            }
        });
        okB.setEnabled(false);
        jPanel2.add(okB);

        cancelB.setText(Local.getString("Cancel"));
        cancelB.setPreferredSize(new java.awt.Dimension(90, 25));
        cancelB.addActionListener(new java.awt.event.ActionListener() {
        	/** 
             * Method actionPerformed
             * Handles cancelB button action event
             * 
             * @param e
             */
            public void actionPerformed(ActionEvent e) {                
                dispose();
            }
        });
        jPanel2.add(cancelB);

        getContentPane().add(jPanel2, java.awt.BorderLayout.SOUTH);

        filePanel.setLayout(new java.awt.BorderLayout());

        filePanel.setBorder(new javax.swing.border.EtchedBorder());
        fileChooser.setControlButtonsAreShown(false);
        fileChooser.addPropertyChangeListener(new PropertyChangeListener() {
        	/** 
             * Method propertyChange
             * Handles event propertyChange of fileChooser
             * 
             * @param evt
             */
            public void propertyChange(PropertyChangeEvent evt) {
                chooserActionPerformed();
                okB.setEnabled(true);
                
            }
        
        });
        
        

        filePanel.add(fileChooser, java.awt.BorderLayout.CENTER);

        optionsPanel.setLayout(new java.awt.GridLayout(5, 2, 5, 0));

        optionsPanel.setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(5, 5, 5, 5)));

        filePanel.add(optionsPanel, java.awt.BorderLayout.SOUTH);

        getContentPane().add(filePanel, java.awt.BorderLayout.CENTER);
        getRootPane().setDefaultButton(okB);
        pack();
    }
    
    /** 
     * Method okB_actionPerformed
     * Handles ok_b action event
     * 
     * @param e
     */
    void okB_actionPerformed(ActionEvent e) {
        CANCELLED = false;
        this.dispose();
    }

    /** 
     * Method chooserActionPerformed
     * Handles file chooser object action performed event
     * 
     */
    private void chooserActionPerformed() {
        okB.setEnabled(fileChooser.getSelectedFile() != null);            
    }
    
    /** 
     * GUI object declarations
     */
    private javax.swing.JButton cancelB;
    private javax.swing.JFileChooser fileChooser;
    
    private javax.swing.JPanel filePanel;
    private javax.swing.JPanel jPanel2;
    
    private javax.swing.JButton okB;
    private javax.swing.JPanel optionsPanel;
    
}