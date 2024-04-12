package com.easyes.config;

import com.easyes.version.EsVersionClient;

import java.util.Map;
import java.util.Set;

/**
 * @author wenzb
 **/
public class GlobalConfig {
	
	// ======================= 必要配置 ==================================
	private final JavadocConfig javadocConfig;
	
	private final PackageConfig packageConfig;
	
	private final IndexConfig indexConfig;
	
	private final TemplateConfig templateConfig;
	
	// ======================= 可选配置 ==================================
	private EntityConfig entityConfig;
	
	private MapperConfig mapperConfig;
	
	public GlobalConfig() {
		this.javadocConfig = new JavadocConfig();
		this.packageConfig = new PackageConfig();
		this.indexConfig = new IndexConfig();
		this.templateConfig = new TemplateConfig();
	}
	
	public JavadocConfig getJavadocConfig() {
		return javadocConfig;
	}
	
	public PackageConfig getPackageConfig() {
		return packageConfig;
	}
	
	public IndexConfig getIndexConfig() {
		return indexConfig;
	}
	
	/**
	 * @see IndexConfig#getGenerateIndexes()
	 */
	public Set<String> getGenerateIndexes() {
		return getIndexConfig().getGenerateIndexes();
	}
	
	public TemplateConfig getTemplateConfig() {
		return templateConfig;
	}
	
	public EntityConfig getEntityConfig() {
		if (entityConfig == null) {
			entityConfig = new EntityConfig();
		}
		return entityConfig;
	}
	
	public MapperConfig getMapperConfig() {
		if (mapperConfig == null) {
			mapperConfig = new MapperConfig();
		}
		return mapperConfig;
	}
	
	/**
	 * @see EntityConfig#isOverwriteEnable()
	 */
	public boolean isEntityOverwriteEnable() {
		return getEntityConfig().isOverwriteEnable();
	}
	
	/**
	 * @see EntityConfig#setOverwriteEnable(boolean)
	 */
	public void setEntityOverwriteEnable(boolean entityOverwriteEnable) {
		getEntityConfig().setOverwriteEnable(entityOverwriteEnable);
	}
	
	/**
	 * @see EntityConfig#isWithLombok()
	 */
	public boolean isEntityWithLombok() {
		return getEntityConfig().isWithLombok();
	}
	
	/**
	 * @see EntityConfig#setWithLombok(boolean)
	 */
	public void setEntityWithLombok(boolean entityWithLombok) {
		getEntityConfig().setWithLombok(entityWithLombok);
	}
	
	
	public void putFieldMapping(String field, String mapping) {
		getEntityConfig().putFieldMapping(field, mapping);
	}
	
	public void putAllFieldMapping(Map<String, String> map) {
		getEntityConfig().putAllFieldMapping(map);
	}
	
	public String getFieldMapping(String field) {
		return getEntityConfig().getMapping(field);
	}
	
	public void putIndexMapping(String index, String mapping) {
		getIndexConfig().putNameMapping(index, mapping);
	}
	
	public void putAllIndexMapping(Map<String, String> map) {
		getIndexConfig().putAllNameMapping(map);
	}
	
	public String getEsVersion() {
		return getIndexConfig().getVersion();
	}
	
	public void setEsVersion(String version) {
		getIndexConfig().setVersion(version);
	}
	
	public EsVersionClient getEsVersionClient() {
		return getIndexConfig().getEsVersionClient();
	}
	
	public void close() {
		getIndexConfig().getEsVersionClient().close();
	}
}
