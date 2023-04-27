package com.kh.ecoFriend.controller;

import com.kh.ecoFriend.vo.member.MemberVO;
import com.kh.ecoFriend.vo.member.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {
  private final UserRepository userRepository;
  @PostMapping(value = "/login")
  public ResponseEntity<SignResponse> signin(@RequestBody SignRequest request) throws Exception {
    return new ResponseEntity<>(memberService.login(request), HttpStatus.OK);
  }

}
