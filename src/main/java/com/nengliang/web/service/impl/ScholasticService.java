package com.nengliang.web.service.impl;

import java.sql.Timestamp;
import java.util.List;

import com.nengliang.web.entity.Scholastic;

public interface ScholasticService {

	

	void insertList(String name, Integer age, String email, String sex, String file, Timestamp creationtime,
			Timestamp creationtime2);

	String selectByName(String name);

	List<Scholastic> selectAll(int pages, Integer limit);

	String selectByImage(String fileImage);

	Integer selectById(Integer id);

	void updateById(Scholastic scholastic);

	void deleteByIds(String ids);

	List<Scholastic> selectParam();

}
