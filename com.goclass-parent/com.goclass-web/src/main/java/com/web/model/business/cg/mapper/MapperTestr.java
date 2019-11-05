package com.web.model.business.cg.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import com.web.model.business.cg.datasourcce.DataSourceConnection;

public class MapperTestr {
	@Test
	public void dataTest() {
		SqlSession sqlSession = new DataSourceConnection().getSqlSession();
		PopulationMapper population = sqlSession.getMapper(PopulationMapper.class);
//		List<Map> selectData = population.selectAllStudent();
		System.out.println(population.selectStudentById(31));
	}
}
