package com.carbid.demo.controller.adminController;

import com.carbid.demo.dto.WinnerDto;
import com.carbid.demo.model.WinnerWithPaper;
import com.carbid.demo.model.WinnerWithoutPaper;
import com.carbid.demo.service.WinnerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@PreAuthorize("hasRole('ADMIN')")
public class Winner_Admin {

    @Autowired
    WinnerServices winnerServices;

    @PostMapping("winner/withPaper/{id}/{carId}")
    public ResponseEntity<?> winnerWithPaper(@PathVariable Long id, @PathVariable Long carId) {
        try {
            return winnerServices.winnerWithPaper(id, carId);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }

    @GetMapping("winnerWithPaper/all")
    public List<WinnerDto> getWinnerByBidWithPaperId() {
        return winnerServices.getWinnerByBidWithPaperId();

    }

    @PostMapping("winner/withoutPaper/{id}/{carId}")
    public ResponseEntity<?> winnerWithoutPaper(@PathVariable Long id, @PathVariable Long carId) {
        try {
            return winnerServices.winnerWithoutPaper(id, carId);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }

    @GetMapping("winnerWithoutPaper/all")
    public List<WinnerDto> getWinnerByBidWithoutPaperId() {
        return winnerServices.getWinnerByBidWithoutPaperId();
    }

}
