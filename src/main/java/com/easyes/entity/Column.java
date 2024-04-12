package com.easyes.entity;

import com.easyes.config.ColumnConfig;
import com.easyes.utils.StringUtil;
import com.easyes.version.EsVersionClient;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * 索引里面的列信息。
 */
public class Column {
	
	/**
	 * 名称。
	 */
	@Getter
	private String name;
	
	/**
	 * 属性名称。
	 */
	@Getter
	private String property;
	
	/**
	 * java属性类型。
	 */
	@Getter
	private String propertyType;
	
	/**
	 * 是否为主键。
	 */
	private boolean isPrimaryKey = false;
	
	
	/**
	 * es的字段类型，比如 keyword/long 等
	 */
	@Setter
	@Getter
	private String rawType;
	
	/**
	 * 字段配置。
	 */
	private ColumnConfig columnConfig;
	
	@Setter
	private EsVersionClient esVersionClient;
	
	/**
	 * 是否需要生成 @IndexField 注解。
	 */
	@Setter
	private boolean needGenIndexFieldAnnotation = false;
	
	/**
	 * IndexField 的参数。
	 */
	private String onIndexFieldValue;
	
	/**
	 * importClass为类的全限定名
	 */
	private static void addImportClass(Set<String> importClasses, String importClass) {
		// 不包含“.”则认为是原始类型，不需要import
		// lang包不需要显式导入
		if (importClass.contains(".") && !importClass.startsWith("java.lang.")) {
			importClasses.add(importClass);
		}
	}
	
	public void setName(String name) {
		this.name = name;
		this.property = buildPropertyName();
	}
	
	public void setPropertyType(String propertyType) {
		if (StringUtil.isBlank(propertyType)) {
			return;
		}
		if (Objects.isNull(esVersionClient)) {
			return;
		}
		this.propertyType = esVersionClient.esType2JavaType(propertyType);
	}
	
	public void setOnIndexFieldValue(String onIndexFieldValue) {
		if (StringUtil.isBlank(propertyType)) {
			return;
		}
		if (Objects.isNull(esVersionClient)) {
			return;
		}
		this.onIndexFieldValue = esVersionClient.esType2FieldType(propertyType);
	}
	
	public boolean isPrimaryKey() {
		return isPrimaryKey;
	}
	
	public void setPrimaryKey(boolean primaryKey) {
		isPrimaryKey = primaryKey;
	}
	
	public String getterMethod() {
		return "get" + StringUtil.firstCharToUpperCase(property);
	}
	
	public String setterMethod() {
		return "set" + StringUtil.firstCharToUpperCase(property);
	}
	
	public String getPropertyDefaultValue() {
		return columnConfig.getPropertyDefaultValue();
	}
	
	public String buildPropertyName() {
		String entityJavaFileName = name;
		return StringUtil.firstCharToLowerCase(StringUtil.underlineToCamel(entityJavaFileName));
	}
	
	public Set<String> getImportClasses() {
		Set<String> importClasses = new LinkedHashSet<>();
		
		addImportClass(importClasses, propertyType);
		
		if (isPrimaryKey) {
			addImportClass(importClasses, "org.dromara.easyes.annotation.IndexId");
		}
		if (onIndexFieldValue != null || needGenIndexFieldAnnotation) {
			addImportClass(importClasses, "org.dromara.easyes.annotation.IndexField");
			if (onIndexFieldValue != null){
				addImportClass(importClasses, "org.dromara.easyes.annotation.rely.FieldType");
			}
		}
		return importClasses;
	}
	
	private void addComma(StringBuilder annotations, boolean needComma) {
		if (needComma) {
			annotations.append(", ");
		}
	}
	
	public String getPropertySimpleType() {
		//		if (columnConfig.getPropertyType() != null) {
		//			if (!columnConfig.getPropertyType().contains(".")) {
		//				return columnConfig.getPropertyType();
		//			}
		//			return StringUtil.substringAfterLast(columnConfig.getPropertyType(), ".");
		//		} else {
		//			return StringUtil.substringAfterLast(propertyType, ".");
		//		}
		return StringUtil.substringAfterLast(propertyType, ".");
	}
	
	public String buildAnnotations() {
		StringBuilder annotations = new StringBuilder();
		
		//@IndexId 的注解
		if (isPrimaryKey) {
			annotations.append("@IndexId");
		}
		
		//@IndexField 注解
		if (onIndexFieldValue != null || needGenIndexFieldAnnotation) {
			annotations.append("@IndexField(");
			boolean needComma = false;
			if (needGenIndexFieldAnnotation) {
				annotations.append("value = \"").append(name).append("\"");
				needComma = true;
			}
			
			if (onIndexFieldValue != null) {
				addComma(annotations, needComma);
				annotations.append("fieldType = ").append("FieldType.").append(onIndexFieldValue);
			}
			annotations.append(")");
		}
		
		//@MultiIndexField 注解
		//@MultiIndexField(
		//            mainIndexField = @IndexField(value = FpAddressBookIndexDef.APP, fieldType = KEYWORD),
		//            otherIndexFields = @InnerIndexField(suffix = FIELD_PINYIN, fieldType = TEXT, analyzer = PINYIN_ANALYZER, searchAnalyzer = PINYIN_ANALYZER)
		//    )
		//		if (columnConfig.getMaskType() != null) {
		//			annotations.append("@ColumnMask(\"").append(columnConfig.getMaskType()).append("\")");
		//		}
		
		return annotations.toString();
	}
	
	@Override
	public String toString() {
		return "Column{" + "name='" + name + '\'' + ", className='" + propertyType + '\'' + '}';
	}
	
}
