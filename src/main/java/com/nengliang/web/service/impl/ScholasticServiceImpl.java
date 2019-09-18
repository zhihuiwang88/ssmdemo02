package com.nengliang.web.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nengliang.web.dao.ScholasticMapper;
import com.nengliang.web.entity.Scholastic;

@Service
public class ScholasticServiceImpl implements ScholasticService {

	@Autowired
	private ScholasticMapper scholasticMapper;

	/**
	 * 查询数据库所有的信息
	 * 
	 */
	
	public List<Scholastic> selectAll(int pages, Integer limit) {
		return scholasticMapper.selectAll(pages,limit);
	}

	public List<Scholastic> selectParam() {
		// TODO Auto-generated method stub
		return scholasticMapper.selectParam();
	}
	
	/**
	 * 插入数据
	 */
	public void insertList(String name, Integer age, String email, String sex, String file, Timestamp creationtime,
			Timestamp creationtime2) {
		scholasticMapper.insertList(name, age, email, sex, file, creationtime, creationtime2);
	}

	/**
	 * 根据名字查询
	 */

	public String selectByName(String name) {
		return scholasticMapper.selectByName(name);
	}

	/**
	 * 根据图片查询
	 */
	public String selectByImage(String fileImage) {
		
		return  scholasticMapper.selectByImage(fileImage);
	}

	/**
	 * 查询参数ID
	 */
	public Integer selectById(Integer id) {
		
		return scholasticMapper.selectById(id);
	}
	
	/**
	 * 根据参数ID更新数据
	 */
	public void updateById(Scholastic scholastic) {
		scholasticMapper.updateById(scholastic);
		
	}

	/**
	 * 根据参数ID删除数据
	 */
	public void deleteByIds(String ids) {
		scholasticMapper.deleteByIds(ids);
		
	}

	

	

}
