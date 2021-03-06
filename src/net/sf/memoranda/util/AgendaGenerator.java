/*
 * AgendaGenerator.java Package: net.sf.memoranda.util Created on 13.01.2004
 * 5:52:54 @author Alex
 */
package net.sf.memoranda.util;

import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.IEvent;
import net.sf.memoranda.EventsManager;
import net.sf.memoranda.EventsScheduler;
import net.sf.memoranda.IProject;
import net.sf.memoranda.ProjectManager;
import net.sf.memoranda.ITask;
import net.sf.memoranda.ITaskList;
import net.sf.memoranda.date.CalendarDate;
import nu.xom.Element;
/**
 *  AgendaGenerator class.
 *  Generates new Agenda objects, handles associated 
 *  tasks and annotation stickers and their operations.
 *  
 * Update: Self Checked altered method with Checkstyle, FixBugs, 
 * and for defects.  
 * Found checkstyle issues with indentation, naming, grammar, and length.
 * ---Some Length issues could not be resolved due to Checkstyle
 * not seeming to recognize reduced character lines.---
 * No Fixbugs found, issues resolved and re-checked - 2/20/2016
 *  
 */

/*$Id: AgendaGenerator.java,v 1.12 2005/06/13 21:25:27 velhonoja Exp $*/

public class AgendaGenerator {

	static String header =
			"<html><head><title></title>\n"
					+ "<style>\n"
					+ "    body, td {font: 12pt sans-serif}\n"
					+ "    h1 {font:20pt sans-serif; "
					+ "background-color:#E0E0E0; margin-top:0}\n"
					+ "    h2 {font:16pt sans-serif; margin-bottom:0}\n"
					+ "    li {margin-bottom:5px}\n"
					+ " a {color:black; text-decoration:none}\n"             
					+ "</style></head>\n"
					+ "<body><table width=\"100%\" height=\"100%\" "
					+ "border=\"0\" cellpadding=\"4\" cellspacing=\"4\">\n"
					+ "<tr>\n";
	static String footer = "</td></tr></table></body></html>";

	public static String generateTasksInfo(
			IProject proj, CalendarDate date, Collection expandedTasks) {    	    	
		ITaskList tl;
		if (proj.getID().equals(CurrentProject.get().getID())) {
			tl = CurrentProject.getTaskList();        	
		}
		else {
			tl = CurrentStorage.get().openTaskList(proj);        	
		}

		String progressString = "";
		int progress = getProgress(tl);
		if (progress > -1) {
			progressString += "<br>" + Local.getString("Total progress") + ": " + progress + "%"; 
		}
		progressString += "</td></tr></table>\n";

		Vector tasks = (Vector) tl.getActiveSubTasks(null,date);        
		if (tasks.size() == 0) {
			progressString += "<p>" + Local.getString("No actual tasks") + ".</p>\n";        	
		}else {
			progressString += Local.getString("Actual tasks") + ":<br>\n<ul>\n";            

			//            TaskSorter.sort(tasks, date, TaskSorter.BY_IMP_RATE); 
			// 								TODO: configurable method
			
			Collections.sort(tasks);
			for (Iterator index = tasks.iterator(); index.hasNext();) {
				ITask tsk = (ITask) index.next();
				// Always show active tasks only on agenda page from now on.
				// If it's not active, then it's probably 
				//  "not on the agenda" isn't it?
				//        		if(Context.get("SHOW_ACTIVE_TASKS_ONLY").equals
				//                  (new Boolean(true))) {
				//                    if (!((t.getStatus() == Task.ACTIVE) || 
				//                            (t.getStatus() == Task.DEADLINE) || 
				//                                (t.getStatus() == Task.FAILED))) {
				//                    	continue;
				//                	}	
				//        		}
				// ignore if it's a sub-task, iterate over ROOT tasks here only
				if (tl.hasParentTask(tsk.getID())) {
					continue;
				}

				progressString = progressString + renderTask(proj, date, tl, tsk, 0,expandedTasks);
				if(expandedTasks.contains(tsk.getID())) {
					progressString = progressString + expandRecursively(proj,date,tl,tsk,expandedTasks,1);
				}        		
			}
			progressString += "\n</ul>\n";
		}

		//        Util.debug("html for project " + p.getTitle() + " is\n" + s); 
		return progressString;
	}

