package pl.java.scalatech.web.polls;

import static org.springframework.http.ResponseEntity.ok;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.java.scalatech.domain.poll.Simple;
import pl.java.scalatech.repository.poll.SimpleRepository;
import pl.java.scalatech.web.common.RestControllerAbstract;

@RestController
@RequestMapping("/simple")
@Profile("poll")
public class SimpleController extends RestControllerAbstract<Simple>{
    
    @Autowired
    public SimpleController(SimpleRepository repository,ProjectionFactory projections) {
        super(repository);      
        this.projections = projections;
    }
    private final ProjectionFactory projections;
    
    
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<Simple> getSimple(@PathVariable Long id) {       
    return verifyAndResponseEntityWrap(id);
    }    
    @RequestMapping(value="/update/{id}", method=RequestMethod.GET)
    public ResponseEntity<Simple> update(@PathVariable Long id) {       
        Simple simple = verify(id);
        simple.setName(""+LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        return ok(service.save(simple));
       
    }
    
    @RequestMapping(value="/", method=RequestMethod.GET)
    public ResponseEntity<Page<Simple>> getAllSimple(Pageable pageable) {       
    return ok(service.findAll(pageable));
    }
    
    @RequestMapping(value="/generate", method=RequestMethod.GET)
    public void generate() {                       
        Stream.generate(new Simple()).limit(50).forEach(s->service.save(s));               
    }

  
    
    @RequestMapping(value="/onlyNames", method=RequestMethod.GET)
    public List<? extends Object> getOnlyName(){
        return service.findAll(new PageRequest(0, 20))
        .map(s -> projections.createProjection(NamesOnly.class, s))//
        .getContent();
    }
    
    interface NamesOnly {

        @Value("#{target.name.toString()}")
        String getName();
    }
}
