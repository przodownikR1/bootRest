package pl.java.scalatech.web.polls;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Optional.of;
import static java.util.function.Function.identity;
import static org.springframework.http.ResponseEntity.ok;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pl.java.scalatech.domain.poll.Simple;
import pl.java.scalatech.repository.poll.SimpleRepository;

@RestController
@RequestMapping("/simple")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SimpleController {

    private final @NonNull SimpleRepository simpleRepository;
    
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<Simple> getSimple(@PathVariable Long id) {       
    return verifyAndResponseEntityWrap(id);
    }
    
    
    @RequestMapping(value="/update/{id}", method=RequestMethod.GET)
    public ResponseEntity<Simple> update(@PathVariable Long id) {       
        Simple simple = verify(id);
        simple.setName(""+LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        return ResponseEntity.ok(simpleRepository.save(simple));
       
    }
    
    @RequestMapping(value="/", method=RequestMethod.GET)
    public ResponseEntity<Page<Simple>> getAllSimple(Pageable pageable) {       
    return ResponseEntity.ok(simpleRepository.findAll(pageable));
    }
    
    @RequestMapping(value="/generate", method=RequestMethod.GET)
    public void generate() {                       
        Stream.generate(new Simple()).limit(50).forEach(s->simpleRepository.save(s));               
    }

    private ResponseEntity<Simple> verifyAndResponseEntityWrap(Long id) {
        return of(simpleRepository.findOne(id)).map(p -> ok(p)).orElseThrow(()->new ResourceNotFoundException("Poll with id " + id + " not found"));
    }
    
    private Simple verify(Long id) {        
        return of(simpleRepository.findOne(checkNotNull(id))).map(identity()).orElseThrow(()->new ResourceNotFoundException("Poll with id " + id + " not found"));
    }
}
