package com.be.service.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.be.model.Employer;
import com.be.service.impl.UserDetailsImpl;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployerDetails extends UserDetailsImpl {
	private static final long serialVersionUID = -7922651730893684620L;

	public EmployerDetails() {
		super();
	}

	public EmployerDetails(Long id, String email, String name, String password, Boolean active,
			Collection<? extends GrantedAuthority> authorities) {
		super(id, email, password, name, active, authorities);

	}

	public static EmployerDetails build(Employer user) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_EMPLOYER"));

		return new EmployerDetails(user.getId(), user.getEmail(), user.getName(), user.getPassword(), user.getActive(),
				authorities);
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return active;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return active;
	}

}
