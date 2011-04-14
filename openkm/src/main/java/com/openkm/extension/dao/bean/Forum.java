package com.openkm.extension.dao.bean;

import java.io.Serializable;
import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.Set;

public class Forum implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private String description;
	private Calendar date;
	private String lastUser;
	private Calendar lastDate;
	private int numTopics;
	private int numPosts;
	private boolean active;
	private Set<ForumTopic> topics = new LinkedHashSet<ForumTopic>();
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}

	public Set<ForumTopic> getTopics() {
		return topics;
	}

	public void setTopics(Set<ForumTopic> topics) {
		this.topics = topics;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public String getLastUser() {
		return lastUser;
	}

	public void setLastUser(String lastUser) {
		this.lastUser = lastUser;
	}

	public Calendar getLastDate() {
		return lastDate;
	}

	public void setLastDate(Calendar lastDate) {
		this.lastDate = lastDate;
	}
	
	public int getNumTopics() {
		return numTopics;
	}

	public void setNumTopics(int numTopics) {
		this.numTopics = numTopics;
	}

	public int getNumPosts() {
		return numPosts;
	}

	public void setNumPosts(int numPosts) {
		this.numPosts = numPosts;
	}
}
