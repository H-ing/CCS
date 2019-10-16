package com.web.model.rpc.client.container;

public class ResultOfGetClassStrategyRule {
	int statusCode;
	String message;
	ClassStrategyRule rule;
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
	public ClassStrategyRule getRule() {
		return rule;
	}
	public void setRule(ClassStrategyRule rule) {
		this.rule = rule;
	}

}
