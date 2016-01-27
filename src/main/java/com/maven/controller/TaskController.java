package com.maven.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.identity.User;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.maven.util.AbstractController;
import com.maven.util.UserUtil;

/**
 * 任务控制器
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/chapter6")
public class TaskController extends AbstractController{

	private static String TASK_LIST = "redirect:/chapter6/task/list";
	
	/**
     * 读取启动流程的表单字段
     */
	@RequestMapping("/task/list")
	public String todoTasks(HttpServletRequest request,HttpSession session) throws Exception{
		
		User user = UserUtil.getUserFromSession(session);
		
		List<Task> tasks = taskService.createTaskQuery().taskCandidateOrAssigned(user.getId()).list();
		
		request.setAttribute("tasks", tasks);
		
		return "task-list";
	}
	
	/**
	 * 签收任务
	 */
	@RequestMapping(value = "task/claim/{id}")
	public String claim(@PathVariable("id") String taskId, HttpSession session, RedirectAttributes redirectAttributes) {
        String userId = UserUtil.getUserFromSession(session).getId();
        taskService.claim(taskId, userId);
        redirectAttributes.addFlashAttribute("message", "任务已签收");
        return TASK_LIST;
    }
	
	/**
     * 读取用户任务的表单字段
     */
    @RequestMapping("task/getform/{taskId}")
    public String readTaskForm(@PathVariable("taskId") String taskId,HttpServletRequest request) throws Exception{
    	
    	TaskFormData taskFormData = formService.getTaskFormData(taskId);
    	 if (taskFormData.getFormKey() != null){
    		 Object renderedTaskForm = formService.getRenderedTaskForm(taskId);
             Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
             request.setAttribute("task", task);
    		 request.setAttribute("taskFormData", renderedTaskForm);
    		 request.setAttribute("hasFormKey", true);
    	 }else{
    		 
    		 request.setAttribute("taskFormData", taskFormData);
    	 }
    	return "task-form";
    }
    
    /**
     * 读取启动流程的表单字段
     */
    @RequestMapping(value = "task/complete/{taskId}")
    public String completeTask(@PathVariable("taskId") String taskId, HttpServletRequest request) throws Exception {
    	
    	TaskFormData taskFormData = formService.getTaskFormData(taskId);
    	String formKey = taskFormData.getFormKey();
    	// 从请求中获取表单字段的值
        List<FormProperty> formProperties = taskFormData.getFormProperties();
        Map<String, String> formValues = new HashMap<String, String>();
        
        if (StringUtils.isNotBlank(formKey)) { // formkey表单
            Map<String, String[]> parameterMap = request.getParameterMap();
            Set<Entry<String, String[]>> entrySet = parameterMap.entrySet();
            for (Entry<String, String[]> entry : entrySet) {
                String key = entry.getKey();
                formValues.put(key, entry.getValue()[0]);
            }
        } else { // 动态表单
            for (FormProperty formProperty : formProperties) {
                if (formProperty.isWritable()) {
                    String value = request.getParameter(formProperty.getId());
                    formValues.put(formProperty.getId(), value);
                }
            }
        }
    	
        formService.submitTaskFormData(taskId, formValues);
        return TASK_LIST;
    }
	
	
	
}
