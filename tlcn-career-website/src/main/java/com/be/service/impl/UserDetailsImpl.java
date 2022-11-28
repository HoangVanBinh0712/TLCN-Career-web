package com.be.service.impl;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class UserDetailsImpl implements UserDetails {
	protected static final long serialVersionUID = 905992472963249600L;
	protected Long id;
	protected String email;
	protected String password;
	protected String name;
	protected Boolean active;
	protected Collection<? extends GrantedAuthority> authorities;

	protected UserDetailsImpl() {
		super();
	}

	protected UserDetailsImpl(Long id, String email, String password,
			String name, Boolean active, Collection<? extends GrantedAuthority> authorities) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.name = name;
		this.active = active;
		this.authorities = authorities;
	}
}
