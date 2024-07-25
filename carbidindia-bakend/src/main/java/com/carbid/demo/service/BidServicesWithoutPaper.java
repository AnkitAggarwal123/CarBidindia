package com.carbid.demo.service;

import com.carbid.demo.dto.*;
import com.carbid.demo.model.*;
import com.carbid.demo.repo.IBidWithoutPaper;
import com.carbid.demo.repo.ICar;
import com.carbid.demo.repo.IUser;
import com.carbid.demo.repo.IWinnerWithoutPaper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BidServicesWithoutPaper {

    @Autowired
    IBidWithoutPaper iBidWithoutPaper;

    @Autowired
    IWinnerWithoutPaper iWinnerWithoutPaper;

    @Autowired
    IUser iUser;

    @Autowired
    ICar iCar;

    public List<CarBidsDto> getBidsForAllCars() {
        List<Car> cars = iCar.findAll();
        List<BidWithoutPaper> bids = iBidWithoutPaper.findAll();

        List<CarBidsDto> carBidsDtos = new ArrayList<>();

        for (Car car : cars) {
            List<BidDetailsDto> bidDtos = bids.stream()
                    .filter(bid -> bid.getCar().getId().equals(car.getId()))
                    .map(bid -> new BidDetailsDto(bid.getId(), bid.getAmount(), bid.getUser().getName(), bid.getCreatedAt()))
                    .sorted((b1, b2) -> b2.getAmount().compareTo(b1.getAmount())) // Sort by amount in descending order
                    .collect(Collectors.toList());

            carBidsDtos.add(new CarBidsDto(car.getId(), car.getCarName(), car.getRegistrationNumber(), bidDtos));
        }

        return carBidsDtos;
    }


    @Transactional
    public String placeBid(BidDto bidDto, String userName) {
        User user = iUser.findByEmail(userName);


        Car car = iCar.findById(bidDto.getCarId())
                .orElseThrow(() -> new RuntimeException("Car not found"));

        // Retrieve all bids placed by the user on the specific car
        List<BidWithoutPaper> userBids = iBidWithoutPaper.findAllByCarIdAndUserId( car.getId(), user.getId());

        // Check if the user has already placed 20 bids on this car
        if (userBids.size() >= 20) {
            return "You have reached the maximum number of bids for this car";
        }

        // Create a new BidWithPaper instance
        BidWithoutPaper bid = new BidWithoutPaper();
        bid.setAmount(bidDto.getAmount());
        bid.setUser(user);
        bid.setCar(car);
        bid.setDelete(true);

        // Save the bid
        iBidWithoutPaper.save(bid);

        return "Bid placed successfully of withoutPaper";
    }

    public List<CarBidCountDTO> getAllCarBidCountsForUser(String userName) {

        List<Car> allCars = iCar.findAll();

        User user = iUser.findByEmail(userName);

        return allCars.stream()
                .map(car -> {
                    Integer bidCount = iBidWithoutPaper.countByCarIdAndUserId(car.getId(), user.getId());
                    return new CarBidCountDTO(car.getId(), car.getCarName(), bidCount);
                })
                .collect(Collectors.toList());
    }

    public ResponseEntity<String> deleteBid(Long bidId) {
        // Check if the bid exists
        if (!iBidWithoutPaper.existsById(bidId)) {
            return new ResponseEntity<>("Bid not found.", HttpStatus.NOT_FOUND);
        }

        // Find the bid to be deleted
        BidWithoutPaper bid = iBidWithoutPaper.findById(bidId).orElse(null);

        // Check if there are any winners associated with this bid
        if (bid != null) {
            List<WinnerWithoutPaper> winners = iWinnerWithoutPaper.findByBidWithoutPaper(bid);

            // Delete all winners associated with this bid
            if (winners != null && !winners.isEmpty()) {
                iWinnerWithoutPaper.deleteAll(winners);
            }
        }

        // Delete the bid
        iBidWithoutPaper.deleteById(bidId);

        return new ResponseEntity<>("Bid deleted successfully.", HttpStatus.OK);
    }

    public List<CommonBidDto> getWinningBid(String name) {

        List<BidWithoutPaper> bidWithoutPapers = iBidWithoutPaper.findByUserEmail(name);

        List<BidWithoutPaper> winningBid = bidWithoutPapers.stream().filter((bid)-> iWinnerWithoutPaper.existsByBidWithoutPaper(bid)).collect(Collectors.toList());

        List<CommonBidDto> commonBidDtos = winningBid.stream().map((bid)-> {
            CommonBidDto commonBidDto = new CommonBidDto();
            Car car = bid.getCar();
            if(car != null){
                commonBidDto.setCarName(bid.getCar().getCarName());
                commonBidDto.setRegistrationNumber(bid.getCar().getRegistrationNumber());
            }
            commonBidDto.setAmount(bid.getAmount());
            commonBidDto.setDate(convertToDate(bid.getCreatedAt()));
            return commonBidDto;
        }).collect(Collectors.toList());

        return commonBidDtos;

    }

    private Date convertToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public ResponseEntity<?> updateDelete(Long id) {

        List<BidWithoutPaper> bidWithoutPapers = iBidWithoutPaper.findAllByCarId(id);

        List<BidWithoutPaper> bidWithoutPaperList = bidWithoutPapers.stream().map((b)->
                                                                                   {b.setDelete(false);
                                                                                       return b;}).collect(Collectors.toList());
        iBidWithoutPaper.saveAll(bidWithoutPaperList);
        return ResponseEntity.ok("deleted successfully");
    }

    public List<CommonBidDto> getAllWaitingBidWithoutPaper(String name) {

        List<BidWithoutPaper> bid = iBidWithoutPaper.findByUserEmail(name);

        if (bid == null || bid.isEmpty()) {
            return Collections.emptyList();
        }

        List<CommonBidDto> commonBidDtos = bid.stream().filter((b)-> b.isDelete()).map((b)->{
            CommonBidDto commonBidDto = new CommonBidDto();
            commonBidDto.setAmount(b.getAmount());
            commonBidDto.setRegistrationNumber(b.getCar().getRegistrationNumber());
            commonBidDto.setDate(convertToDate(b.getCreatedAt()));
            commonBidDto.setCarName(b.getCar().getCarName());
            return commonBidDto;
        }).collect(Collectors.toList());

        return commonBidDtos;
    }
}
