package com.usermanagent.jwtapp.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.authentication.AuthenticationProvider;//four
import com.usermanagent.jwtapp.filter.JwtAuthFilter;
import com.usermanagent.jwtapp.ser.UserInfoDetails;
 import org.springframework.security.authentication.AuthenticationManager;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	@Autowired
	public JwtAuthFilter authfilt;

	// user creation
	// JavaBeans are classes which encapsulate several objects into a single
	// object. It helps in accessing these object from
	// multiple places. JavaBeans contains several elements
	// like Constructors, Getter/Setter Methods and much more.
	@Bean
	public UserInfoDetails userDetailsService() {
		return new UserInfoDetails();
	}

	// configuring Http request//
	@SuppressWarnings("removal")
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf().disable().addFilter(authfilt).authorizeHttpRequests()
				.requestMatchers("/auth/welcome", "/auth/addNewUser", "/auth/generateToken").permitAll().and()
				.authorizeHttpRequests().requestMatchers("/auth/user/**").authenticated().and().authorizeHttpRequests()
				.requestMatchers("/auth/admin/**").authenticated().and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.authenticationProvider(authenticationProvider())
				.addFilterBefore(authfilt, UsernamePasswordAuthenticationFilter.class).build();
	}

	// //password Encoding
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService((UserDetailsService) userDetailsService()); // missing
																									// userDetailsService
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}

	public Object userinfoDetails() {
		// TODO Auto-generated method stub
		return null;
	}

	// Authentication Exception
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}
