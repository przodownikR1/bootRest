package pl.java.scalatech.domain.poll;

import static org.apache.commons.lang.RandomStringUtils.randomAlphabetic;

import java.time.LocalDateTime;
import java.util.function.Supplier;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PreUpdate;
import javax.persistence.Version;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EntityListeners(AuditingEntityListener.class)
public class Simple implements Supplier<Simple>{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    protected Long id;

    @Version
    @Getter
    private Long version;

    private String name;

    private @CreatedDate LocalDateTime createDate;

    private @LastModifiedDate LocalDateTime lastModifiedDate ;

  
    @Override
    public Simple get() {      
        return Simple.builder().name(randomAlphabetic(15)).build();
    }
}
