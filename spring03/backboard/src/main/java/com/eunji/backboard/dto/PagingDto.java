package com.eunji.backboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PagingDto {
  private int pageSize;       // 화면당 보여지는 게시글 최대개수
  private int totalPageNum;   // 총 페이지 수
  private long totalListSize; // 총 게시글 수

  private int page;           // 현재 페이지
  private int startPage;      // 시작 페이지 번호
  private int endPage;        // 마지막 페이지 번호
  
  private int startIndex;     // 시작 인덱스 번호

  private int block;          // 현재 블럭(구간)
  private int totalBlockNum;  // 총 블럭 수
  private int prevBlock;      // 이전 블럭
  private int nextBlock;      // 다음 블럭

  /*
   * 전체 리스트 크기와 현재 페이지와 페이지마다 나타낼 글 개수, 블럭 수를 가지고
   * 필요 변수들의 값을 계산하는 생성자
   */
  public PagingDto(Long totalListSize, Integer page, Integer pageSize, Integer blockSize ) {
    this.pageSize = pageSize;
    this.page = page;
    this.totalListSize = totalListSize;
    // 변수 값 계산
    // 전체 블럭 수 계산
    this.totalPageNum = (int) Math.ceil(this.totalListSize * 1.0 / this.pageSize);
    // 현재 블럭 계산
    this.block = (int) Math.ceil((this.page) * 1.0 / blockSize);
    // 한 블럭 시작페이지
    this.startPage = ((this.block - 1) * blockSize + 1);
    // 현재 블럭 마지막 페이지
    this.endPage = this.startPage + blockSize - 1;
    // 블럭 마지막 페이지 검증(한 블랙이 10페이지가 안넘으면 마지막 페이지를 최대 페이지 수로 다시 변경 10 -> 3)
    if(this.endPage > this.totalPageNum) this.endPage = this.totalPageNum;
    // 이전 블럭(클릭 시, 이전 블럭 마지막 페이지)
    this.prevBlock = (this.block * blockSize) - blockSize;
    // 이전 블럭 검증
    if(this.prevBlock < 1) this.prevBlock = 1;    // 1페이지보다 작을 순 없음
    // 다음 블럭 
    this.nextBlock = (this.block * blockSize + 1);
    // 다음 블럭 검증
    if(this.nextBlock > this.totalPageNum) this.nextBlock = this.totalPageNum;    // 전체 페이지 수보다 블럭 수가 클 순 없음
    // 시작 인덱스 번호
    this.startIndex = (this.page - 1) * this.pageSize;

  }
}
