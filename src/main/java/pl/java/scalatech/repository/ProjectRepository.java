package pl.java.scalatech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.java.scalatech.domain.Project;

public interface ProjectRepository extends JpaRepository<Project, Long>{

}
