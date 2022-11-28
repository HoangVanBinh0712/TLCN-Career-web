package com.be.service.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.be.model.User;
import com.be.service.impl.UserDetailsImpl;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetails extends UserDetailsImpl {
	private static final long serialVersionUID = 5049020068819796467L;

	public UserDetails() {
		super();
	}

	public UserDetails(Long id, String email, String name, String password, Boolean active,
			Collection<? extends GrantedAuthority> authorities) {
		super(id, email, password, name, active, authorities);

	}

	public static UserDetails build(User user) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

		return new UserDetails(user.getId(), user.getEmail(), user.getName(), user.getPassword(), user.getActive(),
				authorities);
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

	@Override
	public String getUsername() {
		return email;
	}

}
