package com.iamnzrv.paybot.security;

import com.iamnzrv.paybot.config.JwtConfig;
import com.iamnzrv.paybot.security.jwt.JwtProvider;
import com.iamnzrv.paybot.security.jwt.JwtTokenVerifier;
import com.iamnzrv.paybot.security.jwt.JwtUsernameAndPasswordAuthenticationFilter;
import com.iamnzrv.paybot.service.ApplicationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

  private final PasswordEncoder passwordEncoder;
  private final ApplicationUserService applicationUserService;
  private final JwtConfig jwtConfig;
  private final JwtProvider jwtProvider;

  @Autowired
  public ApplicationSecurityConfig(PasswordEncoder passwordEncoder,
                                   ApplicationUserService applicationUserService,
                                   JwtConfig jwtConfig,
                                   JwtProvider jwtProvider) {
    this.passwordEncoder = passwordEncoder;
    this.applicationUserService = applicationUserService;
    this.jwtConfig = jwtConfig;
    this.jwtProvider = jwtProvider;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(), jwtConfig, jwtProvider))
        .addFilterAfter(new JwtTokenVerifier(jwtConfig, jwtProvider), JwtUsernameAndPasswordAuthenticationFilter.class)
        .authorizeRequests()
        .antMatchers("/api/**").permitAll()
        .antMatchers("/admin/**").hasRole("ADMIN")
        .anyRequest()
        .authenticated();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) {
    auth.authenticationProvider(daoAuthenticationProvider());
  }

  @Bean
  public DaoAuthenticationProvider daoAuthenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setPasswordEncoder(passwordEncoder);
    provider.setUserDetailsService(applicationUserService);
    return provider;
  }

}
