package org.activiti.designer.test.c7;

import java.io.Serializable;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class TaskAssigneeListener implements TaskListener, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void notify(DelegateTask delegateTask) {

		System.out.println(delegateTask.getEventName()+",任务分配给:"+delegateTask.getAssignee());
	}

	
}
