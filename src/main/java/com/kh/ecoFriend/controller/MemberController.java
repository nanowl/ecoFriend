package com.kh.ecoFriend.controller;

import com.kh.ecoFriend.dao.MemberDAO;
import com.kh.ecoFriend.util.SessionManager;
import com.kh.ecoFriend.vo.Member;
import io.swagger.annotations.ApiOperation;
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


  //로그인
  @PostMapping("/login")
  @ApiOperation(value = "로그인", notes = "로그인하면 해당 데이터 모델을 세션에 저장한다.")
  public ResponseEntity<Boolean> login(@RequestBody Map<String, String> request,
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
//      return new ResponseEntity<>(, HttpStatus.OK);
    }

    return new ResponseEntity<>(isLogin, HttpStatus.OK);
  }


  // 세션 조회
  @PostMapping("/session")
  @ApiOperation(value = "세션조회", notes = "클라이언트가 보낸 세션의 아이디 값을 통해 세션 데이터를 조회한다.")
  public ResponseEntity<String> getSession(HttpServletRequest httpServletRequest) {
    Cookie[] cookies = httpServletRequest.getCookies();
    Arrays.asList(cookies).forEach(c -> LOGGER.info(c.getName() + ":" + c.getValue()));
    Member member = (Member) sessionManager.getSession(httpServletRequest);
    return new ResponseEntity<>(member.toString(), HttpStatus.OK);
  }

  // 포트포워딩 테스트
  @GetMapping("/list")
  @ApiOperation(value = "회원조회")
  public String getMember(@RequestParam("email") String email) {
    MemberDAO dao = new MemberDAO();
    Member member = dao.getMemberData(email);
    return member.toString();
  }
  // 로그아웃
  @PostMapping("/logout")
  @ApiOperation(value = "로그아웃", notes = "세션을 삭제한다.")
  public void logOut(HttpServletRequest request) {
    LOGGER.info("" + sessionManager.getSession(request));
    sessionManager.expire(request);
  }
}


