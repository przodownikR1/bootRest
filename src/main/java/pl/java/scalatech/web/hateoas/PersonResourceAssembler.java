package pl.java.scalatech.web.hateoas;

import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import pl.java.scalatech.domain.Role;
@Component
class RoleResourceAssembler extends ResourceAssemblerSupport<Role, RoleResource> {

  public RoleResourceAssembler() {
    super(RoleResourceController.class, RoleResource.class);
  }

  @Override
  public RoleResource toResource(Role role) {
    RoleResource resource = new RoleResource(role.getName(), role.getDesc());
    resource.add(ControllerLinkBuilder.linkTo(RoleResourceController.class).slash(role.getName()).withSelfRel());
    return resource;
  }

  
  
}

