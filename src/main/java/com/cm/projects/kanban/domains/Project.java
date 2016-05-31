package com.cm.projects.kanban.domains;

import javax.persistence.*;

@Entity
public class Project {
	@Id
	@GeneratedValue
	private long id;
	private String title;
	private String description;
	/**
	 * The duration should be given in terms of months
	 */
	private int duration;
	private String start_date;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	@Override
	public String toString(){
	   return "Id: "+id+", Title: "+title+", description: "+description+", duration: "+duration+", Start Date: "+start_date;
	}
	
	
}
