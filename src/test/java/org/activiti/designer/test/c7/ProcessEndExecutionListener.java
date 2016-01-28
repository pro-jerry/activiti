package org.activiti.designer.test.c7;

import java.io.Serializable;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;


public class ProcessEndExecutionListener implements ExecutionListener,Serializable{

	
	private static final long serialVersionUID = 1L;

	@Override
	public void notify(DelegateExecution execution) throws Exception {

		execution.setVariable("setInEndListener", true);
		System.out.println(this.getClass().getSimpleName()+" [,] "+execution.getEventName());
	}

}
