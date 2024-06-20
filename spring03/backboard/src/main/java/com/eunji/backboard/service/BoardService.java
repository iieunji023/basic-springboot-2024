package com.eunji.backboard.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.eunji.backboard.entity.Board;
import com.eunji.backboard.repository.BoardRepository;

import lombok.RequiredArgsConstructor;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class BoardService {
  private final BoardRepository boardRepository;

  public List<Board> getList() {
    return this.boardRepository.findAll();
  }

  // 페이징
  public Page<Board> getList(int page) {
    Pageable pageable = PageRequest.of(page, 10);   // pageSize를 동적으로도 변경할 수 있음. 나중에...

    List<Sort.Order> sorts = new ArrayList<>();
    sorts.add(Sort.Order.desc("createDate"));
    
    return this.boardRepository.findAll(pageable);

  }

  public Board getBoard(Long bno) throws Exception {
    Optional<Board> board = this.boardRepository.findByBno(bno);
    if(board.isPresent()) {   // 데이터가 존재하면 (board가 null이 아니라면)
      return board.get();

    } else {
      throw new Exception("board not found");

    }
  }

  public void setBoard(String title, String content){
    // 빌더로 생성한 객체
    Board board = Board.builder().title(title).content(content)
                       .createDate(LocalDateTime.now()).build();

    // 객체 저장
    this.boardRepository.save(board);
  }
  
}
