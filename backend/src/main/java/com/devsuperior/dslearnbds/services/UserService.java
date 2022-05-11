package com.devsuperior.dslearnbds.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dslearnbds.dtos.UserDTO;
import com.devsuperior.dslearnbds.entities.User;
import com.devsuperior.dslearnbds.repositories.UserRepository;
import com.devsuperior.dslearnbds.services.exceptions.ResourceNotFoundException;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private AuthService auth;
	
	@Transactional(readOnly = true)
	public UserDTO findById(Long id) {
		auth.validateUserAccess(id);
		var optional = repository.findById(id);
		User user = optional.orElseThrow(() -> new ResourceNotFoundException("Usuario não encontrado!"));
		return new UserDTO(user);		
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = repository.findByEmail(username);
		
		if (user == null) {
			throw new UsernameNotFoundException("Email não encontrado!");
		}
		return user;
	}
}
