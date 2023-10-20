package ntnu.idata2503.group9.stockappbackend.Security;
import ntnu.idata2503.group9.stockappbackend.Models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


/**
 * Contains authentication infromation needed by UserDetailsService
 */
public class AccessUserDetails implements UserDetails {

    private final String email;
    private final String password;
    private final boolean isActive;
    private final Set<GrantedAuthority> authorities = new HashSet<>();

    public AccessUserDetails(User user) {
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.isActive = user.isActive();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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
        return this.isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.isActive;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}