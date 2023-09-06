package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(indexName = "logs-applicationframework")
public class LogLine {
	
	@Id
	private String _id;
	private String host;
	private String version,path,timestamp;
    private String type;
	private String LEVEL;
    private String detail;
    private String message;
	private String year,month,day,time;
  
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	
	  public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		
	@JsonProperty("@version")
	public String getVersion() {
		return version;
	}
	
	@JsonProperty("@version")
	public void setVersion(String version) {
		this.version = version;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	@JsonProperty("@timestamp")
	public String getTimestamp() {
		return timestamp;
	}
	@JsonProperty("@timestamp")
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	@JsonProperty("LEVEL")
	public String getLEVEL() {
		return LEVEL;
	}
	@JsonProperty("LEVEL")
	public void setLEVEL(String lEVEL) {
		LEVEL = lEVEL;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "LogLine [_id=" + _id + ", host=" + host + ", version=" + version + ", path=" + path + ", timestamp="
				+ timestamp + ", LEVEL=" + LEVEL + ", detail=" + detail + ", message=" + message + ", type=" + type
				+ ", year=" + year + ", month=" + month + ", day=" + day + ", time=" + time + "]";
	}

    
}