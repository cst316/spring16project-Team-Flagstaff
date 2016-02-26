package net.sf.memoranda.ui;

import net.sf.memoranda.util.Local;

/**
 * AllFilesFilter.java
 * Created on 25.02.2003, 17:30:12 Alex
 * Package:
 *
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * AllFilesFilter class.
 * This Class establishes the file filters used by the
 * fileChooser objects to have file types sorted by.
 * 
 * <p>Update: Self Checked altered method with Checkstyle, FixBugs, 
 * and for defects.  
 * Found checkstyle issues with indentation, naming, and grammar.
 * No Fixbugs found, issues resolved and re-checked - 2/20/2016
 */
/*$Id: AllFilesFilter.java,v 1.5 2004/01/30 12:17:41 alexeya Exp $*/
public class AllFilesFilter extends FileFilter {

  public static final String RTF = "RTF";
  public static final String DOCX = "DOCX";
  public static final String PDF = "PDF";
  public static final String HTML = "HTML";
  public static final String HTM = "HTM";
  public static final String XHTML = "XHTML";
  public static final String XML = "XML";
  public static final String ZIP = "ZIP";
  public static final String EXE = "EXE";
  public static final String COM = "COM";
  public static final String BAT = "BAT";
  public static final String JAR = "JAR";
  public static final String ICO = "ICO";
  public static final String WAV = "WAV";
  //Line 39 Added by Thomas Johnson
  //For US-55, TSK-59 on 2/20/2016
  public static final String TXT = "TXT";

  String type = "";
  
  /**
   * Constructor for AllFilesFilter.
   */
  public AllFilesFilter(String typeInput) {
    super();
    type = typeInput;
  }

  /**
   * @see javax.swing.filechooser.FileFilter#accept(java.io.File)
   */
  //Lines 67-68 Added by Thomas Johnson
  //For US-55, TSK-59 on 2/20/2016
  public boolean accept(File file) {
    if (file.isDirectory()) {
      return true;
    }
    String ext = getExtension(file);
    if (type.equals(RTF)) {
      return ext.equals("rtf");
    } else if (type.equals(ZIP)) {
      return ext.equals("zip");
    } else if (type.equals(DOCX)) {
      return ext.equals("docx");
    } else if (type.equals(PDF)) {
      return ext.equals("pdf");
    } else if (type.equals(TXT)) {
      return ext.equals("txt");
    } else if (type.equals(EXE)) {
      return (ext.equals("exe") || ext.equals("com") || ext.equals("bat"));
    } else if (type.equals(JAR)) {
      return ext.equals("jar");
    } else if (type.equals(WAV)) {
      return (ext.equals("wav") || ext.equals("au"));
    } else if (type.equals(XHTML)) {
      return (ext.equals("xhtml") || ext.equals("xml"));
    } else if (type.equals(ICO)) {
      return (ext.equals("ico") || ext.equals("png"));
    }
    return ext.startsWith("htm");
  }

  /**
   * @see javax.swing.filechooser.FileFilter#getDescription()
   */
  //Lines 96-97 Added by Thomas Johnson
  //For US-55, TSK-59 on 2/20/2016
  public String getDescription() {
    if (type.equals(RTF)) {
      return "Rich Text Format (*.rtf)";
    } else if (type.equals(ZIP)) {
      return "ZIP archives (*.zip)";
    } else if (type.equals(DOCX)) {
      return "Word Documents (*.docx)";
    } else if (type.equals(PDF)) {
      return "PDF Documents (*.pdf)";
    } else if (type.equals(TXT)) {
      return "Text Documents (*.txt)";
    } else if (type.equals(EXE)) {
      return Local.getString("Executable Files") + " (*.exe, *.com, *.bat)";
    } else if (type.equals(JAR)) {
      return "JAR " + Local.getString("Files") + " (*.jar)";
    } else if (type.equals(WAV)) {
      return Local.getString("Sound files") + " (*.wav, *.au)";
    } else if (type.equals(XHTML)) {
      return "XHTML files (*.xhtml, *.xml)";
    } else if (type.equals(ICO)) {
      return Local.getString("Icon") + " " + Local.getString("Files") + " (*.ico, *.png)";
    }
    return "HTML files (*.html, *.htm)";
  }

  private static String getExtension(File file) {
    String ext = "";
    String string = file.getName();
    int index = string.lastIndexOf('.');
    if (index > 0 && index < string.length() - 1) {
      ext = string.substring(index + 1).toLowerCase();
    }
    return ext;
  }
}
