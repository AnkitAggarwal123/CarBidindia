package com.carbid.demo.scheduler;

import com.carbid.demo.model.Car;
import com.carbid.demo.repo.ICar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Component
public class CarAuctionScheduler {

    @Autowired
    private ICar carRepository;

    @Scheduled(fixedRate = 60000) // every 60 seconds
    @Transactional
    public void updateCarVisibility() {
        LocalDateTime now = LocalDateTime.now();
        List<Car> cars = carRepository.findAllByVisibleTrueAndAuctionEndTimeBefore(now);
        for (Car car : cars) {
            car.setVisible(false);
            carRepository.save(car);
        }
    }
}
