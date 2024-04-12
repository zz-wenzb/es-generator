package com.easyes.config;

import lombok.Getter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 生成 Entity 的配置。
 *
 * @author wenzb
 */
@SuppressWarnings("unused")
public class EntityConfig implements Serializable {
	
	/**
	 * 索引字段需要映射成的字段.
	 */
	private final Map<String, String> fieldMapping = new HashMap<>();
	
	/**
	 * Entity 类的前缀。 -- GETTER -- 获取类前缀。
	 */
	@Getter
	private String classPrefix = "";
	
	/**
	 * Entity 类的后缀。 -- GETTER -- 获取类后缀。
	 */
	@Getter
	private String classSuffix = "";
	
	/**
	 * Entity 类的父类，可以自定义一些 BaseEntity 类。 -- GETTER -- 获取父类。
	 */
	@Getter
	private Class<?> superClass;
	
	/**
	 * 是否覆盖之前生成的文件。 -- GETTER -- 是否覆盖原有文件。
	 */
	@Getter
	private boolean overwriteEnable;
	
	/**
	 * Entity 默认实现的接口。 -- GETTER -- 获取实现接口。
	 */
	@Getter
	private Class<?>[] implInterfaces = {Serializable.class};
	
	/**
	 * Entity 是否使用 Lombok 注解。 -- GETTER -- 是否使用 Lombok。
	 */
	@Getter
	private boolean withLombok;
	
	/**
	 * Entity 是否使用 Swagger 注解。 -- GETTER -- 是否启用 Swagger。
	 */
	@Getter
	private boolean withSwagger;
	
	/**
	 * Swagger 版本
	 */
	@Getter
	private SwaggerVersion swaggerVersion;
	
	/**
	 * 设置类前缀。
	 */
	public EntityConfig setClassPrefix(String classPrefix) {
		this.classPrefix = classPrefix;
		return this;
	}
	
	/**
	 * 设置类后缀。
	 */
	public EntityConfig setClassSuffix(String classSuffix) {
		this.classSuffix = classSuffix;
		return this;
	}
	
	/**
	 * 设置父类。
	 */
	public EntityConfig setSuperClass(Class<?> superClass) {
		this.superClass = superClass;
		return this;
	}
	
	/**
	 * 设置是否覆盖原有文件。
	 */
	public EntityConfig setOverwriteEnable(boolean overwriteEnable) {
		this.overwriteEnable = overwriteEnable;
		return this;
	}
	
	/**
	 * 设置实现接口。
	 */
	public EntityConfig setImplInterfaces(Class<?>... implInterfaces) {
		this.implInterfaces = implInterfaces;
		return this;
	}
	
	/**
	 * 设置是否使用 Lombok。
	 */
	public EntityConfig setWithLombok(boolean withLombok) {
		this.withLombok = withLombok;
		return this;
	}
	
	/**
	 * 设置是否启用 Swagger。
	 */
	public EntityConfig setWithSwagger(boolean withSwagger) {
		this.withSwagger = withSwagger;
		this.swaggerVersion = SwaggerVersion.FOX;
		return this;
	}
	
	/**
	 * 设置 Swagger 版本
	 */
	public EntityConfig setSwaggerVersion(SwaggerVersion swaggerVersion) {
		this.swaggerVersion = swaggerVersion;
		this.withSwagger = swaggerVersion != null;
		return this;
	}
	
	public void putFieldMapping(String field, String mapping) {
		this.fieldMapping.put(field, mapping);
	}
	
	public void putAllFieldMapping(Map<String, String> map) {
		this.fieldMapping.putAll(map);
	}
	
	public String getMapping(String field) {
		return this.fieldMapping.get(field);
	}
	
	@Getter
	public enum SwaggerVersion {
		
		FOX("FOX"),
		DOC("DOC");
		
		private final String name;
		
		SwaggerVersion(String name) {
			this.name = name;
		}
		
	}
	
}
