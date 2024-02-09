package org.example.controller;

import org.example.dto.TransactionRequestDto;
import org.example.dto.TransactionResponseDto;
import org.example.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/transactions")
public class TransactionsController {

    @Autowired
    TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionResponseDto> initiateTransaction(TransactionRequestDto transactionRequestDto) throws Exception{
        return new ResponseEntity<>(transactionService.createTransaction(transactionRequestDto), HttpStatus.OK);
    }
}
