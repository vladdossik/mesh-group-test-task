package mesh_group.test.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mesh_group.test.exception.JwtFilterException;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) {
        try {
            String header = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (header != null && header.startsWith("Bearer ")) {
                String token = header.substring(7);
                Long userId = jwtTokenProvider.validateAndGetUserId(token);
                if (userId != null) {
                    JwtAuthenticationToken authentication = new JwtAuthenticationToken(userId);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            log.error("Error while validating token", e);
        }
        try {
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            throw new JwtFilterException("Error while doing filter", ex);
        }
    }
}