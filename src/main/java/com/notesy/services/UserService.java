package com.notesy.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.notesy.entities.User;
import com.notesy.repositories.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtAuthenticationService jwtService;

	public List<User> allUsers() {
		List<User> users = new ArrayList<>();
		userRepository.findAll().forEach(users::add);
		return users;
	}

	public User getEmailFromJwt(HttpServletRequest httpRequest) {
		final String authHeader = httpRequest.getHeader("Authorization");
		final String jwt = authHeader.substring(7);
		final String userEmail = jwtService.extractUsername(jwt);
		User user = userRepository.findByEmail(userEmail).get();
		return user;
	}

}
