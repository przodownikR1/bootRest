package pl.java.scalatech.repository.poll;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.java.scalatech.domain.poll.Option;

public interface OptionRepository extends JpaRepository<Option, Long>{

}
