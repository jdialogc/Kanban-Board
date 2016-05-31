package com.cm.projects.kanban.domains;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
public class Task {

	@Id
	@GeneratedValue
	private long id;
        @NotNull(message = "The title field is required")
        @Size(min = 1, max = 200, message ="Title field cannot be empty and should not be more than 200 chars")
	private String title;
	@Column(columnDefinition = "TEXT")
	private String description;
	private String color;
	private String status;
        @NotNull
        @Min(1)
	private long projectId;
	
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

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String toString(){
		return "TaskId: "+id+" Title: "+title+" Description: "+ description+" Color: "+color+" Status: "+status+" project "+projectId;
	}

	public long getProjectId() {
		return projectId;
	}

	public void setProjectId(long project_id) {
		this.projectId = project_id;
	}
}
