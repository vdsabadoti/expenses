package fr.vds.expenses.controller;

import fr.vds.expenses.bll.LoginService;
import fr.vds.expenses.bll.LoginServiceImpl;
import fr.vds.expenses.bo.LoginInterface;
import fr.vds.expenses.bo.ResponseService;
import fr.vds.expenses.bo.User;
import fr.vds.expenses.bo.UserTokenInterface;
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

    @RequestMapping(method = RequestMethod.POST, path= "/login")
    public ResponseService<UserTokenInterface> login(
            @RequestBody LoginInterface loginInterface
    ) {
        ResponseService<UserTokenInterface> responseService = new ResponseService<>();
        responseService.code = "700";
        responseService.message = "Authentification failed";
        responseService.data = new UserTokenInterface(0, "");
        if (this.loginService.authentificationCheck(loginInterface)){
            User user = new User(loginInterface.getUsername(),loginInterface.getMail(), loginInterface.getPassword());
            //loadUser thanks to its mail then
            //call JWT service to generate a Token with user mail
            responseService.code = "200";
            responseService.message = "Authentification successfull";
            responseService.data =
                    new UserTokenInterface(
                            1, this.jwtService.generateToken(user)
                            );
        }

        return responseService;
    }

}
