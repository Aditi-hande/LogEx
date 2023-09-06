package com.example.demo.model;

import java.util.HashSet;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Schedule {

	@Id
	ObjectId _id;
	String name;
	String group;
	String cron;
	private HashSet<String> queryList = new HashSet<String>();
	public Schedule(ObjectId _id, String name, String group, String cron, HashSet<String> queryList) {
		super();
		this._id = _id;
		this.name = name;
		this.group = group;
		this.cron = cron;
		this.queryList = queryList;
	}
	public ObjectId get_id() {
		return _id;
	}
	public void set_id(ObjectId _id) {
		this._id = _id;
	}
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getCron() {
		return cron;
	}
	public void setCron(String cron) {
		this.cron = cron;
	}
	public HashSet<String> getQueryList() {
		return queryList;
	}
	public void setQueryList(HashSet<String> queryList) {
		this.queryList = queryList;
	}
	@Override
	public String toString() {
		return "Schedule [_id=" + _id + ", name=" + name + ", group=" + group + ", cron=" + cron + ", queryList="
				+ queryList + "]";
	}
	
	
}
