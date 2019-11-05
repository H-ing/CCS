package com.web.model.business.cg.tool.transform;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateTaskTransform {
	//历史     物理     地理    政治    生物   化学   语文    数学   英语   体育     信息与技术    音乐     班会
	static Short[] subjectCodeList = {1,2,4,8,16,32,64,128,256,512,1024,2048,4096};
	//历地政  历地生  历地化     历政生     历政化         历化生     物地政        物地生   物地化   物政生  物政化  物生化
 	// 13     21     37      25     41       49     14        22    38    26    42     50
	static Short[] sectionStudentNumberTransform = {1+4+8,1+4+16,1+4+32,1+8+16,1+8+32,1+16+32,
			2+4+8,2+4+16,2+4+32,2+8+16,2+8+32,2+16+32};
	static Map<String, String> subjectSelectionStringMap = new HashMap<String,String>() {{
		put("13","历地政");
		put("21","历地生");
		put("37","历地化");
		put("25","历政生");
		put("41","历政化");
		put("49","历化生");
		put("14","物地政");
		put("22","物地生");
		put("38","物地化");
		put("26","物政生");
		put("42","物政化");
		put("50","物化生");
		put("12","地政");
		put("20","地生");
		put("36","地化");
		put("24","政生");
		put("40","政化");
		put("48","化生");
		put("1", "历史");
		put("2", "物理");
		put("4", "地理");
		put("8", "政治");
		put("16", "生物");
		put("32", "化学");
		put("64", "语文");
		put("128", "数学");
		put("256", "英语");
		put("512", "体育");
		put("1024", "信息与技术");
		put("2048", "音乐");
		put("4096", "班会");
	}
	};

	
	static public Map<String, String> getSubjectSelectionStringMap() {
		return subjectSelectionStringMap;
	}

	static public Short[] getSubjectCodeList() {
		return subjectCodeList;
	}
	static public Short[] getSectionStudentNumberTransform() {
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
