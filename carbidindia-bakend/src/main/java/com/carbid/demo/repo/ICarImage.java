package com.carbid.demo.repo;

import com.carbid.demo.model.CarImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICarImage extends JpaRepository<CarImage, Long> {
}
