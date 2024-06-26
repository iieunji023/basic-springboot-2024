package com.eunji.backboard.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Log4j2
public class MainController {

    @GetMapping("/hello")
    public String hello() {
        log.info("getHello() 실행");
        return "hello";
    }

    @GetMapping("/")
    public String main() {
        return "redirect:/board/list/free";      // localhost8080/ -> localhost8080/board/list 변경
    }
    
}
