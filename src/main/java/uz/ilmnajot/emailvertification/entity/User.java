package uz.ilmnajot.emailvertification.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.ilmnajot.emailvertification.enums.RoleName;

import java.util.Collection;
import java.util.Collections;

@EqualsAndHashCode(callSuper = true)
@Entity(name="users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends BaseEntity implements UserDetails {

    private String name;

    private String lastName;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private RoleName role;

    private String sendHashCode;

    private String confirmCode;


    private Boolean accountNonExpired = true;

    private Boolean accountNonLocked = true;

    private Boolean credentialsNonExpired = true;

    private Boolean enabled = false;

    public User(User user) {

    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public User(
            String name,
            String lastName,
            String email,
            String password,
            RoleName role,
            String sendHashCode,
            String confirmCode,
            Boolean enabled) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.sendHashCode = sendHashCode;
        this.confirmCode = confirmCode;
        this.enabled = enabled;
    }
}


