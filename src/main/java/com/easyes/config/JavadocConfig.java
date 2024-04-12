package com.easyes.config;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class JavadocConfig implements Serializable {
	
	/**
	 * 作者。
	 */
	private String author = System.getProperty("user.name");
	
	/**
	 * 自。
	 */
	private Supplier<String> since = () -> DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDate.now());
	
	/**
	 * Entity 包注释。
	 */
	private String entityPackage = "实体类层（Entity）软件包。";
	
	/**
	 * Mapper 包注释。
	 */
	private String mapperPackage = "映射层（Mapper）软件包。";
	
	/**
	 * 获取作者。
	 */
	public String getAuthor() {
		return author;
	}
	
	/**
	 * 设置作者。
	 */
	public JavadocConfig setAuthor(String author) {
		this.author = author;
		return this;
	}
	
	/**
	 * 获取自。
	 */
	public String getSince() {
		return since.get();
	}
	
	/**
	 * 设置自。
	 */
	public JavadocConfig setSince(String since) {
		this.since = () -> since;
		return this;
	}
	
	/**
	 * 设置自。
	 */
	public JavadocConfig setSince(Supplier<String> since) {
		this.since = since;
		return this;
	}
	
	/**
	 * 获取实体类层包注释。
	 */
	public String getEntityPackage() {
		return entityPackage;
	}
	
	/**
	 * 设置实体类层包注释。
	 */
	public JavadocConfig setEntityPackage(String entityPackage) {
		this.entityPackage = entityPackage;
		return this;
	}
	
	/**
	 * 获取映射层包注释。
	 */
	public String getMapperPackage() {
		return mapperPackage;
	}
	
	/**
	 * 设置映射层包注释。
	 */
	public JavadocConfig setMapperPackage(String mapperPackage) {
		this.mapperPackage = mapperPackage;
		return this;
	}
	
}
