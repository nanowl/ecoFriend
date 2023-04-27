package com.kh.ecoFriend.vo.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
  private final UserRepository memberRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    MemberVO member = memberRepository.findByEmail(username).orElseThrow(
      () -> new UsernameNotFoundException("Invalid authentication!")
    );

    return new UserDetails(member);
  }
}
