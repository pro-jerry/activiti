package com.maven.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.maven.util.AbstractController;
import com.maven.util.UserUtil;

/**
 * 用户相关控制器
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/user")
public class UseController extends AbstractController{

	private static Logger logger = LoggerFactory.getLogger(UseController.class);
	
	// Activiti Identify Service
    private IdentityService identityService = processEngine.getIdentityService();
    
    @RequestMapping("/logon")
    public String logon(@RequestParam("username") String userName, 
    		@RequestParam("password") String password, HttpSession session){
    	
    	logger.debug("logon request: {username={}, password={}}", userName, password);
    	boolean checkPassword = identityService.checkPassword(userName, password);
    	if(checkPassword){
    		 // 查看用户是否存在
            User user = identityService.createUserQuery().userId(userName).singleResult();
            UserUtil.saveUserToSession(session, user);
            /**
             * 读取角色
             */
            List<Group> groupList = identityService.createGroupQuery().groupMember(user.getId()).list();
            for(Group group:groupList){
            	
            	System.out.println(group.getName()+"--"+group.getType()+"--"+group.getId()+"--"+group.getType());
            }
            session.setAttribute("groups", groupList);
            String[] groupNames = new String[groupList.size()];
            for(int i=0;i<groupNames.length;i++){
            	groupNames[i] = groupList.get(i).getName();
            	System.out.println(groupNames[i]);
            }
            System.out.println(ArrayUtils.toString(groupNames)+"---");
            session.setAttribute("groupNames", ArrayUtils.toString(groupNames));
            
            return "index";
    	}else{
    		
    		return "redirect:/login.jsp?error=true";
    	}
    }
}
