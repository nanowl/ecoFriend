package com.kh.ecoFriend.vo.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
public interface UserRepository extends JpaRepository<MemberVO, Long> {
  Optional<MemberVO> findByEmail(String email);
}

