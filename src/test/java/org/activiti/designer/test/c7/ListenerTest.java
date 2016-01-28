package org.activiti.designer.test.c7;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.activiti.designer.test.AbstractTest;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.Deployment;
import org.junit.Test;

public class ListenerTest extends AbstractTest{

	@Test
	@Deployment(resources={"diagrams/listener.bpmn"})
	public void testListener(){
		
		Map<String,Object> variables = new HashMap<String, Object>();
		//创建一个监听器实例并设置到变量中
		variables.put("endListener", new ProcessEndExecutionListener());
		variables.put("assignmentDelegate", new TaskAssigneeListener());
		variables.put("name", "Henry Yan");
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("listener",variables);
		
		//校验是否执行了启动监听
		String processInstanceId = processInstance.getId();
		assertTrue((Boolean)runtimeService.getVariable(processInstanceId, "setInStartListener"));
		
		//校验任务监听器是否是否被执行
		Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).taskAssignee("jenny").singleResult();
		String setInTaskCreate = (String) taskService.getVariable(task.getId(), "setInTaskCreate");
		
		assertEquals("create,Hello Henry Yan",setInTaskCreate);
		taskService.complete(task.getId());
		
		
	}
}
