package com.eunji.backboard.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailService {
  private final JavaMailSender javaMailSender;
  private final ResetService resetService;    // ResetService는 예외
  // private final PasswordEncoder passwordEncoder;

  // 메일에서 초기화할 화면으로 이동 URL
  private String resetPassUrl = "http://localhost:8080/member/reset-password";

  @Value("${spring.mail.username}")
  private String from;

  // 중복되지 않는 ID생성
  private String makeUuid() {
    return UUID.randomUUID().toString();
  }

  public void sendMail(String to, String subject, String message){
    MimeMessage mimeMessage = javaMailSender.createMimeMessage();   // MIME type 설정

    try {
      // MimeMessageHelper로 MimeMessage 구성, 이메일에 작성되는 글은 UTF-8로 생성
      MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, false, "UTF-8");
      // 이메일 수신자 설정
      mmh.setTo(to);
      // 이메일 제목 설정
      mmh.setSubject(subject);
      // 본문 내용 설정
      mmh.setText(message, true);
      // 이메일 발신자 설정
      mmh.setFrom(new InternetAddress(from));
      // 이메일 전송
      javaMailSender.send(mimeMessage);
      
    } catch (MessagingException e) {
      throw new RuntimeException();
      
    }
  }

  // 패스워드 초기화 메일 전송 METHOD(!!!)
  @Transactional
  public Boolean sendResetPasswordEmail(String email) {
    String uuid = makeUuid();
    String subject = "요청하신 비밀번호 재설정입니다.";
    String message = "BackBoard"
                  +  "<br><br>" + "아래 링크를 클릭하면 비밀번호 재설정 페이지로 이동합니다." + "<br>"
                  +  "<a href='" + resetPassUrl + "/" + uuid + "'>"
                  +  resetPassUrl + "/" + uuid + "</a>" + "<br><br>";

    try{
      sendMail(email, subject, message);
      saveUuidAndEmail(uuid, email);

      return true;

    }catch(Exception e) {
      return false;

    }
  }

  // uuid 정보를 db에 넣기 위한 method
  private void saveUuidAndEmail(String uuid, String email) {
    this.resetService.setReset(uuid, email);
  }

}
