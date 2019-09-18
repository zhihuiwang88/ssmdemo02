package com.nengliang.web.controller;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.mysql.fabric.xmlrpc.base.Array;
import com.nengliang.web.entity.Scholastic;
import com.nengliang.web.service.impl.ScholasticService;
import com.nengliang.web.util.JsonResult;


/**
 * 实现用户表的CRUD
 * mvc+mybatis generetor + layui
 * @author Dell
 *@Data 20190612
 */
@Controller
@RequestMapping("/scholastic")
public class ScholasticController {
    
	//日志打印
	private  static final Logger logger = LoggerFactory.getLogger(ScholasticController.class);
	
	@Autowired
	private ScholasticService scholasticService;
	
	
	/*
	 *查询出数据库表中的所有信息 
	 *
	 *20190613 目前项目只做到所有数据返回前端
	 *http://localhost:8081/ssmupload/scholastic/listAll
	 *@Param page:当前页，limit:每页显示多少条数据
	 *20190627补充bug说明： 编辑弹窗radio性别没显示；编辑弹窗图片没显示；添加弹窗图片有时候未上传到电脑上
	 */
	
	@RequestMapping("/listAll")
	public String   selectList() {
		return	"list";	
	}
	
	@RequestMapping("/selectAll")
	@ResponseBody
	public  JsonResult  selectAllPerson(@RequestParam("page") Integer page,@RequestParam("limit") Integer limit) {
		JsonResult jsonResult = new JsonResult();
		int pages = (page-1)*limit;
		
		List<Scholastic>  schcList =  scholasticService.selectParam();
		List<Scholastic>  scholasticList =  scholasticService.selectAll(pages,limit);
		jsonResult.setCode(0);
		jsonResult.setCount(schcList.size());
		jsonResult.setData(scholasticList);
		return	jsonResult;	
	}
	