	/**
	 * Method expandRecursively.
	 * @param t
	 * @param expandedTasks
	 */
	private static String expandRecursively(IProject proj,CalendarDate date, ITaskList tl,ITask tsk, Collection expandedTasks, int level) {
		Util.debug("Expanding task " + tsk.getText() + " level " + level);

		Collection st = tl.getActiveSubTasks(tsk.getID(),date);

		Util.debug("number of subtasks " + st.size());

		String string = "\n<ul>\n";

		for (Iterator iter = st.iterator(); iter.hasNext();) {
			ITask subTask = (ITask) iter.next();
			//			if(Context.get("SHOW_ACTIVE_TASKS_ONLY").
			//               equals(new Boolean(true))) {
			//                if (!((subTask.getStatus() == Task.ACTIVE) || 
			//                         (subTask.getStatus() == Task.DEADLINE) || 
			//                                (subTask.getStatus() == Task.FAILED))) {
			//                	continue;
			//            	}	
			//			}
			string = string + renderTask(proj,date,tl,subTask,level,expandedTasks);
			if (expandedTasks.contains(subTask.getID())) {
				string = string + expandRecursively
						 (proj,date,tl,subTask,expandedTasks,level + 1);
			}
		}
		string += "\n</ul>\n";

		return string;
	}

	/**
	 * Method renderTask.
	 * @param p
	 * @param date
	 * @param s
	 * @param t
	 * @return
	 */
	private static String renderTask(IProject proj, CalendarDate date, ITaskList tl, ITask tsk, int level, Collection expandedTasks) {
		String string = "";

		int pg = tsk.getProgress();
		String progress = "";
		if (pg == 100){
			progress = "<font color=\"green\">"+Local.getString("Completed")+"</font>";
		}else{
			progress = pg + Local.getString("% done");
		}

		//		String nbsp = "&nbsp;&nbsp;";
		//		String spacing = "";
		//		for(int i = 0; i < level; i ++) {
		//			spacing = spacing + nbsp;
		//		}
		//		Util.debug("Spacing for task " + t.getText() + " is " + spacing);

		String subTaskOperation = "";
		if (tl.hasSubTasks(tsk.getID())) {
			//			Util.debug("Task " + t.getID() + " has subtasks");
			if (expandedTasks.contains(tsk.getID())) {
				//				Util.debug("Task " + t.getID() 
				//                      + " is in list of expanded tasks");
				subTaskOperation = "<a href=\"memoranda:closesubtasks#" + tsk.getID()+ "\">(-)</a>";				
			}
			else {
				//				Util.debug("Task " + t.getID() 
			    //                      + " is not in list of expanded tasks");
				subTaskOperation = "<a href=\"memoranda:expandsubtasks#" + tsk.getID()+ "\">(+)</a>";
			}
		}

		string += "<a name=\"" + tsk.getID() + "\"><li><p>" + subTaskOperation + "<a href=\"memoranda:tasks#"
				+ proj.getID()
				+ "\"><b>"
				+ tsk.getText()
				+ "</b></a> : " 
				+ progress                 
				+ "</p>"
				+ "<p>"
				+ Local.getString("Priority")
				+ ": "
				+ getPriorityString(tsk.getPriority())
				+ "</p>";
		/*<<<<<<< AgendaGenerator.java
		if (!(t.getStartDate().getDate()).after(t.getEndDate().getDate())) {
		    if (t.getEndDate().equals(date))
		        s += "<p><font color=\"#FF9900\"><b>"
		            + Local.getString("Should be done today")
		            + ".</b></font></p>";
		    else {
		        Calendar endDateCal = t.getEndDate().getCalendar();
		        Calendar dateCal = date.getCalendar();
		        int numOfDays = (endDateCal.get(Calendar.YEAR)*365 
		                   + endDateCal.get(Calendar.DAY_OF_YEAR)) - 
		                (dateCal.get(Calendar.YEAR)*365 
		                          + dateCal.get(Calendar.DAY_OF_YEAR));
		        String days = "";
		        if (numOfDays > 1)
		            days = Local.getString("in")
		                      +" "+numOfDays+" "+Local.getString("day(s)");
		        else
		            days = Local.getString("tomorrow");
		        s += "<p>"
		            + Local.getString("Deadline")
		            + ": <i>"
		            + t.getEndDate().getMediumDateString()
		            + "</i> ("+days+")</p>";
		    }                    
		}
=======*/
		if (tsk.getEndDate().equals(date))
			string += "<p><font color=\"#FF9900\"><b>"
					+ Local.getString("Should be done today")
					+ ".</b></font></p>";
		else {
			Calendar endDateCal = tsk.getEndDate().getCalendar();
			Calendar dateCal = date.getCalendar();
			int numOfDays = (endDateCal.get(Calendar.YEAR)*365 
					    + endDateCal.get(Calendar.DAY_OF_YEAR)) 
							- (dateCal.get(Calendar.YEAR)*365 
							     + dateCal.get(Calendar.DAY_OF_YEAR));
			String days = "";
			if(numOfDays > 0) {
				if (numOfDays > 1) {
					days = Local.getString("in")
							+" "+numOfDays+" "+Local.getString("day(s)");		        
				}else {
					days = Local.getString("tomorrow");		        
				}
				string += "<p>"
						+ Local.getString("Deadline")
						+ ": <i>"
						+ tsk.getEndDate().getMediumDateString()
						+ "</i> ("+days+")</p>";		        
			}else if ((numOfDays < 0) && (numOfDays > -10000)) {
				String overdueDays = String.valueOf(-1 * numOfDays);
				string += "<p><font color=\"#FF9900\"><b>"
						+ overdueDays + " "
						+ Local.getString("days overdue")
						+ ".</b></font></p>";
			}else {
				// tasks that have no deadline
				string += "<p>"
						+ Local.getString("No Deadline")
						+ "</p>";		        
			}
		}                     
		//>>>>>>> 1.4
		string += "</li>\n";
		return string;
	}

