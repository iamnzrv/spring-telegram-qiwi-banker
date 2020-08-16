package com.iamnzrv.paybot.controller.auth;

import com.iamnzrv.paybot.model.user.ApplicationUser;
import com.iamnzrv.paybot.security.roles.ApplicationUserRole;
import com.iamnzrv.paybot.service.ApplicationUserService;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/registration")
public class RegistrationController {

  private final ApplicationUserService applicationUserService;

  @Autowired
  public RegistrationController(ApplicationUserService applicationUserService) {
    this.applicationUserService = applicationUserService;
  }

  @PostMapping(path = "/register/user")
  public ApplicationUser registerUser(@NotNull @RequestBody ApplicationUser applicationUser) {
    return applicationUserService.registerUser(applicationUser, ApplicationUserRole.USER);
  }
}
