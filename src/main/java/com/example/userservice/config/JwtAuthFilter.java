package com.example.userservice.config;

import com.example.userservice.base.ResponseBuilder;
import com.example.userservice.service.auth.AuthService;
import com.example.userservice.uitls.JsonUtils;
import io.jsonwebtoken.Jwts;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Component
@Primary
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {
    private final AuthService authService;

    public JwtAuthFilter(AuthService authService) {
        this.authService = authService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
       String authHeader = request.getHeader("Authorization");
       String authToken;
        if (!StringUtils.isBlank(authHeader) && authHeader.startsWith("Bearer ")) {
            authToken = authHeader.replace("Bearer ", "");
            authToken = authToken.trim();
        } else {
            authToken = authHeader;
            authToken = authToken.trim();
        }

        UserDetails userDetails = null;
        if (!StringUtils.isEmpty(authToken)) {
//            userDetails = serverSecurityService.get(authToken);
            //TODO: verify token
            var httpStatus = this.authService.verify(authToken);
            if (Objects.equals(httpStatus, 403)) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("text/plain; charset=UTF-8");
                response.getWriter().write(JsonUtils.stringify(ResponseBuilder.error(401,"Token quá hạn hoặc không hợp lệ để thực hiện chức năng này." )));
                return;
            }
        } else {
            throw new AuthenticationCredentialsNotFoundException("Không tìm thấy token");
        }
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails == null ? List.of() : userDetails.getAuthorities());

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return !path.startsWith("/api") || path.startsWith("/api/ping")
                || path.startsWith("/api/v1/auth/token")
                || path.startsWith("/api/test-redis");
    }

}
