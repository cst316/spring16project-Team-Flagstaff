/**
 * 
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
