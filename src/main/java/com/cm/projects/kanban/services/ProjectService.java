/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cm.projects.kanban.services;

import com.cm.projects.kanban.domains.Project;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author User
 */

public interface ProjectService {
    public Project getProject(long projectId);    
}
