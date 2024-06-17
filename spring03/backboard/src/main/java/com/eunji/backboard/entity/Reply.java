package com.eunji.backboard.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reply {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long rno;

  @Column(name = "content", length = 1000)
  private String content;

  @CreatedDate
  @Column(name = "createDate", updatable = false)     
  private LocalDateTime createDate;   // 글 생성일

  // 중요, ERD로 DB를 설계하지 않고 엔티티 클래스로 관계를 형성하려면 반드시 사용해야 한다.
  // RelationShip 다대일 설정
  @ManyToOne    // 1:다의 관계(보드가 1, 댓글이 다)
  private Board board;
  
}
