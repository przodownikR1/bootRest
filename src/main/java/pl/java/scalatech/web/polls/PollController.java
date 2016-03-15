package pl.java.scalatech.web.polls;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Optional.of;
import static java.util.function.Function.identity;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pl.java.scalatech.domain.poll.Poll;
import pl.java.scalatech.repository.poll.PollRespository;

@RestController
@RequestMapping("/poll")
@Profile("poll")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PollController {

    private final @NonNull PollRespository pollRespository;
    
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<Poll> getPoll(@PathVariable Long id) {       
    return verifyAndResponseEntityWrap(id);
    }

    private ResponseEntity<Poll> verifyAndResponseEntityWrap(Long id) {
        return of(pollRespository.findOne(id)).map(p -> ok(p)).orElseThrow(
                ()->new ResourceNotFoundException(Poll.class.getSimpleName(),id));
    }
    
    private Poll verify(Long id) {        
        return of(pollRespository.findOne(checkNotNull(id))).map(identity()).orElseThrow(()->new ResourceNotFoundException(Poll.class.getSimpleName(),id));
    }
        
    @RequestMapping(value="/", method=RequestMethod.GET)
    public ResponseEntity<Page<Poll>> getAllPolls(Pageable pageable) {       
    return ResponseEntity.ok(pollRespository.findAll(pageable));
    }
    
    @RequestMapping(value="/", method=RequestMethod.POST)
    public ResponseEntity<Void> createPoll(@Valid @RequestBody Poll poll) {
    Poll loaded = pollRespository.save(checkNotNull(poll));
    return ResponseEntity.created(fromCurrentRequest().path("/{id}").buildAndExpand(loaded.getId()).toUri()).build();
    }
    
    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public ResponseEntity<?> updatePoll(@Valid @RequestBody Poll poll, @PathVariable Long id) {
    verify(id);        
    Poll loaded = pollRespository.save(checkNotNull(poll));
    return ResponseEntity.ok(loaded);
    }
     
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Void> deletePoll(@PathVariable Long id) {
    pollRespository.delete(verify(id));
    return ResponseEntity.noContent().build();
    }
    
}
