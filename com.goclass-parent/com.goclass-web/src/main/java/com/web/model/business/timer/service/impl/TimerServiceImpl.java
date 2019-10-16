package com.web.model.business.timer.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.goclass.mapper.TbTimerMapper;
import com.goclass.pojo.TbTimer;
import com.goclass.pojo.TimerSchedul;
import com.goclass.pojo.TimerSchedulItem;
import com.web.model.business.timer.service.TimerService;

@Service
public class TimerServiceImpl implements TimerService {

	@Autowired
	private TbTimerMapper tbTimerMapper;

	@Override
	public void add(TimerSchedul timerSchedul) {
		TbTimer timer = new TbTimer();
		timer.setTimerNum(timerSchedul.getItemNum());
		timer.setName(timerSchedul.getName());
		timer.setTimerSchedul(JSON.toJSONString(timerSchedul.getItems()));
		tbTimerMapper.insert(timer);
	}

	@Override
	public List<TimerSchedul> loadAll() {
		List<TbTimer> list = tbTimerMapper.selectByExample(null);
		List<TimerSchedul> result = new ArrayList<TimerSchedul>();
		for (TbTimer tbTimer : list) {
			TimerSchedul timerSchedul  = new TimerSchedul();
			timerSchedul.setId(tbTimer.getId());
			timerSchedul.setItemNum(tbTimer.getTimerNum());
			timerSchedul.setName(tbTimer.getName());
			List<TimerSchedulItem> scheduls = JSONArray.parseArray(tbTimer.getTimerSchedul(), TimerSchedulItem.class);
			timerSchedul.setItems(scheduls);
			
			result.add(timerSchedul);
		}
		return result;
	}

	@Override
	public TimerSchedul loadOne(Long id) {
		TbTimer tbTimer = tbTimerMapper.selectByPrimaryKey(id);
		TimerSchedul timerSchedul  = new TimerSchedul();
		timerSchedul.setId(tbTimer.getId());
		timerSchedul.setItemNum(tbTimer.getTimerNum());
		timerSchedul.setName(tbTimer.getName());
		List<TimerSchedulItem> scheduls = JSONArray.parseArray(tbTimer.getTimerSchedul(), TimerSchedulItem.class);
		timerSchedul.setItems(scheduls);
		return timerSchedul;
	}

	@Override
	public void update(TbTimer tbTimer) {
		tbTimerMapper.updateByPrimaryKey(tbTimer);
	}

	@Override
	public void delete(Long[] ids) {
		for (Long id : ids) {
			tbTimerMapper.deleteByPrimaryKey(id);
		}
	}
	

}
