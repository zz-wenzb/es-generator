package com.easyes.generator;

import com.easyes.generator.impl.EntityGenerator;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static com.easyes.constant.GenerateTypeConst.ENTITY;

/**
 * 代码生成器工厂，用于创建各种类型文件的生成。
 *
 * @see com.easyes.constant.GenerateTypeConst
 */
public class GeneratorFactory {

    private static final Map<String, IGenerator> generators = new HashMap<>();

    static {
        registerGenerator(ENTITY, new EntityGenerator());
//        registerGenerator(MAPPER, new MapperGenerator());
//        registerGenerator(PACKAGE_INFO, new PackageInfoGenerator());
    }

    private GeneratorFactory() {
    }

    /**
     * 获取指定类型文件的生成器。
     *
     * @param genType 生成类型
     * @return 该类型的文件生成器
     */
    public static IGenerator getGenerator(String genType) {
        return generators.get(genType);
    }

    /**
     * 获取所有的文件生成器。
     *
     * @return 所有的文件生成器
     */
    public static Collection<IGenerator> getGenerators() {
        return generators.values();
    }

    /**
     * 注册文件生成器。
     *
     * @param name      生成器名称
     * @param generator 生成器
     */
    public static void registerGenerator(String name, IGenerator generator) {
        generators.put(name, generator);
    }

}
