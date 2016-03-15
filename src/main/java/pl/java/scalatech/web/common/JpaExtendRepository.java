package pl.java.scalatech.web.common;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.java.scalatech.domain.AbstractEntity;

public interface JpaExtendRepository<T extends AbstractEntity> extends JpaRepository<T, Long>{
    
    default T findByOne(T t){
        return findOne(t.getId());
        
    }

}