	public static int getProgress(ITaskList tl) {
		Vector vect = (Vector) tl.getAllSubTasks(null);
		if (vect.size() == 0){
			return -1;
		}
		int progress = 0;
		for (Enumeration en = vect.elements(); en.hasMoreElements();) {
			ITask tsk = (ITask) en.nextElement();
			progress += tsk.getProgress();
		}
		return (progress * 100) / (vect.size() * 100);
	}

	public static String getPriorityString(int priority) {
		switch (priority) {
		case ITask.PRIORITY_NORMAL :
			return "<font color=\"green\">"+Local.getString("Normal")+"</font>";
		case ITask.PRIORITY_LOW :
			return "<font color=\"#3333CC\">"+Local.getString("Low")+"</font>";
		case ITask.PRIORITY_LOWEST :
			return "<font color=\"#666699\">"+Local.getString("Lowest")+"</font>";
		case ITask.PRIORITY_HIGH :
			return "<font color=\"#FF9900\">"+Local.getString("High")+"</font>";
		case ITask.PRIORITY_HIGHEST :
			return "<font color=\"red\">"+Local.getString("Highest")+"</font>";
		}
		return "";
	}

	public static String generateProjectInfo(IProject proj, CalendarDate date, Collection expandedTasks) {
		String string = "<h2><a href=\"memoranda:project#"
				+ proj.getID()
				+ "\">"
				+ proj.getTitle()
				+ "</a></h2>\n"
				+ "<table border=\"0\" width=\"100%\" cellpadding=\"2\" "
				+ "bgcolor=\"#EFEFEF\"><tr><td>" 
				+ Local.getString("Start date")+": <i>"
				+ proj.getStartDate().getMediumDateString()+"</i>\n";
		if (proj.getEndDate() != null){
			string += "<br>" + Local.getString("End date")
			+": <i>"+proj.getEndDate().getMediumDateString()
			+"</i>\n"; 
		}
		return string + generateTasksInfo(proj, date,expandedTasks);   
	}

	public static String generateAllProjectsInfo(CalendarDate date, Collection expandedTasks) {
		String string =
				"<td width=\"66%\" valign=\"top\">"
						+ "<h1>"
						+ Local.getString("Projects and tasks")
						+ "</h1>\n";
		string += generateProjectInfo(CurrentProject.get(), date, expandedTasks);        
		for (Iterator i = ProjectManager.getActiveProjects().iterator();
				i.hasNext();
				) {
			IProject proj = (IProject) i.next();
			if (!proj.getID().equals(CurrentProject.get().getID())){
				string += generateProjectInfo(proj, date, expandedTasks);
			}
		}
		return string + "</td>";
	}

