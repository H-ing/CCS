package com.web.model.business.cg.datasourcce;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class DataSourceConnection {
	// 指定全局配置文件 
	String resource = "resources/cg/mybatis-config.xml";
	SqlSession sqlSession;
	public DataSourceConnection() {
		InputStream inputStream;
		try {
			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream); 
			// 获取sqlSession 
			sqlSession = sqlSessionFactory.openSession();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 
		
	public SqlSession getSqlSession() {
		return sqlSession;
	}
	public void sessionClose() {
		sqlSession.close();
	}
}
