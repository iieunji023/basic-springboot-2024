package com.eunji.backboard.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;   // 복합쿼리 생성용
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import com.eunji.backboard.common.NotFoundException;
import com.eunji.backboard.entity.Board;
import com.eunji.backboard.entity.Category;
import com.eunji.backboard.entity.Member;
import com.eunji.backboard.entity.Reply;
import com.eunji.backboard.repository.BoardRepository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
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
    List<Sort.Order> sorts = new ArrayList<>();
    sorts.add(Sort.Order.desc("createDate"));
    Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));   // pageSize를 동적으로도 변경할 수 있음. 나중에...
    
    return this.boardRepository.findAll(pageable);

  }

  // 24.06.24 검색 추가 메서드
  public Page<Board> getList(int page, String keyword) {
    List<Sort.Order> sorts = new ArrayList<>();
    sorts.add(Sort.Order.desc("createDate"));
    Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));   // pageSize를 동적으로도 변경할 수 있음. 나중에...
    
    // Specification<Board> spec = searchBoard(keyword);
    // return this.boardRepository.findAll(spec, pageable);   // Specification 인터페이스로 쿼리 생성로직 만들어서
    return this.boardRepository.findAllByKeyword(keyword, pageable);

  }

  // 24.06.25 카테고리 추가
  public Page<Board> getList(int page, String keyword, Category category) {
    List<Sort.Order> sorts = new ArrayList<>();
    sorts.add(Sort.Order.desc("createDate"));
    Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));   // pageSize를 동적으로도 변경할 수 있음. 나중에...
    
    Specification<Board> spec = searchBoard(keyword, category.getId());
    return this.boardRepository.findAll(spec, pageable);   // Specification 인터페이스로 쿼리 생성로직 만들어서
    // return this.boardRepository.findAllByKeyword(keyword, pageable);

  }

  public Board getBoard(Long bno) {
    Optional<Board> board = this.boardRepository.findByBno(bno);
    if(board.isPresent()) {   // 데이터가 존재하면 (board가 null이 아니라면)
      return board.get();

    } else {
      throw new NotFoundException("Member not found!");

    }
  }

  // 24.06.21. Member 추가
  public void setBoard(String title, String content, Member writer){
    // 빌더로 생성한 객체
    Board board = Board.builder().title(title).content(content)
                       .createDate(LocalDateTime.now()).build();

    board.setWriter(writer);

    // 객체 저장
    this.boardRepository.save(board); // PK가 없으면 INSERT
  }

  // 24.06.25 category 저장 추가
  public void setBoard(String title, String content, Member writer, Category category){
    // 빌더로 생성한 객체
    Board board = Board.builder().title(title).content(content)
                       .createDate(LocalDateTime.now()).build();

    board.setCategory(category);    // 카테고리 추가
    board.setWriter(writer);

    // 객체 저장
    this.boardRepository.save(board); // PK가 없으면 INSERT
  }

  // 24.06.24. modBoard 추가 작성
  public void modBoard(Board board, String title, String content){
    board.setTitle(title);
    board.setContent(content);
    board.setModifyDate(LocalDateTime.now()); // 수정된 일시 추가

    this.boardRepository.save(board);   // PK가 있으면 UPDATE
  }

  // 삭제
  public void remBoard(Board board) {
    this.boardRepository.delete(board);
  }

  // 검색쿼리 대신 검색 기능 생성
  public Specification<Board> searchBoard(String keyword) {
    return new Specification<Board>() {
      private static final long serialVersionUID = 1L;  // 필요한 값이라서 추가

      @Override
      @Nullable
      public Predicate toPredicate(Root<Board> b, CriteriaQuery<?> query, CriteriaBuilder cb) {
        // query를 JPA로 생성
        query.distinct(true); // 중복 제거
        Join<Board, Reply> r = b.join("replyList", JoinType.LEFT);

        return cb.or(cb.like(b.get("title"), "%" + keyword + "%"),    // 게시글 제목에서 검색
                     cb.like(b.get("content"), "%" + keyword + "%"),   // 게시글 내용에서 검색
                     cb.like(r.get("content"), "%" + keyword + "%") // 댓글 내용에서 검색
                     );
      }
    };
  }

  // 24.06.25 카테고리 추가된 메서드
  public Specification<Board> searchBoard(String keyword, Integer cateId) {
    return new Specification<Board>() {
      private static final long serialVersionUID = 1L;  // 필요한 값이라서 추가

      @Override
      @Nullable
      public Predicate toPredicate(Root<Board> b, CriteriaQuery<?> query, CriteriaBuilder cb) {
        // query를 JPA로 생성
        query.distinct(true); // 중복 제거
        Join<Board, Reply> r = b.join("replyList", JoinType.LEFT);

        return cb.and(cb.equal(b.get("category").get("id"), cateId),
                      cb.or(cb.like(b.get("title"), "%" + keyword + "%"),     // 게시글 제목에서 검색
                            cb.like(b.get("content"), "%" + keyword + "%"),   // 게시글 내용에서 검색
                            cb.like(r.get("content"), "%" + keyword + "%")    // 댓글 내용에서 검색
                            ));
      }
    };
  }

  // 조회수 증가 메서드
  @Transactional    // 조회하면서 업데이트하므로!
  public Board hitBoard(Long bno) {
    // Optional 기능 => Null 체크!
    Optional<Board> oboard = this.boardRepository.findByBno(bno);

    if(oboard.isPresent()) {
      Board board = oboard.get();
      // board.setHit(board.getHit() + 1);   // !!!!! 예외발생
      board.setHit(Optional.ofNullable(board.getHit()).orElse(0) + 1);    // getHit() 값이 null일때 0으로 바꿔라
      
      return board;

    } else {
      throw new NotFoundException("board NOT FOUND!!");
    }

  }

}
                                                                                               