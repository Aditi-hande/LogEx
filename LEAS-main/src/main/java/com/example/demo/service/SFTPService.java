package com.example.demo.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.demo.implementation.LogRepositoryImplementation;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

@Service
public class SFTPService {
	
	static Logger LOG = LogManager.getLogger(SFTPService.class);
	private String remoteHost = "127.0.0.1";
    private String knownHostsFileLoc = "/home/maitraya/.ssh/known_hosts";
	@Value("${sftp.sessionTimeout}")
	private Integer sessionTimeout;
	@Value("${sftp.channelTimeout}")
	private Integer channelTimeout;
	
    private ChannelSftp setupJsch(String username,String password) throws JSchException {
    	try {
	        JSch jsch = new JSch();
	        Session jschSession = jsch.getSession(username, remoteHost);
	        jschSession.setConfig("StrictHostKeyChecking", "no");
	        jschSession.setPassword(password);
	        jsch.setKnownHosts(knownHostsFileLoc);
	        jschSession.connect(sessionTimeout);
	        Channel channel = jschSession.openChannel("sftp");
	        return (ChannelSftp) channel;
    	}catch(JSchException jex) {
    		LOG.error("Error in creating channel "+jex.getMessage());
    	}
    	return null;
    }
    
    private void disconnectChannelSftp(ChannelSftp channelSftp) {
		try {
			if( channelSftp == null) 
				return;
			
			if(channelSftp.isConnected()) 
				channelSftp.disconnect();
			
			if(channelSftp.getSession() != null) 
				channelSftp.getSession().disconnect();
			
		} catch(Exception ex) {
			LOG.error("SFTP disconnect error", ex);
		}
	}
    
    public boolean jschUpload(String username,String password,String localFile,String remotefile) throws JSchException, SftpException {
        ChannelSftp channelSftp = setupJsch(username,password);
        if (channelSftp==null) {
        	return false;
        }
        try {
//        	LOG.info("Curr directory ="+channelSftp.pwd());
        	channelSftp.connect(channelTimeout);
        	LOG.info("local file "+localFile);
        	LOG.info("remote file "+remotefile);
        	remotefile = "internship/LEAS/files2/"+localFile.substring(6);
        	channelSftp.put(localFile,remotefile);
        	return true;
        }catch(SftpException ex) {
        	LOG.error("Error uploading file ",ex);
        }finally{
        	disconnectChannelSftp(channelSftp);
        }
        return false;
    }
}
