package com.easyes.entity;


import com.easyes.config.EntityConfig;
import com.easyes.config.GlobalConfig;
import com.easyes.config.IndexConfig;
import com.easyes.config.MapperConfig;
import com.easyes.utils.StringUtil;
import lombok.Getter;
import lombok.Setter;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.settings.Settings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 索引信息。
 */
public class Index {
	
	/**
	 * 索引名。
	 */
	@Setter
	@Getter
	private String name;
	
	/**
	 * 所包含的列。
	 */
	@Setter
	@Getter
	private List<Column> columns = new ArrayList<>();
	
	/**
	 * 全局配置。
	 */
	@Setter
	@Getter
	private GlobalConfig globalConfig;
	
	// 别名
	@Setter
	@Getter
	private List<String> alias;
	
	@Setter
	@Getter
	private Settings settings;
	
	@Getter
	private MappingMetaData mappingMetaData;
	
	public void setMappingMetaData(MappingMetaData mappingMetaData) {
		this.mappingMetaData = mappingMetaData;
		
		Map<String, Object> sourceAsMap = mappingMetaData.getSourceAsMap();
		Map<String, Object> properties = (Map<String, Object>) sourceAsMap.get("properties");
		
		Set<Map.Entry<String, Object>> entries = properties.entrySet();
		this.addColumn(buildIdColumn());
		for (Map.Entry<String, Object> entry : entries) {
			this.addColumn(buildColumn(entry.getKey(), (Map<String, Object>) entry.getValue()));
		}
	}
	
	public void build() {
		globalConfig.getEsVersionClient().buildIndex(this);
	}
	
	private Column buildColumn(String field, Map<String, Object> value) {
		Column column = new Column();
		String fieldMapping = globalConfig.getFieldMapping(field);
		if (StringUtil.isNotBlank(fieldMapping)) {
			column.setName(fieldMapping);
		} else {
			column.setName(field);
		}
		String type = (String) value.get("type");
		//					column.setPropertyType();
		column.setRawType(type);
		column.setEsVersionClient(globalConfig.getEsVersionClient());
		column.setPropertyType(type);
		column.setOnIndexFieldValue(type);
		
		//todo 判断是否驼峰，然后增加value
		column.setNeedGenIndexFieldAnnotation(true);
		//					String str = FieldType.getField(type).getStr(field);
		//					System.out.println(str);
		return column;
	}
	
	private Column buildIdColumn() {
		Column column = new Column();
		column.setName("id");
		column.setPrimaryKey(true);
		column.setRawType("keyword");
		column.setEsVersionClient(globalConfig.getEsVersionClient());
		column.setPropertyType("keyword");
		return column;
	}
	
	
	public List<Column> getSortedColumns() {
		ArrayList<Column> arrayList = new ArrayList<>(columns);
		// 生成字段排序
		arrayList.sort(
				Comparator.comparingInt((Column c) -> c.getProperty().length()).thenComparing(Column::getProperty));
		return arrayList;
	}
	
	public void addColumn(Column column) {
		columns.add(column);
	}
	
	// ===== 构建实体类文件 =====
	
	/**
	 * 构建 import 导包。
	 */
	public List<String> buildImports() {
		Set<String> imports = new HashSet<>();
		for (Column column : columns) {
			imports.addAll(column.getImportClasses());
		}
		EntityConfig entityConfig = globalConfig.getEntityConfig();
		if (entityConfig.getSuperClass() != null) {
			imports.add(entityConfig.getSuperClass().getName());
		}
		
		if (entityConfig.getImplInterfaces() != null) {
			for (Class<?> entityInterface : entityConfig.getImplInterfaces()) {
				imports.add(entityInterface.getName());
			}
		}
		
		imports.add("org.dromara.easyes.annotation.IndexName");
		return imports.stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList());
	}
	
	/**
	 * 构建 @IndexName(...) 注解。
	 */
	public String buildIndexAnnotation() {
		StringBuilder indexAnnotation = new StringBuilder();
		
		indexAnnotation.append("@IndexName(value = \"").append(name).append("\"");
		//@IndexName(value = "取证手机-通讯录", aliasName = "取证手机")
		if (alias != null && !alias.isEmpty()) {
			indexAnnotation.append(", aliasName = \"").append(alias.get(0)).append("\"");
		}
		return indexAnnotation.append(")").toString();
	}
	
	// ===== 构建相关类名 =====
	
	/**
	 * 获取生成 Java 文件名。
	 */
	public String getEntityJavaFileName() {
		String entityJavaFileName = name;
		
		IndexConfig indexConfig = globalConfig.getIndexConfig();
		
		String mapping = indexConfig.getMapping(name);
		if (StringUtil.isNotBlank(mapping)) {
			entityJavaFileName = mapping;
		}
		
		String indexPrefix = indexConfig.getIndexPrefix();
		if (indexPrefix != null) {
			String[] indexPrefixes = indexPrefix.split(",");
			for (String index : indexPrefixes) {
				String trimPrefix = index.trim();
				if (!trimPrefix.isEmpty() && name.startsWith(trimPrefix)) {
					entityJavaFileName = name.substring(trimPrefix.length());
					break;
				}
			}
		}
		return StringUtil.firstCharToUpperCase(StringUtil.underlineToCamel(entityJavaFileName));
	}
	
	/**
	 * 构建 entity 的 Class 名称。
	 */
	public String buildEntityClassName() {
		return getEntityJavaFileName();
	}
	
	/**
	 * 构建 mapper 的 Class 名称。
	 */
	public String buildMapperClassName() {
		String entityJavaFileName = getEntityJavaFileName();
		MapperConfig mapperConfig = globalConfig.getMapperConfig();
		return mapperConfig.getClassPrefix() + entityJavaFileName + mapperConfig.getClassSuffix();
	}
	
	/**
	 * 构建 extends 继承。
	 */
	public String buildExtends() {
		EntityConfig entityConfig = globalConfig.getEntityConfig();
		if (entityConfig.getSuperClass() != null) {
			return " extends " + entityConfig.getSuperClass().getSimpleName();
		} else {
			return "";
		}
	}
	
	/**
	 * 构建 implements 实现。
	 */
	public String buildImplements() {
		Class<?>[] entityInterfaces = globalConfig.getEntityConfig().getImplInterfaces();
		if (entityInterfaces != null && entityInterfaces.length > 0) {
			return " implements " + Arrays.stream(entityInterfaces).map(Class::getSimpleName)
					.collect(Collectors.joining(", "));
		} else {
			return "";
		}
	}
	
	@Override
	public String toString() {
		return "Table{" + "name='" + name + '\'' + ", columns=" + columns + '}';
	}
	
}
