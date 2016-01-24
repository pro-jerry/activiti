package com.maven.dao;

import com.maven.pojo.Penalties;

public interface PenaltiesMapper {
    int deleteByPrimaryKey(Integer paymentno);

    int insert(Penalties record);

    int insertSelective(Penalties record);

    Penalties selectByPrimaryKey(Integer paymentno);

    int updateByPrimaryKeySelective(Penalties record);

    int updateByPrimaryKey(Penalties record);
}