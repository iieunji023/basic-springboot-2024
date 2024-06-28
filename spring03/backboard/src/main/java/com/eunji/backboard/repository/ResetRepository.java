package com.eunji.backboard.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eunji.backboard.entity.Reset;

public interface ResetRepository extends JpaRepository<Reset, Integer>{
  Optional<Reset> findByUuid(String uuid);  // UUID 테이블 검색
  
}
