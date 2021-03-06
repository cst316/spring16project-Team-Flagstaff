/**
 * DefaultTask.java
 * Created on 12.02.2003, 15:30:40 Alex
 * Package: net.sf.memoranda
 *
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package net.sf.memoranda;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Vector;

import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.date.CurrentDate;
import nu.xom.Attribute;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.Node;

/**
 *
 */
/*$Id: TaskImpl.java,v 1.15 2005/12/01 08:12:26 alexeya Exp $*/
@SuppressWarnings("rawtypes")
public class TaskImpl implements ITask, Comparable {

	private Element _element = null;
	private ITaskList _tl = null;

	/**
	 * Constructor for DefaultTask.
	 */
	public TaskImpl(Element taskElement, ITaskList tl) {
		_element = taskElement;
		_tl = tl;
	}


	public Element getContent() {
		return _element;
	}

	public CalendarDate getStartDate() {
		return new CalendarDate(_element.getAttribute("startDate").getValue());
	}

	public void setStartDate(CalendarDate date) {
		setAttr("startDate", date.toString());
	}

	public CalendarDate getEndDate() {
		String ed = _element.getAttribute("endDate").getValue();
		if (ed != "")
			return new CalendarDate(_element.getAttribute("endDate").getValue());
		ITask parent = this.getParentTask();
		if (parent != null)
			return parent.getEndDate();
		IProject pr = this._tl.getProject();
		if (pr.getEndDate() != null)
			return pr.getEndDate();
		return this.getStartDate();

	}

	/**
	 * Set the end date for the task
	 * @param date
	 * 
	 * Find Bugs fix - Code Defect List id = 007
	 * Code Review by ghgofort - 2/29/16
	 */
	public void setEndDate(CalendarDate date) {
		if (date == null) {
			setAttr("endDate", "");
		}else {
			setAttr("endDate", date.toString());
		}
	}

	public long getEffort() {
		Attribute attr = _element.getAttribute("effort");
		if (attr == null) {
			return 0;
		}
		else {
			try {
				return Long.parseLong(attr.getValue());
			}
			catch (NumberFormatException e) {
				System.out.println(e.toString());
				e.printStackTrace();
				return 0;
			}
		}
	}

	public void setEffort(long effort) {
		setAttr("effort", String.valueOf(effort));
	}

	/* 
	 * @see net.sf.memoranda.Task#getParentTask()
	 */
	public ITask getParentTask() {
		Node parentNode = _element.getParent();
		if (parentNode instanceof Element) {
			Element parent = (Element) parentNode;
			if (parent.getLocalName().equalsIgnoreCase("task")) 
				return new TaskImpl(parent, _tl);
		}
		return null;
	}

	public String getParentId() {
		ITask parent = this.getParentTask();
		if (parent != null)
			return parent.getID();
		return null;
	}

	public String getDescription() {
		Element thisElement = _element.getFirstChildElement("description");
		if (thisElement == null) {
			return null;
		}
		else {
			return thisElement.getValue();
		}
	}

	public void setDescription(String s) {
		Element desc = _element.getFirstChildElement("description");
		if (desc == null) {
			desc = new Element("description");
			desc.appendChild(s);
			_element.appendChild(desc);    	
		}
		else {
			desc.removeChildren();
			desc.appendChild(s);    	
		}
	}

	/**s
	 * @see net.sf.memoranda.ITask#getStatus()
	 */
	public int getStatus(CalendarDate date) {
		CalendarDate start = getStartDate();
		CalendarDate end = getEndDate();
		if (isFrozen())
			return ITask.FROZEN;
		if (isCompleted())
			return ITask.COMPLETED;

		if (date.inPeriod(start, end)) {
			if (date.equals(end))
				return ITask.DEADLINE;
			else
				return ITask.ACTIVE;
		}
		else if(date.before(start)) {
			return ITask.SCHEDULED;
		}

		if(start.after(end)) {
			return ITask.ACTIVE;
		}

		return ITask.FAILED;
	}
	/**
	 * Method isDependsCompleted.
	 * @return boolean
	 */
	/*
    private boolean isDependsCompleted() {
        Vector v = (Vector) getDependsFrom();
        boolean check = true;
        for (Enumeration en = v.elements(); en.hasMoreElements();) {
            Task t = (Task) en.nextElement();
            if (t.getStatus() != Task.COMPLETED)
                check = false;
        }
        return check;
    }
	 */
	private boolean isFrozen() {
		return _element.getAttribute("frozen") != null;
	}

