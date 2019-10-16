package com.web.model.rpc.client.container;

import java.util.List;
import java.util.Map;

public class ClassStrategyRule {
	Map<Short, Integer> subjectTeacherNumber;
	Map<Short, Integer> sectionStudentNumber;
	List<Integer> maxAndMinClassStudentNumber;
	int runing_time = 50;
	public Map<Short, Integer> getSubjectTeacherNumber() {
		return subjectTeacherNumber;
	}
	public void setSubjectTeacherNumber(Map<Short, Integer> subjectTeacherNumber) {
		this.subjectTeacherNumber = subjectTeacherNumber;
	}
	public Map<Short, Integer> getSectionStudentNumber() {
		return sectionStudentNumber;
	}
	public void setSectionStudentNumber(Map<Short, Integer> sectionStudentNumber) {
		this.sectionStudentNumber = sectionStudentNumber;
	}
	public List<Integer> getMaxAndMinClassStudentNumber() {
		return maxAndMinClassStudentNumber;
	}
	public void setMaxAndMinClassStudentNumber(List<Integer> maxAndMinClassStudentNumber) {
		this.maxAndMinClassStudentNumber = maxAndMinClassStudentNumber;
	}
	public int getRuning_time() {
		return runing_time;
	}
	public void setRuning_time(int runing_time) {
		this.runing_time = runing_time;
	}
	
}
