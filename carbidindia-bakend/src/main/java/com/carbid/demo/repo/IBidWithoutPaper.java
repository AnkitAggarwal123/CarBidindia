package com.carbid.demo.repo;

import com.carbid.demo.model.BidWithPaper;
import com.carbid.demo.model.BidWithoutPaper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IBidWithoutPaper extends JpaRepository<BidWithoutPaper, Long> {


    List<BidWithoutPaper> findAllByCarId(Long id);

    List<BidWithoutPaper> findAllByCar_IdAndApproveUser_Id(Long carId, Long userId);

    Integer countByCar_IdAndApproveUser_Id(Long carId, Long userId);

    List<BidWithoutPaper> findByApproveUser_Email(String email);

//    List<BidWithoutPaper> findAllByCar_Id(Long carId);
}