	/**
	 * 添加数据接口
	 */
	@RequestMapping("/addList")
	public String insertAllList() {
		return "add";
	}
	
	
	@RequestMapping(value="/insertList",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult insertList(@RequestParam(value="name",required=false) String name,@RequestParam(value="age",required=false) Integer age,
			@RequestParam(value="email",required=false) String email,@RequestParam(value="sex",required=false) String sex,
			@RequestParam(value="file",required=false) String file) {
		JsonResult jsonResult = new JsonResult();
		Scholastic scholastic = new Scholastic();

		if(StringUtils.isEmpty(name) || StringUtils.isEmpty(age) || StringUtils.isEmpty(email) || StringUtils.isEmpty(sex)) {
			jsonResult.setMsg("参数：空指针异常");
			jsonResult.setCode(100);
			return jsonResult;
		}
		
		
		// 判断姓名格式正确否
		String patternName = "^[\u4e00-\u9fa5]{2,6}$";
		Pattern r = Pattern.compile(patternName);
		String repname = name.replaceAll(" ", "");
		Matcher matcherName = r.matcher(repname);
		if(!matcherName.matches()) {
			jsonResult.setMsg("姓名格式不对");
			jsonResult.setCode(101);
			return jsonResult;
		}
		
		// 判断年龄符合格式不
		if(age <= 0 || age > 150) {
			jsonResult.setMsg("年龄不符合格式要求");
			jsonResult.setCode(102);
			return jsonResult;
		}
		// 判断邮箱符合格式不
		String patternEmail = "^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$";
		Pattern rn = Pattern.compile(patternEmail);
		String repemail = email.replaceAll(" ", "");
		Matcher matcherEmail = rn.matcher(repemail);
		if(!matcherEmail.matches()) {
			jsonResult.setMsg("邮箱写错了格式");
			jsonResult.setCode(103);
			return jsonResult;
		}
		// 判断图片是否为空
		if(StringUtils.isEmpty(file)) {
			jsonResult.setMsg("请上传图片");
			jsonResult.setCode(104);
			return jsonResult;
		}
		
		// 判断图片存在不
		String fileImage = "E:\\image\\" + file;
		String oldImage = scholasticService.selectByImage(fileImage);
		if(fileImage.equals(oldImage)) {
			jsonResult.setMsg("数据库图片已经存在，请重新上传");
			jsonResult.setCode(105);
			return jsonResult;
		}
		
		scholastic.setUsername(repname);
		scholastic.setUserage(age);
		scholastic.setUsergender(sex);
		scholastic.setMailbox(repemail);
		scholastic.setHeadportrait(fileImage);
		
		// 获取当前插入的时间 https://www.cnblogs.com/zhaotiancheng/p/6413067.html
		 String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		 Timestamp creationtime =Timestamp.valueOf(nowTime);
		 scholastic.setCreationtime(creationtime);
		 scholastic.setModifytime(creationtime);
		 // 查询数据库是否存在此姓名
		 String oldName = scholasticService.selectByName(repname);
		 if(oldName != null && oldName != "") {
			 jsonResult.setMsg("姓名已经存在");
			 jsonResult.setCode(106);
			 return jsonResult;
		 }
		 if(StringUtils.isEmpty(oldName)) {
			 // 插入不重复的姓名
			 scholasticService.insertList(repname,age,email,sex,fileImage,creationtime,creationtime);
		 }
		
		 jsonResult.setCode(200);
		 jsonResult.setData(scholastic);
		 return jsonResult;
	}
	
	/**
	 * 根据ID更新数据
	 * 查询参数ID是否为空
	 * 姓名是否重复，和校验
	 * 邮箱校验
	 * 
	 */
	@RequestMapping("/updateParam")
	public String update(){
		return "update";
	}
	
	@RequestMapping(value="/updateById",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult updateParam(Scholastic scholastic){
		JsonResult jsResult = new JsonResult();
		// 查询参数ID是否为空
		Integer  idpram = scholasticService.selectById(scholastic.getId());
		
		if(StringUtils.isEmpty(idpram)) {
			jsResult.setCode(111);
			jsResult.setMsg("数据库里的ID不存在");
			return  jsResult;
		}
		// 判断名重复不以及名字符合格式不
		String userName = scholastic.getUsername();
		String  updateName= userName.replaceAll(" ", "");
		String oldName = scholasticService.selectByName(updateName);
		if(updateName.equals(oldName)) {
			jsResult.setCode(112);
			jsResult.setMsg("数据库名字重复");
			return  jsResult;
		}
		
		String patternName = "^[\u4e00-\u9fa5]{2,6}$";
		Pattern r = Pattern.compile(patternName);
		Matcher matcherName = r.matcher(updateName);
		if(!matcherName.matches()) {
			jsResult.setMsg("姓名格式不对");
			jsResult.setCode(113);
			return jsResult;
		}
		
		// 判断邮箱符合格式不
		String email = scholastic.getMailbox();
		String patternEmail = "^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$";
		Pattern rn = Pattern.compile(patternEmail);
		String repemail = email.replaceAll(" ", "");
		Matcher matcherEmail = rn.matcher(repemail);
		if(!matcherEmail.matches()) {
			jsResult.setMsg("邮箱写错了格式");
			jsResult.setCode(114);
			return jsResult;
		}

		// 判断图片为空不
		String updateFile = scholastic.getHeadportrait();
		String pathFile = "E:\\image\\" + updateFile;
		
		if(StringUtils.isEmpty(updateFile)) {
			jsResult.setMsg("请上传图片");
			jsResult.setCode(115);
			return jsResult;
		}
		
		// 判断图片存在不
		
		String oldImage = scholasticService.selectByImage(pathFile);
		
		if(pathFile.equals(oldImage)) {
			jsResult.setMsg("数据库图片已经存在，请重新上传");
			jsResult.setCode(116);
			return jsResult;
		}
		
		// 获取当前插入的时间 https://www.cnblogs.com/zhaotiancheng/p/6413067.html
		 String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		 Timestamp modifytime =Timestamp.valueOf(nowTime);
		
				 
		Integer useAge = scholastic.getUserage();
		
		// 判断年龄符合格式不
		if( useAge<= 0 || useAge > 150) {
			jsResult.setMsg("年龄不符合格式要求");
			jsResult.setCode(117);
			return jsResult;
		}
		
		System.out.println(scholastic.getUsergender());
		scholastic.setUsername(updateName);
		scholastic.setUserage(useAge);
		scholastic.setUsergender(scholastic.getUsergender());
		scholastic.setMailbox(repemail);
		scholastic.setHeadportrait(pathFile);
		scholastic.setModifytime(modifytime);
		scholasticService.updateById(scholastic);
		
		
		 jsResult.setCode(200);
		 jsResult.setMsg("更新数据成功");
		return  jsResult;
	}
	
	/**
	 * 批量删除
	 * @Pram ids: 21,22,23, 参数为数组格式
	 * 对ids以,截取
	 * 判断参数数组是多个还是一个
	 */
	@RequestMapping(value="deleteByIds",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult deleteById(@RequestParam("ids") String ids) {
		JsonResult jResult = new JsonResult();
		
		if(StringUtils.isEmpty(ids)) {
			jResult.setCode(120);
			jResult.setMsg("IDS为空");
			return jResult;
		}
	
		if(ids.contains(",")){
			String[] strIds = ids.split(",");
			for (int i = 0; i < strIds.length; i++) {
				String string = strIds[i];
				
				scholasticService.deleteByIds(string);
			}
		}
		
		/*if(ids.contains(",")){
			
			
			List<Integer> list = new ArrayList<Integer>();
			String[] strIds = ids.split(",");
			for (String string2 : strIds) {
				list.add(Integer.parseInt(string2));
			}
			//scholasticService.deleteByIds(string);
			
			
			//System.out.println("string:" + string);
			
			
		}*/
		
		jResult.setCode(200);
		jResult.setMsg("删除成功");
		return jResult;
	}
	
	
	
	
	/**
	 * 图片上传
	 */
	@RequestMapping("/upload")
	@ResponseBody
	public JsonResult uploadImg(MultipartFile file, HttpServletRequest request) throws Exception {

		JsonResult jsonResult = new JsonResult();
		// 判断图片是否为空
		if (StringUtils.isEmpty(file)) {
			jsonResult.setMsg("图片不能空了");
			jsonResult.setCode(107);
			return jsonResult;
		}
		// 获取图片的名字
		String fileName = file.getOriginalFilename();
		
		// 判断图片的格式正确否
		String fileType = fileName.substring(fileName.lastIndexOf("."));
		if(!fileType.matches("^.*(jpg|jpeg|png)$")) {
			jsonResult.setMsg("图片格式不正确");
			jsonResult.setCode(108);
			return jsonResult;
		}
		
		// 判断图片重复不
		String oldFile = "E:\\image\\" + fileName;
		String oldImg = scholasticService.selectByImage(oldFile);
		if( oldFile.equals(oldImg)) {
			jsonResult.setMsg("数据库有图片了");
			jsonResult.setCode(109);
			return jsonResult;
		}
		
		//图片存放的路径   E:\image
		//String path = request.getSession().getServletContext().getRealPath("") + "\\static\\" + "image\\";
		String path = "E:\\image\\";
		String filePath = path + fileName;
		File newFile = new File(filePath);
		
		// 图片路径不存在进行创建
		if(!newFile.exists()) {
			newFile.mkdirs();
		}else {
			jsonResult.setMsg("服务器图片有，再次上传吧");
			jsonResult.setCode(110);
			return jsonResult;
		}
		// 图片写到服务器
		file.transferTo(newFile);
		
		// 设置layui图片的接口格式
		jsonResult.setCode(0);
		jsonResult.setMsg("");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("src", filePath);
		jsonResult.setData(map);
		
		return jsonResult;
	}
	
	
	
	
	
	// 最后一个}
}
