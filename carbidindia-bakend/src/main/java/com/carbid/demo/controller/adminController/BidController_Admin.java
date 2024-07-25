package com.carbid.demo.controller.adminController;

import com.carbid.demo.dto.CarBidsDto;
import com.carbid.demo.service.BidServices;
import com.carbid.demo.service.BidServicesWithoutPaper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.encrypt.RsaAlgorithm;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class BidController_Admin {

    @Autowired
    BidServices bidServices;

    @Autowired
    BidServicesWithoutPaper bidServicesWithoutPaper;

    // with paper
    @GetMapping("all/bids")
    public List<CarBidsDto> getAllBid(){
        return bidServices.getBidsForAllCars();
    }

    @DeleteMapping("car/bid/{bidId}")
    public ResponseEntity<String> deleteBid(@PathVariable Long bidId) {
        return bidServices.deleteBid(bidId);
    }

    @PutMapping("all/bid/delete/{carId}")
    public ResponseEntity<?> updateDelete(@PathVariable Long carId){
        return bidServices.updateDelete(carId);
    }



    // without paper
    @GetMapping("all/bids/withoutPaper")
    public List<CarBidsDto> getAllBidWithoutPaper(){
//        System.out.println(principal.getName() + "----------------------------");
        return bidServicesWithoutPaper.getBidsForAllCars();
    }

    @DeleteMapping("car/bid/withoutPaper/{bidId}")
    public ResponseEntity<String> deleteBidWithoutPaper(@PathVariable Long bidId) {
        return bidServicesWithoutPaper.deleteBid(bidId);
    }

    @PutMapping("all/bidWithoutPaper/delete/{carId}")
    public ResponseEntity<?> updateDeleteWithoutPaper(@PathVariable Long carId){
        return bidServicesWithoutPaper.updateDelete(carId);
    }
}

