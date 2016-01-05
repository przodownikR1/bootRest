package pl.java.scalatech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.java.scalatech.domain.Technology;

public interface TechnologyRepository extends JpaRepository<Technology, Long>{

}
