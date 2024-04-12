package com.easyes.template;

import java.io.File;
import java.util.Map;

/**
 * 模板引擎。
 */
@FunctionalInterface
public interface ITemplate {

    /**
     * 使用模板引擎生成代码。
     *
     * @param params           生成参数
     * @param templateFilePath 模板文件位置
     * @param generateFile     生成文件位置
     */
    void generate(Map<String, Object> params, String templateFilePath, File generateFile);

}
