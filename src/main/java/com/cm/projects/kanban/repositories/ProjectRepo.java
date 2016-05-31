package com.cm.projects.kanban.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.cm.projects.kanban.domains.*;

public interface ProjectRepo extends CrudRepository<Project, Long> {
	List<Project> findById(long id);
}
