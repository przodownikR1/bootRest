package pl.java.scalatech.repository.poll;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pl.java.scalatech.domain.poll.Vote;

public interface VoteRepository extends JpaRepository<Vote, Long>{
    @Query(value="select v.* from Option o, Vote v where o.id = ?1 and v.id = o.id", nativeQuery = true)
    public Iterable<Vote> findByPoll(Long pollId);
}
