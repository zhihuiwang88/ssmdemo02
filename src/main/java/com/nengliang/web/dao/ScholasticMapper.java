package com.nengliang.web.dao;

import com.nengliang.web.entity.Scholastic;
import com.nengliang.web.entity.ScholasticExample;

import java.sql.Timestamp;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

public interface ScholasticMapper {
	

	/**
	 * 插入数据
	 * 
	 * @param creationtime2
	 * @param creationtime
	 * @param file
	 * @param sex
	 * @param email
	 * @param age
	 * @param name
	 * @param scholastic
	 * @return
	 */

	void insertList(@Param("username") String name, @Param("userage") Integer age, @Param("mailbox") String email,
			@Param("usergender") String sex, @Param("headportrait") String file,
			@Param("creationtime") Timestamp creationtime, @Param("modifytime") Timestamp creationtime2);

	/**
	 * 根据名字查询
	 */
	String selectByName(String name);

	
    
	/**
	 * 查询所有数据并进行分页
	 * @param page  当前页
	 * @param limit  每页显示多少条数据
	 * @return
	 */
	List<Scholastic> selectAll(@Param("page") int pages, @Param("limits") Integer limit);

	/**
	 * 查询图片
	 * @param fileImage
	 * @return
	 */
	
	String selectByImage(@Param("headportrait") String fileImage);

	/**
	 * 查询ID
	 * @param id
	 * @return
	 */
	Integer selectById(@Param("idParam")Integer id);

	void updateById(Scholastic scholastic);

	void deleteByIds(@RequestParam("ids") String ids);

	List<Scholastic> selectParam();
}