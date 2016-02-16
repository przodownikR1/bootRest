package pl.java.scalatech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import pl.java.scalatech.domain.Role;
@RepositoryRestResource(exported = false)
public interface RoleRepository extends JpaRepository<Role, Long>{

    
    Role findByName(String name);
}
