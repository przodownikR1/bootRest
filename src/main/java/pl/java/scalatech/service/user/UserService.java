package pl.java.scalatech.service.user;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import pl.java.scalatech.domain.User;

public interface UserService {

    User findByLogin(String login);
    User save(User user);
    User findOne(Long userId);
    Page<User> findAllUser(Pageable pageable);
    
     
}
