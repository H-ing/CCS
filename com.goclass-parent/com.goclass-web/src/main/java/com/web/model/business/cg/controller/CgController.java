package com.web.model.business.cg.controller;

import static org.hamcrest.CoreMatchers.nullValue;

import java.util.List;
import java.util.Map;

import javax.tools.Tool;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.web.model.business.cg.bean.create.CreateTaskBean;
import com.web.model.business.cg.tool.transform.CreateTaskTransform;
import com.web.model.rpc.server.call.CallingTool;
import com.web.model.rpc.server.source.ClassStrategyRule;
import com.web.model.rpc.server.source.ResultOfClassStrategyCreateTask;
import com.web.model.rpc.server.source.ResultOfClassStrategyGetTaskResult;
import com.web.model.rpc.server.source.ResultOfClassStrategyGetTasksStatus;
import com.web.model.rpc.server.source.ResultOfClassStrategyRunTask;
import com.web.model.rpc.server.source.ResultOfGetClassStrategyRule;

@RestController

public class CgController {
	CallingTool globalCallingTool = new CallingTool();
	//创建分班任务
	@RequestMapping("/class/grouping/createtask")
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
		globalCallingTool.createTaskForClassStrategy(classStrategyRule);
		return globalCallingTool.createTaskForClassStrategy(classStrategyRule);
	}
	//运行分班任务
	@RequestMapping("/class/grouping/runtask")
	public ResultOfClassStrategyRunTask runTask(
			@RequestParam(value = "taskId") int taskId,
			@RequestParam(value = "stage") int stage){
		return globalCallingTool.runTaskForClassStrategy(taskId,stage);
	}
	//获取现有任务及其运行情况
	@RequestMapping("/class/grouping/taskstatus")
	public ResultOfClassStrategyGetTasksStatus getTasksStatusForClassStrategy() {
		return globalCallingTool.getTasksStatusForClassStrategy();
	}
	//获取任务结果
	@RequestMapping("/class/grouping/result")
	public ResultOfClassStrategyGetTaskResult getTaskResultForClassStrategy(
			@RequestParam(value = "taskId") int taskId,
			@RequestParam(value = "stage") int stage
			) {
		return globalCallingTool.getTaskResultForClassStrategy(taskId, stage);
	}
	//获取分班任务的现存规则
	@RequestMapping("/class/grouping/rule")
	public ResultOfGetClassStrategyRule getClassStrategyRule(
			@RequestParam(value = "taskId") int taskId) {
		return globalCallingTool.getClassStrategyRule(taskId);
	}
	
}
