package fr.vds.expenses.security;

import fr.vds.expenses.bo.User;
import fr.vds.expenses.dal.UserDAO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    //SpringCore => on va chercher dans application.properties la valeur de la variable
    @Value("${app.jwt.secret}")
    private String SECRET_KEY;
    private UserDAO userDAO;

    public JwtService(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    public User findUserByMail(String mail){
        //TODO verify PASSWORD
        return this.userDAO.findUserByMail(mail);
    }
    public Key getSecretKey(){
        //on transforme la cle en bytes
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        //on choisit l'algo de cryptage
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(User user) {
        //list des clés-valeurs qu'on souhaite crypter à l'interieur
        //JWT => est en fait une valeur crypté
        Map<String, Object> claims = new HashMap<String, Object>();

        String token = Jwts.builder()
                //setClamins => on passe le valeur à crypter
                .setClaims(claims)
                //setSubject => ID du token, doit être unique dans le métier, important pour le retrouver
                .setSubject(user.getMail())
                //date de création du token
                .setIssuedAt(new Date(System.currentTimeMillis()))
                //validité du JWT : aujourd'hui + combien de temps
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                //clé secrete : ne doit pas être visible dans le code
                //on rajoute une couche de cryptage
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                //compacter en String
                .compact();

        return token;
    }

}

