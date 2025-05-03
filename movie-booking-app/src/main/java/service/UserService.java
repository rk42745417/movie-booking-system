package service;

import model.User;
import repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) {
        return userRepository.saveUser(user);
    }

    public User findByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    public boolean isAdmin(User user) {
        return user.isAdmin();
    }
}

