package com.eunji.backboard.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 게시판 보드 테이블 엔티티
@Getter
@Setter
@Entity             // 테이블화
@Builder            // 객체 생성을 간략화
@NoArgsConstructor  // 파라미터 없는 기본 생성자 자동생성
@AllArgsConstructor // 멤버변수 모두를 파라미터로 가지는 생성자 자동생성
public class Board {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)   // 나중에 Oracle로 바꾸겠다는 의미
  private Long bno;                   // PK

  @Column(name = "title", length = 250)
  private String title;               // 글 제목

  @Column(name = "content", length = 4000)
  private String content;             // 글 내용

  @CreatedDate
  @Column(name = "createDate", updatable = false)          // updatable = false : 작성날짜 바꾸지 않겠다는 의미
  private LocalDateTime createDate;   // 글 생성일, 컬럼명 언더바(_)일 때 변수명은 카멜표기로 사용

  // 사용자가 여러 개의 게시글을 작성할 수 있다. 다대일 설정(Member가 부모, Board가 자식)
  @ManyToOne
  private Member writer;

  // 중요, RelationShip 일대다
  @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)    // 1:다의 관계(보드가 1, 댓글이 다), 게시글이 지워지면 하위에 있는 댓글도 지워진다.
  private List<Reply> replyList;

}
