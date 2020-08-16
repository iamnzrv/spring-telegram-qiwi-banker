package com.iamnzrv.paybot.repository;

import com.iamnzrv.paybot.model.user.ApplicationUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface ApplicationUserRepository extends CrudRepository<ApplicationUser, Long> {
  Optional<UserDetails> findUserByUsername(String username);
  Optional<ApplicationUser> findAccountByUsername(String username);
}
