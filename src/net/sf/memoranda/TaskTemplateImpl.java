/**
 * 
 */
package net.sf.memoranda;

import java.util.ArrayList;
import java.util.Hashtable;

import nu.xom.Document;
import nu.xom.Element;

/**
 * @author ggoforth
 * @param <T>
 *
 */
public class TaskTemplateImpl<T> implements TaskTemplate<T> {
	Element _root;
	ArrayList<Project> _projects;
	private String id;
	String name;
	private ArrayList<CustomField<T>> fields;
	
	/**
	 * Constructor taking 3 parameters for root element
	 * @param Element root -> root element from XML 
	 */
	public TaskTemplateImpl(Element root){
		_root = root;
	}
	/**
	 * Constructor takes with 2 arguments
	 * @param String id
	 * @param String name
	 */
	public TaskTemplateImpl(String id, String name){
		this.id = id;
		this.name = name;
		_root = new Element("tasklist");
	}
	
	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id=id;
	}

	@Override
	public ArrayList<CustomField<T>> getFields() {
		return fields;
	}

	@Override
	public void setFields(ArrayList<CustomField<T>> fields) {
		this.fields=fields;
		
	}

	@Override
	public void addField(CustomField<T> field) {
		fields.add(field);
		
	}
	/**
	 * Returns the CustomField object at the given index
	 */
	public CustomField<T> getField(int index) {
		CustomField<T> field = fields.get(index);
		return field;
	}

	@Override
	public void removeField(String index) {
		fields.remove(index);
		
	}

	public boolean saveTemplate(TaskTemplateImpl<T> taskTemp){
		boolean success = false;
		//********Save to XML storage using TaskTemplateManager********
		TaskTemplateManager.removeTemplate(id);
		TaskTemplateManager.createTemplate(id);
		try{
			
			success=true;
			
		}catch(Exception e){
			System.out.println(e.toString());
			e.printStackTrace();
		}
		return success;
	}
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String name) {
		this.name=name;		
	}

	/**
	 * Adds the Project to the list of projects associated with the template
	 * @param projectId
	 */
	@Override
	public void addProject(String projectId) {
		
	}
	/**
	 * Returns the list of projects associated with the template
	 */
	@Override
	public ArrayList<Project> getProjects() {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * Remove Project Id from the list of projects associated with this task template
	 * @param projectId
	 * @return
	 */
	@Override
	public boolean removeProject(String projectId) {
		// TODO Auto-generated method stub
		return false;
	}
}
