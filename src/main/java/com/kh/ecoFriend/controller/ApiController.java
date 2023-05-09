package com.kh.ecoFriend.controller;

import com.kh.ecoFriend.api.config.email.RegisterMail;
import com.kh.ecoFriend.api.entity.Item;
import com.kh.ecoFriend.dao.ApiDAO;
import com.kh.ecoFriend.dao.MemberDAO;
import com.kh.ecoFriend.util.SessionManager;
import com.kh.ecoFriend.vo.Member;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Base64;
import java.util.List;
import java.util.Map;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class ApiController {

  private final static ApiDAO apiDAO = new ApiDAO();
  private final static MemberDAO memberDAO = new MemberDAO();
  private final static SessionManager sessionManager = new SessionManager();
  private final static RegisterMail confirm = new RegisterMail();

  @PostMapping("/list")
  @ApiOperation(value = "공공데이터 조회", notes = "전기차 충전소에 대한 각종 실시간 공공데이터를 받아온다.")
  public ResponseEntity<List<Item>> getApi(@RequestBody Map<String, String> request) {
    ResponseEntity<String> jsonData = apiDAO.getData(request);
    return new ResponseEntity<>(apiDAO.getItem(jsonData), HttpStatus.OK);
  }

  // 구글 로그인
  @PostMapping("/googleLogin")
  @ApiOperation(value = "구글 로그인 기능", notes = "리액트에서 구글 로그인을 시도하면 보내오는 jwt 데이터의 payLoad부분을 파싱하여 사용자 정보를 읽어온다.")
  public ResponseEntity<Boolean> emailLogin(@RequestBody String jwt, HttpServletResponse response) {
    final String payloadJWT = jwt.split("\\.")[1];
    Base64.Decoder decoder = Base64.getUrlDecoder();

    final String payload = new String(decoder.decode(payloadJWT));
    JsonParser jsonParser = new BasicJsonParser();
    Map<String, Object> jsonArray = jsonParser.parseMap(payload);
    String id = jsonArray.get("email").toString();
    boolean verified = (boolean) jsonArray.get("email_verified");
    boolean isLogin = memberDAO.googleLogin(id, verified);

    if (isLogin) {
      Member member = (Member) memberDAO.getMemberData(id);
      sessionManager.createSession(member, response);
    }
    return new ResponseEntity<>(isLogin, HttpStatus.OK);
  }
  // 이메일 인증
  @PostMapping("/emailConfirm")
  @ApiOperation(value = "이메일 인증 기능")
  public String emailConfirm(@RequestBody Map<String, String> request) throws Exception {
    String email = request.get("email");
    System.out.println("conEmail : " + email);
    String code = confirm.sendSimpleMessage(email);
    System.out.println("인증코드 : " + code);
    return code;
  }
  @PostMapping("/keyTest")
  @ApiOperation(value = "저장된 키 확인")
  public ResponseEntity<Boolean> beanTest(@RequestBody Map<String, String> request) {
    String email = request.get("email");
    String key = request.get("key");

    boolean isConfirm = apiDAO.getConfirmKey(email, key);

    return new ResponseEntity<>(isConfirm, HttpStatus.OK);
  }
}
