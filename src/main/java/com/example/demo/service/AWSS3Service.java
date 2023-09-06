package com.example.demo.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class AWSS3Service {
	
	
	private String bucketName;
	
//	@Autowired
//	private AmazonS3 s3Client;
	
	
//	public Boolean uploadFile(File file) {
//		String filename = file.getName();
//		s3Client.putObject(new PutObjectRequest(bucketName, filename, file));
//		return true;
//	}
	
}
