package com.easyes.version;

import com.easyes.entity.Index;
import com.easyes.factory.ElasticSearchFactory;
import org.elasticsearch.action.admin.indices.alias.get.GetAliasesRequest;
import org.elasticsearch.client.GetAliasesResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.cluster.metadata.AliasMetaData;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.settings.Settings;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author wenzb
 **/
public class Es_7_14 implements EsVersionClient {
	
	protected RestHighLevelClient client;
	
	public Es_7_14() {
		this.client = ElasticSearchFactory.initClient();
	}
	
	@Override
	public void close() {
		if (Objects.nonNull(this.client)) {
			try {
				client.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	@Override
	public Collection<String> allIndexes() {
		// 查询有哪些索引
		GetAliasesResponse getIndexResponse;
		try {
			getIndexResponse = client.indices().getAlias(new GetAliasesRequest(), RequestOptions.DEFAULT);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		Map<String, Set<AliasMetaData>> map = getIndexResponse.getAliases();
		return map.keySet();
	}
	
	@Override
	public String esType2FieldType(String esType) {
		switch (esType) {
			case "geo_point":
				return "GEO_POINT";
			case "date":
				return "DATE";
			case "long":
				return "LONG";
			case "double":
				return "DOUBLE";
			case "keyword":
			default:
				return "KEYWORD";
		}
	}
	
	@Override
	public String esType2JavaType(String esType) {
		switch (esType) {
			case "keyword":
			case "geo_point":
				return String.class.getName();
			case "date":
				return LocalDate.class.getName();
			case "dateTime":
				return LocalDateTime.class.getName();
			case "long":
				return Long.class.getName();
			case "double":
				return Double.class.getName();
			default:
				return esType;
		}
	}
	
	@Override
	public void buildIndex(Index index) {
		try {
			// 构建索引列
			String indexName = index.getName();
			GetIndexRequest request = new GetIndexRequest(indexName);
			GetIndexResponse response = client.indices().get(request, RequestOptions.DEFAULT);
			
			Map<String, List<AliasMetaData>> aliases = response.getAliases();
			index.setAlias(aliases.get(indexName).stream().map(AliasMetaData::getAlias).collect(Collectors.toList()));
			
			Map<String, Settings> settings = response.getSettings();
			index.setSettings(settings.get(indexName));
			
			Map<String, MappingMetaData> mappings = response.getMappings();
			index.setMappingMetaData(mappings.get(indexName));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
