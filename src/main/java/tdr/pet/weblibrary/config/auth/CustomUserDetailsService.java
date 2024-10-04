package tdr.pet.weblibrary.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import tdr.pet.weblibrary.model.entity.User;
import tdr.pet.weblibrary.service.UserService;

import java.util.Objects;

public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private Environment environment;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public CustomUserDetailsService(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            if (environment.containsProperty("app.root.user.name") && environment.containsProperty("app.root.user.password")
                    && Objects.equals(environment.getProperty("app.root.user.name"), username)) {
                return org.springframework.security.core.userdetails.User.builder()
                        .passwordEncoder(passwordEncoder::encode)
                        .username(username)
                        .password(environment.getProperty("app.root.user.password"))
                        .roles("ADMINISTRATOR")
                        .build();
            }
        } catch (IllegalArgumentException ignored) {
        }
        User user = userService.findByEmailOrUsername("", username);
        if (user.isActive()) {
            return org.springframework.security.core.userdetails.User.builder()
                    .passwordEncoder(passwordEncoder::encode)
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .roles(user.getUserRole().toString())
                    .build();
        } else {
            throw new UsernameNotFoundException("User is inactive");
        }
    }
}
