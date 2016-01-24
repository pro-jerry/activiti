package org.activiti.designer.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.history.HistoricDetail;
import org.activiti.engine.history.HistoricFormProperty;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricVariableUpdate;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.Deployment;
import org.junit.Test;

public class LeaveDynamicFormTest extends AbstractTest{

	@Test
	@Deployment(resources = "diagrams/leave.bpmn")
	public void allApproved(){
		
		// 验证是否部署成功
        long count = repositoryService.createProcessDefinitionQuery().count();
        assertEquals(1, count);

        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey("leave").singleResult();

        // 设置当前用户
        String currentUserId = "henryyan";
        identityService.setAuthenticatedUserId(currentUserId);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, String> variables = new HashMap<String, String>();
        Calendar ca = Calendar.getInstance();
        String startDate = sdf.format(ca.getTime());
        ca.add(Calendar.DAY_OF_MONTH, 2); // 当前日期加2天
        String endDate = sdf.format(ca.getTime());

        // 启动流程
        variables.put("startDate", startDate);
        variables.put("endDate", endDate);
        variables.put("reason", "公休");
        ProcessInstance processInstance = formService.submitStartFormData(processDefinition.getId(), variables);
        assertNotNull(processInstance);

        // 部门领导审批通过
        Task deptLeaderTask = taskService.createTaskQuery().taskCandidateGroup("deptLeader").singleResult();
        variables = new HashMap<String, String>();
        variables.put("deptLeaderApproved", "true");
        formService.submitTaskFormData(deptLeaderTask.getId(), variables);

        // 人事审批通过
        Task hrTask = taskService.createTaskQuery().taskCandidateGroup("hr").singleResult();
        System.out.println(hrTask);
        variables = new HashMap<String, String>();
        variables.put("hrApproved", "true");
        System.out.println(variables);
        formService.submitTaskFormData(hrTask.getId(), variables);

        // 销假（根据申请人的用户ID读取）
        Task reportBackTask = taskService.createTaskQuery().taskAssignee(currentUserId).singleResult();
        variables = new HashMap<String, String>();
        variables.put("reportBackDate", sdf.format(ca.getTime()));
        formService.submitTaskFormData(reportBackTask.getId(), variables);

        // 验证流程是否已经结束
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().finished().singleResult();
        assertNotNull(historicProcessInstance);

        // 读取历史变量
        Map<String, Object> historyVariables = packageVariables(processInstance);

        // 验证执行结果
        assertEquals("ok", historyVariables.get("result"));
	}

	//读取历史变量并封装到Map中
	private Map<String, Object> packageVariables(ProcessInstance processInstance) {

		Map<String,Object> historyVariables = new HashMap<String, Object>();
		List<HistoricDetail> list = 
				historyService.createHistoricDetailQuery()
				.processInstanceId(processInstance.getId()).list();
		
		for(HistoricDetail historicDetail:list){
			
			if(historicDetail instanceof HistoricFormProperty){
				
				//表单中的字段
				HistoricFormProperty field = (HistoricFormProperty) historicDetail;
				historyVariables.put(field.getPropertyId(), field.getPropertyValue());
				System.out.println("from field: TaskId= "+field.getTaskId()+","+field.getPropertyId()+" = "+field.getPropertyValue());;
				
				//普通变量
			}else if(historicDetail instanceof HistoricVariableUpdate){
				HistoricVariableUpdate  variable = (HistoricVariableUpdate) historicDetail;
				historyVariables.put(variable.getVariableName(), variable.getValue());
				System.out.println(" variable： "+variable.getVariableName()+" = "+variable.getValue());
			}
		}
		
		
		
		return historyVariables;
	}
}
