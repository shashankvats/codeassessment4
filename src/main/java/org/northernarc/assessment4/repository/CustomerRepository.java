package org.northernarc.assessment4.repository;

import org.northernarc.assessment4.dto.CustomerSummaryDTO;
import org.northernarc.assessment4.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // Task 3: Derived Query Method
    List<Customer> findByBranch(String branch);


    // Security Helper
    java.util.Optional<Customer> findByEmail(String email);
}
