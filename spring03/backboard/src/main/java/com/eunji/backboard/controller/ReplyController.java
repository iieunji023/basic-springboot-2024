package com.eunji.backboard.controller;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eunji.backboard.entity.Board;
import com.eunji.backboard.entity.Member;
import com.eunji.backboard.entity.Reply;
import com.eunji.backboard.service.BoardService;
import com.eunji.backboard.service.MemberService;
import com.eunji.backboard.service.ReplyService;
import com.eunji.backboard.validation.ReplyForm;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ResponseStatusException;

@RequestMapping("/reply")
@Controller
@RequiredArgsConstructor
@Log4j2
public class ReplyController {
  private final ReplyService replyService;
  private final BoardService boardService;
  private final MemberService memberService;    // 작성자 입력을 위해 추가

  // Principal 객체 추가하면 로그인한 사용자명(Member 객체)을 알 수 있음(Member 객체를 조회할 수 있다)
  @PreAuthorize("isAuthenticated()")  // 로그인시만 작성가능
  @PostMapping("/create/{bno}")
  public String create(Model model, @PathVariable("bno") Long bno,
                      @Valid ReplyForm replyForm, BindingResult bindingResult,
                       Principal principal) throws Exception {

      Board board = this.boardService.getBoard(bno);    // 게시글 정보 가져오기
      Member writer = this.memberService.getMember(principal.getName()); // principal.getName() -> 지금 로그인 중인 사람의 ID값

      if(bindingResult.hasErrors()) {
        model.addAttribute("board", board);
        return "board/detail";
      }                  

      Reply reply = this.replyService.setReply(board, replyForm.getContent(), writer);
      log.info("[ReplyController] 댓글 저장 처리 완료");
      
      return String.format("redirect:/board/detail/%s#reply_%s", bno, reply.getRno());  // 새로 생성된 댓글 위치로
  }

  @PreAuthorize("isAuthenticated()")  // 로그인시만 작성가능
  @GetMapping("/modify/{rno}")
  public String modify(ReplyForm replyForm, @PathVariable("rno") Long rno, Principal principal) {
    Reply reply = this.replyService.getReply(rno);

    if(!reply.getWriter().getUsername().equals(principal.getName())){
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
    }
    replyForm.setContent(reply.getContent());
    return "reply/modify";  // templates/reply/modify.html
  }

  @PreAuthorize("isAuthenticated()")  // 로그인시만 작성가능
  @PostMapping("/modify/{rno}")
  public String modify(@Valid ReplyForm replyForm, 
                       @PathVariable("rno") Long rno,
                        BindingResult bindingResult,
                        Principal principal) {
      if(bindingResult.hasErrors()) {
        return "reply/modify";
      }
      Reply reply = this.replyService.getReply(rno);

      if(!reply.getWriter().getUsername().equals(principal.getName())){
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
      }
      
      this.replyService.modReply(reply, replyForm.getContent());

      // 수정이 완료되는 그 댓글로 위치
      return String.format("redirect:/board/detail/%s#reply_%s", reply.getBoard().getBno(), reply.getRno());
  }

  // 삭제
  @PreAuthorize("isAuthenticated()")  // 로그인시만 작성가능
  @GetMapping("/delete/{rno}")
  public String delete(@PathVariable("rno") Long rno, Principal principal) {
    Reply reply = this.replyService.getReply(rno);

    if(!reply.getWriter().getUsername().equals(principal.getName())){
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
    }

    this.replyService.remReply(reply);    // 지우기

    return String.format("redirect:/board/detail/%s", reply.getBoard().getBno());

  }
  

}
