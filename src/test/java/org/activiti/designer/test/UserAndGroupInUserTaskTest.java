package org.activiti.designer.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.Deployment;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserAndGroupInUserTaskTest extends AbstractTest{

	@Before
	public void setUp() throws Exception{
		
		//初始化7个Service实例
		super.setUp();
		//创建并保存数组对象
		Group group = identityService.newGroup("deptLeader");
		group.setName("部门领导");
		group.setType("assignment");
		identityService.saveGroup(group);
		
		//创建并保存用户对象
		User user = identityService.newUser("henryyen");
		user.setFirstName("Henrry");
		user.setLastName("Yan");
		user.setEmail("jerry@163.com");
		identityService.saveUser(user);
		
		//把用户henryyen 加入到组 deptLeader
		identityService.createMembership("henryyen", "deptLeader");
	}
	
	@After
	public void afterInvokeTestMethod(){
		
		identityService.deleteMembership("henryyen", "deptLeader");
		identityService.deleteGroup("deptLeader");
		identityService.deleteUser("henryyen");
	}
	
	@Test
	@Deployment(resources={"diagrams/userAndGroupInUserTask.bpmn"})
	public void testuserAndGroupInUserTask(){
		
		//启动流程
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("userAndGroupInUserTask");
		assertNotNull(processInstance);
		//根据角色查询任务
		Task task = taskService.createTaskQuery().taskCandidateUser("henryyen").singleResult();
		taskService.claim(task.getId(), "henryyen");
		taskService.complete(task.getId());
	}
	
	@Test
	@Deployment(resources={"diagrams/userAndGroupInUserTask.bpmn"})
	 public void testUserTaskWithGroupContainsTwoUser(){
		
		//setUp的基础上再添加一个用户，然后加入到组deptLeader
		User user = identityService.newUser("jackchen");
		user.setFirstName("Jack");
		user.setLastName("Chen");
		user.setEmail("hj@163.com");
		identityService.saveUser(user);
		
		//把用户jackchen加入到组deptLeader中
		identityService.createMembership("jackchen", "deptLeader");
		//启动流程
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("userAndGroupInUserTask");
		assertNotNull(processInstance);
		System.err.println(processInstance);
		
		//jackchen作为候选人的任务
		Task jackchenTask = taskService.createTaskQuery().taskCandidateUser("jackchen").singleResult();
		assertNotNull(jackchenTask);
		System.err.println(jackchenTask);
		//jackchen签收的任务
		taskService.claim(jackchenTask.getId(), "jackchen");
		
		 // henryyan作为候选人的任务
        Task henryyanTask = taskService.createTaskQuery().taskCandidateUser("henryyan").singleResult();
//        assertNotNull(henryyanTask);
        System.err.println(henryyanTask);
        // jackchen签收任务
        taskService.claim(jackchenTask.getId(), "jackchen");

        // 再次查询用户henryyan是否拥有刚刚的候选任务
        henryyanTask = taskService.createTaskQuery().taskCandidateUser("henryyan").singleResult();
        assertNull(henryyanTask);
        System.err.println(henryyanTask);
	}
	
	
}
