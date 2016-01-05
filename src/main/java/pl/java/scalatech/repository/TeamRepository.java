package pl.java.scalatech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.java.scalatech.domain.Team;

public interface TeamRepository extends JpaRepository<Team, Long>{

}
