package com.maven.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.activiti.engine.identity.User;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
	
	@RequestMapping("/task/list")
	public String todoTasks(HttpServletRequest request,HttpSession session) throws Exception{
		
		User user = UserUtil.getUserFromSession(session);
		
		List<Task> tasks = taskService.createTaskQuery().taskCandidateOrAssigned(user.getId()).list();
		
		request.setAttribute("tasks", tasks);
		
		return "task-list";
	}
}
