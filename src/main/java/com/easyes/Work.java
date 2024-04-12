package com.easyes;

import com.easyes.config.GlobalConfig;

import java.util.Collections;

/**
 * @author wenzb
 **/
public class Work {
	
	public static void main(String[] args) {
		
		GlobalConfig globalConfig = new GlobalConfig();
		globalConfig.setEntityOverwriteEnable(true);
		globalConfig.setEntityWithLombok(true);
		globalConfig.putFieldMapping("es-筛选时间", "create_time");
		globalConfig.putIndexMapping("社会数据-涉案-案件嫌疑人信息-0", "sd_involved_case_suspect_es");
		globalConfig.setEsVersion("7.4.0");
		
		CodeGenerate generate = new CodeGenerate(globalConfig);
		generate.generate(Collections.singletonList("社会数据-涉案-案件嫌疑人信息-0"));
	}
}
