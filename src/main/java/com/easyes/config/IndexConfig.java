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

package com.easyes.config;

import com.easyes.entity.EsVersionEnum;
import com.easyes.version.EsVersionClient;
import lombok.Getter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 索引策略配置。
 *
 * @author wenzb
 */
@SuppressWarnings("unused")
public class IndexConfig implements Serializable {
	
	/**
	 * 索引前缀，多个前缀用英文逗号（,） 隔开。
	 * -- GETTER --
	 *  获取索引前缀。
	 
	 */
	@Getter
	private String indexPrefix;
	
	/**
	 * 生成哪些索引，白名单。
	 * -- GETTER --
	 *  获取生成哪些索引。
	 
	 */
	@Getter
	private Set<String> generateIndexes;
	
	/**
	 * 不生成哪些索引，黑名单。
	 * -- GETTER --
	 *  获取不生成哪些索引。
	 
	 */
	@Getter
	private Set<String> unGenerateIndexes;
	
	/**
	 * 需要忽略的列 全局配置。
	 * -- GETTER --
	 *  获取需要忽略的列 全局配置。
	 
	 */
	@Getter
	private Set<String> ignoreColumns;
	
	/**
	 * 索引名（或别名）需要转换的名称(需要小写且以下划线分割)。
	 */
	@Getter
	private final Map<String, String> nameMapping = new HashMap<>();
	
	@Getter
	private String version;
	
	// es版本操作
	@Getter
	private EsVersionClient esVersionClient;
	
	public void setVersion(String version){
		this.esVersionClient = EsVersionEnum.of(version);
		this.version = version;
	}
	
	/**
	 * 设置需要忽略的列  全局配置。
	 */
	public IndexConfig setIgnoreColumns(String... columns) {
		if (ignoreColumns == null) {
			ignoreColumns = new HashSet<>();
		}
		for (String column : columns) {
			if (column != null && !column.trim().isEmpty()) {
				ignoreColumns.add(column.trim().toLowerCase());
			}
		}
		return this;
	}
	
	
	/**
	 * 设置生成哪些索引。
	 */
	public IndexConfig setGenerateIndex(String... indexes) {
		if (generateIndexes == null) {
			generateIndexes = new HashSet<>();
		}
		
		for (String index : indexes) {
			if (index != null && !index.trim().isEmpty()) {
				generateIndexes.add(index.trim());
			}
		}
		
		return this;
	}
	
	/**
	 * 设置不生成哪些索引。
	 */
	public IndexConfig setUnGenerateTable(String... indexes) {
		if (unGenerateIndexes == null) {
			unGenerateIndexes = new HashSet<>();
		}
		
		for (String index : indexes) {
			if (index != null && !index.trim().isEmpty()) {
				unGenerateIndexes.add(index.trim());
			}
		}
		
		return this;
	}
	
	public boolean isSupportGenerate(String index) {
		if (unGenerateIndexes != null && unGenerateIndexes.contains(index)) {
			return false;
		}
		
		if (generateIndexes == null || generateIndexes.isEmpty()) {
			return true;
		}
		
		for (String generateIndex : generateIndexes) {
			if (generateIndex.equals(index)) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * 设置索引前缀。
	 */
	public IndexConfig setIndexPrefix(String... indexPrefix) {
		this.indexPrefix = String.join(",", indexPrefix);
		return this;
	}
	
	/**
	 * 设置生成哪些索引。
	 */
	public IndexConfig setGenerateIndexes(Set<String> generateIndexes) {
		this.generateIndexes = generateIndexes;
		return this;
	}
	
	/**
	 * 设置不生成哪些索引。
	 */
	public IndexConfig setUnGenerateIndexes(Set<String> unGenerateIndexes) {
		this.unGenerateIndexes = unGenerateIndexes;
		return this;
	}
	
	/**
	 * 将名称映射加入。
	 *
	 * @param name    索引名称
	 * @param mapping 转换的名字
	 * @return IndexConfig
	 */
	public IndexConfig putNameMapping(String name, String mapping) {
		nameMapping.put(name, mapping);
		return this;
	}
	
	public IndexConfig putAllNameMapping(Map<String, String> map) {
		nameMapping.putAll(map);
		return this;
	}
	
	public String getMapping(String name) {
		return nameMapping.get(name);
	}
}
