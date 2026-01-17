package com.hotel.service;

import com.hotel.entity.User;
import com.hotel.payload.LoginDto;
import com.hotel.respository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
private JWTService jwtService;
    public UserService(UserRepository userRepository, JWTService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;

    }

    public List<User> getAllUsers() {
       return userRepository.findAll();


    }

    public User getUserById(long id) {
        Optional<User> opId = userRepository.findById(id);
        User user = opId.get();
        return user;
    }

    public String deleteUserById(long id) {
        if(userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new RuntimeException("User not found");
        }
        return "Record deleted";
    }




    public User updateUserById(long id, User user) {
        Optional<User> updateId = userRepository.findById(id);
        User newUser = new User();
        if (updateId.isPresent()) {
            newUser = updateId.get();
            newUser.setEmail(user.getEmail());
            newUser.setUsername(user.getUsername());
            return userRepository.save(newUser);
        } else {
            throw new RuntimeException("User not found with id " + id);
        }
    }

  /*  public String verifyLogin(
            LoginDto loginDto
    ){
        Optional<User> opUsername = userRepository.findByUsername(loginDto.getUsername());
        if (opUsername.isPresent()){
            User user = opUsername.get();
            if (BCrypt.checkpw(loginDto.getPassword(),user.getPassword())){
                String token = jwtService.generateToken(user.getUsername());
                return token;
        }else {
            return null;
        }
    }
    return null;
}*/
  public String verifyLogin(
          LoginDto loginDto
  ){
      Optional<User> opUseremail = userRepository.findByEmail(loginDto.getEmail());
      if (opUseremail.isPresent()){
          User user = opUseremail.get();
          if (BCrypt.checkpw(loginDto.getPassword(),user.getPassword())){
              String token = jwtService.generateToken(user.getEmail());
              return token;
          }else {
              return null;
          }
      }
      return null;
  }


    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }
    public boolean hasUserCreatedTask(Long userId) {
        return userRepository.hasUserCreatedTask(userId);
    }

}