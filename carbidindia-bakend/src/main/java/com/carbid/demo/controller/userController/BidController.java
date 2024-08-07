package com.carbid.demo.controller.userController;

import com.carbid.demo.dto.BidDto;
import com.carbid.demo.dto.CarBidCountDTO;
import com.carbid.demo.dto.CommonBidDto;
import com.carbid.demo.model.BidWithPaper;
import com.carbid.demo.service.BidServices;
import com.carbid.demo.service.BidServicesWithoutPaper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@PreAuthorize("hasRole('USER')")
public class BidController {

    @Autowired
    BidServices bidServices;

    @Autowired
    BidServicesWithoutPaper bidServicesWithoutPaper;

    @GetMapping("user")
    public String check(){
        return "user";
    }



    // with paper
    @PostMapping("/bids")
    public Map<String, Object> placeBid(@RequestBody BidDto bidDto, Principal principal) {
        return bidServices.placeBid(bidDto, principal.getName());
    }


    @GetMapping("/bid/count")
    public List<CarBidCountDTO> getBidCount(Principal principal){
        return bidServices.getAllCarBidCountsForUser(principal.getName());
    }

//    @GetMapping("/maximum/amount/{carId}")
//    public BigDecimal maximumBid(@PathVariable Long carId){
//        return bidServices.maximumBid(carId);
//    }

    @GetMapping("winning/bids/withPaper")
    public List<CommonBidDto> getWinningBidWithPaper(Principal principal){
        return bidServices.getWinningBid(principal.getName());
    }

    @GetMapping("all/waiting/bidWithPaper")
    public List<CommonBidDto> getAllWaitingBid(Principal principal){
        return bidServices.getAllWaitingBid(principal.getName());
    }

    //without paper

    // without paper bid

    @PostMapping("/bids/withoutPaper")
    public Map<String, Object> placeBidWithoutPaper(@RequestBody BidDto bidDto, Principal principal) {
        System.out.println(principal.getName());
        return bidServicesWithoutPaper.placeBid(bidDto, principal.getName());
    }

    @GetMapping("/bid/count/withoutPaper")
    public List<CarBidCountDTO> getBidCountWithoutPaper(Principal principal){
        return bidServicesWithoutPaper.getAllCarBidCountsForUser(principal.getName());
    }

    @GetMapping("winning/bids/withoutPaper")
    public List<CommonBidDto> getWinningBidWithoutPaper(Principal principal){
        return bidServicesWithoutPaper.getWinningBid(principal.getName());
    }

    @GetMapping("all/waiting/bidWithoutPaper")
    public List<CommonBidDto> getAllWaitingBidWithoutPaper(Principal principal){
        return bidServicesWithoutPaper.getAllWaitingBidWithoutPaper(principal.getName());
    }


}
