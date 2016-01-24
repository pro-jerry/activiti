package org.activiti.designer.test;

import static org.junit.Assert.*;

import java.util.List;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Rule;
import org.junit.Test;

public class IdentifyServiceTest {

	@Rule
	public ActivitiRule activitiRule = new ActivitiRule("activiti.cfg.xml");
	
	/**
	 * 用户管理API演示
	 */
	@Test
	public void testUser(){
		
		System.out.println("123");
		//获取IdentityService实例
		IdentityService identityService = activitiRule.getIdentityService();
		
		//创建一个用户
		User user = identityService.newUser("henryyan");
		user.setFirstName("Jerry");
		user.setLastName("Yan");
		user.setEmail("jerry@163.com");
		
		//保存用户到数据库
		identityService.saveUser(user);
		//验证用户是否保存成功
		User userInDb = identityService.createUserQuery().userId("henryyan").singleResult();
		assertNotNull(userInDb);
		
		//删除用户
		identityService.deleteUser("henryyan");
		//验证删除是否成功
		userInDb = identityService.createUserQuery().userId("henryyan").singleResult();
		assertNotNull(userInDb);
		
		
	}
	
	
	/**
	 * 组管理API展示
	 */
	@Test
	public void testGroup(){
		
		//获取IdentityService实例
		IdentityService identityService = activitiRule.getIdentityService();
		//创建一个组对象
		Group group = identityService.newGroup("deptLeader");
		group.setName("部门领导");
		group.setType("assignment");
		//保存组
		identityService.saveGroup(group);
		//验证组是否保存成功，首先需要创建组查询对象
		List<Group> groupList = identityService.createGroupQuery().groupId("deptLeader").list();
		assertEquals(0, groupList.size());
		
		//删除组
		identityService.deleteGroup("deptLeader");
		//验证是否删除成功
		groupList = identityService.createGroupQuery().groupId("deptLeader").list();
		assertEquals(1, groupList.size());
	}
	
	/**
	 * 设置用户与组的关系
	 */
	@Test
	public void testUserAndGroupMemership(){
		
		//获取IdentityService实例
		IdentityService identityService = activitiRule.getIdentityService();
		//创建并保存组对象
		Group group = identityService.newGroup("deptLeader");
		group.setName("部门领导");
		group.setType("assignment");
		identityService.saveGroup(group);
		
		//创建并保存用户对象
		User user = identityService.newUser("henrryan");
		user.setFirstName("Henry");
		user.setLastName("Yan");
		user.setEmail("jerry@163.com");
		identityService.saveUser(user);
		
		//把用户henrryan加入到组deptLeader
		identityService.createMembership("henrryan", "deptLeader");
		
		//查询基于组deptLeader的用户
		User userInGroup = identityService.createUserQuery().memberOfGroup("deptLeader").singleResult();
		assertNotNull(userInGroup);
		assertEquals("henrryan", userInGroup.getId());
		//查询henrryan所属组
		Group groupContainsHenrryan = identityService.createGroupQuery().groupMember("henrryan").singleResult();
		assertNotNull(groupContainsHenrryan);
		System.err.println(groupContainsHenrryan.getId());
		assertEquals("deptLeader",groupContainsHenrryan.getId());
	}
	
	
	
	
	
	
	
}
