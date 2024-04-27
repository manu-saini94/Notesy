package com.notesy.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.notesy.dto.LoginDto;
import com.notesy.dto.RegistrationDto;
import com.notesy.entities.User;
import com.notesy.responses.LoginResponse;
import com.notesy.services.AuthenticationService;
import com.notesy.services.JwtAuthenticationService;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
	private final JwtAuthenticationService jwtService;

	private final AuthenticationService authenticationService;

	public AuthenticationController(JwtAuthenticationService jwtService, AuthenticationService authenticationService) {
		this.jwtService = jwtService;
		this.authenticationService = authenticationService;
	}

	@PostMapping("/signup")
	public ResponseEntity<User> register(@RequestBody RegistrationDto registerDto) {
		User registeredUser = authenticationService.signup(registerDto);

		return ResponseEntity.ok(registeredUser);
	}

	@PostMapping("/forgot")
	public ResponseEntity<Boolean> forgotPassword(@RequestParam String email) {
		boolean isSent = authenticationService.forgot(email);
		return ResponseEntity.ok(isSent);
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginDto loginUserDto) {
		User authenticatedUser = authenticationService.authenticate(loginUserDto);

		String jwtToken = jwtService.generateToken(authenticatedUser);

		LoginResponse loginResponse = new LoginResponse().setToken(jwtToken)
				.setExpiresIn(jwtService.getExpirationTime());

		return ResponseEntity.ok(loginResponse);
	}

}
