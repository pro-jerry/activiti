package org.activiti.designer.test;

import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.junit.Test;

public class ClasspathDeploymentTest extends AbstractTest{

	
	@Test
	public void testClasspathDeployment(){
		
		 //1.定义classpath
		 String bpmnClasspath = "diagrams/userAndGroupInUserTask.bpmn";
	     String pngClasspath = "diagrams/userAndGroupInUserTask.png";
		
	     //2.创建部署构建器
	     DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
	     
	     //3。添加资源
	     deploymentBuilder.addClasspathResource(bpmnClasspath);
	     deploymentBuilder.addClasspathResource(pngClasspath);
	     
	     //4.执行部署
	     deploymentBuilder.deploy();
	     
	     //5.验证流程定义是否部署成功
	     ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
	     long count = processDefinitionQuery.processDefinitionKey("userAndGroupInUserTask").count();
	     System.out.println(count);
	     
	     //6.读取文件图片
	     ProcessDefinition processDefinition = processDefinitionQuery.singleResult();
	     String diagramResourceName = processDefinition.getDiagramResourceName();
	     System.out.println(processDefinition.getDescription());
	     System.out.println(diagramResourceName);
	}
}
