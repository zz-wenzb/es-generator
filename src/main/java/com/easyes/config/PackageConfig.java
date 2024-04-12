package com.easyes.config;

import java.io.Serializable;
import java.util.Objects;

/**
 * 生成软件包的配置。
 *
 * @author wenzb
 */
@SuppressWarnings("unused")
public class PackageConfig implements Serializable {

    /**
     * 代码生成目录。
     */
    private String sourceDir;

    /**
     * 根包。
     */
    private String basePackage = "com.easyes";

    /**
     * Entity 所在包。
     */
    private String entityPackage;

    /**
     * Mapper 所在包。
     */
    private String mapperPackage;

    /**
     * 获取生成目录。
     */
    public String getSourceDir() {
        if (Objects.isNull(sourceDir) || sourceDir.isEmpty()) {
            return System.getProperty("user.dir") + "/src/main/java";
        }
        return sourceDir;
    }

    /**
     * 设置生成目录。
     */
    public PackageConfig setSourceDir(String sourceDir) {
        this.sourceDir = sourceDir;
        return this;
    }

    /**
     * 获得根包路径。
     */
    public String getBasePackage() {
        return basePackage;
    }

    /**
     * 设置根包路径。
     */
    public PackageConfig setBasePackage(String basePackage) {
        this.basePackage = basePackage;
        return this;
    }

    /**
     * 获取实体类层包路径。
     */
    public String getEntityPackage() {
        if (Objects.isNull(entityPackage) || entityPackage.isEmpty()) {
            return basePackage.concat(".entity");
        }
        return entityPackage;
    }

    /**
     * 设置实体类层包路径。
     */
    public PackageConfig setEntityPackage(String entityPackage) {
        this.entityPackage = entityPackage;
        return this;
    }

    /**
     * 获取映射层包路径。
     */
    public String getMapperPackage() {
        if (Objects.isNull(mapperPackage) || mapperPackage.isEmpty()) {
            return basePackage.concat(".mapper");
        }
        return mapperPackage;
    }

    /**
     * 设置映射层包路径。
     */
    public PackageConfig setMapperPackage(String mapperPackage) {
        this.mapperPackage = mapperPackage;
        return this;
    }
}
