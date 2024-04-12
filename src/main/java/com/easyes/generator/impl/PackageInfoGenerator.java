package com.easyes.generator.impl;

import com.easyes.config.GlobalConfig;
import com.easyes.config.JavadocConfig;
import com.easyes.config.PackageConfig;
import com.easyes.entity.Index;
import com.easyes.generator.IGenerator;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.easyes.constant.TemplateConst.PACKAGE_INFO;

/**
 * package-info.java 生成器。
 *
 * @author wenzb
 */
public class PackageInfoGenerator implements IGenerator {
	
	private String templatePath;
	
	public PackageInfoGenerator() {
		this(PACKAGE_INFO);
	}
	
	public PackageInfoGenerator(String templatePath) {
		this.templatePath = templatePath;
	}
	
	@Override
	public void generate(Index index, GlobalConfig globalConfig) {
		
		JavadocConfig javadocConfig = globalConfig.getJavadocConfig();
		PackageConfig packageConfig = globalConfig.getPackageConfig();
		
		String sourceDir = packageConfig.getSourceDir();
		
		List<Data> dataList = new ArrayList<>();
		dataList.add(new Data(sourceDir, packageConfig.getEntityPackage(), javadocConfig.getEntityPackage()));
		dataList.add(new Data(sourceDir, packageConfig.getMapperPackage(), javadocConfig.getMapperPackage()));
		
		dataList.forEach(data -> {
			Map<String, Object> params = new HashMap<>(3);
			params.put("packageName", data.packageName);
			params.put("packageComment", data.packageComment);
			params.put("javadocConfig", javadocConfig);
			globalConfig.getTemplateConfig().getTemplate().generate(params, templatePath, data.filePath);
		});
	}
	
	@Override
	public String getTemplatePath() {
		return templatePath;
	}
	
	@Override
	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}
	
	/**
	 * 内置类，用于存放数据。
	 */
	private static class Data {
		
		String packageName;
		
		String packageComment;
		
		File filePath;
		
		Data(String sourceDir, String packageName, String packageComment) {
			this.packageName = packageName;
			this.packageComment = packageComment;
			this.filePath = getFilePath(sourceDir, packageName);
		}
		
		File getFilePath(String sourceDir, String packageName) {
			return new File(sourceDir, packageName.replace(".", "/") + "/package-info.java");
		}
		
	}
	
}