	private boolean isCompleted() {
		return getProgress() == 100;
	}

	/**
	 * @see net.sf.memoranda.ITask#getID()
	 */
	public String getID() {
		return _element.getAttribute("id").getValue();
	}

	/**
	 * @see net.sf.memoranda.ITask#getText()
	 */
	public String getText() {
		return _element.getFirstChildElement("text").getValue();
	}

	public String toString() {
		return getText();
	}

	/**
	 * @see net.sf.memoranda.ITask#setText()
	 */
	public void setText(String s) {
		_element.getFirstChildElement("text").removeChildren();
		_element.getFirstChildElement("text").appendChild(s);
	}

	/**
	 * @see net.sf.memoranda.ITask#freeze()
	 */
	public void freeze() {
		setAttr("frozen", "yes");
	}

	/**
	 * @see net.sf.memoranda.ITask#unfreeze()
	 */
	public void unfreeze() {
		if (this.isFrozen())
			_element.removeAttribute(new Attribute("frozen", "yes"));
	}

	/**
	 * @see net.sf.memoranda.ITask#getDependsFrom()
	 */
	public Collection getDependsFrom() {
		Vector v = new Vector();
		Elements deps = _element.getChildElements("dependsFrom");
		for (int i = 0; i < deps.size(); i++) {
			String id = deps.get(i).getAttribute("idRef").getValue();
			ITask t = _tl.getTask(id);
			if (t != null)
				v.add(t);
		}
		return v;
	}
	/**
	 * @see net.sf.memoranda.ITask#addDependsFrom(net.sf.memoranda.ITask)
	 */
	public void addDependsFrom(ITask task) {
		Element dep = new Element("dependsFrom");
		dep.addAttribute(new Attribute("idRef", task.getID()));
		_element.appendChild(dep);
	}
	/**
	 * @see net.sf.memoranda.ITask#removeDependsFrom(net.sf.memoranda.ITask)
	 */
	public void removeDependsFrom(ITask task) {
		Elements deps = _element.getChildElements("dependsFrom");
		for (int i = 0; i < deps.size(); i++) {
			String id = deps.get(i).getAttribute("idRef").getValue();
			if (id.equals(task.getID())) {
				_element.removeChild(deps.get(i));
				return;
			}
		}
	}
	/**
	 * @see net.sf.memoranda.ITask#getProgress()
	 */
	public int getProgress() {
		return new Integer(_element.getAttribute("progress").getValue()).intValue();
	}
	/**
	 * @see net.sf.memoranda.ITask#setProgress(int)
	 */
	public void setProgress(int p) {
		if ((p >= 0) && (p <= 100))
			setAttr("progress", new Integer(p).toString());
	}
	/**
	 * @see net.sf.memoranda.ITask#getPriority()
	 */
	public int getPriority() {
		Attribute pa = _element.getAttribute("priority");
		if (pa == null)
			return ITask.PRIORITY_NORMAL;
		return new Integer(pa.getValue()).intValue();
	}
	/**
	 * @see net.sf.memoranda.ITask#setPriority(int)
	 */
	public void setPriority(int p) {
		setAttr("priority", String.valueOf(p));
	}

	private void setAttr(String a, String value) {
		Attribute attr = _element.getAttribute(a);
		if (attr == null)
			_element.addAttribute(new Attribute(a, value));
		else
			attr.setValue(value);
	}

	/**
	 * A "Task rate" is an informal index of importance of the task
	 * considering priority, number of days to deadline and current 
	 * progress. 
	 * 
	 * rate = (100-progress) / (numOfDays+1) * (priority+1)
	 * @param CalendarDate
	 * @return long
	 */

