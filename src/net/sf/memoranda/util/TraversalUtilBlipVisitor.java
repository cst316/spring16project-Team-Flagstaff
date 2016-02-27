package net.sf.memoranda.util;

import java.io.*;
import java.util.Date;
import java.util.regex.Pattern;
import net.sf.memoranda.util.Configuration;
import java.io.File;
import java.io.OutputStream;
import javax.xml.bind.JAXBException;
import javax.xml.bind.JAXBContext;
import org.eclipse.persistence.jaxb.JAXBContextFactory;
import org.docx4j.utils.SingleTraversalUtilVisitorCallback;
import org.docx4j.utils.TraversalUtilVisitor;
import org.docx4j.wml.Body;
import org.docx4j.dml.CTBlip;
import java.util.List;

/** The TraversalUtilBlipVisitor Class, previously created by Thomas Johnson.
 * This class assisst in image capture for the DOCXFileExport class.
 *
 * Changes for Sprint 2: Currently this Sprint the class was moved 
 * from the "ui" directory into the "util" directory. 
 * Unused improts have also been removed. - Thomas J, 2/14/2016
 */

@SuppressWarnings({ "unused" })
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
