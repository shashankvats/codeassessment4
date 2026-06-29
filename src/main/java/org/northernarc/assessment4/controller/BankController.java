package org.northernarc.assessment4.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.northernarc.assessment4.dto.CustomerSummaryDTO;
import org.northernarc.assessment4.dto.DashboardResponse;
import org.northernarc.assessment4.model.Account;
import org.northernarc.assessment4.model.Customer;
import org.northernarc.assessment4.model.Transaction;
import org.northernarc.assessment4.service.BankService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BankController {

    private final BankService bankService;

    // --- Core Onboarding & Setup Endpoints ---
    @PostMapping("/customers")
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer) {
        return new ResponseEntity<>(bankService.saveCustomer(customer), HttpStatus.CREATED);
    }

    @PostMapping("/accounts")
    public ResponseEntity<Account> createAccount(@Valid @RequestBody Account account) {
        return new ResponseEntity<>(bankService.saveAccount(account), HttpStatus.CREATED);
    }

    // --- Task 6: Pagination & Sorting (Default: Balance DESC) ---
    @GetMapping("/accounts")
    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    public ResponseEntity<Page<Account>> getAllAccounts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        // Strictly balancing Sort criteria by Balance DESC as demanded by Task 6
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "balance"));
        return ResponseEntity.ok(bankService.getAllAccountsPaginated(pageable));
    }

    // --- Task 9: Role Based Access Control Endpoints ---
    @DeleteMapping("/accounts/{accountNumber}")
    @PreAuthorize("hasRole('ADMIN')") // Task 9: ADMIN can delete accounts
    public ResponseEntity<Void> deleteAccount(@PathVariable String accountNumber) {
        bankService.deleteAccount(accountNumber);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/accounts/{accountNumber}/balance")
    @PreAuthorize("hasRole('MANAGER')") // Task 9: MANAGER can update accounts
    public ResponseEntity<Void> updateAccountBalance(
            @PathVariable String accountNumber,
            @RequestParam double amount) {
        bankService.increaseAccountBalance(accountNumber, amount);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/accounts/view/{accountNumber}")
    @PreAuthorize("hasRole('USER')") // Task 9: USER can only view accounts
    public ResponseEntity<List<Account>> viewAccountsByType(@RequestParam String type) {
        return ResponseEntity.ok(bankService.getAccountsByType(type));
    }

    // --- Task 7: DTO Projection Aggregation ---
    @GetMapping("/customers/{customerId}/summary")
    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    public ResponseEntity<CustomerSummaryDTO> getCustomerSummary(@PathVariable Long customerId) {
        return null;
    }

    // --- Task 3 & 4: Query Operations ---
    @GetMapping("/customers/rich")
    public ResponseEntity<List<Customer>> getRichCustomers(@RequestParam double threshold) {
        return null;
    }

    @GetMapping("/branches/balances")
    public ResponseEntity<Map<String, Double>> getBranchBalances() {
        return null;
    }

    @GetMapping("/customers/multiple-accounts")
    public ResponseEntity<List<Customer>> getCustomersWithMultipleAccounts() {
        return null;
    }

    @GetMapping("/transactions/latest")
    public ResponseEntity<Transaction> getLatestTransaction() {
        return null;
    }

    @GetMapping("/accounts/idle")
    public ResponseEntity<List<Account>> getIdleAccounts() {
        return null;
    }

    // --- Final Challenge: Metrics Dashboard Generation ---
    @GetMapping("/dashboard")
    public ResponseEntity<DashboardResponse> getDashboardMetrics() {
        return null;
    }
}
