
package net.sf.memoranda.ui;

import java.io.*;
import java.util.Date;
import java.util.regex.Pattern;
import net.sf.memoranda.util.Configuration;

import javax.swing.text.Document;
import javax.swing.text.html.HTMLDocument;

import net.sf.memoranda.Note;
import net.sf.memoranda.ui.ExceptionDialog;
import net.sf.memoranda.ui.htmleditor.AltHTMLWriter;


import org.apache.xml.serialize.Method;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.cyberneko.html.parsers.SAXParser;
import org.xml.sax.InputSource;


import java.io.File;
import java.io.OutputStream;

import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.NumberingDefinitionsPart;


import org.docx4j.openpackaging.contenttype.ContentType;
import org.docx4j.relationships.Relationship;
import org.docx4j.wml.CTAltChunk;
import org.docx4j.openpackaging.parts.PartName;
import org.docx4j.jaxb.Context;
import org.docx4j.Docx4jProperties;
import org.docx4j.openpackaging.contenttype.ContentTypeManager;

import javax.xml.bind.JAXBException;
import javax.xml.bind.JAXBContext;
import org.eclipse.persistence.jaxb.JAXBContextFactory;

import org.docx4j.convert.in.xhtml.XHTMLImporterImpl;
import org.docx4j.openpackaging.exceptions.Docx4JException;

import org.docx4j.convert.out.pdf.PdfConversion;
import org.docx4j.convert.out.pdf.viaXSLFO.PdfSettings;
import org.docx4j.utils.SingleTraversalUtilVisitorCallback;
import org.docx4j.utils.TraversalUtilVisitor;
import org.docx4j.wml.Body;
import org.docx4j.dml.CTBlip;

import java.util.List;

public class TraversalUtilBlipVisitor extends TraversalUtilVisitor<CTBlip> {
	
	@Override
	public void apply(CTBlip element, Object parent, List<Object> siblings) {

		if (element.getEmbed()!=null) {
			
			String relId = element.getEmbed();
			// Add r:link
			element.setLink(relId);
			// Remove r:embed
			element.setEmbed(null);
			
			System.out.println("Converted a:blip with relId " + relId);
			
		}
	}

}