package com.discussion.csye6220.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		System.out.println("*** INSIDE OF doFilterInternal method in JWTAuthenticationFilter ***");

		String authorizationHeader = request.getHeader("Authorization");
		String jwt;
		String userEmail;

		// Checking if auth Header is present
		if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
			System.out.println("*** AUTH HEADER IS NOT PRESENT ***");
			filterChain.doFilter(request, response);
			return;
		}
		System.out.println("*** AUTH HEADER IS PRESENT ***");
		jwt = authorizationHeader.substring(7);
		userEmail = jwtUtil.extractUserEmail(jwt);

		System.out.println(userEmail);
		if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			if (SecurityContextHolder.getContext().getAuthentication() == null)
				System.out.println("*** USER IS NOT AUTHENTICATED ***");
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

			if (jwtUtil.isTokenValid(jwt, userDetails)) {
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
						null, userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			} else {
				System.out.println("*** TOKEN IS INVALID ***");
			}

		} else {
			System.out.println("*** USER IS ALREADY AUTHENTICATED ***");
		}

		filterChain.doFilter(request, response);

	}

}
