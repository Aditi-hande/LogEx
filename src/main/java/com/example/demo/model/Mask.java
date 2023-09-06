package com.example.demo.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;


@Document
public class Mask {
	@Id
	ObjectId _id;
	String maskName;
	String regex;
	String replaceString;
	String description;
	
	public Mask() {}
	public Mask(String maskName, String regex, String replaceString, String description) {
		super();
		this.maskName = maskName;
		this.regex = regex;
		this.replaceString = replaceString;
		this.description = description;
	}
	
	
	public ObjectId get_id() {
		return _id;
	}
	public void set_id(ObjectId _id) {
		this._id = _id;
	}
	public String getMaskName() {
		return maskName;
	}
	public void setMaskName(String maskName) {
		this.maskName = maskName;
	}
	public String getRegex() {
		return regex;
	}
	public void setRegex(String regex) {
		this.regex = regex;
	}
	public String getReplaceString() {
		return replaceString;
	}
	public void setReplaceString(String replaceString) {
		this.replaceString = replaceString;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "Mask [_id=" + _id + ", maskName=" + maskName + ", regex=" + regex + ", replaceString=" + replaceString
				+ ", description=" + description + "]";
	}
	
}
