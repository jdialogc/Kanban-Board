package com.cm.projects.kanban.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cm.projects.kanban.domains.Project;
import com.cm.projects.kanban.domains.Task;
import com.cm.projects.kanban.repositories.ProjectRepo;
import com.cm.projects.kanban.repositories.TaskRepo;
import com.cm.projects.kanban.validators.ProjectValidator;
import javax.transaction.Transactional;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class Forms {
	@Autowired
	ProjectRepo projectRepo;
	ProjectValidator projectValidator;
        TaskRepo taskRepo;
	
	@Autowired
	public void setProjectValidator(ProjectValidator pv){
		this.projectValidator = pv;
	}
        @Autowired
        public void setTaskRepo(TaskRepo taskRepo){
            this.taskRepo = taskRepo;
        }
	@RequestMapping(value = "/project/add", method = RequestMethod.POST)
	public Object addProject(Project project, BindingResult bind){
		System.out.println("Project created successfully: "+project.toString());
		projectValidator.validate(project, bind);
		HashMap<String, String> result = new HashMap<>();
		if(bind.hasErrors()){
			List<FieldError> errors = bind.getFieldErrors();
			
			result.put("Errors", "");
			String msg = "";
			for(int i = 0; i < bind.getFieldErrorCount() ; i++){
				msg += errors.get(i).getDefaultMessage()+"<br />";
				
			}
			result.put("Errors", msg);
			//System.err.println(bind.getAllErrors());
			return result;
		}
		projectRepo.save(project);
		result.put("id", ""+project.getId());
		return result;
	}
	@RequestMapping(value = "/projects/{projectId}/add/task", method = RequestMethod.POST)
	public Object addTask(@PathVariable("projectId") long projectId,@Valid Task task, BindingResult bind){
                //task.setProject_id(projectId);
                System.out.println(task.toString());
                HashMap<String, String> result = new HashMap<>();
                if(bind.hasErrors()){
                    List<FieldError> errors = bind.getFieldErrors();
			
			result.put("Errors", "");
			String msg = "";
			for(int i = 0; i < bind.getFieldErrorCount() ; i++){
				msg += errors.get(i).getDefaultMessage()+"<br />";
				
			}
			result.put("Errors", msg);
			//System.err.println(bind.getAllErrors());
			return result;
                }
                taskRepo.save(task);
		result.put("taskId", ""+task.getId());
		return result;
	}
        @RequestMapping(value = "/tasks/update/status/{taskId}", method = RequestMethod.POST)
	public Object updateTaskStatus(@PathVariable("taskId") long taskId, @RequestParam("projectId") long projectId, @RequestParam("status") String status){
                HashMap<String, String> result = new HashMap<>();
                String msg = "";
                if(taskId < 1){
                    msg += "Task Id should be greater than zero<br />";
                    result.put("Errors", msg);
                    return result;
                }
                
                if(status.length() < 1){
                    msg += "Status length should be more than 1";
                    result.put("Errors", msg);
                    return result;
                }
                //System.out.println("Status Occurance "+taskRepo.countByprojectIdAndStatus(projectId, status));
                long occurance = taskRepo.countByprojectIdAndStatus(projectId, status);
                if((status.equalsIgnoreCase("tasks-progress") || status.equalsIgnoreCase("tasks-done") || status.equalsIgnoreCase("testing-processing") || status.equalsIgnoreCase("testing-done")) && occurance > 0){
                    result.put("denied", "Please process the existing task first only one task allowed for this column");
                    return result;
                }else if((status.equalsIgnoreCase("progress-progress") || status.equalsIgnoreCase("progress-done")) && occurance > 2){
                    result.put("denied", "Please process the existing task first only Three task allowed for this column");
                    return result;
                }
                taskRepo.setFixedStatusFor(status, taskId);
		result.put("status", "successful ("+status+ ", "+taskId+")");
		return result;
	}
        @RequestMapping(value = "/task/delete", method = RequestMethod.POST)
	public Object deleteTask(@RequestParam("taskId") long taskId){            
                HashMap<String, String> result = new HashMap<>();
                taskRepo.delete(taskId);
                result.put("status", "done");
		return result;
	}
        @RequestMapping(value = "/project/delete", method = RequestMethod.POST)
        @Transactional
	public Object deleteProject(@RequestParam("projectId") long projectId){            
                HashMap<String, String> result = new HashMap<>();
                taskRepo.deleteByprojectId(projectId);//remove tasks
                projectRepo.delete(projectId);                
                result.put("status", "done");
		return result;
	}
        @RequestMapping(value = "/projects/{projectId}/update/task/{taskId}", method = RequestMethod.POST)
	public Object updateTask(@PathVariable("projectId") long projectId,@PathVariable("taskId") long taskId, @Valid Task task, BindingResult bind){
                //task.setProject_id(projectId);
                System.out.println(task.toString());
                HashMap<String, String> result = new HashMap<>();
                if(bind.hasErrors()){
                    List<FieldError> errors = bind.getFieldErrors();
			
			result.put("Errors", "");
			String msg = "";
			for(int i = 0; i < bind.getFieldErrorCount() ; i++){
				msg += errors.get(i).getDefaultMessage()+"<br />";
				
			}
			result.put("Errors", msg);
			//System.err.println(bind.getAllErrors());
			return result;
                }
                Task taskNew = taskRepo.findOne(taskId);
                taskNew.setColor(task.getColor());
                taskNew.setDescription(task.getDescription());
                taskNew.setStatus(task.getStatus());
                taskNew.setTitle(task.getTitle());
                taskRepo.save(taskNew);
		result.put("taskId", ""+task.getId());
		return result;
	}
        @RequestMapping(value = "/project/update/{projectId}", method = RequestMethod.POST)
	public Object updateTask(@PathVariable("projectId") long projectId, @Valid Project project, BindingResult bind){
                HashMap<String, String> result = new HashMap<>();
                if(bind.hasErrors()){
                    List<FieldError> errors = bind.getFieldErrors();			
			result.put("Errors", "");
			String msg = "";
			for(int i = 0; i < bind.getFieldErrorCount() ; i++){
				msg += errors.get(i).getDefaultMessage()+"<br />";
				
			}
			result.put("Errors", msg);
			//System.err.println(bind.getAllErrors());
			return result;
                }
//                Task taskNew = taskRepo.findOne(taskId);
//                taskNew.setColor(task.getColor());
//                taskNew.setDescription(task.getDescription());
//                taskNew.setStatus(task.getStatus());
//                taskNew.setTitle(task.getTitle());
//                taskRepo.save(taskNew);
                  Project newProject = projectRepo.findOne(projectId);
                  newProject.setDescription(project.getDescription());
                  newProject.setTitle(project.getTitle());
                  newProject.setDuration(project.getDuration());
                  newProject.setStart_date(project.getStart_date());
                  projectRepo.save(newProject);
		  result.put("taskId", ""+newProject.getId());
		return result;
	}
        
        @RequestMapping("/exit")
	public void exit(){
            HashMap<String,String> result = new HashMap<>();
                System.exit(0);
	}

}
