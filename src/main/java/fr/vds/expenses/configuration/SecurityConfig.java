package fr.vds.expenses.configuration;

import fr.vds.expenses.bll.SQLUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.bind.annotation.SessionAttributes;

@Configuration
@EnableWebSecurity
@SessionAttributes({"utilisateurSession"})
public class SecurityConfig {

	@Autowired
	private SQLUserDetailsServiceImpl sqlUserDetailsService;

	//Injection d'une méthode dans le middleware pour definir la façon de se connecter (logique de connexion)
	@Autowired
	public void configAuthBuilder(AuthenticationManagerBuilder builder) throws Exception {
		builder.userDetailsService(this.sqlUserDetailsService);
	}

	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
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
