package fr.vds.expenses.controller;

import fr.vds.expenses.bo.LoginInterface;
import fr.vds.expenses.bo.User;
import fr.vds.expenses.security.JwtService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class AuthRestController {

    private JwtService jwtService;

    public AuthRestController(JwtService jwtService){
        this.jwtService = jwtService;
    }

    @RequestMapping(method = RequestMethod.GET, path= "/login")
    public String login(
            @RequestBody LoginInterface loginInterface
    ) {
        User user = new User(loginInterface.getUsername(),loginInterface.getMail(), loginInterface.getPassword());
        //loadUser thanks to its mail then
        //call JWT service to generate a Token with user mail
        String token = this.jwtService.generateToken(user);

        return token;
    }

}
