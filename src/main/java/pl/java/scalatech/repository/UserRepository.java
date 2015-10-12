package pl.java.scalatech.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.java.scalatech.domain.User;

public interface UserRepository extends JpaRepository<User, Long>{
    
    Optional<User> findByLogin(String login);

}
