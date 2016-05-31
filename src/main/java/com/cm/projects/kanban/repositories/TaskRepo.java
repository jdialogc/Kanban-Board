package com.cm.projects.kanban.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.cm.projects.kanban.domains.Task;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface TaskRepo extends CrudRepository<Task, Long> {
	List<Task> findByProjectId(long project_id);
        /**
         * Used to find tasks using their status i.e backlog, task-done
         * @param status status to be used to select
         * @return returns a list of Tasks
         */
        List<Task> findByStatus(String status);   
        /**
         * Used to task using projectId and status
         * @param projectId project id of tasks
         * @param status status of the tasks
         * @return returns a list of Tasks
         */
        List<Task> findByprojectIdAndStatus(long projectId, String status);
        /**
         * Used to count the status of a given project
         * @param status status to be counted
         * @param projectId project id of the project to be considered
         * @return returns long
         */
        long  countByprojectIdAndStatus(long projectId, String status);
        /**
         * Used to update task status
         * @param status new status
         * @param taskId task to be updated
         */
        @Transactional
        @Modifying
        @Query("UPDATE Task t SET t.status = ?1 WHERE t.id = ?2")
        void setFixedStatusFor(String status, long taskId);
        
        void deleteByprojectId(long projectId);
}
