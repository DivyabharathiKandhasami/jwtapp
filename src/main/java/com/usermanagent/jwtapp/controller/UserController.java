package com.usermanagent.jwtapp.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.usermanagent.jwtapp.entity.AuthRequest;
import com.usermanagent.jwtapp.entity.User;
import com.usermanagent.jwtapp.ser.JwtService;
import com.usermanagent.jwtapp.ser.UserInfoService;
@RequestMapping("/api/start")
@RestController
public class UserController {
	@Autowired
	public UserInfoService userinfoservice;

	@Autowired
	public JwtService jwtService;

	@Autowired
	public AuthenticationManager authenticationManager;

//This one for adding new user
	@PostMapping("/addNewUser")
	public String addNewUser(@RequestBody User userinfo) {
		userinfoservice.addNewUser(userinfo);
		return "New user added successfully";

	}

	// This one for displaying that message
	@GetMapping("/welcome")
	public String Welcome() {
		return "Welcome this endpoint is not secure";
	}

//this is responsible for user authority
	@GetMapping("/user/userProfile")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public String userProfile() {
		return "Welcome to User Profile";
	}

//this is responsible for Administration  authority
	@GetMapping("/admin/adminProfile")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String adminProfile() {
		return "Welcome to Admin Profile";
	}

	@PostMapping("/generateToken")
	public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
		org.springframework.security.core.Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
		if (authentication.isAuthenticated()) {
			return jwtService.generateToken(authRequest.getUserName());
		} else {
			throw new UsernameNotFoundException("invalid user request !");
		}
	}
}
