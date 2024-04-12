package com.easyes;

import com.easyes.config.GlobalConfig;
import com.easyes.entity.Index;
import com.easyes.generator.GeneratorFactory;
import com.easyes.generator.IGenerator;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author wenzb
 **/
public class CodeGenerate {
	
	protected GlobalConfig globalConfig;
	
	public CodeGenerate(GlobalConfig globalConfig) {
		this.globalConfig = globalConfig;
	}
	
	public void generate() {
		generate(globalConfig.getEsVersionClient().allIndexes());
	}
	
	public void generate(Collection<String> indexNames) {
		if (Objects.isNull(indexNames)) {
			return;
		}
		List<Index> indexes = indexNames.stream().map(name -> {
			Index index = new Index();
			index.setName(name);
			index.setGlobalConfig(globalConfig);
			return index;
		}).collect(Collectors.toList());
		generate(indexes);
	}
	
	public void generate(List<Index> indexes) {
		if (indexes == null || indexes.isEmpty()) {
			System.err.printf("index %s not found.%n", globalConfig.getGenerateIndexes());
			return;
		} else {
			System.out.printf("find indexes: %s%n", indexes.stream().map(Index::getName).collect(Collectors.toSet()));
		}
		for (Index index : indexes) {
			index.build();
			Collection<IGenerator> generators = GeneratorFactory.getGenerators();
			for (IGenerator generator : generators) {
				generator.generate(index, globalConfig);
			}
		}
		globalConfig.close();
		System.out.println("Code is generated successfully.");
	}
	
}
