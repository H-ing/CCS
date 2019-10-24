package com.web.model.business.cg.tool.transform;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateTaskTransform {
	//物理 历史 化学 生物 地理 政治
	Short[] subjectCodeList = {1,2,4,8,16,32};
	//物化生 物化地 物化政 物生地 物生政 物地政 历化生 历化地 历化政 历生地 历生政 历地政
	Short[] sectionStudentNumberTransform = {1+4+8,1+4+16,1+4+32,1+8+16,1+8+32,1+16+32,
			2+4+8,2+4+16,2+4+32,2+8+16,2+8+32,2+16+32};
	
	public Short[] getSubjectCodeList() {
		return subjectCodeList;
	}
	public Short[] getSectionStudentNumberTransform() {
		return sectionStudentNumberTransform;
	}
	//对科目进行编码
	public Map<Short, Integer> subjectTeacherNumberTransform(int[] teacherNumberList) {
		int i ;
		Map<Short, Integer> transformDataMap = new HashMap();
		for(i=0;i<teacherNumberList.length;i++) {
			transformDataMap.put(subjectCodeList[i], teacherNumberList[i]);
		}
		return transformDataMap;
	}
	public Map<Short, Integer> sectionStudentNumberTransform(int[] selectionList){
		int i;
		Map<Short, Integer> transformDataMap = new HashMap();
		for(i=0;i<selectionList.length;i++) {
			transformDataMap.put(sectionStudentNumberTransform[i], selectionList[i]);
		}
		return transformDataMap;
	}
}
