package com.eunji.backboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.eunji.backboard.entity.Board;
import com.eunji.backboard.service.BoardService;
import com.eunji.backboard.service.ReplyService;

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
                       @RequestParam(value = "content") String content) throws Exception {
      Board board = this.boardService.getBoard(bno);    // 게시글 정보 가져오기
      this.replyService.setReply(board, content);

      log.info("[ReplyController] 댓글 저장 처리 완료");
      
      return String.format("redirect:/board/detail/%s", bno);
  }
}
