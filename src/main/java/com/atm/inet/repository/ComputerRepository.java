package com.atm.inet.repository;

import com.atm.inet.entity.Computer;
import com.atm.inet.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComputerRepository extends JpaRepository<Computer, String> {
    List<Computer> findAllByType(Type type);
}
