package org.northernarc.assessment4.service;


import lombok.RequiredArgsConstructor;
import org.northernarc.assessment4.model.Customer;
import org.northernarc.assessment4.repository.CustomerRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Find the customer by email (acting as the username)
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Customer not found with email: " + email));

        // Assign a default role for the customer context mapping (e.g., ROLE_USER)
        // Note: For a dynamic production application, you can map an explicit 'role' column from the Customer entity
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_USER")
        );

        // Return a Spring Security core User instance carrying the secure state matrix
        return new User(
                customer.getEmail(),
                customer.getPassword(),
                authorities
        );
    }
}
