package pl.java.scalatech.web.hateoas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import pl.java.scalatech.domain.Role;
import pl.java.scalatech.repository.RoleRepository;

@RestController
@RequestMapping("/roleResource")
public class RoleResourceController {

    @Autowired
    private RoleRepository roleRepository;

    /*
     * @RequestMapping(method = RequestMethod.GET)
     * public HttpEntity<RoleResource> showAll() {
     * }
     */

    @Autowired
    private RoleResourceAssembler roleResourceAssembler;
    
    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public HttpEntity<RoleResource> findByName(@PathVariable String name) {
        Role role = roleRepository.findByName(name);
        return new ResponseEntity<>(new RoleResource(role.getName(), role.getDesc()), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public HttpEntity<PagedResources<Role>> getRoles(Pageable pageable, PagedResourcesAssembler pagedResourcesAssembler) {
        Page<Role> rolePage = roleRepository.findAll(pageable);

        return new ResponseEntity<>(pagedResourcesAssembler.toResource(rolePage, roleResourceAssembler), HttpStatus.OK);
    }
}
