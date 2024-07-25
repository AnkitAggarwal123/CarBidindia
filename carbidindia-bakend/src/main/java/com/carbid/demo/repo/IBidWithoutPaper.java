package com.carbid.demo.repo;

import com.carbid.demo.model.BidWithPaper;
import com.carbid.demo.model.BidWithoutPaper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IBidWithoutPaper extends JpaRepository<BidWithoutPaper, Long> {
    List<BidWithoutPaper> findAllByCarIdAndUserId(Long id, Long id1);

    Integer countByCarIdAndUserId(Long id, Long userId);


    List<BidWithoutPaper> findByUserEmail(String name);

    List<BidWithoutPaper> findAllByCarId(Long id);
}
