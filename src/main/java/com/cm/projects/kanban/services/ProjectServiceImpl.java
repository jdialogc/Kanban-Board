/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cm.projects.kanban.services;

import com.cm.projects.kanban.domains.Project;
import com.cm.projects.kanban.repositories.ProjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Cornelius M
 */
@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {
    @Autowired
            ProjectRepo projectRepo;
    /**
     * Used to retrieve project from project repository using project id and formating the results before returning the result
     * @param projectId project id for the project to be returned
     * @return returns project
     */
    @Override
    public Project getProject(long projectId) {
        Project project = projectRepo.findOne(projectId);
        if(project != null){
            project.setTitle(project.getTitle().toUpperCase());
        }
        return project;
    }

}