	public static String generateEventsInfo(CalendarDate date) {
		String string =
				"<td width=\"34%\" valign=\"top\">"
						+ "<a href=\"memoranda:events\"><h1>"
						+ Local.getString("Events")
						+ "</h1></a>\n"
						+ "<table width=\"100%\" valign=\"top\" border=\"0\" "
						+ "cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#FFFFF6\">\n";
		Vector vect = (Vector) EventsManager.getEventsForDate(date);
		int number = 0;
		for (Iterator index = vect.iterator(); index.hasNext();) {
			IEvent evt = (IEvent) index.next();
			String txt = evt.getText();
			String iurl =
					net.
					sf.
					memoranda.
					ui.
					AppFrame.
					class.getResource("resources/agenda/spacer.gif").
					toExternalForm();
			if (date.equals(CalendarDate.today())) {
				if (evt.getTime().after(new Date())){
					txt = "<b>" + txt + "</b>";
				}
				if ((EventsScheduler.isEventScheduled())
						&& (EventsScheduler.
								getFirstScheduledEvent().
								getTime().
								equals(evt.getTime()))) {
					iurl =
							net.
							sf.
							memoranda.
							ui.
							AppFrame.
							class.getResource("resources/agenda/arrow.gif").
							toExternalForm();
				}
			}
			String icon =
					"<img align=\"right\" width=\"16\" height=\"16\" src=\""
							+ iurl
							+ "\" border=\"0\"  hspace=\"0\" vspace=\"0\" alt=\"\">";

			string += "<tr>\n<td>"
					+ icon
					+ "</td>"
					+ "<td nowrap class=\"eventtime\">"
					+ evt.getTimeString()
					+ "</td>"
					+ "<td width=\"100%\" class=\"eventtext\">&nbsp;&nbsp;"
					+ txt
					+ "</td>\n"
					+ "</tr>";

		}
		return string + "</table>";
	}

	/**This Method generates the labels for the buttons, which
	* are attached to the Annotation Sticker area of the Agenda Panel
	* Changes were made to this method by Thomas Johnson on 2/20/2016 
	* for the User Story# 55 - All Tasks.
	* 
	* Update: Self Checked altered method with Checkstyle, FixBugs, 
	* and for defects, found no issues. - 2/20/2016
	* 
	* @param CalendarDate
	* @return sheader1
	*/
	public static String generateStickers(CalendarDate date){
		String iurl = net.sf.memoranda.ui.AppFrame.class.
				getResource("resources/agenda/addsticker.gif").toExternalForm();
		String iurl2 =net.sf.memoranda.ui.AppFrame.class.
				getResource("resources/agenda/removesticker.gif").toExternalForm();
		//Line 375-379 altered by Thomas Johnson on 2/20/2016, for US-55, Task 58
		String sheader1 = "<hr><hr><table border=\"0\" cellpadding=\"0\" width=\"100%\"><tr><td><a href=\"memoranda:importstickers\"><b>"+Local.getString("Import annotation")+"</b></a></td></tr></table>"
				 +   "<table border=\"0\" cellpadding=\"0\" width=\"100%\"><tr><td><a href=\"memoranda:addsticker\"><img align=\"left\" width=\"22\" height=\"22\" src=\""				
				 + iurl
				+ "\" border=\"0\"  hspace=\"0\" vspace=\"0\" alt=\"New sticker\"></a></td><td width=\"100%\"><a href=\"memoranda:addsticker\"><b>&nbsp;"
				+Local.getString("Add sticker")+"</b></a></td></tr></table>";
		PriorityQueue priQueue = sortStickers();
		while(!priQueue.Vacia()){
		Element el = priQueue.extraer();
		String id = el.getAttributeValue("id");
		String txt = el.getValue();
		//Line 386-395 altered by Thomas Johnson on 2/20/2016, for US-55, TSK-59 & TSK-60
		sheader1 += "\n<table border=\"0\" cellpadding=\"0\" width=\"100%\"><table width=\"100%\"><tr bgcolor=\"#E0E0E0\"><td><a href=\"memoranda:editsticker#"+id
				+"\">"+Local.getString("EDIT")+"</a></td><td width=\"70%\"><a href=\"memoranda:exportstickertxt#"+id
				+"\">"+Local.getString("EXPORT TXT")+"</a></td><td width=\"70%\"><a href=\"memoranda:exportstickerhtml#"+id
				+"\">"+Local.getString("EXPORT HTML")+"</a></td><td width=\"70%\"><a href=\"memoranda:expandsticker#"+id
				+"\">"+Local.getString("OPEN IN A NEW WINDOW")
				+"</></td><td align=\"right\">" 
				+"&nbsp;"+"<a href=\"memoranda:removesticker#"+id+"\"><img align=\"left\" width=\"14\" height=\"14\" src=\""
                + iurl2+ "\" border=\"0\"  "
                + "hspace=\"0\" vspace=\"0\" alt=\"Remove sticker\"></a></td></table></tr><tr><td>"
                +txt+"</td></tr></table>";
        }
        sheader1 += "<hr>";
		return sheader1;
	}

