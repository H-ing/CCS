package com.web.model.business.user.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.goclass.mapper.TbRoleMapper;
import com.goclass.mapper.TbUserMapper;
import com.goclass.pojo.TbRole;
import com.goclass.pojo.TbRoleExample;
import com.goclass.pojo.TbUser;
import com.goclass.pojo.TbUserExample;
import com.goclass.pojo.TbUserExample.Criteria;
import com.goclass.result.CommonResult;
import com.web.common.config.redis.RedisConstant;
import com.web.common.utils.JwtHelper;
import com.web.common.utils.Md5Util;

import com.web.model.business.user.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private TbUserMapper tbUserMapper;
	
	@Autowired
	private TbRoleMapper tbRoleMapper;
	
	@Autowired
	private RedisTemplate redisTemplate;
	
	@Override
	public TbUser selectUserByAccounts(String accounts) {
		
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		criteria.andAccountsEqualTo(accounts);
		List<TbUser> list = tbUserMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
//
//	@Override
//	public CommonResult login(String accounts, String password) {
//		TbUser user = selectUserByAccounts(accounts);
//		if (user != null) {
//			if (Md5Util.getSaltverifyMD5(password, user.getPassword(), user.getSalt())) {
//				SecurityUserInfo userInfo = new SecurityUserInfo(user.getUserId(), user.getUsername(), user.getAccounts(), user.getPassword(), user.getPassword(), user.getRoleId(), user.getStatus(), true, true, true, true);
//				TbRoleExample example = new TbRoleExample();
//		        com.goclass.pojo.TbRoleExample.Criteria criteria = example.createCriteria();
//		        criteria.andRoleIdLessThanOrEqualTo(userInfo.getRoleId());
//				List<TbRole> roles = tbRoleMapper.selectByExample(example);
//				
//				List<GrantedAuthority> authorities = new ArrayList<>();	//权限集合
//				if (roles != null && roles.size() > 0) {
//					for (TbRole tbRole : roles) {
//						String permission = tbRole.getRole();	//权限
//						authorities.add(new SimpleGrantedAuthority(permission));
//					}
//				}
//				//权限赋予
//				userInfo.setAuthorities(authorities);
//				
//				Map<String, Object> mapPy = new HashMap<>();
//				/**
//			     * 根据authorities、userId和userName生成token
//			     */
//				mapPy.put("userId", user.getUserId());
//				mapPy.put("username", user.getUsername());
//				mapPy.put("authorities", authorities);
//				//构造token
//				String accessToken = JwtHelper.generateToken(mapPy);
//				
//				Map<Object, Object> mapResult = new HashMap<>();
//				mapResult.put("userId", user.getUserId());
//				mapResult.put("username", user.getUsername());
//				mapResult.put("accessToken", accessToken);
//				mapResult.put("loginTime", new Date());
//				
//				//将token存进redis
//				redisTemplate.boundHashOps("token").put(accessToken, userInfo);
//				return new CommonResult("200", "success", JSON.toJSONString(mapResult));
//			}
//			return new CommonResult("401", "error,check accounts or password");
//		}
//		return new CommonResult("401", "error,check accounts or password");
//	}

	@Override
	public TbUser self(Long userId) {
		return tbUserMapper.selectByPrimaryKey(userId);
	}

}
