package org.activiti.designer.test;

import java.io.IOException;
import java.io.InputStream;

import org.activiti.engine.repository.ProcessDefinition;
import org.junit.Test;

public class ReadXmlResourceTest extends AbstractTest {

	
	@Test
	public void testClasspathDeployment() throws IOException{
		
		//定义classpath
		String bpmnClasspath="diagrams/userAndGroupInUserTask.bpmn";
		//添加资源
		repositoryService.createDeployment().addClasspathResource(bpmnClasspath).deploy();
		//获取流程定义对象
		ProcessDefinition pd = repositoryService.createProcessDefinitionQuery().singleResult();
		String resourceName = pd.getResourceName();
		System.out.println("资源名称:"+resourceName);
		
		//读取资源字节流
		InputStream resourceAsStream = repositoryService.getResourceAsStream(pd.getDeploymentId(),resourceName);
		//输出流的内容
		StringBuffer out = new StringBuffer();
		byte[] b = new byte[4096];
		for(int n;(n=resourceAsStream.read(b))!=-1;){
			out.append(new String(b,0,n));
		}
		System.out.println(out);
	}
}
