package com.goclass.mapper;


import com.goclass.pojo.TbTimer;
import com.goclass.pojo.TbTimerExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbTimerMapper {
    int countByExample(TbTimerExample example);

    int deleteByExample(TbTimerExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TbTimer record);

    int insertSelective(TbTimer record);

    List<TbTimer> selectByExample(TbTimerExample example);

    TbTimer selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TbTimer record, @Param("example") TbTimerExample example);

    int updateByExample(@Param("record") TbTimer record, @Param("example") TbTimerExample example);

    int updateByPrimaryKeySelective(TbTimer record);

    int updateByPrimaryKey(TbTimer record);
}