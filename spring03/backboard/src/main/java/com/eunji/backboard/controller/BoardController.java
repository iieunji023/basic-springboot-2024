package com.eunji.backboard.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eunji.backboard.entity.Board;
import com.eunji.backboard.service.BoardService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RequestMapping("/board")   // Restful URL은 /board로 시작
@Controller
@RequiredArgsConstructor
public class BoardController {
  private final BoardService boardService;

  // @RequestMapping("/list", method=RequestMethod.GET)
  // Model -> controller에 있는 객체를 View로 보내주는 역할을 하는 객체
  @GetMapping("/list")
  public String list(Model model) {
    List<Board> boardList = this.boardService.getList();    // Thymeleaf, JSP, mustache 등 VIEW로 보내는 기능
    model.addAttribute("boardList", boardList);
    
    return "board/list";    // templates/board/list.html 랜더링해서 리턴해라!
  }
  
  @GetMapping("/detail/{bno}")
  public String detail(Model model, @PathVariable("bno") Long bno) throws Exception {
    Board board = this.boardService.getBoard(bno);
    model.addAttribute("board", board);

    return "board/detail";
  }

}
