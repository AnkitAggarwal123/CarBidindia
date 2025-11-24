package com.carbid.demo.repo;

import com.carbid.demo.model.ApproveUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUser extends JpaRepository<ApproveUser, Long> {
    ApproveUser findByPhoneNumber(String phoneNumber);

    ApproveUser findByEmail(String username);

    boolean existsByEmail(String email);
}
