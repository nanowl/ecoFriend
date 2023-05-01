package com.kh.ecoFriend.dao;

import com.kh.ecoFriend.config.JwtTokenProvider;
import com.kh.ecoFriend.vo.member.User;
import com.kh.ecoFriend.vo.member.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberDAO {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtTokenProvider jwtProvider;

  public User login(String email) throws Exception {
    User member = userRepository.findByUserEmail(email)
      .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));

    return User.builder()
      .userEmail(member.getUserEmail())
      .userBirth(member.getUserBirth())
      .userNickname(member.getUserNickname())
      .admin(member.getAdmin())
      .gender(member.getGender())
      .userSequenceId(member.getUserSequenceId())
      .roles(Collections.singletonList("ROLE_USER")) // 최초 가입시 USER 로 설정
      .build();
  }

}
