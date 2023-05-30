package com.eauction.eauctionbuyerservice.repository;

import com.eauction.eauctionbuyerservice.models.Bid;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface BidRepository extends MongoRepository<Bid, String> {

    List<Bid> findAllByProductId(String productId);

    Optional<Bid> findByProductIdAndEmail(String productId, String emailId);
}
