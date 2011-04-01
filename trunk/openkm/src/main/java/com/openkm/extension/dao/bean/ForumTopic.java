package com.openkm.extension.dao.bean;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class ForumTopic implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String title;
	private Set<ForumPost> posts = new HashSet<ForumPost>();
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public Set<ForumPost> getPosts() {
		return posts;
	}
	
	public void setPosts(Set<ForumPost> posts) {
		this.posts = posts;
	}
}
