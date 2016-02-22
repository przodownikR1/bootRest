package pl.java.scalatech.domain.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.java.scalatech.domain.poll.Simple;

public interface SimpleTupleRepository extends JpaRepository<Simple, Long>, MyJpaSpecificationExecutor<Simple> {
}