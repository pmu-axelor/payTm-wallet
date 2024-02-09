package org.example.controller;

import org.example.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/wallet")
public class WalletController {

    @Autowired
    WalletService walletService;

    @GetMapping("/balance/{id}")
    public ResponseEntity<Double> getWalletBalance(@PathVariable("id") Long userId){
        return new ResponseEntity<>(walletService.getWalletBalance(userId), HttpStatus.OK);
    }

}
