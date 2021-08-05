package ru.geekbrains.shop.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {

    private Long id;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String repeatedPassword;

    private Set<RoleDTO> roles;

    public UserDTO(Long id, String username, Set<RoleDTO> roles) {
        this.id = id;
        this.username = username;
        this.roles = new HashSet<>(roles);
    }
}
