package com.example.secAuth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.secAuth.entity.UserSec;
import com.example.secAuth.entity.UserRepo;
import com.example.secAuth.model.SignInDto;

@RestController
@RequestMapping("/home")
public class HomeController {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@GetMapping("/login")
	public ModelAndView signInPage() {
		return new ModelAndView("loginPage");
	}

	@PostMapping("/authLogin")
	public ResponseEntity<?> authenticateLogin(@ModelAttribute SignInDto signIndto) {

		System.out.println("the username entered ===" + signIndto.getUserName());
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(signIndto.getUserName(), signIndto.getPassword()));
		if (!authentication.isAuthenticated()) {
			return ResponseEntity.ok("Invalid Credentials");
		}

		return ResponseEntity.ok(signIndto.getUserName());
	}

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@ModelAttribute UserSec user) {

		System.out.println("the value of the user name entered here =====" + user.getUsername());
		UserSec newUser = new UserSec();
		newUser.setUsername(user.getUsername());
		newUser.setPassword(passwordEncoder.encode(user.getPassword()));
		newUser.setRole("USER");
		userRepo.save(newUser);

		return ResponseEntity.ok("User Successfully Registered !");
	}
}
