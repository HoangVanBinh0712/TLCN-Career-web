package com.be.utility.jwt;

import java.util.Date;
import java.util.function.Function;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.be.service.impl.UserDetailsImpl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtils {
	@Value("${career.app.jwtSecret}")
	private String jwtSecret;
	@Value("${career.app.jwtExpirationMs}")
	private Long jwtExpirationMs;

	@Value("${career.app.jwtRefreshTokenSecret}")
	private String jwtRefreshSecret;

	@Value("${career.app.jwtRefreshTokenExpirationMs}")
	private Long jwtRefhresExpirationMs;

	public String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");
		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.substring(7, headerAuth.length());
		}
		return null;
	}

	public String parseJwtRefresh(HttpServletRequest request) {
		String headerAuth = request.getHeader("refresh-token");
		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.substring(7, headerAuth.length());
		}
		return null;
	}

	public String parseJwt(String headerAuth) {
		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.substring(7, headerAuth.length());
		}
		return null;
	}

	public String generateJwtToken(Authentication authentication, String rolePrefix) {
		String username = ((UserDetailsImpl) authentication.getPrincipal()).getUsername();

		return Jwts.builder().setSubject(rolePrefix + username).setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
	}

	public String generateJwtRefreshToken(Authentication authentication, String rolePrefix) {
		String username = ((UserDetailsImpl) authentication.getPrincipal()).getUsername();

		return Jwts.builder().setSubject(rolePrefix + username).setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + jwtRefhresExpirationMs))
				.signWith(SignatureAlgorithm.HS512, jwtRefreshSecret).compact();
	}

	public String generateJwtToken(String username, String rolePrefix) {
		return Jwts.builder().setSubject(rolePrefix + username).setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
	}

	public String getUsernameWithRolePrefixFromJwtToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	public Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	private Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
	}

	//Refresh
	public String getUsernameWithRolePrefixFromJwtRefreshToken(String token) {
		return getClaimFrommRefreshToken(token, Claims::getSubject);
	}

	public Boolean isRefreshTokenExpired(String token) {
		final Date expiration = getExpirationDateFromRefreshToken(token);
		return expiration.before(new Date());
	}

	private Date getExpirationDateFromRefreshToken(String token) {
		return getClaimFrommRefreshToken(token, Claims::getExpiration);
	}

	private <T> T getClaimFrommRefreshToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromRefreshToken(token);
		return claimsResolver.apply(claims);
	}
	private Claims getAllClaimsFromRefreshToken(String refreshToken) {
		return Jwts.parser().setSigningKey(jwtRefreshSecret).parseClaimsJws(refreshToken).getBody();
	}

}
