package com.web.model.business.cg.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.model.business.cg.bean.create.CreateTaskBean;
import com.web.model.business.cg.bean.tserver.ServerMessageBean;
import com.web.model.business.cg.tool.transform.CreateTaskTransform;
import com.web.model.rpc.client.container.ResultOfClassStrategyCreateTask;
import com.web.model.rpc.client.container.ResultOfClassStrategyGetTasksStatus;
import com.web.model.rpc.client.container.ResultOfClassStrategyRunTask;
//import com.web.model.rpc.client.container.ResultOfGetClassStrategyRule;
import com.web.model.rpc.server.call.CallingTool;
import com.web.model.rpc.server.call.PropertyCopyTool;
import com.web.model.rpc.server.source.ClassStrategyRule;
//import com.web.model.rpc.server.source.ResultOfClassStrategyCreateTask;
import com.web.model.rpc.server.source.ResultOfGetClassStrategyRule;
import com.web.model.rpc.server.source.ResultOfClassStrategyDelTask;
import com.web.model.rpc.server.source.ResultOfClassStrategyGetTaskResult;
//import com.web.model.rpc.server.source.ResultOfClassStrategyGetTaskResult;
//import com.web.model.rpc.server.source.ResultOfClassStrategyGetTaskResult;
//import com.web.model.rpc.server.source.ResultOfClassStrategyGetTasksStatus;
//import com.web.model.rpc.server.source.ResultOfClassStrategyRunTask;
//import com.web.model.rpc.server.source.ResultOfGetClassStrategyRule;

@RestController

public class CgController {
	CallingTool globalCallingTool = new CallingTool();
	
	//创建分班任务
	@RequestMapping(value = "/api/admin/class/grouping/createtask" , method = RequestMethod.POST)
	public ResultOfClassStrategyCreateTask createTask(
			@RequestBody CreateTaskBean createTaskData) {
		CreateTaskTransform tools = new CreateTaskTransform();
		ClassStrategyRule classStrategyRule = new ClassStrategyRule();
		//封装参数
		classStrategyRule.setSubjectTeacherNumber(tools.subjectTeacherNumberTransform(
				createTaskData.getSubjectTeacherNumber()));
		classStrategyRule.setSectionStudentNumber(tools.sectionStudentNumberTransform(
				createTaskData.getSectionStudentNumber()));
		classStrategyRule.setMaxAndMinClassStudentNumber(createTaskData.getMaxAndMinClassStudentNumber());
		classStrategyRule.setRuning_time(createTaskData.getRuningTime());
		//远程调用
		ResultOfClassStrategyCreateTask returnMessage = new ResultOfClassStrategyCreateTask();
		try {
			PropertyCopyTool.Copy(globalCallingTool.createTaskForClassStrategy(classStrategyRule), returnMessage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("复制失败");
			e.printStackTrace();
		}
		return returnMessage;
	}
	
	
	//运行分班任务
	@RequestMapping(value = "/api/admin/class/grouping/runtask" , method = RequestMethod.POST)
	public ResultOfClassStrategyRunTask runTask(
			@RequestParam(value = "taskId") int taskId,
			@RequestParam(value = "stage") int stage){
    	ResultOfClassStrategyRunTask returnMessage = new ResultOfClassStrategyRunTask();
    	try {
			PropertyCopyTool.Copy(globalCallingTool.runTaskForClassStrategy(taskId, stage), returnMessage);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("复制失败");
			e.printStackTrace();
		}
		return returnMessage;
	}
	
	
	//获取现有任务及其运行情况
	@RequestMapping(value = "/api/admin/class/grouping/taskstatus" , method = RequestMethod.GET)
	public ResultOfClassStrategyGetTasksStatus getTasksStatusForClassStrategy() {
    	ResultOfClassStrategyGetTasksStatus returnMessage = new ResultOfClassStrategyGetTasksStatus();
    	try {
			PropertyCopyTool.Copy(globalCallingTool.getTasksStatusForClassStrategy(), returnMessage);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("复制失败");
			e.printStackTrace();
		}
		return returnMessage;
	}
	
	
	//获取任务结果
	@RequestMapping(value = "/api/admin/class/grouping/result" , method = RequestMethod.GET)
	public ResultOfClassStrategyGetTaskResult getTaskResultForClassStrategy(
			@RequestParam(value = "taskId") int taskId,
			@RequestParam(value = "stage") int stage
			) {
//    	ResultOfClassStrategyGetTaskResult returnMessage = new ResultOfClassStrategyGetTaskResult();
		return globalCallingTool.getTaskResultForClassStrategy(taskId, stage);
	}
	
	
	//获取分班任务的现存规则
	@RequestMapping(value = "/api/admin/class/grouping/rule" , method = RequestMethod.GET)
	public ResultOfGetClassStrategyRule getClassStrategyRule(
			@RequestParam(value = "taskId") int taskId) {
//    	ResultOfGetClassStrategyRule returnMessage = new ResultOfGetClassStrategyRule();
//    	try {
////    		ResultOfGetClassStrategyRule resultOfGetClassStrategyRule = globalCallingTool.getClassStrategyRule(taskId);
//			PropertyCopyTool.Copy(globalCallingTool.getClassStrategyRule(taskId), returnMessage);
//			PropertyCopyTool.Copy(globalCallingTool.getClassStrategyRule(taskId).getRule(), 
//					returnMessage.getRule());
//		} catch (Exception e) {
//			// TODO: handle exception
//			System.out.println("复制失败");
//			e.printStackTrace();
//		}
		return globalCallingTool.getClassStrategyRule(taskId);
	}
	
	
	//删除分班任务
	@RequestMapping(value = "/api/admin/class/grouping/delete" , method = RequestMethod.DELETE)
	public ResultOfClassStrategyDelTask delTaskForClassStrategy(
			@RequestParam(value = "taskId") int taskId
			) {
		return globalCallingTool.delTaskForClassStrategy(taskId);
	}
	
	//测试 服务是否正常
	@RequestMapping(value = "/test/ping", method = RequestMethod.GET)
	public ServerMessageBean serverTest() {
		ServerMessageBean returnMessage = new ServerMessageBean();
		//测试rpc是否能连接
		
		if(globalCallingTool.ping()){
			returnMessage.setRpcService("rpc server is OK");
		}else {
			returnMessage.setRpcService("fail to connect rpc server");
		}
		
		//测试数据源是否正常   待添加
		returnMessage.setDataSource("data source is OK");
		return returnMessage;
	}
}
