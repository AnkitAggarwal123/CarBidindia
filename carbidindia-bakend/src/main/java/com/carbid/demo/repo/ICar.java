package com.carbid.demo.repo;

import com.carbid.demo.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ICar extends JpaRepository<Car, Long> {
    List<Car> findAllByVisibleTrueAndAuctionEndTimeBefore(LocalDateTime now);
}
