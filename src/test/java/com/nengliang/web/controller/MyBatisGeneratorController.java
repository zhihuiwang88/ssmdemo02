package com.nengliang.web.controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

/**
 * MyBatis Generator生成DAO( interface User)，Mapper(User.xml)，Model(User.java)
 * @author Dell
 *@Data 2019/09/12
 */
public class MyBatisGeneratorController {
	
	public static void main(String[] args) throws SQLException, IOException, InterruptedException, InvalidConfigurationException, XMLParserException {
		List<String> warnings = new ArrayList<String>();
		boolean overwrite = true;
		String pathName = "D:\\eclipseworkspaces\\ssmgenerator\\src\\main\\resources\\generatorConfig.xml";
		File configFile = new File(pathName);
		ConfigurationParser cp = new ConfigurationParser(warnings);
		org.mybatis.generator.config.Configuration config = cp.parseConfiguration(configFile);
		DefaultShellCallback callback = new DefaultShellCallback(overwrite);
		MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
		myBatisGenerator.generate(null);
	}	

}
