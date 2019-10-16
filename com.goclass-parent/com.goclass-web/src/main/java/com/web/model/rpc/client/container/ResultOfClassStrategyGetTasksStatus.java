package com.web.model.rpc.client.container;

import java.util.Map;

public class ResultOfClassStrategyGetTasksStatus {
	int statusCode;
	Map<Integer, Short> tasksStatusMap;
	Map<Integer, Short> tasksStageMap;
	String message;
	
	
	public Map<Integer, Short> getTasksStatusMap() {
		return tasksStatusMap;
	}
	public void setTasksStatusMap(Map<Integer, Short> tasksStatusMap) {
		this.tasksStatusMap = tasksStatusMap;
	}
	public Map<Integer, Short> getTasksStageMap() {
		return tasksStageMap;
	}
	public void setTasksStageMap(Map<Integer, Short> tasksStageMap) {
		this.tasksStageMap = tasksStageMap;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
