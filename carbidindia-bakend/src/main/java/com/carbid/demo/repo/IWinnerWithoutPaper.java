package com.carbid.demo.repo;

import com.carbid.demo.model.BidWithoutPaper;
import com.carbid.demo.model.WinnerWithPaper;
import com.carbid.demo.model.WinnerWithoutPaper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IWinnerWithoutPaper extends JpaRepository<WinnerWithoutPaper, Long> {

    List<WinnerWithoutPaper> findByBidWithoutPaper(BidWithoutPaper bid);

    Optional<WinnerWithoutPaper> findByBidWithoutPaperId(Long bidWithoutPaperId);

    boolean existsByBidWithoutPaper(BidWithoutPaper bid);
}
