package it.epicode.be.devicedistribution.models;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.Email;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccessDetails implements UserDetails {
	
	private static final long serialVersionUID = 1L;

	private Long id;
	private String username;
	private String email;

	@Email
	private String password;
	private Collection<? extends GrantedAuthority> authorities;	

	public static AccessDetails build(User u) {
		List<GrantedAuthority> authorities = u.getRoles().stream().map(
				role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList() );
		return new AccessDetails(u.getId(), u.getUsername(), u.getEmail(), u.getPassword(), authorities);
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
}