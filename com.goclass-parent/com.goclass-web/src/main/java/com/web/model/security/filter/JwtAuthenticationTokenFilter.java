package com.web.model.security.filter;


import java.io.IOException;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.alibaba.fastjson.JSON;
import com.goclass.result.CommonResult;
import com.web.common.config.redis.RedisConstant;
import com.web.common.utils.JwtHelper;
import com.web.common.utils.SpringUtils;
import com.web.model.security.authentication.SecurityUserInfo;

import io.jsonwebtoken.Claims;

/**
 * 自定义执行登录拦截处理filter前的filter
 * 
 * @author Administrator
 *
 */

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter{
	
 
    @Autowired
    private RedisTemplate redisTemplate;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		//获取前端传过来的token
		String token = request.getHeader("token");
//		logger.info("token: " + token);
		//判断token是否存在
		if (token != null) {
			//验证token是否可解析
			Claims claims = JwtHelper.verifyJwt(token);
//			logger.info(claims);
			if (claims != null) {
				//检查缓存中是否存在登录信息
				this.init();
				SecurityUserInfo userInfo = (SecurityUserInfo) redisTemplate.boundHashOps(RedisConstant.PRE_ + RedisConstant.TOKEN).get(token);
				
				if (userInfo != null && SecurityContextHolder.getContext().getAuthentication() == null) {
					Collection<? extends GrantedAuthority> authorities = userInfo.getAuthorities();
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userInfo, null, authorities);
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				}
			}
		}
		filterChain.doFilter(request, response);
		
	}
	
	//手动注入RedisTemplate，filter不属于spring的范涛，无法使用注解自动注入
	public void init() {
		if (redisTemplate == null) {
			redisTemplate = (RedisTemplate) SpringUtils.getBean("redisTemplate");
		}
	}
}
