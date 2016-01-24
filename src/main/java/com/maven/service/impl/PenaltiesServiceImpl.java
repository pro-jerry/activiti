package com.maven.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.dao.PenaltiesMapper;
import com.maven.pojo.Penalties;
import com.maven.service.PenaltiesService;


@Service
public class PenaltiesServiceImpl implements PenaltiesService{

	@Autowired
	private PenaltiesMapper penaltiesMapper;
	
	public Penalties getPenaltiesById(int id) {

		return penaltiesMapper.selectByPrimaryKey(id);
	}

	
}
