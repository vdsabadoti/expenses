package fr.vds.expenses.controller;

import fr.vds.expenses.bll.LoginService;
import fr.vds.expenses.bll.ParticipantService;
import fr.vds.expenses.bll.TemporaryService;
import fr.vds.expenses.bo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@SessionAttributes({"user"})
public class LoginController {

    private TemporaryService temporaryService;
    private LoginService loginService;

    private ParticipantService participantService;

    public LoginController(ParticipantService participantService, TemporaryService temporaryService, LoginService loginService){
        this.loginService = loginService;
        this.temporaryService = temporaryService;
        this.participantService = participantService;
    }

    @ModelAttribute("user")
    public User createUser(){
        return new User();
    }
    
    public String indexPage(
            @ModelAttribute("user") User user    ){

        return "index";
    }

    ////////////////// STEP ONE : START ///////////////////////////

    //SLIDE 1 ===> GET => LOGIN PAGE
    //RETURN SLIDE ? => DEMANDE MDP

    @GetMapping("/userOne") //mulan
    public String userOneConnection(
            @ModelAttribute("user") User user
            ){
        User userOne = participantService.getUserFromDataBase(1);
        //RAF : control userOne is not null
        user.setUsername(userOne.getUsername());
        user.setId(userOne.getId());
        return "password-control";
    }

    @GetMapping("/userTwo") //spiderman
    public String userTwoConnection(
            @ModelAttribute("user") User user
    ){
        User userTwo = participantService.getUserFromDataBase(2);
        //RAF : control userOne is not null
        user.setUsername(userTwo.getUsername());
        user.setId(userTwo.getId());
        return "password-control";
    }


    //SLIDE 1 ===> POST => DEMANDE MDP
    //RETURN SLIDE 2 => INDEX: LIST OF EXPENSES BY USER

    /*
    * Get user
    * Get list of expenses by user
    * Send to model
    * Call html page
    * */

    @PostMapping("/password")
    public String password(
            @ModelAttribute("user") User user,
            @RequestParam(name="password") String password,
            Model model
    ){
        if ("corsaires".equals(password)) {
            return "redirect:/expenses";
        }else{
            //add message error
            return "password-control";
        }
    }
    


    }