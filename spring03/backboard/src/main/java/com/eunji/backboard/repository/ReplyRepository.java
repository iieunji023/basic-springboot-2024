package com.eunji.backboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eunji.backboard.entity.Reply;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long>{
  
}
