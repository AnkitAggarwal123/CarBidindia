package com.carbid.demo.repo;

import com.carbid.demo.model.RequestUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRequestUser extends JpaRepository<RequestUser, Long> {
    boolean existsByEmail(String email);
}
