package pl.java.scalatech.domain.poll;

import static org.apache.commons.lang.RandomStringUtils.randomAlphabetic;

import java.time.LocalDateTime;
import java.util.function.Supplier;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.java.scalatech.domain.AbstractEntity;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EntityListeners(AuditingEntityListener.class)
public class Simple extends AbstractEntity implements Supplier<Simple>{

    private static final long serialVersionUID = -2694435948470261613L;

    private String name;

    private @CreatedDate LocalDateTime createDate;

    private @LastModifiedDate LocalDateTime lastModifiedDate ;
    
    
  
    @Override
    public Simple get() {      
        return Simple.builder().name(randomAlphabetic(15)).build();
    }

}
