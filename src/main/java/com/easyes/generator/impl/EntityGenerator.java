package com.easyes.generator.impl;


import com.easyes.config.EntityConfig;
import com.easyes.config.GlobalConfig;
import com.easyes.config.PackageConfig;
import com.easyes.entity.Index;
import com.easyes.generator.IGenerator;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static com.easyes.constant.TemplateConst.ENTITY;

/**
 * Entity 生成器。
 *
 * @author wenzb
 */
public class EntityGenerator implements IGenerator {
	
	private String templatePath;
	
	public EntityGenerator() {
		this(ENTITY);
	}
	
	public EntityGenerator(String templatePath) {
		this.templatePath = templatePath;
	}
	
	@Override
	public void generate(Index index, GlobalConfig globalConfig) {
		PackageConfig packageConfig = globalConfig.getPackageConfig();
		EntityConfig entityConfig = globalConfig.getEntityConfig();
		
		String entityPackagePath = packageConfig.getEntityPackage().replace(".", "/");
		File entityJavaFile = new File(packageConfig.getSourceDir(),
				entityPackagePath + "/" + index.buildEntityClassName() + ".java");
		
		if (entityJavaFile.exists() && !entityConfig.isOverwriteEnable()) {
			return;
		}
		//排除忽略列
		if (globalConfig.getIndexConfig().getIgnoreColumns() != null) {
			index.getColumns().removeIf(column -> globalConfig.getIndexConfig().getIgnoreColumns()
					.contains(column.getName().toLowerCase()));
		}
		
		Map<String, Object> params = new HashMap<>(4);
		params.put("index", index);
		params.put("entityConfig", entityConfig);
		params.put("packageConfig", packageConfig);
		params.put("javadocConfig", globalConfig.getJavadocConfig());
		globalConfig.getTemplateConfig().getTemplate().generate(params, templatePath, entityJavaFile);
		
		System.out.println("Entity ---> " + entityJavaFile);
	}
	
	@Override
	public String getTemplatePath() {
		return templatePath;
	}
	
	@Override
	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}
	
}
