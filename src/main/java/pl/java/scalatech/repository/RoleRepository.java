package pl.java.scalatech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.java.scalatech.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{

}
