package pl.java.scalatech.web.hateoas;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import pl.java.scalatech.domain.User;
import pl.java.scalatech.web.UserController;
@Component
public class UserResourceAssembler extends ResourceAssemblerSupport<User, Resource> {


    public UserResourceAssembler() {
        super(UserController.class, Resource.class);
    }

    @Override
    public Resource<User> toResource(User user) {
        return new Resource<User>(user, ControllerLinkBuilder.linkTo(UserController.class).
                slash(user.getId()).withSelfRel());
    }



}
