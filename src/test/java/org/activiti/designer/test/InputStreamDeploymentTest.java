package org.activiti.designer.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.junit.Test;

public class InputStreamDeploymentTest extends AbstractTest{

	
	/**
	 * 从具体的文件中读取输入流配置
	 * @throws FileNotFoundException 
	 */
	@Test
	public void testInputStreamFromAbsoluteFilePath() throws FileNotFoundException{
		
		String filepath="D://Java//eclipse-jee-luna-SR2-win32//eclipse//project//maven//src//test//resources//diagrams//userAndGroupInUserTask.bpmn";
		
		//读取classpath的资源作为输入流
		FileInputStream fileInputStream = new FileInputStream(filepath);
		repositoryService.createDeployment().addInputStream("userAndGroupInUserTask", fileInputStream);
	
		//验证是否部署成功
		ProcessDefinitionQuery pdq = repositoryService.createProcessDefinitionQuery();
		long count = pdq.processDefinitionKey("userAndGroupInUserTask").count();
		System.out.println(count);
	}
	
}
