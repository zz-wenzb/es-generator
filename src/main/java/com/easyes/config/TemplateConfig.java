package com.easyes.config;

import com.easyes.generator.GeneratorFactory;
import com.easyes.template.ITemplate;
import com.easyes.template.impl.EnjoyTemplate;

import java.io.Serializable;

import static com.easyes.constant.GenerateTypeConst.ENTITY;
import static com.easyes.constant.GenerateTypeConst.MAPPER;

/**
 * 模板配置。
 *
 * @author wenzb
 */
@SuppressWarnings("unused")
public class TemplateConfig implements Serializable {
	
	/**
	 * 生成代码的模板引擎。
	 */
	private ITemplate template = new EnjoyTemplate();
	
	/**
	 * 获取模板引擎。
	 */
	public ITemplate getTemplate() {
		return template;
	}
	
	/**
	 * 设置模板引擎。
	 */
	public TemplateConfig setTemplate(ITemplate template) {
		this.template = template;
		return this;
	}
	
	/**
	 * 获取生成 Entity 模板文件的位置。
	 */
	public String getEntity() {
		return GeneratorFactory.getGenerator(ENTITY).getTemplatePath();
	}
	
	/**
	 * 设置生成 Entity 模板文件的位置。
	 */
	public TemplateConfig setEntity(String entity) {
		GeneratorFactory.getGenerator(ENTITY).setTemplatePath(entity);
		return this;
	}
	
	/**
	 * 获取生成 Mapper 模板文件的位置。
	 */
	public String getMapper() {
		return GeneratorFactory.getGenerator(MAPPER).getTemplatePath();
	}
	
	/**
	 * 设置生成 Mapper 模板文件的位置。
	 */
	public TemplateConfig setMapper(String mapper) {
		GeneratorFactory.getGenerator(MAPPER).setTemplatePath(mapper);
		return this;
	}
	
}
