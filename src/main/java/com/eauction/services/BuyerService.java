package com.eauction.services;

import com.eauction.models.Bid;
import com.eauction.models.Product;
import com.eauction.repository.BidRepository;
import com.eauction.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BuyerService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BidRepository bidRepository;

    private static final Logger log = LoggerFactory.getLogger(BuyerService.class);

    public Bid saveBid(Bid bid) {
        return bidRepository.save(bid);
    }

    public Bid updateBid(String productId, String emailId, double bidAmount) {
        Optional<Bid> bid = bidRepository.findByProductIdAndEmail(productId, emailId);
        Bid updatedBid = bid.get();
        updatedBid.setBidAmount(bidAmount);
        return bidRepository.save(updatedBid);
    }

    public List<Bid> getAllBids(String producId) {
        return bidRepository.findAllByProductId(producId);
    }

    public Optional<Product> getProduct(String productId) {
        return productRepository.findById(productId);
    }

    public Optional<Bid> getBidByProduct(String productId, String email) {
        return bidRepository.findByProductIdAndEmail(productId, email);
    }
}
