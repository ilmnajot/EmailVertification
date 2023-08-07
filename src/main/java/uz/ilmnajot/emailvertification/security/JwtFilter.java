package uz.ilmnajot.emailvertification.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.ilmnajot.emailvertification.service.CustomUserDetails;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    private final CustomUserDetails customUserDetails;

    public JwtFilter(JwtProvider jwtProvider, CustomUserDetails customUserDetails) {
        this.jwtProvider = jwtProvider;
        this.customUserDetails = customUserDetails;
    }

    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer ")) {
            String token = authorization.substring(7);
            String usernameFromToken = jwtProvider.getUsernameFromToken(token);
            if (usernameFromToken != null) {
                UserDetails userDetails = customUserDetails.loadUserByUsername(token);
                SecurityContextHolder.getContext().setAuthentication(
                        new UsernamePasswordAuthenticationToken(userDetails,null, new ArrayList<>() ));
            }
        }
        filterChain.doFilter(request, response);
    }
}
