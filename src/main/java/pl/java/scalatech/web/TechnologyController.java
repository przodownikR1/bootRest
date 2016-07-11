package pl.java.scalatech.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.domain.Technology;
import pl.java.scalatech.repository.TechnologyRepository;

@RestController
@RequestMapping("/technologies")
@Slf4j
public class TechnologyController extends GenericController<Technology>{
    
    
    @Autowired
    public TechnologyController(TechnologyRepository repo) {
        super(repo);
    }

    @Override
    Class<?> getDomainClass() {       
        return Technology.class;
    }

    
}
