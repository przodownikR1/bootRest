package pl.java.scalatech.web.hateoas;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Sort;
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

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public HttpEntity<RoleResource> findById(@PathVariable Long id) {
        Role role= roleRepository.findOne(id);
        if(role != null){
            return new ResponseEntity<>(new RoleResource(role.getName(), role.getDesc()), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                        
    }
    
    
    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public HttpEntity<RoleResource> findByName(@PathVariable String name) {
        return roleRepository.findByName(name)
                .map(role -> new ResponseEntity<>(new RoleResource(role.getName(), role.getDesc()), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public HttpEntity<PagedResources<Role>> getRoles(Pageable pageable, PagedResourcesAssembler pagedResourcesAssembler) {
        Page<Role> rolePage = roleRepository.findAll(pageable);
        return new ResponseEntity<>(pagedResourcesAssembler.toResource(rolePage, roleResourceAssembler), HttpStatus.OK);
    }

    @RequestMapping(value="/all",method = RequestMethod.GET)
    public HttpEntity<PagedResources<Role>> list(@PageableDefault(sort = "createdBy", direction = Sort.Direction.DESC) Pageable pageable,
            PagedResourcesAssembler assembler) {

        Page<Role> role = roleRepository.findAll(pageable);
        return new ResponseEntity<>(assembler.toResource(role, assembler), HttpStatus.OK);

    }
}
