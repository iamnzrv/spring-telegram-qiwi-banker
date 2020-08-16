package com.iamnzrv.paybot.service;


import com.iamnzrv.paybot.exceptions.UserExceptions;
import com.iamnzrv.paybot.model.user.ApplicationUser;
import com.iamnzrv.paybot.model.user.Wallet;
import com.iamnzrv.paybot.repository.ApplicationUserRepository;
import com.iamnzrv.paybot.security.roles.ApplicationUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ApplicationUserService implements UserDetailsService {

  private final ApplicationUserRepository applicationUserRepository;
  private final PaymentService paymentService;

  private final PasswordEncoder passwordEncoder;

  @Autowired
  public ApplicationUserService(
      ApplicationUserRepository applicationUserRepository,
      PasswordEncoder passwordEncoder,
      PaymentService paymentService
      ) {
    this.applicationUserRepository = applicationUserRepository;
    this.passwordEncoder = passwordEncoder;
    this.paymentService = paymentService;
  }

  public ApplicationUser registerUser(ApplicationUser applicationUser, ApplicationUserRole role) throws InvalidParameterException {
    String username = applicationUser.getUsername();
    String password = applicationUser.getPassword();
    if (username != null && password != null && isDataValid(username, password)) {
      Optional<ApplicationUser> userByUsername = applicationUserRepository.findAccountByUsername(applicationUser.getUsername());
      if (userByUsername.isPresent()) {
        throw new UserExceptions.UserAlreadyExistsException();
      }
      applicationUser.setApplicationUserRole(role);
      applicationUser.setPassword(passwordEncoder.encode(applicationUser.getPassword()));
      applicationUser.setAccountNonExpired(true);
      applicationUser.setAccountNonLocked(true);
      applicationUser.setCredentialsNotExpired(true);
      applicationUser.setEnabled(true);
      ApplicationUser savedUser = applicationUserRepository.save(applicationUser);
      Wallet wallet = new Wallet(0L);
      wallet.setUser(savedUser);
      applicationUser.setWallet(paymentService.saveWallet(wallet));
      return applicationUserRepository.save(applicationUser);
    } else {
      throw new UserExceptions.UserDataIsInvalidException();
    }
  }

  public boolean isDataValid(String username, String password) {
    if (username.isEmpty() || password.isEmpty()) {
      return false;
    }
    Pattern usernamePattern = Pattern.compile("^[a-z0-9_-]{3,16}$");
    Pattern passwordPattern = Pattern.compile("^[a-zA-Z0-9_-]{6,18}$");
    Matcher u = usernamePattern.matcher(username);
    Matcher p = passwordPattern.matcher(password);
    return u.find() && p.find();
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return applicationUserRepository
        .findUserByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s not found", username)));
  }

  public Optional<ApplicationUser> loadAccountByUsername(String username) {
    return applicationUserRepository
        .findAccountByUsername(username);
  }

  public ApplicationUser loadUserById(Long id) {
    return applicationUserRepository
        .findById(id)
        .orElseThrow(UserExceptions.UserNotFoundException::new);
  }
}
