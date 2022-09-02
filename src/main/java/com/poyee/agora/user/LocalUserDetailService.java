package com.poyee.agora.user;

import com.poyee.agora.entity.User;
import com.poyee.agora.exception.NotFoundException;
import com.poyee.agora.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service()
public class LocalUserDetailService implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Override
	@Transactional
	public LocalUser loadUserByUsername(final String email) throws UsernameNotFoundException {
		User user = userService.findByEmail(email);

		if (user == null) {
			throw new UsernameNotFoundException("user " + email + " was not found");
		}

		return createLocalUser(user);
	}

	@Transactional
	public LocalUser loadUserById(Long id) {
		User user = userService.findById(id).orElseThrow(() -> new NotFoundException("user" + id + " not found"));
		return createLocalUser(user);
	}

	private LocalUser createLocalUser(User user) {
		return new LocalUser(user.getEmail(), "", true, true, true, true, UserUtils.buildSimpleGrantedAuthorities(user.getRoles()), user);
	}
}
