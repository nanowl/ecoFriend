package com.kh.ecoFriend.config;

import com.kh.ecoFriend.vo.member.CustomUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

import com.kh.ecoFriend.vo.member.Autholity;
import java.util.List;

// 토큰을 생성하고 검증하는 클래스입니다.
// 해당 컴포넌트는 필터클래스에서 사전 검증을 거칩니다.
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
//  @Value("${jwt.secret.key}")
//  private String salt;

  private String secretKey = "jwtTokenTest";

  // 만료시간 : 1Hour
  private final long exp = 1000L * 60 * 60;

  private final CustomUserDetailsService userDetailsService;

  @PostConstruct
  protected void init() {
    secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
  }

  // 토큰 생성
  public String createToken(String account, List<Autholity> roles) {
    Claims claims = Jwts.claims().setSubject(account);
    claims.put("roles", roles);
    Date now = new Date();
    return Jwts.builder()
      .setClaims(claims)
      .setIssuedAt(now)
      .setExpiration(new Date(now.getTime() + exp))
      .signWith(SignatureAlgorithm.HS256, secretKey)
      .compact();
  }

  // 권한정보 획득
  // Spring Security 인증과정에서 권한확인을 위한 기능
  public Authentication getAuthentication(String token) {
    UserDetails userDetails = userDetailsService.loadUserByUsername(this.getEmail(token));
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  // 토큰에 담겨있는 유저 email 획득
  public String getEmail(String token) {
    return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
  }

  // Authorization Header를 통해 인증을 한다.
  public String resolveToken(HttpServletRequest request) {
    return request.getHeader("Authorization");
  }

  // 토큰 검증
  public boolean validateToken(String token) {
    try {
      // Bearer 검증
      if (!token.substring(0, "BEARER ".length()).equalsIgnoreCase("BEARER ")) {
        return false;
      } else {
        token = token.split(" ")[1].trim();
      }
      Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
      // 만료되었을 시 false
      return !claims.getBody().getExpiration().before(new Date());
    } catch (Exception e) {
      return false;
    }
  }
}
