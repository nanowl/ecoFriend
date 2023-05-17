package com.kh.ecoFriend.util;

import com.kh.ecoFriend.controller.MemberController;
import com.kh.ecoFriend.vo.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionManager {
  private Map<String, Member> sessionStore = new ConcurrentHashMap<>();
  private static final Logger LOGGER = LoggerFactory.getLogger(MemberController.class);

  /**
   * 세션 생성
   */
  public String createSession(Member value){

    LOGGER.info("memberInfo : " + value.toString());
    // 세션 id를 생성하고, 값을 세션에 저장
    String sessionId = UUID.randomUUID().toString();
    sessionStore.put(sessionId, value);
    LOGGER.info("session : " + sessionStore.get(sessionId));
    LOGGER.info("session : " + sessionStore.keySet());
    LOGGER.info("session : " + sessionStore.size());
    // 쿠키 생성
    return sessionId;
  }

  /**
   * 세션 조회
   */
  public Member getSession(String uuid){
    LOGGER.info("uuid : " + uuid);
    return sessionStore.get(uuid);
  }

  /**
   * 세션 만료
   */
  public void expire(String uuid){
    Member member = getSession(uuid);
    LOGGER.info("삭제할 session : " + member.getCustEmail());
    sessionStore.remove(uuid);
  }

//  private Cookie findCookie(HttpServletRequest request, String cookieName) {
//    return Arrays.stream(request.getCookies())
//      .filter(cookie -> cookie.getName().equals(cookieName))
//      .findAny()
//      .orElse(null);
//  }

}
