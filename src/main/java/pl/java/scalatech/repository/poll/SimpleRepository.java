package pl.java.scalatech.repository.poll;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.stream.Stream;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.concurrent.ListenableFuture;

import pl.java.scalatech.domain.poll.Simple;

public interface SimpleRepository extends JpaRepository<Simple, Long> {

    @Async
    Future<Simple> findByName( String username );
    
    Slice<Simple> findTop100ByName ( String name, Pageable pageable );
    
    List<Simple>  findFirst10ByName ( String name, Sort sort);

    @Async
    CompletableFuture<List<Simple>> findByVersion ( Long version );

    @Async
    ListenableFuture<List<Simple>> findByLastModifiedDateIsNotNull();

    default Optional<Simple> findBySimple(Simple s) {
        final Simple result = this.findOne(s.getId());
        return result == null ? Optional.empty() : Optional.of(result);
    }

    Optional<Simple> findOneById(String productId);

    @Query("select s from Simple s")
    Stream<Simple> findAllByQueryWithStream();

}