    private static PriorityQueue sortStickers(){
        Map stickers = EventsManager.getStickers();
        PriorityQueue priorityQueue = new PriorityQueue(stickers.size());
    	for (Iterator index = stickers.keySet().iterator(); index.hasNext();) {
        	String id = (String)index.next();
        	Element el = (Element)stickers.get(id);
        	int num=2;
        	num=Integer.parseInt(el.getAttributeValue("priority"));
        	priorityQueue.insertar(new Pair(el,num));
    	}
    	return priorityQueue;
    }
    
	private static String addExpandHyperLink(String txt, String id) {
		String ret="";
		int first=txt.indexOf(">");
		int last=txt.lastIndexOf("<");
		ret=txt.substring(0, first+1)+"<a href=\"memoranda:expandsticker#"+id+"\">"+txt.substring(first+1, last)
				+"</a>"+txt.substring(last);
		return ret;
	}
	private static String addEditHyperLink(String txt, String id) {
		
		String ret="";
		int first=txt.indexOf(">");
		int last=txt.lastIndexOf("<");
		ret=txt.substring(0, first+1)+"<a href=\"memoranda:editsticker#"+id+"\">"+txt.substring(first+1, last)+"</a>"+txt.substring(last);
		 return ret;
		 }
	
	public static String getAgenda(CalendarDate date, Collection expandedTasks) {
		String string = header;
		string += generateAllProjectsInfo(date, expandedTasks);
		string += generateEventsInfo(date);
		string += generateStickers(date);
		//        /*DEBUG*/System.out.println(s+FOOTER);
		return string + footer;
	}
	/*    
    we do not need this. Tasks are sorted using the Comparable interface
    public static class TaskSorter {

        public static final int BY_IMP_RATE = 0;
        public static final int BY_END_DATE = 1;
        public static final int BY_PRIORITY = 2;
        public static final int BY_COMPLETION = 3;

        private static Vector tasks = null;
        private static CalendarDate date = null;  
        private static int mode = 0;

        public static long calcTaskRate(Task t, CalendarDate d) {
            /*
	 * A "Task rate" is an informal index of importance of the task
	 * considering priority, number of days to deadline and current
	 * progress.
	 * 
	 * rate = (100-progress) / (numOfDays+1) * (priority+1)
	 * /
            Calendar endDateCal = t.getEndDate().getCalendar();
            Calendar dateCal = d.getCalendar();
            int numOfDays = (endDateCal.get(Calendar.YEAR)*365 
               + endDateCal.get(Calendar.DAY_OF_YEAR)) - 
                            (dateCal.get(Calendar.YEAR)*365 + dateCal.get(Calendar.DAY_OF_YEAR));
            if (numOfDays < 0) return -1; //Something wrong ?
            return (100-t.getProgress()) / (numOfDays+1) * (t.getPriority()+1);
        }

        public static long getRate(Object task) {
            Task t = (Task)task;
            switch (mode) {
                case BY_IMP_RATE: return -1*calcTaskRate(t, date);
                case BY_END_DATE: return t.getEndDate().getDate().getTime();
                case BY_PRIORITY: return 5-t.getPriority();
                case BY_COMPLETION: return 100-t.getProgress();
            }
            return -1;         
        }

        private static void doSort(int L, int R) { // Hoar's QuickSort
            int i = L;
            int j = R;
            long x = getRate(tasks.get((L + R) / 2));
            Object w = null;
            do {
                while (getRate(tasks.get(i)) < x) 
                    i++;
                while (x < getRate(tasks.get(j)) && j > 0) 
                    if (j > 0) j--;              
                if (i <= j) {
                    w = tasks.get(i);
                    tasks.set(i, tasks.get(j));
                    tasks.set(j, w);
                    i++;
                    j--;
                }
            }
            while (i <= j);
            if (L < j) 
                doSort(L, j);       
            if (i < R) 
                doSort(i, R);         
        }

        public static void sort(Vector theTasks, CalendarDate theDate, int theMode) {
            if (theTasks == null) return;
            if (theTasks.size() <= 1) return;
            tasks = theTasks; 
            date = theDate;
            mode = theMode;
            doSort(0, tasks.size() - 1);
        }

    }
	 */
}
