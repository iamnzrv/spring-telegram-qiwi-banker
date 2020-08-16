package com.iamnzrv.paybot.model.user;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.iamnzrv.paybot.security.roles.ApplicationUserRole;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

@Entity
@Data
@Table(name = "application_users")
public class ApplicationUser implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", unique = true, nullable = false)
  private Long id;

  @Column(name = "login")
  private String username;

  @Column(name = "password")
  @JsonProperty(access = WRITE_ONLY)
  private String password;

  @OneToOne(cascade = CascadeType.REMOVE)
  @JoinColumn(name = "wallet_id")
  @EqualsAndHashCode.Exclude
  Wallet wallet;

  @Column(name = "granted_authorities")
  private ApplicationUserRole applicationUserRole;

  @Column(name = "is_account_non_expired")
  private boolean isAccountNonExpired;

  @Column(name = "is_account_non_locked")
  private boolean isAccountNonLocked;

  @Column(name = "is_credentials_non_expired")
  private boolean isCredentialsNotExpired;

  @Column(name = "is_account_enabled")
  private boolean isEnabled;

  public ApplicationUser() {
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return applicationUserRole.getGrantedAuthorities();
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return isAccountNonExpired;
  }

  @Override
  public boolean isAccountNonLocked() {
    return isAccountNonLocked;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return isCredentialsNotExpired;
  }

  @Override
  public boolean isEnabled() {
    return isEnabled;
  }
}
