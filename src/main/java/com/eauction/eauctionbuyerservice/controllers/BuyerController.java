package com.eauction.eauctionbuyerservice.controllers;

import com.eauction.eauctionbuyerservice.exception.ResourceNotFoundException;
import com.eauction.eauctionbuyerservice.models.Bid;
import com.eauction.eauctionbuyerservice.models.Product;
import com.eauction.eauctionbuyerservice.services.BuyerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;
@RestController
@RequestMapping("/e-auction/api/v1/buyer")
public class BuyerController {

    @Autowired
    private BuyerService buyerService;

    private static final Logger log = LoggerFactory.getLogger(BuyerController.class);

    @PostMapping(value = "/place-bid")
    public ResponseEntity<?> placeBid(@RequestBody Bid bid) {
        try {
            Optional<Product> product = buyerService.getProduct(bid.getProductId());
            if(!product.isPresent()) {
                throw new ResourceNotFoundException("Cannot find any product with given ID-" + "ProductId must be of an existing product available for auction");
            }
            Optional<Bid> addBid = buyerService.getBidByProduct(bid.getProductId(), bid.getEmail());
            if(addBid.isPresent()) {
                throw new RuntimeException("More than one bid on a product by same user is not allowed");
            }
            if(new Date().after(product.get().getBidEndDate())) {
                throw new RuntimeException("Bid cannot be placed after product bid end date");
            }
            Bid addedBid = buyerService.saveBid(bid);
            log.info("Bid added: {}", addedBid);
            return new ResponseEntity<>(addedBid, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Bid not added: ",e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/update-bid/{productId}/{buyerEmailId}/{newBidAmount}")
    public ResponseEntity<?> updateBid(@PathVariable(value = "productId") String productId,
                                       @PathVariable(value = "buyerEmailId") String buyerEmailId,
                                       @PathVariable(value = "newBidAmount") double newBidAmount) {
        try {
            Optional<Product> product = buyerService.getProduct(productId);
            if(!product.isPresent()) {
                throw new ResourceNotFoundException("Cannot find any product with the given Id. "+
                        "Product Id must be of an existing product available for auction.");
            }
            if(new Date().after(product.get().getBidEndDate())) {
                throw new RuntimeException("Bid cannot be updated after product bid end date.");
            }
            Bid updatedBid = buyerService.updateBid(productId, buyerEmailId, newBidAmount);
            log.info("Bid details updated: {}", updatedBid);
            return new ResponseEntity(updatedBid, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Bid details not updated:", e);
            return new ResponseEntity("Bid details not updated", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}