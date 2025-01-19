package mesh_group.test.security;

import lombok.EqualsAndHashCode;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

@EqualsAndHashCode(callSuper = true)
public class JwtAuthenticationToken extends AbstractAuthenticationToken {
    private final Long userId;

    public JwtAuthenticationToken(Long userId) {
        super(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        this.userId = userId;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return userId;
    }
}