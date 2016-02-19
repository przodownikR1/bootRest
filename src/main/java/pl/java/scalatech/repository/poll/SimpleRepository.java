package pl.java.scalatech.repository.poll;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.java.scalatech.domain.poll.Simple;

public interface SimpleRepository extends JpaRepository<Simple, Long>{

}
