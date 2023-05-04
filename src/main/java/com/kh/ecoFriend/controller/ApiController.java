package com.kh.ecoFriend.controller;

import com.kh.ecoFriend.api.entity.Item;
import com.kh.ecoFriend.dao.ApiDAO;
import com.kh.ecoFriend.dao.MemberDAO;
import com.kh.ecoFriend.util.SessionManager;
import com.kh.ecoFriend.vo.Member;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class ApiController {

  private static final Logger LOGGER = LoggerFactory.getLogger(MemberController.class);
  private static final ApiDAO apiDAO = new ApiDAO();
  private static final MemberDAO memberDAO = new MemberDAO();
  private static final SessionManager sessionManager = new SessionManager();

  @PostMapping("/list")
  @ApiOperation(value = "공공데이터 조회", notes = "전기차 충전소에 대한 각종 실시간 공공데이터를 받아온다.")
  public ResponseEntity<List<Item>> getApi(@RequestBody Map<String, String> request) {
    ResponseEntity<String> jsonData = apiDAO.getData(request);
    return new ResponseEntity<>(apiDAO.getItem(jsonData), HttpStatus.OK);
  }

  // 구글 로그인
  @PostMapping("/googleLogin")
  @ApiOperation(value = "구글 로그인 기능", notes = "리액트에서 구글 로그인을 시도하면 보내오는 jwt 데이터의 payLoad부분을 파싱하여 사용자 정보를 읽어온다.")
  public void emailLogin(@RequestBody String jwt) {
    final String payloadJWT = jwt.split("\\.")[1];
    Base64.Decoder decoder = Base64.getUrlDecoder();

    final String payload = new String(decoder.decode(payloadJWT));
    JsonParser jsonParser = new BasicJsonParser();
    Map<String, Object> jsonArray = jsonParser.parseMap(payload);
    String id = jsonArray.get("email").toString();
    boolean verified = (boolean) jsonArray.get("email_verified");
    boolean isLogin = memberDAO.googleLogin(id, verified);
  }
}
