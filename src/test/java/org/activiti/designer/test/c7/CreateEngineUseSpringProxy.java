package org.activiti.designer.test.c7;

import static org.junit.Assert.*;

import org.activiti.engine.RuntimeService;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CreateEngineUseSpringProxy {

	@Test
	public void createEngineUseSpring(){
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-test.xml");
		ProcessEngineFactoryBean factoryBean = context.getBean(ProcessEngineFactoryBean.class);
		//验证获取的工厂对象是否为空
		assertNotNull(factoryBean);
		RuntimeService bean = context.getBean(RuntimeService.class);
		assertNotNull(bean);
	}
}
