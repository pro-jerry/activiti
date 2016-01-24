package com.maven.util;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;

/**
 * Activiti工具类
 * @author Administrator
 *
 */
public class ActivitiUtils {

	private static ProcessEngine processEngine;
	
	/**
	 * 单例模式获取引擎对象
	 * @return
	 */
	public static ProcessEngine getProcessEngine(){
		
		if(processEngine == null){
			
			processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault().buildProcessEngine();
			
		}
		return processEngine;
	}
}
