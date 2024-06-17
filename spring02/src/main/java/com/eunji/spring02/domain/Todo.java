package com.eunji.spring02.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Todo {
  private int tno;

  private String title;

  private LocalDateTime dueDate;  // db에서 언더바(_)있는 컬럼 카멜로 표기

  private String writer;

  private int isDone;
  
}
