package com.easyes.config;

import java.io.Serializable;

/**
 * 字段的单独设置。
 */
@SuppressWarnings({"rawtypes", "UnusedReturnValue", "unused"})
public class ColumnConfig implements Serializable {
	
	
	/**
	 * 字段名称。
	 */
	private String columnName;
	
	/**
	 * <p>属性的类型。
	 *
	 * <p>原始类型直接写类型名称，例如：int/long/float/double/boolean<br/>
	 * 对象类型请写对应类的全限定名，例如：java.lang.String/com.example.enums.Gender
	 */
	private String propertyType;
	
	/**
	 * 属性的默认值，例如：long 类型默认值：0L，枚举类型默认值：Gender.MALE。
	 */
	private String propertyDefaultValue;
	
	/**
	 * 脱敏方式。
	 */
	private String maskType;
	
	/**
	 * 字段是否为主键。
	 */
	private boolean isPrimaryKey = false;
	
	
	public static ColumnConfig create() {
		return new ColumnConfig();
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	public String getColumnName() {
		return this.columnName;
	}
	
	public ColumnConfig setColumnName(String columnName) {
		this.columnName = columnName;
		return this;
	}
	
	public String getPropertyType() {
		return this.propertyType;
	}
	
	public ColumnConfig setPropertyType(String propertyType) {
		this.propertyType = propertyType;
		return this;
	}
	
	public String getPropertyDefaultValue() {
		return this.propertyDefaultValue;
	}
	
	public ColumnConfig setPropertyDefaultValue(String propertyDefaultValue) {
		this.propertyDefaultValue = propertyDefaultValue;
		return this;
	}
	
	public String getMaskType() {
		return this.maskType;
	}
	
	public ColumnConfig setMaskType(String maskType) {
		this.maskType = maskType;
		return this;
	}
	
	public boolean isPrimaryKey() {
		return this.isPrimaryKey;
	}
	
	public ColumnConfig setPrimaryKey(boolean primaryKey) {
		this.isPrimaryKey = primaryKey;
		return this;
	}
	
	public static final class Builder {
		
		private final ColumnConfig columnConfig;
		
		private Builder() {
			this.columnConfig = new ColumnConfig();
		}
		
		public Builder columnName(String columnName) {
			this.columnConfig.setColumnName(columnName);
			return this;
		}
		
		public Builder propertyType(String propertyType) {
			this.columnConfig.setPropertyType(propertyType);
			return this;
		}
		
		public Builder propertyDefaultValue(String propertyDefaultValue) {
			this.columnConfig.setPropertyDefaultValue(propertyDefaultValue);
			return this;
		}
		
		public Builder maskType(String maskType) {
			this.columnConfig.setMaskType(maskType);
			return this;
		}
		
		public ColumnConfig build() {
			return this.columnConfig;
		}
		
	}
	
}
