package com.carbid.demo.repo;

import com.carbid.demo.model.BidWithPaper;
import com.carbid.demo.model.BidWithoutPaper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IBidWithPaper extends JpaRepository<BidWithPaper, Long> {
    List<BidWithPaper> findAllByCarIdAndUserId(Long id, Long id1);

    Integer countByCarIdAndUserId(Long carId, Long userId);

    List<BidWithPaper> findAllByCarId(Long carId);

    List<BidWithPaper> findByUserEmail(String name);
}
