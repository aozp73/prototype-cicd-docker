package com.portfolio.portfolio_project.core.config;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.portfolio.portfolio_project.domain.jpa.user.User;
import com.portfolio.portfolio_project.domain.jpa.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService{
	
	private final UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Optional<User> userEntity = userRepository.findByEmail(email);
		
		return userEntity.map(PrincipalDetails::new).orElseThrow(() -> new UsernameNotFoundException("올바르지 않은 정보입니다."));
	}
}
