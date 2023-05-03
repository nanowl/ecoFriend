package com.kh.ecoFriend.controller;

import com.kh.ecoFriend.dao.MemberDAO;
import com.kh.ecoFriend.util.SessionManager;
import com.kh.ecoFriend.vo.Member;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

  private static final Logger LOGGER = LoggerFactory.getLogger(MemberController.class);
  private static final SessionManager sessionManager = new SessionManager();

  @PostMapping("/login")
  public ResponseEntity<Member> login(@RequestBody Map<String, String> request,
                                                   HttpServletResponse httpServletResponse) {
    MemberDAO dao = new MemberDAO();
    boolean isLogin = false;
    //전달 값 확인
    LOGGER.info("request : " + request);

    // 로그인 여부 검증
    isLogin = dao.loginCheck(request.get("email"), request.get("pwd"));
    if (isLogin) {
      Member member = dao.getMemberData(request.get("email"));
      sessionManager.createSession(member, httpServletResponse);
      return new ResponseEntity<>(member, HttpStatus.OK);
    }

    return new ResponseEntity<>(null, HttpStatus.OK);
  }

  @PostMapping("/session")
  public ResponseEntity<String> getSession(HttpServletRequest httpServletRequest) {
    Cookie[] cookies = httpServletRequest.getCookies();
    Arrays.asList(cookies).forEach(c -> LOGGER.info(c.getName() + ":" + c.getValue()));
    Member member = (Member) sessionManager.getSession(httpServletRequest);
    return new ResponseEntity<>(member.toString(), HttpStatus.OK);
  }

  @PostMapping("/test")
  public void Test(HttpServletRequest request) {
    LOGGER.info("" + sessionManager.getSession(request));
    sessionManager.expire(request);
  }
}


