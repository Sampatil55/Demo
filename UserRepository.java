package com.hotel.respository;

import com.hotel.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByMobile(String mobile);
    Optional<User> findByUsername(String username);

    List<User> findAll();

    @Query("SELECT CASE WHEN SIZE(u.tasks) > 0 THEN true ELSE false END FROM User u WHERE u.id = :userId")
    Boolean hasUserCreatedTask(@Param("userId") long userId);


}