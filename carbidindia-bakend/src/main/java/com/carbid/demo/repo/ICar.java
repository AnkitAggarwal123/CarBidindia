package com.carbid.demo.repo;

import com.carbid.demo.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ICar extends JpaRepository<Car, Long> {
    List<Car> findAllByVisibleTrueAndAuctionEndTimeBefore(LocalDateTime now);

    Optional<Car> findByRegistrationNumber(String registrationNumber);
}
