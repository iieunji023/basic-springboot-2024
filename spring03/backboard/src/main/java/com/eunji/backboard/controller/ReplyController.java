package com.eunji.backboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eunji.backboard.entity.Board;
import com.eunji.backboard.service.BoardService;
import com.eunji.backboard.service.ReplyService;
import com.eunji.backboard.validation.ReplyForm;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RequestMapping("/reply")
@Controller
@RequiredArgsConstructor
@Log4j2
public class ReplyController {
  private final ReplyService replyService;
  private final BoardService boardService;

  @PostMapping("/create/{bno}")
  public String create(Model model, @PathVariable("bno") Long bno,
                      @Valid ReplyForm replyForm, BindingResult bindingResult) throws Exception {
      Board board = this.boardService.getBoard(bno);    // 게시글 정보 가져오기

      if(bindingResult.hasErrors()) {
        model.addAttribute("board", board);
        return "board/detail";
      }                  

      this.replyService.setReply(board, replyForm.getContent());
      log.info("[ReplyController] 댓글 저장 처리 완료");
      
      return String.format("redirect:/board/detail/%s", bno);
  }
}
