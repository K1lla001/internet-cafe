package com.atm.inet.repository;

import com.atm.inet.entity.computer.Computer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ComputerRepository extends JpaRepository<Computer, String>, JpaSpecificationExecutor<Computer>{

    Optional<Computer> findByType_Id(String id);
}
