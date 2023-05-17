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
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

  private static final Logger LOGGER = LoggerFactory.getLogger(MemberController.class);
  private static final SessionManager sessionManager = new SessionManager();
  private static final MemberDAO memberDAO = new MemberDAO();

  //로그인
  @PostMapping("/login")
  @ApiOperation(value = "로그인", notes = "로그인하면 해당 데이터 모델을 세션에 저장한다.")
  public ResponseEntity<String> login(@RequestBody Map<String, String> request) {
    boolean isLogin = false;
    //전달 값 확인
    LOGGER.info("request : " + request);
    String uuid = "";
    // 로그인 여부 검증
    isLogin = memberDAO.loginCheck(request.get("email"), request.get("pwd"));
    if (isLogin) {
      Member member = memberDAO.getMemberData(request.get("email"));
      uuid = sessionManager.createSession(member);
//      return new ResponseEntity<>(, HttpStatus.OK);
    }
    System.out.println(isLogin);
    return new ResponseEntity<>(uuid, HttpStatus.OK);
  }

  @PostMapping("/signup")
  @ApiOperation(value = "회원가입")
  public ResponseEntity<Boolean> signUp(@RequestBody Map<String, String> request) {
    boolean isSignUp = false;
    isSignUp = memberDAO.signUpUserData(request);
    return new ResponseEntity<>(isSignUp, HttpStatus.OK );
  }



  // 세션 조회
  @PostMapping("/session")
  @ApiOperation(value = "세션조회", notes = "클라이언트가 보낸 세션의 아이디 값을 통해 세션 데이터를 조회한다.")
  public ResponseEntity<Member> getSession(@RequestParam(required = false) String uuid) {
//    Arrays.asList(cookies).forEach(c -> LOGGER.info(c.getName() + ":" + c.getValue()));
    Member member = null;
    member = (Member) sessionManager.getSession(uuid);
    return new ResponseEntity<>(member, HttpStatus.OK);
  }

  // 중복체크
  @GetMapping("/check")
  @ApiOperation(value = "이메일 중복체크")
  public ResponseEntity<Boolean> getMember(@RequestParam(required = false) String email) {
//    String email = request.get("email");
    // 조회가 되면 false, 안 되면 true
    boolean isCheck =  memberDAO.getCheckEmail(email);
    return new ResponseEntity<>(isCheck, HttpStatus.OK);
  }
  // 로그아웃
  @PostMapping("/logout")
  @ApiOperation(value = "로그아웃", notes = "세션을 삭제한다.")
  public void logOut(@RequestParam(required = false) String uuid) {
    LOGGER.info("" + sessionManager.getSession(uuid));
    sessionManager.expire(uuid);
  }

  @GetMapping("/list")
  @ApiOperation(value = "회원조회")
  public ResponseEntity<Member> getMemberInfo(@RequestParam(required = false) String email) {
    Member member = memberDAO.getMemberData(email);
    return new ResponseEntity<>(member, HttpStatus.OK);
  }
}


