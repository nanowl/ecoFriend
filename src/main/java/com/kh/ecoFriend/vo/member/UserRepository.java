package com.kh.ecoFriend.vo.member;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUserEmail(String email);
}