	private long calcTaskRate(CalendarDate d) {
		Calendar endDateCal = getEndDate().getCalendar();
		Calendar dateCal = d.getCalendar();
		int numOfDays = (endDateCal.get(Calendar.YEAR)*365 
				+ endDateCal.get(Calendar.DAY_OF_YEAR)) - 
				(dateCal.get(Calendar.YEAR)*365 
				+ dateCal.get(Calendar.DAY_OF_YEAR));
		if (numOfDays < 0) return -1; //Something wrong ?
		return (100-getProgress()) / (numOfDays+1) * (getPriority()+1);
	}

	/**
	 * @see net.sf.memoranda.ITask#getRate()
	 */

	public long getRate() {
		/*	   Task t = (Task)task;
	   switch (mode) {
		   case BY_IMP_RATE: return -1*calcTaskRate(t, date);
		   case BY_END_DATE: return t.getEndDate().getDate().getTime();
		   case BY_PRIORITY: return 5-t.getPriority();
		   case BY_COMPLETION: return 100-t.getProgress();
	   }
       return -1;
		 */
		return -1*calcTaskRate(CurrentDate.get());
	}

	/*
	 * Comparable interface
	 */

	public int compareTo(Object o) {
		ITask task = (ITask) o;
		if(getRate() > task.getRate())
			return 1;
		else if(getRate() < task.getRate())
			return -1;
		else 
			return 0;
	}

	public boolean equals(Object o) {
		return ((o instanceof ITask) && (((ITask)o).getID().equals(this.getID())));
	}

	/* 
	 * @see net.sf.memoranda.Task#getSubTasks()
	 */
	@SuppressWarnings("unchecked")
	public Collection getSubTasks() {
		Elements subTasks = _element.getChildElements("task");
		return convertToTaskObjects(subTasks);
	}

	private Collection convertToTaskObjects(Elements tasks) {
		Vector v = new Vector();
		for (int i = 0; i < tasks.size(); i++) {
			ITask t = new TaskImpl(tasks.get(i), _tl);
			v.add(t);
		}
		return v;
	}

	/* 
	 * @see net.sf.memoranda.Task#getSubTask(java.lang.String)
	 */
	public ITask getSubTask(String id) {
		Elements subTasks = _element.getChildElements("task");
		for (int i = 0; i < subTasks.size(); i++) {
			if (subTasks.get(i).getAttribute("id").getValue().equals(id))
				return new TaskImpl(subTasks.get(i), _tl);
		}
		return null;
	}

	/* 
	 * @see net.sf.memoranda.Task#hasSubTasks()
	 */
	public boolean hasSubTasks(String id) {
		Elements subTasks = _element.getChildElements("task");
		for (int i = 0; i < subTasks.size(); i++) 
			if (subTasks.get(i).getAttribute("id").getValue().equals(id))
				return true;
		return false;
	}

	/**
	 * Gets a CustomField<T> object by index in the list
	 * @return CustomField<T>
	 * @param int index
	 */
	@SuppressWarnings("unchecked")
	public <T> CustomField<T> getField(String name) {
		Elements cflds = _element.getChildElements("customField");
		if(cflds!=null){
			for (int i = 0; i < cflds.size(); i++) {
				if (cflds.get(i).getFirstChildElement("name").
						getValue().equals(name)){
					Element e = cflds.get(i);
					String fname="", min="", max="",
							req="", data="", type="";
					CustomField cf=null;
					boolean isReq =
							(req.compareToIgnoreCase("true")==0)? 
									true:false;
					if(e.getFirstChildElement("name").getValue()!=null)
						fname = e.getFirstChildElement("name").getValue();
					if(e.getAttributeValue("minValue")!=null)
						min = e.getAttributeValue("minValue");
					if(e.getAttributeValue("maxValue")!=null)
						max = e.getAttributeValue("maxValue");
					if(e.getAttributeValue("isRequired")!=null)
						req = e.getAttributeValue("isRequired");
					if(e.getFirstChildElement("data").getValue()!=null)
						data = e.getFirstChildElement("data").getValue();
					if(e.getFirstChildElement("dataType").getValue()!=null)
						type = e.getFirstChildElement("type").getValue();
					if(type.compareToIgnoreCase("Integer")==0){
						int d = Integer.parseInt(data);
						cf = new CustomField<Integer>(fname, isReq, d);
					}else if(type.compareToIgnoreCase("CalendarDate")==0){
						CalendarDate cd = new CalendarDate(data);
						cf = new CustomField<CalendarDate>(fname, isReq, cd);
					}else{
						cf = new CustomField<String>(fname, isReq, data);
					}
					return cf;
				}
			}
		}
		return null;


	}

