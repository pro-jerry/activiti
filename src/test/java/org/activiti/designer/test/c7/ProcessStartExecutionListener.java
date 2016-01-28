package org.activiti.designer.test.c7;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

public class ProcessStartExecutionListener implements ExecutionListener{

	
	private static final long serialVersionUID = 1L;

	@Override
	public void notify(DelegateExecution execution) throws Exception {

		execution.setVariable("setInStartListener", true);
		System.out.println(this.getClass().getSimpleName()+" , "+execution.getEventName());
	}
	
	

}
