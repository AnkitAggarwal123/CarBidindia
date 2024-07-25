package com.carbid.demo.service;

import aj.org.objectweb.asm.commons.Remapper;
import com.carbid.demo.dto.WinnerDto;
import com.carbid.demo.model.BidWithPaper;
import com.carbid.demo.model.BidWithoutPaper;
import com.carbid.demo.model.WinnerWithPaper;
import com.carbid.demo.model.WinnerWithoutPaper;
import com.carbid.demo.repo.IBidWithPaper;
import com.carbid.demo.repo.IBidWithoutPaper;
import com.carbid.demo.repo.IWinnerWithPaper;
import com.carbid.demo.repo.IWinnerWithoutPaper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WinnerServices {

    @Autowired
    IBidWithPaper iBidWithPaper;

    @Autowired
    IBidWithoutPaper iBidWithoutPaper;

    @Autowired
    IWinnerWithPaper iWinnerWithPaper;

    @Autowired
    BidServices bidServices;

    @Autowired
    BidServicesWithoutPaper bidServicesWithoutPaper;

    @Autowired
    IWinnerWithoutPaper iWinnerWithoutPaper;

    public ResponseEntity<?> winnerWithPaper(Long id, Long carId) {
        try {
            BidWithPaper bid = iBidWithPaper.findById(id)
                    .orElseThrow(() -> new RuntimeException("Bid not found with id " + id));

            WinnerWithPaper winnerWithPaper = new WinnerWithPaper(null, bid, bid.getCar());

            iWinnerWithPaper.save(winnerWithPaper);
            bidServices.updateDelete(carId);
            return ResponseEntity.status(HttpStatus.CREATED).body("Winner with paper created successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }

    public ResponseEntity<?> winnerWithoutPaper(Long id, Long carId) {
        try {
            BidWithoutPaper bid = iBidWithoutPaper.findById(id)
                    .orElseThrow(() -> new RuntimeException("Bid not found with id " + id));

            WinnerWithoutPaper winnerWithoutPaper = new WinnerWithoutPaper(null, bid, bid.getCar());

            iWinnerWithoutPaper.save(winnerWithoutPaper);
            bidServicesWithoutPaper.updateDelete(carId);
            return ResponseEntity.status(HttpStatus.CREATED).body("Winner without paper created successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }

    public List<WinnerDto> getWinnerByBidWithPaperId() {
        List<WinnerWithPaper> winnerWithPapers = iWinnerWithPaper.findAll();

        List<WinnerDto> winnerDtos = winnerWithPapers.stream()
                .map(winner -> {
                    Long bidId = (winner.getBidWithPaper() != null) ? winner.getBidWithPaper().getId() : null;
                    Long carId = (winner.getCar() != null) ? winner.getCar().getId() : null;
                    return new WinnerDto(bidId, carId);
                })
                .collect(Collectors.toList());

        return winnerDtos;
    }

    public List<WinnerDto> getWinnerByBidWithoutPaperId() {
        List<WinnerWithoutPaper> winnerWithoutPapers = iWinnerWithoutPaper.findAll();

        List<WinnerDto> winnerDtos = winnerWithoutPapers.stream()
                .map(winner -> {
                    Long bidId = (winner.getBidWithoutPaper() != null) ? winner.getBidWithoutPaper().getId() : null;
                    Long carId = (winner.getCar() != null) ? winner.getCar().getId() : null;
                    return new WinnerDto(bidId, carId);
                })
                .collect(Collectors.toList());

        return winnerDtos;
    }
}
