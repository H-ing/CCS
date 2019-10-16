package com.web.model.business.timer.service;

import java.util.List;

import com.goclass.pojo.TbTimer;
import com.goclass.pojo.TimerSchedul;
import com.goclass.pojo.TimerSchedulItem;

public interface TimerService {
	public void add(TimerSchedul timerSchedul);
	
	public List<TimerSchedul> loadAll();
	
	public TimerSchedul loadOne(Long id);
	
	public void update(TbTimer tbTimer);
	
	public void delete(Long[] ids);
}
