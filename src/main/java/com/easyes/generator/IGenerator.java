package com.easyes.generator;

import com.easyes.config.GlobalConfig;
import com.easyes.entity.Index;

/**
 * 文件生成器接口。
 *
 * @author wenzb
 */
public interface IGenerator {

    /**
     * 获取模板文件位置。
     *
     * @return 路径
     */
    String getTemplatePath();

    /**
     * 设置模板文件位置。
     *
     * @param templatePath 模板文件位置
     */
    void setTemplatePath(String templatePath);

    /**
     * 根据模板生成文件。
     *
     * @param index        索引
     * @param globalConfig 全局配置
     */
    void generate(Index index, GlobalConfig globalConfig);

}
