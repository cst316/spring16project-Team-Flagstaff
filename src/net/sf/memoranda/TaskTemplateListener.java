/**
 * Interface that defines the interactions between TaskTemplate listener methods and the task template list through the TaskTemplateManager class.
 * @author ggoforth -> Galen Goforth
 * ASURiteID: ghgofort
 * Class: CST 316 - Spring 2016 Online A
 */
package net.sf.memoranda;

/**
 * @author ggoforth
 *
 */
public interface TaskTemplateListener {
	public void TaskTemplateChanged(String id);
	public void TaskTemplateAdded();
	public void TaskTemplateRemoved();
}