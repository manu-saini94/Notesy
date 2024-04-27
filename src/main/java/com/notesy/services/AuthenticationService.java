package com.notesy.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.notesy.dto.LoginDto;
import com.notesy.dto.RegistrationDto;
import com.notesy.entities.User;
import com.notesy.repositories.UserRepository;

@Service
public class AuthenticationService {

	private Random random = new Random(1000);

	@Autowired
	private EmailService emailService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	public User signup(RegistrationDto input) {
		User user = new User().setFullName(input.getFullName()).setEmail(input.getEmail())
				.setPassword(passwordEncoder.encode(input.getPassword()));

		return userRepository.save(user);
	}

	public User authenticate(LoginDto input) {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(input.getEmail(), input.getPassword()));

		return userRepository.findByEmail(input.getEmail()).orElseThrow();
	}

	public boolean forgot(String email) {
		int otp = random.nextInt(10000);
		String subject = "OTP from Notesy";
		String message = "<h1> OTP = " + otp + "</h1>";
		String to = email;

		boolean flag = emailService.sendEmail(subject, message, to);

		return flag;
	}
}
