package pl.java.scalatech.audit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import pl.java.scalatech.domain.Person;
import pl.java.scalatech.repository.PersonRepository;

@Component
public class SpringSecurityAuditorAware implements AuditorAware<Person> {

    @Autowired private PersonRepository personRepository;
    
    @Override
    public Person getCurrentAuditor() {
        Person user = personRepository.findAll().get(0);  
        return user;
    }

}