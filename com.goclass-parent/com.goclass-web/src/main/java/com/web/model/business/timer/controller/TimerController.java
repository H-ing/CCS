package com.web.model.business.timer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.goclass.pojo.TbTimer;
import com.goclass.pojo.TimerSchedul;
import com.web.model.business.timer.service.TimerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api/admin")
@Api(description = "上课时间时段表api")
public class TimerController {
	
	@Autowired
	private TimerService timerService;
	
	@PostMapping("/timer/schedul")
	@ApiOperation("添加一个上课时间时段表")
	public void add(@RequestBody @ApiParam(value = "上课时间时段表实体", required = true) TimerSchedul timer) {
		timerService.add(timer);
	}

	@GetMapping("/timer/scheduls")
	@ApiOperation("列出上课时间时段表")
	public List<TimerSchedul> loadAll() {
		return timerService.loadAll();
	}

	@GetMapping("/timer/schedul/{id}")
	@ApiOperation("根据id查询上课时间时段表")
	public TimerSchedul loadOne(@PathVariable @ApiParam(value = "上课时间时段表id", required = true) Long id) {
		return timerService.loadOne(id);
	}

	@PutMapping("/timer/schedul")
	@ApiOperation("更新上课时间时段表")
	public void update(@RequestBody @ApiParam(value = "上课时间时段表实体", required = true) TbTimer tbTimer) {
		timerService.update(tbTimer);
	}

	@DeleteMapping("/timer/schedul")
	@ApiOperation("根据id列表删除上课时间时段表")
	public void delete(@ApiParam(value = "上课时间时段id列表", required = true) Long[] ids) {
		timerService.delete(ids);
	}
}
