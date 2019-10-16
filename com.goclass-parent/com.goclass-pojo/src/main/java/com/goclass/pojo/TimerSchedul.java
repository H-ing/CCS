package com.goclass.pojo;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "上课时间表实体")
public class TimerSchedul {
	@ApiModelProperty(notes = "时间表id，添加新表时不需要", example = "1", required = false, position = 0)
	private Long id;
	@ApiModelProperty(notes = "时间表名字", example = "初定上课时间表", required = true, position = 1)
	private String name;
	@ApiModelProperty(notes = "时间表单项数目", example = "1", required = true, position = 2)
	private Integer itemNum;
	@ApiModelProperty(notes = "时间表单项集合", required = true, position = 3)
	private List<TimerSchedulItem> items;
	
	public TimerSchedul() {
	}
	
	
	public TimerSchedul(Long id, String name, Integer itemNum, List<TimerSchedulItem> items) {
		this.id = id;
		this.name = name;
		this.itemNum = itemNum;
		this.items = items;
	}



	@Override
	public String toString() {
		return "TimerSchedul [name=" + name + ", itemNum=" + itemNum + ", items=" + items + "]";
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<TimerSchedulItem> getItems() {
		return items;
	}
	public void setItems(List<TimerSchedulItem> items) {
		this.items = items;
	}

	public Integer getItemNum() {
		return itemNum;
	}

	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}
}