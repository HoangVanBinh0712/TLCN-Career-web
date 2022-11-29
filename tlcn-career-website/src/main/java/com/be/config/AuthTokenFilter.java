package com.be.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.be.service.impl.UserDetailsServiceImpl;
import com.be.utility.jwt.JwtUtils;

public class AuthTokenFilter extends OncePerRequestFilter {
	@Autowired
	JwtUtils jwtUtils;
	@Autowired
	private CustomAuthenticationEntryPoint authenticationExceptionHandling;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// System.out.println(request.getRequestURI());
		if (!request.getRequestURI().contains("api/refreshtoken") && !request.getRequestURI().contains("api/post"))
			try {
				String token = jwtUtils.parseJwt(request);

				if (token != null && !jwtUtils.isTokenExpired(token)) {
					String usernameWithRolePrefix = jwtUtils.getUsernameWithRolePrefixFromJwtToken(token);

					UserDetails userDetails = userDetailsService.loadUserByUsername(usernameWithRolePrefix);
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			} catch (Exception e) {
				authenticationExceptionHandling.commence(request, response,
						new AuthenticationException(e.getMessage(), e) {
						});
				return;
			}

		filterChain.doFilter(request, response);
	}
}