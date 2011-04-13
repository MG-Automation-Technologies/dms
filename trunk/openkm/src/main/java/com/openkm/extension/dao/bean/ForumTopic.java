package com.openkm.extension.dao.bean;

import java.io.Serializable;
import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * ForumTopic
 * 
 * @author jllort
 *
 */
public class ForumTopic implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String title;
	private Calendar date;
	private String user;
	private String uuid;
	private int replies;
	private int views;
	private String lastUser;
	private Calendar lastDate;
	private Set<ForumPost> posts = new LinkedHashSet<ForumPost>();
	
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

	public int getReplies() {
		return replies;
	}

	public void setReplies(int replies) {
		this.replies = replies;
	}

	public int getViews() {
		return views;
	}

	public void setViews(int views) {
		this.views = views;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
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

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
}
