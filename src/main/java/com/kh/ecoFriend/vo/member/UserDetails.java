package com.kh.ecoFriend.vo.member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.stream.Collectors;

public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {

  private final MemberVO member;

  public UserDetails(MemberVO member) {
    this.member = member;
  }

  public final MemberVO getMember() {
    return member;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return member.getRoles().stream().map(o -> new SimpleGrantedAuthority(
      o.getName()
    )).collect(Collectors.toList());
  }

  @Override
  public String getPassword() {
    return member.getPwd();
  }

  @Override
  public String getUsername() {
    return member.getName();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}