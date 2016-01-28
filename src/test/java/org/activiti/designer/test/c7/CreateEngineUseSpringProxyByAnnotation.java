package org.activiti.designer.test.c7;

import static org.junit.Assert.*;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-test.xml")
public class CreateEngineUseSpringProxyByAnnotation {

	//注入runtimeService
	@Autowired
	RuntimeService runtimeService;
	
	//注入factoryBean
	@Autowired
	ProcessEngineFactoryBean factoryBean;
	
	@Test
	public void testService() throws Exception{
		
		assertNotNull(runtimeService);
		ProcessEngine processEngine = factoryBean.getObject();
		assertNotNull(processEngine.getRuntimeService());
	}
}
