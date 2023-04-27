package com.kh.ecoFriend.dao;

import com.kh.ecoFriend.config.JwtTokenProvider;
import com.kh.ecoFriend.vo.member.Autholity;
import com.kh.ecoFriend.vo.member.MemberVO;
import com.kh.ecoFriend.vo.member.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberDAO {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtTokenProvider jwtProvider;

  public MemberVO login(String email) throws Exception {
    MemberVO member = userRepository.findByEmail();

    if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
      throw new BadCredentialsException("잘못된 계정정보입니다.");
    }

    return SignResponse.builder()
      .id(member.getId())
      .account(member.getAccount())
      .name(member.getName())
      .email(member.getEmail())
      .nickname(member.getNickname())
      .roles(member.getRoles())
      .token(jwtProvider.createToken(member.getAccount(), member.getRoles()))
      .build();

  }

}
