package com.example.demo.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Target {
	
	@Id
	ObjectId _id;

	String type;
	String targetName;
	String description;
	String location;
	String filename;
	String userId;
	String password;
	public Target(ObjectId _id, String type, String targetName, String description, String location, String filename,
			String userId, String password) {
		super();
		this._id = _id;
		this.type = type;
		this.targetName = targetName;
		this.description = description;
		this.location = location;
		this.filename = filename;
		this.userId = userId;
		this.password = password;
	}
	public ObjectId get_id() {
		return _id;
	}
	public void set_id(ObjectId _id) {
		this._id = _id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTargetName() {
		return targetName;
	}
	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "Target [_id=" + _id + ", type=" + type + ", targetName=" + targetName + ", description=" + description
				+ ", location=" + location + ", filename=" + filename + ", userId=" + userId + ", password=" + password
				+ "]";
	}
	

	
	
}
