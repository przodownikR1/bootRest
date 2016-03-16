package pl.java.scalatech.web.common;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Optional.of;
import static java.util.function.Function.identity;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.lang.reflect.ParameterizedType;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.domain.AbstractEntity;
import pl.java.scalatech.web.polls.ResourceNotFoundException;
@RequiredArgsConstructor(onConstructor = @__(@Autowired) )
@Slf4j
public abstract class RestControllerAbstract<T extends AbstractEntity> {

    private Class<T> clazz =getDomainClass();
    
    protected final @NonNull JpaRepository<T, Long> service;
         
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private final Class<T> getDomainClass() {
        if (clazz == null) {
            ParameterizedType thisType = (ParameterizedType) getClass().getGenericSuperclass();
            clazz= (Class) thisType.getActualTypeArguments()[0];
        }
        return clazz;
    }
    
    @RequestMapping(method = GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<T>> getResources(@PageableDefault(size = 5) Pageable pageable) {
        return ok(service.findAll(pageable));
    }

    @RequestMapping(method = GET, value = "/{resourceId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<T> getResourceById(@PathVariable("resourceId") Long resourceId) {
        return verifyAndResponseEntityWrap(resourceId);
    }
    
    @RequestMapping(method=POST,produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addResource(@Valid @RequestBody T t) {
        T loaded = service.save(t); 
        return created(linkTo(methodOn(RestControllerAbstract.class).getResourceById(loaded.getId())).toUri()).build();
    }
    
    @RequestMapping(value = "/{id:\\d+}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteCategoryById(@PathVariable("id") Long id) {
        T loaded = verify(id);     
        service.delete(loaded);
        return ResponseEntity.noContent().build();
    }
     
    protected ResponseEntity<T> verifyAndResponseEntityWrap(Long id) {        
        //log.info("+++++++++++++++++++++++  tutaj : {}  {}",service,service.findAll());
        return of(checkNotNull(this.service).findOne(id)).map(p -> ok(p)).orElseThrow(() -> new ResourceNotFoundException(clazz.getSimpleName(), id));
    }

    protected T verify(Long id) {
        return of(checkNotNull(this.service).findOne(checkNotNull(id))).map(identity()).orElseThrow(() -> new ResourceNotFoundException(clazz.getSimpleName(), id));
    }
    
    protected T ifNotExistsCreate(T t) {
        return of(checkNotNull(this.service).findOne(checkNotNull(t.getId()))).map(identity()).orElseGet(() -> service.save(t));
    }
}
