package fr.vds.expenses.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private JwtService jwtService;

    public JwtFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //Recuperer le block d'authorization de la requete HTTP
        String authHeader = request.getHeader("Authorization");
        //Verifier s'il s'agit d'un Bearer Authentification
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            //si ce n'est pas le cas, on dit à Spring de continuer les traitements
            filterChain.doFilter(request, response);
            return;
        }
        //On recupere le token en faison la soustration du Bearer
        String jwt = authHeader.substring(7);
        //Tester (au moins) l'expiration du token, cad :
        //-- tester la date d'expiration
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(jwtService.getSecretKey())
                .build()
                .parseClaimsJws(jwt)
                .getBody();

        Function<Claims, Date> expirationFunction = Claims::getExpiration; //fonction anonyme
        Date expirationDate = expirationFunction.apply(claims);
        //-- tester que la date ne dépasse pas le temps actuel
        if (expirationDate.before(new Date())) {
            //invalid
            System.out.println("expirationDate: " + expirationDate);
            return;
        }

        //on recupere le mail du token
        String username = claims.getSubject();

        //-- tester que c'est la première fois qu'on valide un user spring security
        if (SecurityContextHolder.getContext().getAuthentication() == null) {

            //instancier un UserDetails pour gerer les autorisations (authorities), ensuite mettre à jour le contexte avec
            UserDetails userDetails = this.jwtService.findUserByMail(username);
            System.out.println("User: " + userDetails.getUsername() + " Roles: " + userDetails.getAuthorities().toString());
            //transferer l'user dans le authToken (car on remplace basic auth) => on recupere les authorities
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
            //on recupere l'objet UserDetails et on met dans le details de l'authToken
            authToken.setDetails(userDetails);
            //Mise à jour du contexte de Spring Security
            SecurityContextHolder.getContext().setAuthentication(authToken);

        }

        filterChain.doFilter(request, response);
    }
}
