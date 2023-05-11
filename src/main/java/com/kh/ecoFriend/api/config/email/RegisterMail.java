package com.kh.ecoFriend.api.config.email;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RegisterMail implements MailServiceInter {
  MailConfig mailConfig = new MailConfig();
  JavaMailSender emailSender = mailConfig.javaMailService(); // Bean 등록해둔 MailConfig 를 emailSender라는 이름으로 AutoWired
  private String ePw; // 인증번호

  // 발급된 키 값을 저장하는 객체 생성
  private static Map<String, String> confirmKey = new ConcurrentHashMap<>();

  public String getConKey(String email) {
    String key = confirmKey.get(email);
    return key;
  }
  public void setConKey(String email, String key) {
    confirmKey.put(email, key);
  }

  // 메일 내용 작성
  @Override
  public MimeMessage createMessage(String to) throws MessagingException, UnsupportedEncodingException {
    System.out.println("보내는 대상 : " + to);
    System.out.println("인증 번호 : " + ePw);

    MimeMessage message = emailSender.createMimeMessage();

    message.addRecipients(Message.RecipientType.TO, to); // 보내는 대상
    message.setSubject("회원가입 이메일 인증");

    String msg = "";
    msg += "<div style='margin:100px;'>";
    msg += "<h1> 안녕하세요</h1>";
    msg += "<h1> ECO-FRIEND 입니다.";
    msg += "<br>";
    msg += "<p>아래 코드를 회원가입 창으로 돌아가 입력해주세요</p>";
    msg += "<br>";
    msg += "<p>항상 당신의 미래를 응원합니다. 감사합니다!</p>";
    msg += "<br>";
    msg += "<div align='center' style='border:1px solid black; font-family:verdana; padding: 10px;'>";
    msg += "<h3 style='color:blue;'>회원가입 인증 코드 입니다.</h3>";
    msg += "<div style='font-size:130%'>";
    msg += "CODE : <strong>";
    msg += ePw + "</strong></div><br>";
    msg += "</div></div>";

    message.setText(msg, "utf-8", "html");
    message.setFrom(new InternetAddress("hanjy1101@naver.com", "ECO-FRIEND"));

    return message;
  }

  // 랜덤 인증 코드 전송
  @Override
  public String createKey() {
    StringBuffer key = new StringBuffer();
    Random rnd = new Random();

    for (int i = 0; i < 8; i++) {
      int index = rnd.nextInt(3);

      switch (index) {
        case 0 :
          key.append((char) ((int) (rnd.nextInt(26)) + 97 ));
          // a~z (ex. 1+97=98 => (char) 98 = 'b')
          break;
        case 1 :
          key.append((char) ((int) (rnd.nextInt(26)) + 65 ));
          // A-Z
          break;
        case 2 :
          key.append((rnd.nextInt(10)));
          // 0~9
          break;
      }
    }
    return key.toString();
  }

  // 메일발송
  // sendSimpleMessage 의 매개변수로 들어온 to 는 곧 이메일 주소가 되고,
  // MimeMessage 객체 안에 내가 전송할 메일의 내용을 담는다.
  // 그리고 Bean 으로 등록해둔 javaMail 객체를 사용해서 이메일 send!!
  @Override
  public String sendSimpleMessage(String to) throws Exception {
    System.out.println("regEmail : " + to);
    ePw = createKey();
    // TODO Auto-generated method stub
    MimeMessage message = createMessage(to);
    try {
      setConKey(to, ePw);
      emailSender.send(message);
    } catch (MailException e) {
      e.printStackTrace();
      throw new IllegalArgumentException();
    }
    return ePw;
  }
}
