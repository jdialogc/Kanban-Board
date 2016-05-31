package com.cm.projects.kanban.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cm.projects.kanban.repositories.ProjectRepo;
import com.cm.projects.kanban.domains.*;
import com.cm.projects.kanban.repositories.TaskRepo;
import com.cm.projects.kanban.services.ProjectService;

@Controller
public class Main {
	@Autowired
	ProjectRepo projectRepo;
        @Autowired
        ProjectService projectService;
        @Autowired
        TaskRepo taskRepo;
	@RequestMapping("/")
	public String dashboard(){
		return "redirect:/projects";
	}
	@RequestMapping(value = "/projects", method = RequestMethod.GET)
	public String projects(ModelMap model){		
		model.addAttribute("Projects", projectRepo.findAll());
		return "projects";
	}
        @RequestMapping(value = "/project2", method = RequestMethod.GET)
	public String projects2(){		
		//model.addAttribute("Projects", projectRepo.findAll());
		return "project2";
	}
        
	@RequestMapping(value = {"/project/{projectId}", "/projects/{projectId}" })
	public String project(@PathVariable("projectId") long projectId, ModelMap model){                
		model.addAttribute("project", projectService.getProject(projectId));
                //model.addAttribute("tasks", taskRepo.findByProjectId(projectId));
                model.addAttribute("backlogs", taskRepo.findByprojectIdAndStatus(projectId, "backlog"));
                //System.out.println("Backlog Elements: "+tasks);
                model.addAttribute("tasksProgress", taskRepo.findByprojectIdAndStatus(projectId,"tasks-progress"));
                model.addAttribute("tasksDone", taskRepo.findByprojectIdAndStatus(projectId,"tasks-done"));
                model.addAttribute("progressProgress", taskRepo.findByprojectIdAndStatus(projectId,"progress-progress"));
                model.addAttribute("progressTrack", taskRepo.findByprojectIdAndStatus(projectId,"progress-track"));
                model.addAttribute("progressDone", taskRepo.findByprojectIdAndStatus(projectId,"progress-done"));
                model.addAttribute("testingProcessing", taskRepo.findByprojectIdAndStatus(projectId,"testing-processing"));
                model.addAttribute("testingDone", taskRepo.findByprojectIdAndStatus(projectId,"testing-done"));
                model.addAttribute("done", taskRepo.findByprojectIdAndStatus(projectId,"done"));
                //System.out.println(taskRepo.findByProjectId(projectId));
		return "project2";
	}
	@RequestMapping(value = "/project/delete/{projectId}", method = RequestMethod.POST)
	public String deleteProject(@PathVariable("projectId") int projectId){
		return "projects";
	}
        
        
        
}
