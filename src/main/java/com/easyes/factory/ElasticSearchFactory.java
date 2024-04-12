package com.easyes.factory;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * @author wenzb
 **/
public class ElasticSearchFactory {
	static String ip = "192.168.11.248";
	static int port = 9201;
	static RestHighLevelClient restHighLevelClient = null;
	
	public static  RestHighLevelClient initClient(){
		//这里的RestClient.builder(new HttpHost(ip,port),.........)支持多个httphost连接，也就是支持连接多个elasticsearch
		return restHighLevelClient = new RestHighLevelClient(RestClient.builder(new HttpHost(ip,port)));
	}
}
