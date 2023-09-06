package com.example.demo.jobs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.demo.implementation.LogRepositoryImplementation;
import com.example.demo.model.LogLine;
import com.example.demo.model.Query;
import com.example.demo.model.Target;
import com.example.demo.service.AWSS3Service;
import com.example.demo.service.QueryService;
import com.example.demo.service.SFTPService;
import com.example.demo.service.TargetService;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;


/*
 A class that implements Job interface. This will run the search query on specified index on specified time
 */
@Component
public class basicJob implements Job{
	
	static Logger LOG = LogManager.getLogger(basicJob.class);
	
	@Autowired
	LogRepositoryImplementation lri;
	@Autowired
	QueryService queryService ;
	@Autowired
	TargetService targetService;
	@Autowired
	SFTPService sftpService;
//	@Autowired
//	AWSS3Service awss3Service;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
	    JobDataMap map = context.getMergedJobDataMap();
	    Date firedTime = context.getFireTime();
		LOG.info("Executing job");
	    try {
			basicJobSearchMethod(map,firedTime);
		} catch (IOException e) {
			LOG.error("Error in executing job",e.getMessage());
			e.printStackTrace();
		}
	}
	
	private void basicJobSearchMethod(JobDataMap map,Date firedTime) throws IOException {
		
		HashSet<String> queryList = (HashSet<String>) map.get("query");

		queryList.forEach(x->{
			Query Q = queryService.getByQueryName(x);
			Target t = targetService.getByTargetName(Q.getTargetName());
			String query = Q.getQueryText();
			String index = Q.getElasticSearchIndex();
			ArrayList<String> maskList = Q.getMaskList();
			List<LogLine> logline=null;
			
			try {
				logline = lri.search(query, index,maskList);
			} catch (IOException e) {
				LOG.error("Error in search ",e.getMessage());
				e.printStackTrace();
			}
			
			if(t==null) {
				LOG.info("No targets associated with this query");
			}
			else {
				try {
					if(t.getFilename()==  null) {
						LOG.error("Filename is null");
						return;
					}
					String filename = t.getLocation()+"/"+t.getFilename()+"_"+firedTime.toString();
					LOG.info("Saving to local folder");
					lri.saveToFile(filename,logline);
					List<String> filesToZip= new ArrayList<String>();
					filesToZip.add(filename+".txt");
					filesToZip.add(filename+".csv");
					lri.savetozip(filesToZip);
					LOG.info("Uploading file to sftp");
					boolean status = sftpService.jschUpload(t.getUserId(), t.getPassword(), filename.substring(2)+".zip","./files2/");
					LOG.info("Upload status "+status);
				
				}catch (IOException e) {
					LOG.error("Error creating file "+e);
					e.printStackTrace();
				}catch(JSchException j) {
					LOG.error("Error in jsch "+j);
				}catch(SftpException s) {
					LOG.error("Error in sftp "+s);
				}
			}
        });
		}
}
