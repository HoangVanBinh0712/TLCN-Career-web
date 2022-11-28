package com.be.service.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.be.model.Admin;
import com.be.service.impl.UserDetailsImpl;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminDetails extends UserDetailsImpl {
	private static final long serialVersionUID = 788715377934319508L;

	public AdminDetails() {
		super();
	}

	public AdminDetails(Long id, String email, String name, String password, Boolean active,
			Collection<? extends GrantedAuthority> authorities) {
		super(id, email, password, name, active, authorities);
	}

	public static AdminDetails build(Admin user) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

		return new AdminDetails(user.getId(), user.getEmail(), user.getName(), user.getPassword(), user.getActive(),
				authorities);
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String getUsername() {
		return email;
	}
}
