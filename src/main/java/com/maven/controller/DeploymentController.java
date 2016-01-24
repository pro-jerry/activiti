package com.maven.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.maven.util.AbstractController;

/**
 * 部署流程
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/chapter5")
public class DeploymentController extends AbstractController{

	RepositoryService repositoryService = processEngine.getRepositoryService();
	
	/**
	 * 流程定义列表
	 */
	@RequestMapping("/process-list")
	public String processList(HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		List<ProcessDefinition> processDefinitionList = 
				repositoryService.createProcessDefinitionQuery().list();
		request.setAttribute("processDefinitionList", processDefinitionList);
		return "process-list";
	}
	
	/**
	 * 部署流程资源文件
	 */
	@RequestMapping(value = "/deploy")
	public String deploy(@RequestParam(value = "file", required = true) MultipartFile file){
		
		//获取上传文件的文件名
		String fileName = file.getOriginalFilename();
		
		try {
			//得到输入流(字节流)对象
			InputStream fileInputStream = file.getInputStream();
			//文件扩展名
			String extension = FilenameUtils.getExtension(fileName);
			
			// zip或者bar类型的文件用ZipInputStream方式部署
			DeploymentBuilder deployment = repositoryService.createDeployment();
			if(extension.equals("zip")|| extension.equals("bar")){
				ZipInputStream zip = new ZipInputStream(fileInputStream);
				deployment.addZipInputStream(zip);
			}else{
				
				//其他类型的文件直接部署
				deployment.addInputStream(fileName, fileInputStream);
			}
			deployment.deploy();
			
		} catch (IOException e) {
			logger.error("error on deploy process, because of file input stream");
		}
		
		
		return "redirect:process-list";
	}
	
	/**
	 * 读取流程资源
	 * @throws IOException 
	 */
	@RequestMapping(value = "/read-resource")
	public void readResource(@RequestParam("pdid") String processDefinitionId, 
			@RequestParam("resourceName") String resourceName, 
			HttpServletResponse response) throws IOException{
		
		 ProcessDefinitionQuery pdq = repositoryService.createProcessDefinitionQuery();
		 ProcessDefinition pd = pdq.processDefinitionId(processDefinitionId).singleResult();
		 
		 //通过接口读取资源流
		 InputStream resourceAsStream = 
				 repositoryService.getResourceAsStream(pd.getDeploymentId(), resourceName);
		 //输出资源内容到相应对象
		 byte[] b = new byte[1024];
		 int len = -1;
		 while((len = resourceAsStream.read(b, 0, 1024))!=-1){
			 response.getOutputStream().write(b, 0, len);
		 }
	}
	
	/**
	 * 删除部署流程，级联删除流程定义
	 */
	@RequestMapping(value = "/delete-deployment")
	public String deleteProcessDefinition(@RequestParam("deploymentId") String deploymentId){
		
		repositoryService.deleteDeployment(deploymentId,true);
		return "redirect:process-list";
	}
	
	
	
	
	
	
	
	
}
