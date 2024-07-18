package com.employee.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employee.management.domain.Address;

public interface AddressRepository extends JpaRepository<Address, Long>{

}
