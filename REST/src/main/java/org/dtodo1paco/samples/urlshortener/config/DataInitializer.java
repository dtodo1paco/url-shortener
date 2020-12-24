package org.dtodo1paco.samples.urlshortener.config;

import org.dtodo1paco.samples.urlshortener.model.ServiceUser;
import org.dtodo1paco.samples.urlshortener.repository.ServiceUserRepository;
import org.dtodo1paco.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.dtodo1paco.samples.urlshortener.model.UserConstants.ROLE_ADMIN;

@Component
public class DataInitializer implements ApplicationRunner {

  private static final String USERNAME = "dtodo1paco@gmail.com";

  private ServiceUserRepository userRepository;

  public static ServiceUser getDefaultUser (String username, String password) {
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    ServiceUser defaultUser = new ServiceUser();
    defaultUser.setEnabled(true);
    defaultUser.setFullName("Admin user " + password);
    defaultUser.setPassword(passwordEncoder.encode(password));
    defaultUser.setUsername(username);
    defaultUser.setRole(ROLE_ADMIN);
    return defaultUser;
  }

  @Autowired
  public DataInitializer(ServiceUserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public void run(ApplicationArguments args) {
    List<ServiceUser> admins = userRepository.findByUsername(USERNAME);
    if (admins.isEmpty()) {
      String PASSWORD = StringUtil.generateSecureRandomPassword();
      userRepository.save(getDefaultUser(USERNAME, PASSWORD));
    }
  }

}
