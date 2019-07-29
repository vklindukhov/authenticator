package vklindukhov.authenticator;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

import static java.util.Collections.emptyList;

@Data
@Entity
@Table(name = "user_details")
public class UserDetailsEntity implements UserDetails {
    @Id
    @OneToOne
    private User user;

    @ManyToMany(mappedBy = "users")
    private Set<SecurityGroup> groups;

    UserDetailsEntity(User user, Set<SecurityGroup> groups) {
        this.user = user;
        this.groups = groups;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return emptyList();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.isVisible();
    }
}