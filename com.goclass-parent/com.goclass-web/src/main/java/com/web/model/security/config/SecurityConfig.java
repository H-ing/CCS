package com.web.model.security.config;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.alibaba.fastjson.JSON;
import com.goclass.result.CommonResult;
import com.goclass.result.LoginResult;
import com.web.common.config.redis.RedisConstant;
import com.web.common.utils.JwtHelper;
import com.web.model.security.authentication.CustomAuthenticationFilter;
import com.web.model.security.authentication.SecurityUserInfo;
import com.web.model.security.filter.JwtAuthenticationTokenFilter;

@EnableWebSecurity // 激活WebSecurityConfiguration配置类
@EnableGlobalMethodSecurity(prePostEnabled = true) // 开启注解方式为url添加权限验证
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private RoleUrlConfig roleUrlConfig;
	/**
	 * 注入自定义的AuthenticationProvider
	 */
	@Autowired
	private AuthenticationProvider provider;

	@Autowired
	private RedisTemplate<Object, Object> redisTemplate;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(provider); // 使用自定义验证方式
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf().disable()
				// rest api不需要保存session
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
				// swagger start
				.antMatchers("/swagger-ui.html").permitAll()
				.antMatchers("/swagger-resources/**").permitAll()
				.antMatchers("/images/**").permitAll()
				.antMatchers("/webjars/**").permitAll()
				.antMatchers("/v2/api-docs").permitAll()
				// swagger end

				.antMatchers("/login/example").permitAll()
				.antMatchers(roleUrlConfig.getApiAdminUrl()).access(roleUrlConfig.getRoleAdmin())
				.antMatchers(roleUrlConfig.getDruidUrl()).access(roleUrlConfig.getRoleDba())
				.antMatchers(roleUrlConfig.getApiDbaUrl()).access(roleUrlConfig.getRoleDba())
				.antMatchers(roleUrlConfig.getApiUserUrl()).access(roleUrlConfig.getRoleUser())
				// 所有请求都需要认证
				.anyRequest().authenticated()
				// 这里必须要写formLogin()，不然原有的UsernamePasswordAuthenticationFilter不会出现，也就无法配置我们重新的UsernamePasswordAuthenticationFilter
				.and().formLogin().loginPage("/");

		httpSecurity.headers().cacheControl();

		// 在UsernamePasswordAuthenticationFilter执行前执行该filter
		httpSecurity.addFilterBefore(getJwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);

		// 用重写的Filter替换掉原有的UsernamePasswordAuthenticationFilter
		httpSecurity.addFilterAt(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		// TODO Auto-generated method stub
		super.configure(web);
		web.ignoring().antMatchers("/resources/**").antMatchers("/static/**");
	}
	
	// 注册自定义的OncePerRequestFilter
	@Bean
	Filter getJwtAuthenticationTokenFilter() {
		// TODO Auto-generated method stub
		return new JwtAuthenticationTokenFilter();
	}


	// 注册自定义的UsernamePasswordAuthenticationFilter
	@Bean
	CustomAuthenticationFilter customAuthenticationFilter() throws Exception {
		CustomAuthenticationFilter filter = new CustomAuthenticationFilter();
		filter.setAuthenticationSuccessHandler(new AuthenticationSuccessHandler() {

			@Override
			public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
					Authentication authentication) throws IOException, ServletException {

				Object principal = authentication.getPrincipal();
				SecurityUserInfo userInfo = (SecurityUserInfo) principal;
				String accessToken = null;
				Map<String, Object> mapPy = new HashMap<>();
				/**
				 * 根据authorities、userId和userName生成token
				 */
				mapPy.put("userId", userInfo.getUserId());
				mapPy.put("username", userInfo.getUsername());
				mapPy.put("authorities", userInfo.getAuthorities());
				// 构造token
				accessToken = JwtHelper.generateToken(mapPy);

				// 构造返回结果
				LoginResult result = new LoginResult("200", "success");
				result.setAccessToken(accessToken);
				result.setLoginTime(new Date());
				result.setUserId(userInfo.getUserId());
				result.setUsername(userInfo.getUsername());
				
				// 将登录信息保存到redis
				redisTemplate.boundHashOps(RedisConstant.PRE_ + RedisConstant.TOKEN).put(accessToken, userInfo);
				// 输出登录授权结果
				response.setContentType("application/json;charset=UTF-8");
				response.getWriter().write(JSON.toJSONString(result));

			}
		});
		filter.setAuthenticationFailureHandler(new AuthenticationFailureHandler() {

			@Override
			public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
					AuthenticationException exception) throws IOException, ServletException {
				CommonResult result = new CommonResult("401", exception.getMessage());
				response.setContentType("application/json;charset=UTF-8");
				response.getWriter().write(JSON.toJSONString(result));
			}
		});
		filter.setFilterProcessesUrl("/login");

		// 这句很关键，重用WebSecurityConfigurerAdapter配置的AuthenticationManager，不然要自己组装AuthenticationManager
		filter.setAuthenticationManager(authenticationManagerBean());
		return filter;
	}
	
}
