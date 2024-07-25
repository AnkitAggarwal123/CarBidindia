package com.carbid.demo.repo;

import com.carbid.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUser extends JpaRepository<User, Long> {
    User findByPhoneNumber(String phoneNumber);

    User findByEmail(String username);

    boolean existsByEmail(String email);
}
