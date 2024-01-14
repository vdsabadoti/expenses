package fr.vds.expenses.configuration;

import java.io.IOException;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.bind.annotation.SessionAttributes;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@SessionAttributes({"utilisateurSession"})
public class EniSecurityConfig {

//	@Bean
//	UserDetailsManager userDetailManager(DataSource dataSource) {
//		JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
//		jdbcUserDetailsManager.setUsersByUsernameQuery("SELECT email, password, 1 FROM membre WHERE email = ?");
//		jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("SELECT email, role FROM membre INNER JOIN roles "
//				+ "ON membre.admin = roles.IS_ADMIN"
//				+ " WHERE email = ?");
//		
//		return jdbcUserDetailsManager;
//		
//	}
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http.authorizeHttpRequests(auth -> auth
		.anyRequest().permitAll());
		
		http.formLogin(form ->
				form.loginPage("/login").permitAll().defaultSuccessUrl("/")
				);
		
		http.csrf(csrf-> csrf.disable());
		
		http.logout(logout->
				logout.invalidateHttpSession(true)
				.clearAuthentication(true)
				.deleteCookies("JSESSIONID")
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/login?logout")
				.permitAll()
				);
		
		return http.build();
		
	}
		
}
