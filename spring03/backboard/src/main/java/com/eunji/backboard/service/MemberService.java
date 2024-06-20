package com.eunji.backboard.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.eunji.backboard.entity.Member;
import com.eunji.backboard.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;

  public Member setMember(String username, String email, String password) {
    Member member = Member.builder().username(username).email(email).build();

    BCryptPasswordEncoder pwdEncoder = new BCryptPasswordEncoder();
    member.setPassword(pwdEncoder.encode(password));    // 암호화한 값을 DB에 저장
    this.memberRepository.save(member);

    return member;

  }  
  
}
