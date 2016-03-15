package pl.java.scalatech.web.common;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.http.ResponseEntity.ok;

import java.net.URI;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.web.HateoasPageableHandlerMethodArgumentResolver;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.domain.AbstractEntity;
@Slf4j

public abstract class HateoasAbstractController<T extends AbstractEntity> extends  RestControllerAbstract<T>{
    @Autowired
    public HateoasAbstractController(JpaRepository<T, Long> repo, HateoasPageableHandlerMethodArgumentResolver resolver) {
        super(repo);
        this.resolver = resolver;
    }

    private final @NonNull HateoasPageableHandlerMethodArgumentResolver resolver;
    
  
    
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<PagedResources<Resource<T>>> getResources(@PageableDefault(size = 5) Pageable pageable, PagedResourcesAssembler<T> assembler) {        
        Page<T> items = service.findAll(pageable);
        return ResponseEntity.ok(assembler.toResource(items));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getResourceById(@PathVariable("id") Long id, @MatrixVariable Optional<Map<String, String>> maps) {
       log.info("+++++       id={} , matrixVars={} ", id, maps);
        return getRightResponseEntity(verify(id));
    }


    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<Void> createResource(@Valid @RequestBody T t) {
        T newResource = service.save(t);
        ResourceAssemblerSupport<T, ?> ras = getRas(t);        
        return ResponseEntity.created(URI.create(ras.toResource(newResource).getLink("self").getHref())).build();
    }
     

    @RequestMapping(value = "/{id:\\d+}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateResource(@PathVariable Long id, @RequestBody @Valid final T resource) {        
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(linkTo(methodOn(getClass()).getResourceById(id, null)).toUri());
        return new ResponseEntity<>(getRas(ifNotExistsCreate(resource)), headers, HttpStatus.NO_CONTENT);
    }

    protected HttpHeaders addCORSHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "*");
        headers.add("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE");
        headers.add("Access-Control-Allow-Headers", "Content-Type, X-Requested-With");
        return headers;

    }
    
    @RequestMapping(value="/**", method=RequestMethod.OPTIONS)
    public HttpEntity<?> handleOptionsRequest() {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Allow","GET, HEAD, POST, PUT, OPTIONS");
    return ok().headers(headers).build();
    }

    protected abstract ResponseEntity<?> getRightResponseEntity(T t);

    protected abstract ResourceAssemblerSupport<T, ?> getRas(T t);
    
    
    protected Link createPaginatedResourceLink(Link link) {
        String href = link.getHref();
        UriComponents components = UriComponentsBuilder.fromUriString(href).build();
        TemplateVariables variables = resolver.getPaginationTemplateVariables(null, components);
        return new Link(new UriTemplate(href, variables), link.getRel());
    }
}
