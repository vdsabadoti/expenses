package fr.vds.expenses.configuration;

import fr.vds.expenses.bll.SQLUserDetailsServiceImpl;
import fr.vds.expenses.security.JwtFilter;
import fr.vds.expenses.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.bind.annotation.SessionAttributes;

@Configuration
@EnableWebSecurity
@SessionAttributes({"utilisateurSession"})
public class SecurityConfig {

	private SQLUserDetailsServiceImpl sqlUserDetailsService;
	private JwtFilter jwtFilter;

	public SecurityConfig(JwtFilter jwtFilter, SQLUserDetailsServiceImpl sqlUserDetailsService){
		this.jwtFilter = jwtFilter;
		this.sqlUserDetailsService = sqlUserDetailsService;
	}

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

		http.authorizeHttpRequests(
				(authorizeRequests) -> {
					authorizeRequests
							.requestMatchers(HttpMethod.POST, "/login").permitAll()
							.requestMatchers(HttpMethod.GET, "/getallusers").permitAll()
							.anyRequest().authenticated();
				}
		);
		/*
		http.formLogin(form ->
				form.loginPage("/login").permitAll().defaultSuccessUrl("/")
				);

		 */
		
		http.csrf(csrf-> csrf.disable());

		/*
		http.logout(logout->
				logout.invalidateHttpSession(true)
				.clearAuthentication(true)
				.deleteCookies("JSESSIONID")
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/login?logout")
				.permitAll()
				);

		 */
		

		//Configurer les authentifactions de SpringSecurity
		http.httpBasic(Customizer.withDefaults());

		//Ajouter l'authentification personalisé qu'on a crée
		http.addFilterBefore(this.jwtFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
		
}
