package org.activiti.designer.test.c7;

import java.io.Serializable;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;

public class MyBean implements Serializable{

	 private static final long serialVersionUID = 1L;
	 
	 public void print() {
	        System.out.println("print content by print()");
	 }
	 
	 public String print(String name) {
	        System.out.println("print content by print(String name), value is :" + name);
	        return name += ", added by print(String name)";
	 }
	 
	 public void invokeTask(DelegateTask task) {
	        task.setVariable("setByTask", "I'm setted by DelegateTask, " + task.getVariable("name"));
	 }
	 
	 public String printBkey(DelegateExecution execution) {
	        String processBusinessKey = execution.getProcessBusinessKey();
	        System.out.println("process instance id: " + execution.getProcessInstanceId() + ", business key: " + processBusinessKey);
	        return processBusinessKey;
	 }
}