	/**
	 * Sets the value of a CustomField<T> at the given index
	 * @param CustomField<T>
	 * @param String index 
	 * 
	 */
	public <T> void setField(CustomField<T> field, String name) {
		Elements cfs = _element.getChildElements("customField");
		if(cfs!=null){
			for (int i = 0; i < cfs.size(); i++) {
				if (cfs.get(i).getFirstChildElement("name").
						getValue().equals(name)){
					Element e = cfs.get(i);
					e.removeChildren();
					Element fn = new Element("name");
					fn.appendChild(field.getFieldName());
					e.appendChild(fn);
					e.appendChild(fn);
					fn = new Element("data");
					fn.appendChild(field.dataToString());
					fn.addAttribute(new Attribute("minValue",Integer.
							toString(field.getMin())));
					fn.addAttribute(new Attribute("maxValue",Integer.
							toString(field.getMax())));
					fn.addAttribute(new Attribute("isRequired",Boolean.
							toString(field.isRequired())));
					e.appendChild(fn);
					fn = new Element("dataType");
					fn.appendChild(field.getDataType());
					e.appendChild(fn);
				}
			}
		}
		else{
			System.out.println("***[Debug] Attempted to modify a null field");
		}
	}
	/**
	 * Adds a new CustomField<T> object to the task
	 * @param CustomField<T>
	 */
	public <T> void addField(CustomField<T> field) {
		Element e = new Element("customField");
		Element fn = new Element("name");
		fn.appendChild(field.getFieldName());
		e.appendChild(fn);
		e.appendChild(fn);
		fn = new Element("data");
		fn.appendChild(field.dataToString());
		fn.addAttribute(new Attribute("minValue",Integer.
				toString(field.getMin())));
		fn.addAttribute(new Attribute("maxValue",Integer.
				toString(field.getMax())));
		fn.addAttribute(new Attribute("isRequired",Boolean.
				toString(field.isRequired())));
		e.appendChild(fn);
		fn = new Element("dataType");
		fn.appendChild(field.getDataType());
		e.appendChild(fn);

	}

	/**
	 * Removes a CustomField<T> object from the Task
	 * Also re-indexes all CustomField<T> objects that 
	 * have a higher index
	 * than the parameter index by subtracting 1 from each.
	 * @param String index of the field to be removed
	 */
	public <T> void removeField(String name) {
		Elements cfs = _element.getChildElements("customField");
		int count = getFieldCount();
		if(cfs!=null){
			for (int i = 0; i < cfs.size(); i++) {
				if (cfs.get(i).getFirstChildElement("name").
						getValue().equals(name)){
					Element e = cfs.get(i);
					e.removeChildren();
					e.detach();
					break;
				}
			}
		}
		else{
			System.out.println("***[Debug] Attempted to modify a null field");
		}
	}

	/**
	 * Adds a new CustomField<T> object to the task
	 * @return ArrayList<CustomField<T>>
	 */
	@SuppressWarnings("unchecked")
	public <T> ArrayList<CustomField<T>> getFieldArray() {
		ArrayList<CustomField<T>> cFields = new ArrayList<CustomField<T>>();
		Elements ele = _element.getChildElements("customField");
		for(int x=0;x<ele.size();x++){
			CustomField<T> cf = new CustomField<T>(
				(ele.get(x).getAttributeValue("fieldName")==null)?"":ele.
						get(x).getAttributeValue("fieldName"), // String -> field name
				false, 										   // boolean -> Required field
				(ele.get(x).getValue()==null)?(T)"":(T)ele.
						get(x).getValue()					   // T -> field data
			);
			cFields.add(cf);
		}
		return cFields;
	}

	/**
	 * Returns a count of the custom fields in this task object
	 * @return int 
	 */
	public <T> int getFieldCount() {
		return _element.getChildElements("customField").size();
	}


}
