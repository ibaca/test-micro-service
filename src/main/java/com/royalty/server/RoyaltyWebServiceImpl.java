package com.royalty.server;

import com.royalty.api.RoyaltyWebService;
import com.royalty.api.RoyaltyPayment;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoyaltyWebServiceImpl implements RoyaltyWebService {
    @Autowired PaymentService paymentService;
    @Autowired ViewingService viewingService;

    public ResponseEntity<List<RoyaltyPayment>> getAllPayments() {
        HttpHeaders httpHeaders = new HttpHeaders();
        try {
            List<RoyaltyPayment> payments = paymentService.getAllPayments();
            return new ResponseEntity<>(payments, httpHeaders, HttpStatus.OK);
        } catch (ServiceException e) {
            return new ResponseEntity<>(null, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<RoyaltyPayment> readPayment(
            @PathVariable("rightsOwnerId") String rightsOwnerId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        try {
            RoyaltyPayment payment = paymentService.getPaymentById(rightsOwnerId);
            return new ResponseEntity<>(payment, httpHeaders, HttpStatus.OK);
        } catch (ServiceException e) {
            return new ResponseEntity<>(null, httpHeaders, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> createViewing(
            @RequestParam(value = "episode") String episode,
            @RequestParam(value = "customer") String customer) {
        HttpHeaders httpHeaders = new HttpHeaders();
        try {
            viewingService.createViewing(episode, customer);
        } catch (ServiceException e) {
            return new ResponseEntity<>(httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(httpHeaders, HttpStatus.OK);
    }

    public ResponseEntity<?> resetViewing() {
        HttpHeaders httpHeaders = new HttpHeaders();
        try {
            viewingService.resetViewing();
        } catch (ServiceException e) {
            return new ResponseEntity<>(httpHeaders, HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(httpHeaders, HttpStatus.OK);
    }
}