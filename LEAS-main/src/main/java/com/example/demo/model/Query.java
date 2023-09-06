package com.example.demo.model;

import java.util.ArrayList;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Query {
	@Id
	ObjectId _id;
	String product;
	String queryName;
	String description;
	String queryText;
	String elasticSearchIndex;
	ArrayList<String> maskList= new ArrayList<String> ();
	String targetName;
	String scheduleName;
	public Query(ObjectId _id, String product, String queryName, String description, String queryText,
			String elasticSearchIndex, ArrayList<String> maskList, String targetName, String scheduleName) {
		super();
		this._id = _id;
		this.product = product;
		this.queryName = queryName;
		this.description = description;
		this.queryText = queryText;
		this.elasticSearchIndex = elasticSearchIndex;
		this.maskList = maskList;
		this.targetName = targetName;
		this.scheduleName = scheduleName;
	}
	public ObjectId get_id() {
		return _id;
	}
	public void set_id(ObjectId _id) {
		this._id = _id;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getQueryName() {
		return queryName;
	}
	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getQueryText() {
		return queryText;
	}
	public void setQueryText(String queryText) {
		this.queryText = queryText;
	}
	public String getElasticSearchIndex() {
		return elasticSearchIndex;
	}
	public void setElasticSearchIndex(String elasticSearchIndex) {
		this.elasticSearchIndex = elasticSearchIndex;
	}
	public ArrayList<String> getMaskList() {
		return maskList;
	}
	public void setMaskList(ArrayList<String> maskList) {
		this.maskList = maskList;
	}
	public String getTargetName() {
		return targetName;
	}
	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}
	public String getScheduleName() {
		return scheduleName;
	}
	public void setScheduleName(String scheduleName) {
		this.scheduleName = scheduleName;
	}
	@Override
	public String toString() {
		return "Query [_id=" + _id + ", product=" + product + ", queryName=" + queryName + ", description="
				+ description + ", queryText=" + queryText + ", elasticSearchIndex=" + elasticSearchIndex
				+ ", maskList=" + maskList + ", targetName=" + targetName + ", scheduleName=" + scheduleName + "]";
	}
	
	
	
	


	
}
