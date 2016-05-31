package com.cm.projects.kanban.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.cm.projects.kanban.domains.Project;

@Component
public class ProjectValidator implements Validator{

	@Override
	public boolean supports(Class clazz) {
		// TODO Auto-generated method stub
		return Project.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors e) {
		// TODO Auto-generated method stub
		//ValidationUtils.rejectIfEmpty(e, "description", "description.empty");
        Project p = (Project) obj;
        if(p.getTitle() == null || p.getTitle().isEmpty()){
        	e.rejectValue("title", "title.incorrect", "The title field is required");
        }
        if(p.getDescription() == null || p.getDescription().isEmpty()){
        	e.rejectValue("description", "description.incorrect","The description field is required");
        }
        if (!p.getStart_date().matches("\\d{4}-\\d{2}-\\d{2}")) {
        	e.rejectValue("start_date", "start_date.incorrect","Wrong date format i.e required format is yyyy-mm-dd e.g 2016-05-05");            
        }
        if(p.getDuration() < 0){
        	e.rejectValue("duration", "duration.incorrect", "The duration should be a value greater than zero");
        }
	}

}
