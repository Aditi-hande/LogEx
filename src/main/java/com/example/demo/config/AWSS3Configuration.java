package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3control.AWSS3ControlAsyncClientBuilder;

@Configuration
public class AWSS3Configuration {
	
	private String accessKey;
	private String secretKey;
	private String region;

//	@Bean
//	public AmazonS3 createS3Client() {
//		AWSCredentials credentials= new BasicAWSCredentials(this.accessKey, this.secretKey);
//		return AmazonS3ClientBuilder.standard()
//				.withCredentials(new AWSStaticCredentialsProvider(credentials))
//				.withRegion(region)
//				.build();
//		
//	}
 }
