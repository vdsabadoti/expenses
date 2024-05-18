package fr.vds.expenses.controller;

import fr.vds.expenses.bll.LoginService;
import fr.vds.expenses.bll.LoginServiceImpl;
import fr.vds.expenses.bo.LoginInterface;
import fr.vds.expenses.bo.ResponseService;
import fr.vds.expenses.bo.User;
import fr.vds.expenses.security.JwtService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class AuthRestController {

    private JwtService jwtService;
    private LoginService loginService;

    public AuthRestController(JwtService jwtService, LoginService loginService){

        this.jwtService = jwtService;
        this.loginService = loginService;
    }

    @RequestMapping(method = RequestMethod.GET, path= "/login")
    public ResponseService<String> login(
            @RequestBody LoginInterface loginInterface
    ) {
        ResponseService<String> responseService = new ResponseService<>();
        responseService.code = "700";
        responseService.message = "Authentification failed";
        responseService.data = "";
        if (this.loginService.authentificationCheck(loginInterface)){
            User user = new User(loginInterface.getUsername(),loginInterface.getMail(), loginInterface.getPassword());
            //loadUser thanks to its mail then
            //call JWT service to generate a Token with user mail
            responseService.code = "200";
            responseService.message = "Authentification successfull";
            responseService.data = this.jwtService.generateToken(user);
        }

        return responseService;
    }

}
