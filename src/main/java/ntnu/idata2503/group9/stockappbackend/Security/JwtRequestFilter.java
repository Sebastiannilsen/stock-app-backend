package ntnu.idata2503.group9.stockappbackend.Security;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * A filter that is applied to all HTTP requests and checks for a valid JWT
 * token in
 * the `Authorization: Bearer ...` header.
 *
 * @author gruppe 4
 * @version 1.0
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final static Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class.getSimpleName());
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Performs authentication and authorization based on a JWT (JSON Web Token)
     * extracted
     * from the "Authorization" header in the HTTP request.
     *
     * The method extracts the JWT from the header, validates it, and sets up the
     * authentication in the Spring Security context if the token is valid. The
     * authentication
     * information is then used for authorization throughout the application.
     *
     * @param request     The HTTP request object.
     * @param response    The HTTP response object.
     * @param filterChain The filter chain for invoking subsequent filters in the
     *                    chain.
     * @throws ServletException If an error occurs during the filter execution.
     * @throws IOException      If an I/O error occurs during the filter execution.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");
        String username = null;
        String jwt = null;
        try {
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                jwt = authorizationHeader.substring(7);
                username = jwtUtil.extractUsername(jwt);
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (jwtUtil.validateToken(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    upat.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(upat);
                }
            }
        } catch (JwtException ex) {
            logger.info("Error while parsing JWT token: " + ex.getMessage());
        }
        filterChain.doFilter(request, response);
    }
}
