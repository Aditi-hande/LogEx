package com.example.demo.implementation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.model.LogLine;
import com.example.demo.model.Mask;
import com.example.demo.repository.LogRepository;
import com.example.demo.service.MaskService;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class LogRepositoryImplementation {
	
	static Logger LOG = LogManager.getLogger(LogRepositoryImplementation.class);
	
	@Autowired
	private RestHighLevelClient client ;
	@Autowired
	private MaskService maskService;
	@Autowired
	private LogRepository logRepository;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	public Page<LogLine> findAll() {
		Pageable limit = PageRequest.of(10,20);
		return logRepository.findAll(limit);
	}
	
	public List<LogLine> findById(String id) {
		return logRepository.findBy_id(id);
	}
	
	private String maskResult(String mask,String original,String replaceString) {
		return original.replaceAll(mask,replaceString);
	}
	
	public void saveToFile(String sourcefilename,List<LogLine> loglines) throws IOException{
		PrintWriter pw = null;
		//First Try Block is to Save To Text File
		LOG.info("Saving to file");
		try {
			
			pw = new PrintWriter(new File(sourcefilename+".txt"));
			for(LogLine l: loglines) {
				pw.println(l);
			}
			
		}
		catch (FileNotFoundException fe) {
           LOG.error("The file does not exist"+sourcefilename);
           fe.printStackTrace();
		}
		catch (IndexOutOfBoundsException iob) {
			LOG.error("Caught IndexOutOfBoundsException while saving to file");
			iob.getStackTrace();
		}finally {
			if(pw!=null) {
				LOG.info("Closing PrintWriter");
				pw.close();
			}
			else {
				LOG.info("PrintWriter not open");
			}
		}
		
		//saving to csv
		try {
			Writer writer = new FileWriter(sourcefilename+".csv");
	        ColumnPositionMappingStrategy<LogLine> mappingStrategy = new ColumnPositionMappingStrategy<LogLine>();
	        mappingStrategy.setType(LogLine.class);
		    StatefulBeanToCsv<LogLine> beanToCsv = new StatefulBeanToCsvBuilder<LogLine>(writer).build();
		    beanToCsv.write(loglines);
		    writer.close();
		}catch (Exception e) {
			LOG.error("Error saving file to csv");
			e.getStackTrace();
		}
		
		//trying to zip the file

		
	}
	//saving to zip
	public void savetozip(List<String> sourcefiles) throws IOException {
		FileOutputStream fos = null;
		FileInputStream fis = null;
		ZipOutputStream zos = null;
		ZipEntry zipEntry= null;
		File fileToZip = null;
		try {
			LOG.info("Zipping File");
			String zipfilename = sourcefiles.get(0);
			int posOfLastDot  = zipfilename.lastIndexOf('.');
			if(posOfLastDot>0 && posOfLastDot<(zipfilename.length()-1)) { /*If . is not first or last character*/
				zipfilename = zipfilename.substring(0,posOfLastDot);
			}
			LOG.info("zipfilename "+zipfilename);
			fos = new FileOutputStream(zipfilename+".zip");
			zos = new ZipOutputStream(fos);
	        for(String srcfile:sourcefiles) {
		        fileToZip = new File(srcfile);
		        fis = new FileInputStream(fileToZip);
		        zipEntry = new ZipEntry(fileToZip.getName());
		        zos.putNextEntry(zipEntry);
		        byte[] bytes = new byte[1024];
	            int length;
	            while((length = fis.read(bytes)) >= 0) {
	            	zos.write(bytes, 0, length);
	            }
	            fis.close();
	        }
	        zos.close();
	        fos.close();
        
		}catch (FileNotFoundException ex) {
	        LOG.error("Some/all files don't exist "+ex.getMessage());
	        ex.printStackTrace();
		}
		catch (IndexOutOfBoundsException iob) {
			LOG.error("Caught IndexOutOfBoundsException");
			iob.printStackTrace();
		} catch (IOException io) {
			LOG.error("Caught an IOException");
			io.printStackTrace();
		}finally {
			if(fos!=null) {
				LOG.info("Closing FileOutputStream");
				fos.close();
			}
			else {
				LOG.error("FileStream is Open");
			}
			if(fis!=null) {
				LOG.info("Closing FileOutputStream");
				fis.close();
			}
			else {
				LOG.error("FileOutputStream is Open");
	
			}
			if(zos!=null) {
				LOG.info("Closing ZipOutputStream");
				zos.close();
			}else {
				LOG.error("ZipOutputStream is Open");
			}
		}
	}
	
	public List<LogLine> search(String jsonQuery,String index,ArrayList<String> maskList) throws IOException{
		SearchRequest searchRequest = new SearchRequest(index);
	    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
	    searchSourceBuilder.query(QueryBuilders.wrapperQuery(jsonQuery));
	    searchSourceBuilder.size(100);
	    searchRequest.source(searchSourceBuilder);

	    SearchResponse searchResponse;
		try {
			searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			SearchHits hits = searchResponse.getHits();
		    SearchHit[] searchHits = hits.getHits();
		      
	        List<LogLine> logs = Arrays.stream(searchHits)
	        		.map(e->{

						try {
							LogLine l = objectMapper.readValue(e.getSourceAsString(),LogLine.class);
							l.set_id(e.getId());
							if(maskList==null || maskList.size()==0) {
								LOG.info("No masks found to apply");
							}
							else {
								maskList.forEach(x->{
									 Mask m = maskService.getByMaskName(x);
									 String _mask = m.getRegex();
									 String replaceString = m.getReplaceString();
									 l.setDetail(this.maskResult(_mask,l.getDetail(),replaceString));
								});
							}
							return l;
						} catch (JsonMappingException e1) {
							e1.printStackTrace();
						} catch (JsonProcessingException e1) {
							e1.printStackTrace();
						}
						return null;
					})
					.collect(Collectors.toList());

	        return logs;
	        } catch (IOException ex) {
	        	LOG.error("Could not post {} to ES", jsonQuery);
			ex.printStackTrace();
		}
	    return null;

	}
}
