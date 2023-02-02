package nl.briansporkslede.workshopper.dto;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Validated
public class AuthInputDto {
    @NotBlank
    public String username;
    @NotBlank
    public String password;
}
