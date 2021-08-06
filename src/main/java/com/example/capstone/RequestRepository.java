package com.example.capstone;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long>{

    List<Request> findAllByrequesterID(Long id);

}
