package com.ntg.Ntg_User_Management_System.service;

import com.ntg.Ntg_User_Management_System.model.User;
import com.ntg.Ntg_User_Management_System.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository , PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    // Method to create a new user
    public String createUser(User user) {
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        return userRepository.save(user);
    }
    // Method to find user by id
    public User findUserById(int id){
        return userRepository.findById(id);
    }
    // Method to find user by username
    public User findUserByUsername(String username){
        return userRepository.findByUsername(username);
    }
    // Method to update user
    public String updateUser(User user , int id){
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        return userRepository.update(user , id);
    }
    // Method to set user's is_deleted to true
    public String setUserToDelete(int id){
        return userRepository.setToDelete(id);
    }
    // Method to find all users
    public List<User> findAllUsers(){
        return userRepository.findAll();
    }
    public User login(String username,String enteredPassword){
        String userPassword = userRepository.getPassword(username);
        if(BCrypt.checkpw(enteredPassword,userPassword)){
            return userRepository.findByUsername(username);
        }else {
            throw new RuntimeException("Invalid username or password");
        }

    }
}
