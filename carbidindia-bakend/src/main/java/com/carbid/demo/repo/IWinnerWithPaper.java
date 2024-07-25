package com.carbid.demo.repo;

import aj.org.objectweb.asm.commons.Remapper;
import com.carbid.demo.model.BidWithPaper;
import com.carbid.demo.model.BidWithoutPaper;
import com.carbid.demo.model.WinnerWithPaper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.beans.JavaBean;
import java.util.List;
import java.util.Optional;

public interface IWinnerWithPaper extends JpaRepository<WinnerWithPaper, Long> {
    List<WinnerWithPaper> findByBidWithPaper(BidWithPaper bid);

    Optional<WinnerWithPaper> findByBidWithPaperId(Long bidWithPaperId);

    boolean existsByBidWithPaper(BidWithPaper bid);
}
