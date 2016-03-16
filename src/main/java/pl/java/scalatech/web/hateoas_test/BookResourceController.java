package pl.java.scalatech.web.hateoas_test;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.java.scalatech.domain.Role;
import pl.java.scalatech.repository.UserRepository;

@RestController
@RequestMapping("/books")
@ExposesResourceFor(Book.class)
public class BookResourceController {

    @Autowired
    private EntityLinks entityLinks;

    private BookResourceAssembler assembler = new BookResourceAssembler();

    
    
    @RequestMapping(method = RequestMethod.GET)
    public Object all() {
        return assembler.toResources(Arrays.asList(Book.builder().author("przodownik").name("spring").id(1l).build(),
                Book.builder().author("tyson").name("iron").id(2l).build(),
                Book.builder().author("sienkiewicz").name("krzyzacy").id(3l).build()
                ));
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Object one(@PathVariable("id") Long id) {
        return assembler.toResource(Book.builder().author("przodownik").name("spring").id(1l).build());
    }

    @RequestMapping(value = "meta", method = RequestMethod.GET)
    public ResourceSupport links() {
        ResourceSupport links = new ResourceSupport();
        links.add(entityLinks.linkToCollectionResource(Book.class).withRel("books"));
        links.add(entityLinks.linkToSingleResource(Book.class, 1L).withRel("book"));
        return links;
    }

    

}