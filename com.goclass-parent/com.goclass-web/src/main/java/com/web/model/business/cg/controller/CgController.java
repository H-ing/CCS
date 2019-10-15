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

@RestController

public class CgController {
	CallingTool globalCallingTool = new CallingTool();
	@RequestMapping("/class/grouping/createtask")
	public ResultOfClassStrategyCreateTask createTask(
			@RequestBody CreateTaskBean createTaskData) {
		CreateTaskTransform tools = new CreateTaskTransform();
		ClassStrategyRule classStrategyRule = new ClassStrategyRule();
		classStrategyRule.setSubjectTeacherNumber(tools.subjectTeacherNumberTransform(
				createTaskData.getSubjectTeacherNumber()));
		classStrategyRule.setSectionStudentNumber(tools.sectionStudentNumberTransform(
				createTaskData.getSectionStudentNumber()));
		classStrategyRule.setMaxAndMinClassStudentNumber(createTaskData.getMaxAndMinClassStudentNumber());
		classStrategyRule.setRuning_time(createTaskData.getRuningTime());
		globalCallingTool.createTaskForClassStrategy(classStrategyRule);
		return globalCallingTool.createTaskForClassStrategy(classStrategyRule);
	}
}
