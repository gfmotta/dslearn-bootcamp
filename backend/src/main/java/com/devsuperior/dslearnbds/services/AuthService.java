package com.devsuperior.dslearnbds.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dslearnbds.entities.User;
import com.devsuperior.dslearnbds.repositories.UserRepository;
import com.devsuperior.dslearnbds.services.exceptions.ForbiddenException;
import com.devsuperior.dslearnbds.services.exceptions.UnauthorizedException;

@Service
public class AuthService {

	@Autowired
	private UserRepository userRepository;
	
	@Transactional(readOnly = true)
	public User getAuthenticatedUser() {
		try {
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			return userRepository.findByEmail(username);
		}
		catch(Exception e) {
			throw new UnauthorizedException("Autorização negada: Você não pode realizar essa operação");
		}
	}
	
	public void validateUserAccess(Long id) {
		User loggedUser = getAuthenticatedUser();
		
		if (!loggedUser.getId().equals(id) && !loggedUser.hasAuthority("ROLE_ADMIN")) {
			throw new ForbiddenException("Autorização negada: Você não pode realizar essa operação");
		}
	}
}
