package org.activiti.designer.test;

import org.activiti.engine.identity.User;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.Deployment;
import org.junit.Test;

public class CandidateUserInUserTaskTest extends AbstractTest{

	
	@Test
	@Deployment(resources={"diagrams/userAndGroupInUserTask.bpmn"})
	 public void testMultiCadiateUserInUserTask(){
		
		//添加用户jackchen
		User userJackChen = identityService.newUser("jackchen");
        userJackChen.setFirstName("Jack");
        userJackChen.setLastName("Chen");
        userJackChen.setEmail("jackchen@gmail.com");
        identityService.saveUser(userJackChen);
        
        // 添加用户henryyan
        User userHenryyan = identityService.newUser("henryyan");
        userHenryyan.setFirstName("Henry");
        userHenryyan.setLastName("Yan");
        userHenryyan.setEmail("yanhonglei@gmail.com");
        identityService.saveUser(userHenryyan);
        
        //启动流程
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("userAndGroupInUserTask");
        
        //jackchen作物候选人的任务
        Task jackchenTask = taskService.createTaskQuery().taskCandidateUser("jackchen").singleResult();
        System.out.println(jackchenTask);
        
        //henryyan作为候选人的任务
        Task henryyanTask = taskService.createTaskQuery().taskCandidateUser("henryyan").singleResult();
        System.out.println(henryyanTask);
        
        //jackchen签收任务
        taskService.claim(jackchenTask.getId(), "jackchen");
        
        // 再次查询用户henryyan是否拥有刚刚的候选任务
        henryyanTask = taskService.createTaskQuery().taskCandidateUser("henryyan").singleResult();
        System.out.println(henryyanTask);
	}
}
