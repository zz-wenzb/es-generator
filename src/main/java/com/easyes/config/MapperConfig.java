package com.easyes.config;

import java.io.Serializable;

/**
 * 生成 Mapper 的配置。
 *
 * @author wenzb
 */
@SuppressWarnings("unused")
public class MapperConfig implements Serializable {

    /**
     * Mapper 类的前缀。
     */
    private String classPrefix = "";

    /**
     * Mapper 类的后缀。
     */
    private String classSuffix = "Mapper";

    /**
     * 自定义 Mapper 的父类。
     */
//    private Class<?> superClass = BaseMapper.class;

    /**
     * 是否覆盖之前生成的文件。
     */
    private boolean overwriteEnable;

    /**
     * 是否添加 {@code @Mapper} 注解。
     */
    private boolean mapperAnnotation;

//    public String buildSuperClassImport() {
//        return superClass.getName();
//    }
//
//    public String buildSuperClassName() {
//        return superClass.getSimpleName();
//    }

    /**
     * 获取类前缀。
     */
    public String getClassPrefix() {
        return classPrefix;
    }

    /**
     * 设置类前缀。
     */
    public MapperConfig setClassPrefix(String classPrefix) {
        this.classPrefix = classPrefix;
        return this;
    }

    /**
     * 获取类后缀。
     */
    public String getClassSuffix() {
        return classSuffix;
    }

    /**
     * 设置类后缀。
     */
    public MapperConfig setClassSuffix(String classSuffix) {
        this.classSuffix = classSuffix;
        return this;
    }

//    /**
//     * 获取父类。
//     */
//    public Class<?> getSuperClass() {
//        return superClass;
//    }
//
//    /**
//     * 设置父类。
//     */
//    public MapperConfig setSuperClass(Class<?> superClass) {
//        this.superClass = superClass;
//        return this;
//    }

    /**
     * 是否覆盖原有文件。
     */
    public boolean isOverwriteEnable() {
        return overwriteEnable;
    }

    /**
     * 设置是否覆盖原有文件。
     */
    public MapperConfig setOverwriteEnable(boolean overwriteEnable) {
        this.overwriteEnable = overwriteEnable;
        return this;
    }

    /**
     * 是否添加 {@code @Mapper} 注解。
     */
    public boolean isMapperAnnotation() {
        return mapperAnnotation;
    }

    /**
     * 设置是否添加 {@code @Mapper} 注解。
     */
    public MapperConfig setMapperAnnotation(boolean mapperAnnotation) {
        this.mapperAnnotation = mapperAnnotation;
        return this;
    }

}
