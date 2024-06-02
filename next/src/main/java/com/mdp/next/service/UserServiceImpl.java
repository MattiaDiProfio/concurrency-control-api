package com.mdp.next.service;

import java.util.List;
import java.util.Optional;

import com.mdp.next.entity.User;
import com.mdp.next.repository.*;
import com.mdp.next.exception.UserNotFoundException;
import lombok.AllArgsConstructor;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;   
    AccountRepository accountRepository; 
	private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User getUser(Long userID) {
        return unwrapUser(userRepository.findById(userID), userID);
    }

    @Override
    public List<User> getUsers() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public User createUser(User user) { 
        // save the user's password as its one-way hashed equivalent
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User getUser(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return unwrapUser(user, 404L);
    }

    @Override
    public User updateUser(String newEmail, String newAddress, Long userID) {
        if (!userRepository.findById(userID).isPresent()) throw new UserNotFoundException(userID, "user");
        User user = unwrapUser(userRepository.findById(userID), userID);
        user.setEmail(newEmail);
        user.setAddress(newAddress);
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userID) {
        if (!userRepository.findById(userID).isPresent()) throw new UserNotFoundException(userID, "user");
        else userRepository.deleteById(userID);
    }

    public static User unwrapUser(Optional<User> entity, Long userID) {
        if (entity.isPresent()) return entity.get();
        else throw new UserNotFoundException(userID, "user");
    }

}
