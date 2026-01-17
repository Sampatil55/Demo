package com.hotel.controller;

import com.hotel.entity.User;
import com.hotel.payload.JwtToken;
import com.hotel.payload.LoginDto;
import com.hotel.respository.UserRepository;
import com.hotel.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private UserRepository userRepository;

    public AuthController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }
    private UserService userService;
    @PostMapping("/signup")
    public ResponseEntity<?> createUser(
            @RequestBody User user){
        Optional<User> opUsername = userRepository.findByUsername(user.getUsername());
        if (opUsername.isPresent()){
            return new  ResponseEntity("user already exist", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Optional<User> opEmail = userRepository.findByEmail(user.getEmail());
        if (opEmail.isPresent()){
            return new  ResponseEntity("email already exist", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Optional<User> opMobile = userRepository.findByMobile(user.getMobile());
        if (opMobile.isPresent()){
            return new  ResponseEntity("mobilealready exist", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        user.setPassword(BCrypt.hashpw(user.getPassword(),BCrypt.gensalt(10)));//it used to encrypt the password//gensalt the password is going to encrypt 10 times
       user.setRole("ROLE_USER");
        User saved = userRepository.save(user);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }
    @GetMapping("/getAllUser")
    public ResponseEntity<?> getAlluser() {
        List<User> allUsers = userService.getAllUsers();
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }


    //http://localhost:8080/api/v1/findBy/1
    @GetMapping("/findBy/{id}")
    public ResponseEntity<?> getById(
            @PathVariable long id
    ) {
        User getUser = userService.getUserById(id);
        return new ResponseEntity<>(getUser, HttpStatus.OK);
    }

    //http://localhost:8080/api/v1/updateById/1
    @PutMapping("/updateById/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable long id,
            @RequestBody User user
    ) {
        User updatedUser = userService.updateUserById(id, user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    //http://localhost:8080/api/auth/delete/1
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> removeUser(
            @PathVariable long id
    ) {

        String deleteUser = userService.deleteUserById(id);
        return new ResponseEntity<>(deleteUser, HttpStatus.OK);

    }
    @PostMapping("/manager")
    public ResponseEntity<?> createManager(
            @RequestBody User user){
        Optional<User> opUsername = userRepository.findByUsername(user.getUsername());
        if (opUsername.isPresent()){
            return new  ResponseEntity("user already exist", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Optional<User> opEmail = userRepository.findByEmail(user.getEmail());
        if (opEmail.isPresent()){
            return new  ResponseEntity("email already exist", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Optional<User> opMobile = userRepository.findByMobile(user.getMobile());
        if (opMobile.isPresent()){
            return new  ResponseEntity("mobilealready exist", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        user.setPassword(BCrypt.hashpw(user.getPassword(),BCrypt.gensalt(10)));//it used to encrypt the password//gensalt the password is going to encrypt 10 times
        user.setRole("ROLE_MANAGER");
        User saved = userRepository.save(user);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }
    @PostMapping("/admin")
    public ResponseEntity<?> createBlogManagerAccount(
            @RequestBody User user){
        Optional<User> opUsername = userRepository.findByUsername(user.getUsername());
        if (opUsername.isPresent()){
            return new  ResponseEntity("user already exist", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Optional<User> opEmail = userRepository.findByEmail(user.getEmail());
        if (opEmail.isPresent()){
            return new  ResponseEntity("email already exist", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Optional<User> opMobile = userRepository.findByMobile(user.getMobile());
        if (opMobile.isPresent()){
            return new  ResponseEntity("mobilealready exist", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        user.setPassword(BCrypt.hashpw(user.getPassword(),BCrypt.gensalt(10)));//it used to encrypt the password//gensalt the password is going to encrypt 10 times
        user.setRole("ROLE_ADMIN");
        User saved = userRepository.save(user);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginDto loginDto){
      String token = userService.verifyLogin(loginDto);
      JwtToken jwtToken = new JwtToken();
      jwtToken.setToken(token);
      jwtToken.setType("JWT");
        if (token!=null){
            return new ResponseEntity<>(jwtToken,HttpStatus.OK);
        }
        return new ResponseEntity<>("invalid",HttpStatus.INTERNAL_SERVER_ERROR);
    }



}