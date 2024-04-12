package com.easyes.version;

import com.easyes.entity.Index;

import java.util.Collection;

/**
 * @author wenzb
 **/
public interface EsVersionClient {
	
	String esType2JavaType(String esType);

	void buildIndex(Index index);
	
	void close();
	
	Collection<String> allIndexes();
	
	String esType2FieldType(String esType);
}
