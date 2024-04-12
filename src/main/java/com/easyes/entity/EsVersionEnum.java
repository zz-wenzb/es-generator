package com.easyes.entity;

import com.easyes.version.EsVersionClient;
import com.easyes.version.Es_7_14;

import java.util.Arrays;

/**
 * @author wenzb
 **/
public enum EsVersionEnum {
	
	ES_7_4_0("7.4.0", new Es_7_14()),
	;
	
	private final String version;
	
	private final EsVersionClient esVersionClient;
	
	EsVersionEnum(String version, EsVersionClient esVersionClient) {
		this.version = version;
		this.esVersionClient = esVersionClient;
	}
	
	public static EsVersionClient of(String version) {
		return Arrays.stream(values()).filter(v -> v.version.equals(version)).map(v -> v.esVersionClient).findAny()
				.orElseThrow(() -> new RuntimeException("无法获取客户端"));
	}
}
