package pl.java.scalatech.service.user.impl;

import static com.google.common.base.Preconditions.checkNotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Preconditions;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.domain.User;
import pl.java.scalatech.exception.UserNotFoundException;
import pl.java.scalatech.repository.UserRepository;
import pl.java.scalatech.service.user.UserService;

@Service
@Transactional(readOnly=true)
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService{

    private @NonNull final UserRepository userRepository;
    
    private User validateUser(String login) {
        return   this.userRepository.findByLogin(login).orElseThrow(() -> new UserNotFoundException(login));
    }

    @Override
    public User findByLogin(String login) {
        return validateUser(login);
        
    }

    @Override
    @Transactional(timeout=5)
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Page<User> findAllUser(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User findOne(Long userId) {
        return checkNotNull(userRepository.findOne(userId));
        
    }
    
    
}
