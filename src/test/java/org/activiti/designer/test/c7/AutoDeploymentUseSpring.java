package org.activiti.designer.test.c7;

import org.activiti.engine.RepositoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-autodeployment.xml")
public class AutoDeploymentUseSpring {

	@Autowired
	RepositoryService repositoryService;
	
	@Test
	public void testAutoDeployment(){
		
		long count = repositoryService.createProcessDefinitionQuery().count();
		System.out.println(count);
	}
}
