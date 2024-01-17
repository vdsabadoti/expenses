package fr.vds.expenses.controller;

import fr.vds.expenses.bll.LoginService;
import fr.vds.expenses.bll.TemporaryService;
import fr.vds.expenses.bo.Expense;
import fr.vds.expenses.bo.Participant;
import fr.vds.expenses.bo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes({"user"})
public class LoginController {

    private TemporaryService temporaryService;
    private LoginService loginService;

    public LoginController(TemporaryService temporaryService, LoginService loginService){
        this.loginService = loginService;
        this.temporaryService = temporaryService;
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
        User userOne = temporaryService.getUserFromDataBase(1);
        //RAF : control userOne is not null
        user.setUsername(userOne.getUsername());
        user.setIdUser(userOne.getIdUser());
        return "password-control";
    }

    @GetMapping("/userTwo") //spiderman
    public String userTwoConnection(
            @ModelAttribute("user") User user
    ){
        User userTwo = temporaryService.getUserFromDataBase(2);
        //RAF : control userOne is not null
        user.setUsername(userTwo.getUsername());
        user.setIdUser(userTwo.getIdUser());
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
            List<Expense> expenseListOfUser = temporaryService.getExpensesFromUser(user.getIdUser());
            model.addAttribute("user", user);
            model.addAttribute("expenses", expenseListOfUser);
            return "view-expenses";
        }else{
            //add message error
            return "password-control";
        }
    }
    


    }