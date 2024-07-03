package com.eunji.backboard.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {
  private Long num; // 게시글 번호, 24.07.03 신규추가
  private Long bno;
  private String title; 
  private String content; 
  private LocalDateTime createDate;
  private LocalDateTime modifyDate;
  private Integer hit;
  private String writer;
  private List<ReplyDto> replyList;

}
