package com.carbid.demo.repo;

import com.carbid.demo.model.BidWithPaper;
import com.carbid.demo.model.BidWithoutPaper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IBidWithPaper extends JpaRepository<BidWithPaper, Long> {

    List<BidWithPaper> findAllByCarId(Long carId);

    List<BidWithPaper> findAllByCar_IdAndApproveUser_Id(Long carId, Long userId);

    Integer countByCar_IdAndApproveUser_Id(Long carId, Long userId);

//    List<BidWithPaper> findAllByCar_Id(Long carId);

    List<BidWithPaper> findByApproveUser_Email(String email);
}
