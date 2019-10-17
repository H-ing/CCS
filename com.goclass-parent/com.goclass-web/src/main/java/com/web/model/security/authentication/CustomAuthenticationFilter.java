package com.web.model.security.authentication;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * 自定义登录拦截处理filter
 * 扩展UsernamePasswordAuthenticationFilter，根据json格式数据传递username和password进行验证
 * @author Administrator
 *
 */
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    @SuppressWarnings("finally")
	@Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //当 请求的Content-Type为json进行身份认证
    	if(request.getContentType() != null && (request.getContentType().equals(MediaType.APPLICATION_JSON_UTF8_VALUE)
                ||request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE))){
            //使用jackson反序列化json
            ObjectMapper mapper = new ObjectMapper();
            UsernamePasswordAuthenticationToken authRequest = null;
            try (InputStream is = request.getInputStream()){
                AuthenticationBean authenticationBean = mapper.readValue(is,AuthenticationBean.class);
                authRequest = new UsernamePasswordAuthenticationToken(
                        authenticationBean.getUsername(), authenticationBean.getPassword());
            }catch (IOException e) {
                e.printStackTrace();
                authRequest = new UsernamePasswordAuthenticationToken(
                        "", "");
            }finally {
                setDetails(request, authRequest);
                return this.getAuthenticationManager().authenticate(authRequest);
            }
        }else if (request.getContentType() == null) {
        	//处理content-type不是application/json的情况
			throw new AuthenticationCredentialsNotFoundException("content-type must be application/json");
		}else {
        	//交由UsernamePasswordAuthenticationFilter处理
            return super.attemptAuthentication(request, response);
        }
    }
}