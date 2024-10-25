package com.ekart.springekartapplication.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ekart.springekartapplication.Service.CustomUserDetailsService;
import com.ekart.springekartapplication.UTIL.JwtRequestFilter;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	
	@Autowired
    private CustomAuthenticationEntryPoint authenticationEntryPoint;
	

	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
            .antMatchers("/h2-console/**").permitAll()
            .antMatchers("/favicon.ico**").permitAll()
            .antMatchers("/auth/login").permitAll()
            .antMatchers("/error").permitAll()
            .antMatchers("/api/users/**").permitAll()
            .antMatchers("/product/**").permitAll()
            .antMatchers("/seller/**").hasRole("SELLER")
            .antMatchers("/customer/**").hasRole("CUSTOMER")
            .anyRequest().authenticated()
            .and().exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())  // Register custom handler here
            .authenticationEntryPoint(authenticationEntryPoint)
            .and().headers().frameOptions().disable()  // To allow H2 console in frames
            .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailsService) // Use your custom user details service
				.passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}
