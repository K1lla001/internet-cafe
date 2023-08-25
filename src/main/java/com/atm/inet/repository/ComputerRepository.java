package com.atm.inet.repository;

import com.atm.inet.entity.computer.Computer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ComputerRepository extends JpaRepository<Computer, String>, JpaSpecificationExecutor<Computer>{
}
