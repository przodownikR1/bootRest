package pl.java.scalatech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.java.scalatech.domain.Skill;

public interface SkillRepository extends JpaRepository<Skill, Long>{

}
