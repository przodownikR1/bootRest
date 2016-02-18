package pl.java.scalatech.repository.poll;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.java.scalatech.domain.poll.Poll;

public interface PollRespository extends JpaRepository<Poll, Long>{

}
