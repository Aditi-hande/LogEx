package com.example.demo.config;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

@Configuration
public class ElasticsearchConfiguration extends AbstractElasticsearchConfiguration{
	
	@Override
	@Bean
 public RestHighLevelClient elasticsearchClient() {

	//final CredentialsProvider credentialsProvider =
	//	    new BasicCredentialsProvider();
	//	credentialsProvider.setCredentials(AuthScope.ANY,
	//	    new UsernamePasswordCredentials("elastic", "changeme"));
    final ClientConfiguration clientConfiguration = ClientConfiguration.builder()  
        .connectedTo("localhost:9200")
        .build();

    return RestClients.create(clientConfiguration).rest();                         
} 
}
