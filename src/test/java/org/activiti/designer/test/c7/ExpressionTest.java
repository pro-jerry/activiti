package org.activiti.designer.test.c7;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.activiti.designer.test.AbstractTest;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.Deployment;
import org.junit.Test;

public class ExpressionTest extends AbstractTest{

	@Test
	@Deployment(resources="diagrams/expression.bpmn")
	public void testExpression(){
		
		//首选将需要的变量初始化
		MyBean myBean = new MyBean();
		Map<String,Object> variables = new HashMap<String, Object>();
		//创建一个序列化的MyBean对象
		variables.put("myBean", myBean);
		String name = "Herry Yan";
		variables.put("name", name);
		
		//设置启动流程人ID
		identityService.setAuthenticatedUserId("henryyan");
		//业务ID
		String businessKey = "9999";
		ProcessInstance processInstance = 
				runtimeService.startProcessInstanceByKey("expression",businessKey,variables);
		
		assertEquals("henryyan",runtimeService.getVariable(processInstance.getId(), "authenticatedUserIdForTest"));
		assertEquals("Henry Yan,added by print(String name)",runtimeService.getVariable(processInstance.getId(), "returnValue"));
		assertEquals(businessKey,runtimeService.getVariable(processInstance.getId(), "businessKey"));
		
		//DelegateTask 设置的变量
		Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
		String setByTask = (String) taskService.getVariable(task.getId(), "setByTask");
		assertEquals("I'm setted by DelegateTask, "+name , setByTask);
		
		
		
		
	}
}
