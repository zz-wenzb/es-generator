/*
 *  Copyright (c) 2022-2023, Mybatis-Flex (fuhai999@gmail.com).
 *  <p>
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.easyes.generator.impl;

import com.easyes.config.GlobalConfig;
import com.easyes.config.MapperConfig;
import com.easyes.config.PackageConfig;
import com.easyes.entity.Index;
import com.easyes.generator.IGenerator;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static com.easyes.constant.TemplateConst.MAPPER;

/**
 * Mapper 生成器。
 *
 * @author Michael Yang
 * @author 王帅
 */
public class MapperGenerator implements IGenerator {
	
	private String templatePath;
	
	public MapperGenerator() {
		this(MAPPER);
	}
	
	public MapperGenerator(String templatePath) {
		this.templatePath = templatePath;
	}
	
	@Override
	public void generate(Index index, GlobalConfig globalConfig) {
		
		PackageConfig packageConfig = globalConfig.getPackageConfig();
		MapperConfig mapperConfig = globalConfig.getMapperConfig();
		
		String mapperPackagePath = packageConfig.getMapperPackage().replace(".", "/");
		File mapperJavaFile = new File(packageConfig.getSourceDir(),
				mapperPackagePath + "/" + index.buildMapperClassName() + ".java");
		
		if (mapperJavaFile.exists() && !mapperConfig.isOverwriteEnable()) {
			return;
		}
		
		Map<String, Object> params = new HashMap<>(4);
		params.put("index", index);
		params.put("mapperConfig", mapperConfig);
		params.put("packageConfig", packageConfig);
		params.put("javadocConfig", globalConfig.getJavadocConfig());
		globalConfig.getTemplateConfig().getTemplate().generate(params, templatePath, mapperJavaFile);
		
		System.out.println("Mapper ---> " + mapperJavaFile);
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
