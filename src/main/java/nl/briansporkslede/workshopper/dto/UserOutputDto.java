package nl.briansporkslede.workshopper.dto;

import nl.briansporkslede.workshopper.model.Authority;
import nl.briansporkslede.workshopper.model.Student;
import nl.briansporkslede.workshopper.model.Teacher;
import nl.briansporkslede.workshopper.model.User;

import java.util.Set;

public class UserOutputDto {
    public String username;
    public String password;
    public Boolean enabled;
    public String apikey;
    public String email;
    public Set<Authority> authorities;

    public Teacher mentor;
    public Student student;

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
        this.password = password;
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

    public Teacher getMentor() {
        return mentor;
    }

    public void setMentor(Teacher mentor) {
        this.mentor = mentor;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public UserOutputDto toDto(User user) {
        UserOutputDto dto = new UserOutputDto();

        dto.setUsername(user.getUsername());
        dto.setPassword( user.getPassword());
        dto.setEnabled( user.isEnabled());
        dto.setApikey( user.getApikey());
        dto.setEmail( user.getEmail());
        dto.setAuthorities( user.getAuthorities());
        dto.setMentor( user.getMentor());
        dto.setStudent( user.getStudent());
/*
        dto.username = user.getUsername();
        dto.password = user.getPassword();
        dto.enabled = user.isEnabled();
        dto.apikey = user.getApikey();
        dto.email = user.getEmail();
        dto.authorities = user.getAuthorities();
        dto.mentor = user.getMentor();
        dto.student = user.getStudent();
*/

        return dto;
    }
}
