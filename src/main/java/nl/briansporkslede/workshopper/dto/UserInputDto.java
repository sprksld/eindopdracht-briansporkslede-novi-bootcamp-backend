package nl.briansporkslede.workshopper.dto;

import nl.briansporkslede.workshopper.model.Authority;
import nl.briansporkslede.workshopper.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

public class UserInputDto {
    public String username;
    private String password;    // must use method to ensure encryption
    public Boolean enabled;
    public String apikey;
    public String email;
    public Set<Authority> authorities;

    public String[] roles;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public User toClass() {
        User newUser = new User();

        newUser.setUsername(this.username);
        newUser.setPassword(this.password);
        newUser.setEnabled(this.enabled);
        newUser.setApikey(this.apikey);
        newUser.setEmail(this.email);

        return newUser;
    }
}
