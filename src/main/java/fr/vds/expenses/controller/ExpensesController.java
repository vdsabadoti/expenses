package fr.vds.expenses.controller;

import fr.vds.expenses.bll.LoginService;
import fr.vds.expenses.bll.ParticipantService;
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

    private ParticipantService participantService;

    public ExpensesController(ParticipantService participantService, TemporaryService temporaryService, LoginService loginService) {
        this.loginService = loginService;
        this.temporaryService = temporaryService;
        this.participantService = participantService;
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
            @RequestParam(name = "id") int idExpense,
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
        List<User> lstUsers = participantService.getAllTheUsersFromDatabase();
        List<Participant> participants = participantService.getAllTheParticipantsOfExpense(newExpense.getIdExpense());

        Participant newParticipant = new Participant();

        model.addAttribute("users", lstUsers);
        model.addAttribute("expense", newExpense);
        model.addAttribute("participant", newParticipant);
        model.addAttribute("participants", participants);
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
            @RequestParam(name = "idExpense") int idExpense,
            @ModelAttribute("participant") Participant participant,
            Model model
    ) {
        participantService.createParticipantInExpense(participant, idExpense);
        Expense expenseUpdated = temporaryService.getSingleExpense(idExpense);

        List<User> lstUsers = participantService.getAllTheUsersFromDatabase();
        List<Participant> participants = participantService.getAllTheParticipantsOfExpense(idExpense);

        Participant newParticipant = new Participant();
        model.addAttribute("expense", expenseUpdated);
        model.addAttribute("participant", newParticipant);
        model.addAttribute("users", lstUsers);
        model.addAttribute("participants", participants);
        return "new-expense-participants";
    }

    /*&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&*/
    //SLIDE 4 ====> GET => CREATE EXPENSE
    //RETURN SLIDE 5

    /*
     * Redirection to slide 5
     * MAJ budget of the expense thanks to partipants budgets
     * */

    @GetMapping("expense/create/done")
    public String expenseCompleted(
            @SessionAttribute("user") User user,
            @RequestParam(name = "key") int idExpense,
            Model model
    ) {
        temporaryService.loadBudgetExpense(idExpense);
        return "redirect:/expenses";
    }

    /*&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&*/
    //SLIDE EXTRA ====> GET => DELETE EXPENSE BOUTON
    //RETURN SLIDE 6

    @GetMapping("expense/delete")
    public String deleteExpense(
            @RequestParam(name = "id") int idExpense,
            Model model
    ) {
        temporaryService.deleteExpense(idExpense);
        return "redirect:/expenses";
    }

    /*&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&*/

    /*&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&*/
    //SLIDE EXTRA ====> GET => SHOW ALL EXPENSES
    //EVERYTIME WE MUST GO BACK TO THE EXPENSES LIST : REDIRECT TO THIS FUNCTION

    @GetMapping("expenses")
    public String homePage(
            @SessionAttribute("user") User user,
            Model model
    ) {
        List<Expense> expenseListOfUser = temporaryService.getExpensesFromUser(user.getIdUser());
        model.addAttribute("user", user);
        model.addAttribute("expenses", expenseListOfUser);
        return "view-expenses";
    }

}

/*&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&*/