package com.restapi.rewards.controller;

import com.restapi.rewards.dto.CustomersTransactionSummryDto;
import com.restapi.rewards.dto.RewardPointsByCustomerDto;
import com.restapi.rewards.dto.TransactionRequestDto;
import com.restapi.rewards.dto.TransactionResponseDto;
import com.restapi.rewards.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Tag(name = "Transaction Points Api")
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/v1/transaction")
public class TransactionController {


    private final TransactionService transactionService;

    @Operation(summary = "find all transaction records", description = "This endpoint returns all transaction")
    @GetMapping("/")
    public ResponseEntity<List<TransactionResponseDto>> getAllTransaction() {
        return ResponseEntity.ok(transactionService.getAllTransaction());
    }

    @PostMapping("/")
    @Operation(summary = "Post a New Transaction", description = "This Endpoint commits a new transaction")

    public ResponseEntity<TransactionResponseDto> newTransaction(@Valid @RequestBody TransactionRequestDto requestDto) {
        return ResponseEntity.ok(transactionService.commitNewTransaction(requestDto));
    }

    @Operation(summary = "find all associated transaction with customer against email", description = "This endpoint returns all transaction")
    @GetMapping("/email")
    public ResponseEntity<List<TransactionResponseDto>> getAllTransactionByEmail(@RequestParam String email) {
        return ResponseEntity.ok(transactionService.findTransactionByEmail(email));
    }
    @Operation(summary = "find all associated transaction with customer against id", description = "This endpoint returns all transaction")
    @GetMapping("/id")
    public ResponseEntity<List<TransactionResponseDto>> getAllTransactionByCustomerIdWithDates(RewardPointsByCustomerDto customerDto) {
        return ResponseEntity.ok(transactionService.findTransactionByDateAndCustomerId(customerDto));
    }

    @Operation(summary = "Get Total Reward Points Of Each Customer By Date", description = "This Endpoint Returns Total Reward Points of each Customer")
    @GetMapping("/summary")
    public ResponseEntity<List<CustomersTransactionSummryDto>> getTransactionSummary(@NotNull @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                                                     @NotNull @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(transactionService.transactionSummry(startDate, endDate));
    }

}
