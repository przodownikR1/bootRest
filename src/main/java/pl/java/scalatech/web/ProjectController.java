package pl.java.scalatech.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.domain.Project;
import pl.java.scalatech.repository.ProjectRepository;

@RestController
@RequestMapping("/projects")
@Slf4j
public class ProjectController extends GenericController{

 @Autowired 
    public ProjectController(ProjectRepository repo) {
        super(repo);
    }

    @Override
    Class<Project> getDomainClass() {      
        return Project.class;
    }
}
