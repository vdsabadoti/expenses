package fr.vds.expenses.controller;

import fr.vds.expenses.bll.LoginService;
import fr.vds.expenses.bll.TemporaryService;
import fr.vds.expenses.bo.Expense;
import fr.vds.expenses.bo.Participant;
import fr.vds.expenses.bo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@SessionAttributes({"user"})
public class ExpensesController {

    private TemporaryService temporaryService;
    private LoginService loginService;

    public ExpensesController(TemporaryService temporaryService, LoginService loginService){
        this.loginService = loginService;
        this.temporaryService = temporaryService;
    }

    /*&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&*/
    //SLIDE 2 ====> GET => DETAIL OF AN EXPENSE (ALL THE LINES OF AN EXPENSE)
    //RETURN SLIDE  5

    /*
     * Get expense ID
     * Get the expense thanks to the service layer
     * (in the service layer, feed expense with all the lines)
     * Send expense object to model
     * Call html page
     * */

    @GetMapping("expense/detail")
    public String showSingleExpense(
            @RequestParam(name="id") int idExpense,
            Model model
    ) {
        Expense expense = temporaryService.getSingleExpense(idExpense);
        model.addAttribute("expense", expense);
        return "view-detail-expenses";
    }



    /*&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&*/
    //SLIDE 2 =====> GET => ADD EXPENSE BUTTON
    //RETURN SLIDE 3

    /*
     * Create empty expense object
     * Send to model
     * Call html page
     * */

    @GetMapping("expense/create")
    public String newExpense(
            Model model
    ) {
        Expense expense = new Expense();
        model.addAttribute("expense", expense);
        return "new-expense";
    }


    /*&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&*/
    //SLIDE 3 ====> POST => NEXT
    //RETURN SLIDE 4

    /*
     * Get expense object from model
     * Call service layer to create expense in data base
     * Create empty participant object
     * Send expense object and participant to model
     * Call html page
     * */

    @PostMapping("expense/next")
    public String newExpenseParticipants(
            @ModelAttribute("expense") Expense newExpense,
            @ModelAttribute("user") User user,
            Model model
    ) {
        newExpense.setOwner(user);
        temporaryService.createExpense(newExpense);

        //GET USERS FROM DATABASE
        List<User> lstUsers = temporaryService.getAllTheUsersFromDatabase();

        Participant newParticipant = new Participant();

        model.addAttribute("users", lstUsers);
        model.addAttribute("expense", newExpense);
        model.addAttribute("participant", newParticipant);
        return "new-expense-participants";
    }

    /*&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&*/
    //SLIDE 4 ====> POST => ADD PARTICIPANT BUTTON
    //RETURN SLIDE 4

    /*
     * Get expense object from model
     * Get participant object from model
     * Call service layer to create participant in data base
     * (The service layer must update the expense in data base : budget by year, budget by month)
     * Create empty participant objcet
     * Recharge expense object
     * Send expense object and empty participant objet to model
     * Call html page
     * */

    @PostMapping("/expense/addUser")
    public String newExpenseDone(
            @RequestParam(name="idExpense") int idExpense,
            @ModelAttribute("participant") Participant participant,
            Model model
    ) {
        temporaryService.createParticipantInExpense(participant, idExpense);
        Expense expenseUpdated = temporaryService.getSingleExpense(idExpense);

        Participant newParticipant = new Participant();
        model.addAttribute("expense", expenseUpdated);
        model.addAttribute("participant", newParticipant);
        return "new-expense-participants";
    }

    /*&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&*/
    //SLIDE 4 ====> POST => CREATE EXPENSE
    //RETURN SLIDE 5

    /*
     * Redirection to slide 5
     *
     * */

    ////////////////// STEP ONE : END ///////////////////////////


    /*&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&*/
    //SLIDE 5 ====> GET => ADD DEBT BUTTON
    //RETURN SLIDE 6

    /*&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&*/
    //SLIDE 6 ====> POST => ADD BUTTON
    //RETURN SLIDE 5

    /*&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&*/
    //SLIDE 5 ====> GET => ADD REFUND BUTTON
    //RETURN SLIDE 7

    /*&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&*/
    //SLIDE 7 ====> POST => ADD BUTTON
    //RETURN SLIDE 5

    /*&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&*/
    //SLIDE 5 ====> GET => DELETE BUTTON
    //RETURN SLIDE 2

    /*&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&*/
    //SLIDE 5 ====> GET => DETAIL BUTTON
    //RETURN SLIDE 8

    /*&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&*/
    //SLIDE 8 ====> POST => DELETE BUTTON
    //RETURN SLIDE 5

    /*&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&*/
    //SLIDE 8 ====> POST => OK BUTTON
    //RETURN SLIDE 5


}
